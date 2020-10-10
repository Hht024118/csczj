package com.thsware.framework.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import com.thsware.framework.client.CcbWorkflowClient;
import com.thsware.framework.domain.ZjProjectChangelog;
import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.repository.ZjProjectChangelogRepository;
import com.thsware.framework.service.dto.SysBusiformDTO;
import com.thsware.framework.service.dto.ZjProjectChangelogDTO;
import com.thsware.framework.service.dto.ZjProjectDTO;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import com.thsware.framework.service.mapper.ZjProjectChangelogMapper;
import com.thsware.framework.service.mapper.ZjSpecialtyAuditerMapper;
import com.thsware.framework.web.client.CscggClient;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Service Implementation for managing ZjProjectChangelog.
 */
@Service
@Transactional
public class ZjProjectChangelogService {

    private final Logger log = LoggerFactory.getLogger(ZjProjectChangelogService.class);

    private final ZjProjectChangelogRepository zjProjectChangelogRepository;

    private final ZjProjectChangelogMapper zjProjectChangelogMapper;

    @Autowired
    private ZjSpecialtyAuditerMapper zjSpecialtyAuditerMapper;

    @Autowired
    private ZjProjectService zjProjectService;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    @Autowired
    private CscggClient cscggClient;

    @Autowired
    private CcbWorkflowClient workflowClient;
    public ZjProjectChangelogService(ZjProjectChangelogRepository zjProjectChangelogRepository, ZjProjectChangelogMapper zjProjectChangelogMapper) {
        this.zjProjectChangelogRepository = zjProjectChangelogRepository;
        this.zjProjectChangelogMapper = zjProjectChangelogMapper;
    }

    /**
     * Save a zjProjectChangelog.
     *
     * @param zjProjectChangelogDTO the entity to save
     * @return the persisted entity
     */
    public ZjProjectChangelogDTO save(ZjProjectChangelogDTO zjProjectChangelogDTO) {
        log.debug("Request to save ZjProjectChangelog : {}", zjProjectChangelogDTO);
        ZjProjectChangelog zjProjectChangelog = zjProjectChangelogMapper.toEntity(zjProjectChangelogDTO);
        zjProjectChangelog = zjProjectChangelogRepository.save(zjProjectChangelog);
        Map<String, Object> busiFormMap = JSON.parseObject(zjProjectChangelogDTO.getBusiFormData(),new TypeReference<Map<String, Object>>(){} );
        if(null==busiFormMap.get("id")){
            busiFormMap.put("id",zjProjectChangelog.getId());
            zjProjectChangelogDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
        }
        if(StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getId())){
            zjProjectChangelogDTO.setId(zjProjectChangelog.getId());
        }
        Map<String,Object> result=workflowClient.sendOrSubmit(zjProjectChangelogDTO);
        if((boolean)result.get("success")){
            if(zjProjectChangelogDTO.getAuditResult().equals("1")){
                if (StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getFlowState())) {
                    zjProjectChangelogDTO.setFlowState("审批中");
                }
                else if (zjProjectChangelogDTO.getFlowState().equals("审批中")) {
                    zjProjectChangelogDTO.setFlowState("结束");
                    Optional<ZjProjectDTO> op = zjProjectService.findOne(zjProjectChangelogDTO.getProjectId());
                    ZjProjectDTO zjProjectDTO = op.get();
                    if(zjProjectChangelogDTO.getChangeType().equals("xmfzr")){
                        zjProjectDTO.setProjectManager(zjProjectChangelogDTO.getNewFieldId());
                        zjProjectDTO.setProjectManagerName(zjProjectChangelogDTO.getNewFieldText());
                        zjProjectService.save(zjProjectDTO);
                        SysBusiformDTO maps = cscggClient.getBusiforms(zjProjectDTO.getId()).getBody();
                        maps.setProjectLeader(zjProjectChangelogDTO.getNewFieldId());
                        maps.setProjectLeaderName(zjProjectChangelogDTO.getNewFieldText());
                        cscggClient.updateBusiforms(maps);
                    } else if (zjProjectChangelogDTO.getChangeType().equals("cbbm")){
                        zjProjectDTO.setImplementUnit(zjProjectChangelogDTO.getNewFieldText());
                        zjProjectService.save(zjProjectDTO);
                    } else if (zjProjectChangelogDTO.getChangeType().equals("bzr")){
                        Optional<ZjSpecialtyDTO> optional = zjSpecialtyService.findOne(zjProjectChangelogDTO.getSpecialtyId());
                        ZjSpecialtyDTO zjSpecialtyDTO = optional.get();
                        zjSpecialtyDTO.setEstablishmentPersonId(zjProjectChangelogDTO.getNewFieldId());
                        zjSpecialtyDTO.setEstablishmentPersonName(zjProjectChangelogDTO.getNewFieldText());
                        zjSpecialtyService.save(zjSpecialtyDTO);
                    } else if (zjProjectChangelogDTO.getChangeType().equals("ejsh")){
                        if(StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getOldFieldId())){
                            ZjSpecialtyAuditer zjSpecialtyAuditer = new ZjSpecialtyAuditer();
                            zjSpecialtyAuditer.setZjProjectId(zjProjectChangelogDTO.getProjectId());
                            zjSpecialtyAuditer.setZjSpecialtyId(zjProjectChangelogDTO.getSpecialtyId());
                            zjSpecialtyAuditer.setType("ejshr");
                            zjSpecialtyAuditer.setPersonId(zjProjectChangelogDTO.getNewFieldId());
                            zjSpecialtyAuditer.setPersonName(zjProjectChangelogDTO.getNewFieldText());
                            zjSpecialtyAuditerService.save(zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditer));
                        } else {
                            if (StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getNewFieldId())) {
                                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.
                                    findAllByZjSpecialtyIdAndPersonIdAndType(zjProjectChangelogDTO.getSpecialtyId(), zjProjectChangelogDTO.getOldFieldId(), "ejshr");
                                if (zjSpecialtyAuditerList.size() > 0) {
                                    ZjSpecialtyAuditer zjSpecialtyAuditer = zjSpecialtyAuditerList.get(0);
                                    zjSpecialtyAuditerService.delete(zjSpecialtyAuditer.getId());
                                }
                            } else {
                                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.
                                    findAllByZjSpecialtyIdAndPersonIdAndType(zjProjectChangelogDTO.getSpecialtyId(), zjProjectChangelogDTO.getOldFieldId(), "ejshr");
                                if (zjSpecialtyAuditerList.size() > 0) {
                                    ZjSpecialtyAuditer zjSpecialtyAuditer = zjSpecialtyAuditerList.get(0);
                                    zjSpecialtyAuditer.setPersonId(zjProjectChangelogDTO.getNewFieldId());
                                    zjSpecialtyAuditer.setPersonName(zjProjectChangelogDTO.getNewFieldText());
                                    zjSpecialtyAuditerService.save(zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditer));
                                }
                            }
                        }
                    } else if (zjProjectChangelogDTO.getChangeType().equals("sjsh")){
                        if(StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getOldFieldId())){
                            ZjSpecialtyAuditer zjSpecialtyAuditer = new ZjSpecialtyAuditer();
                            zjSpecialtyAuditer.setZjProjectId(zjProjectChangelogDTO.getProjectId());
                            zjSpecialtyAuditer.setZjSpecialtyId(zjProjectChangelogDTO.getSpecialtyId());
                            zjSpecialtyAuditer.setType("sjshr");
                            zjSpecialtyAuditer.setPersonId(zjProjectChangelogDTO.getNewFieldId());
                            zjSpecialtyAuditer.setPersonName(zjProjectChangelogDTO.getNewFieldText());
                            zjSpecialtyAuditerService.save(zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditer));
                        } else {
                            if (StringUtils.isNullOrEmpty(zjProjectChangelogDTO.getNewFieldId())) {
                                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.
                                    findAllByZjSpecialtyIdAndPersonIdAndType(zjProjectChangelogDTO.getSpecialtyId(), zjProjectChangelogDTO.getOldFieldId(), "ejshr");
                                if (zjSpecialtyAuditerList.size() > 0) {
                                    ZjSpecialtyAuditer zjSpecialtyAuditer = zjSpecialtyAuditerList.get(0);
                                    zjSpecialtyAuditerService.delete(zjSpecialtyAuditer.getId());
                                }
                            } else {
                                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.
                                    findAllByZjSpecialtyIdAndPersonIdAndType(zjProjectChangelogDTO.getSpecialtyId(), zjProjectChangelogDTO.getOldFieldId(), "sjshr");
                                if (zjSpecialtyAuditerList.size() > 0) {
                                    ZjSpecialtyAuditer zjSpecialtyAuditer = zjSpecialtyAuditerList.get(0);
                                    zjSpecialtyAuditer.setPersonId(zjProjectChangelogDTO.getNewFieldId());
                                    zjSpecialtyAuditer.setPersonName(zjProjectChangelogDTO.getNewFieldText());
                                    zjSpecialtyAuditerService.save(zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditer));
                                }
                            }
                        }
                     } else if (zjProjectChangelogDTO.getChangeType().equals("xmsjbg")){
                        Map<String, Object> oldText = JSON.parseObject(zjProjectChangelogDTO.getOldFieldText(),new TypeReference<Map<String, Object>>(){} );
                        Map<String, Object> newText = JSON.parseObject(zjProjectChangelogDTO.getNewFieldText(),new TypeReference<Map<String, Object>>(){} );
                        String changeView = (String) newText.get("changeView");
                        if( changeView.startsWith("1") ){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            ParsePosition pos = new ParsePosition(0);
                            Date date = sdf.parse(String.valueOf(newText.get("jhwcsj")),pos);
                            zjProjectDTO.setPlanFinishDate(date.toInstant());
                        }
                        if( changeView.equals("010") || changeView.equals("011") || changeView.equals("110") || changeView.equals("111") ){
                            zjProjectDTO.setContentScope((String) newText.get("zynr"));
                        }
                        if( changeView.endsWith("1") ){
                            zjProjectDTO.setProjectProgress((String) newText.get("jdgl"));
                        }
                        zjProjectService.save(zjProjectDTO);
                    }
                }
            }
            else if (zjProjectChangelogDTO.getAuditResult().equals("2")) {
                zjProjectChangelogDTO.setFlowState("");
            }
            else if (zjProjectChangelogDTO.getAuditResult().equals("3")) {

            }
            else if (zjProjectChangelogDTO.getAuditResult().equals("6")) {
                zjProjectChangelogDTO.setFlowState("已作废");
            }

        } else {

        }
        zjProjectChangelog = zjProjectChangelogMapper.toEntity(zjProjectChangelogDTO);
        zjProjectChangelog = zjProjectChangelogRepository.save(zjProjectChangelog);
        return zjProjectChangelogMapper.toDto(zjProjectChangelog);
    }

    /**
     * Get all the zjProjectChangelogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ZjProjectChangelogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjProjectChangelogs");
        return zjProjectChangelogRepository.findAll(pageable)
            .map(zjProjectChangelogMapper::toDto);
    }


    /**
     * Get one zjProjectChangelog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ZjProjectChangelogDTO> findOne(String id) {
        log.debug("Request to get ZjProjectChangelog : {}", id);
        return zjProjectChangelogRepository.findById(id)
            .map(zjProjectChangelogMapper::toDto);
    }

    /**
     * Delete the zjProjectChangelog by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjProjectChangelog : {}", id);
        zjProjectChangelogRepository.deleteById(id);
    }
}
