package com.thsware.framework.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mysql.jdbc.StringUtils;
import com.netflix.discovery.converters.Auto;
import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.client.CcbWorkflowClient;
import com.thsware.framework.config.Constants;
import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.repository.ZjSpecialtyAuditerRepository;
import com.thsware.framework.repository.ZjSpecialtyRepository;
import com.thsware.framework.security.ThsSecurityUtils;
import com.thsware.framework.service.dto.*;
import com.thsware.framework.service.mapper.ZjSpecialtyMapper;
import com.thsware.framework.web.client.CscggClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Service Implementation for managing ZjSpecialty.
 */
@Service
@Transactional
public class ZjSpecialtyService {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyService.class);

    private final ZjSpecialtyRepository zjSpecialtyRepository;

    private final ZjSpecialtyMapper zjSpecialtyMapper;

    @Autowired
    private ZjSpecialtyAuditerRepository zjSpecialtyAuditerRepository;

    @Autowired
    private CcbWorkflowClient workflowClient;

    @Autowired
    private ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    @Autowired
    private ZjAssistantService zjAssistantService;

    @Autowired
    private ZjCheckLogService zjCheckLogService;

    @Autowired
    private ZjProjectService zjProjectService;

    @Autowired
    private CscggClient cscggClient;

    public ZjSpecialtyService(ZjSpecialtyRepository zjSpecialtyRepository, ZjSpecialtyMapper zjSpecialtyMapper) {
        this.zjSpecialtyRepository = zjSpecialtyRepository;
        this.zjSpecialtyMapper = zjSpecialtyMapper;
    }

    /**
     * Save a zjSpecialty.
     *
     * @param zjSpecialtyDTO the entity to save
     * @return the persisted entity
     */
    public ZjSpecialtyDTO save(ZjSpecialtyDTO zjSpecialtyDTO) {
        log.debug("Request to save ZjSpecialty : {}", zjSpecialtyDTO);
        ZjSpecialty zjSpecialty = zjSpecialtyMapper.toEntity(zjSpecialtyDTO);

        // 自校汇总需要先保存工程的二三审人
        if (zjSpecialty.getState()!=null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJHZ)) {
            List<ZjSpecialtyAuditerDTO> zjSpecialtyAuditerDTOList = zjSpecialtyDTO.getZjSpecialtyAuditerDTOList();
            zjSpecialtyAuditerService.deleteBySpecialtyId(zjSpecialtyDTO.getId());
            for (ZjSpecialtyAuditerDTO zjSpecialtyAuditerDTO : zjSpecialtyAuditerDTOList) {
                zjSpecialtyAuditerService.save(zjSpecialtyAuditerDTO);
            }
        }
        zjSpecialty = zjSpecialtyRepository.save(zjSpecialty);
        if (StringUtils.isNullOrEmpty(zjSpecialtyDTO.getId())) {
            if (null != zjSpecialtyDTO.getZjAssistantList() && zjSpecialtyDTO.getZjAssistantList().size() > 0) {
                zjAssistantService.deleteByZjSpecialtyId(zjSpecialty.getId());
                for (ZjAssistantDTO zjAssistantDTO : zjSpecialtyDTO.getZjAssistantList()) {
                    zjAssistantDTO.setZjSpecialtyId(zjSpecialty.getId());
                    zjAssistantService.save(zjAssistantDTO);
                }
            }
        }


        if(null!=zjSpecialtyDTO) {
            // 前台是save or submit
            if (zjSpecialtyDTO.getSaveType() != null && "submit".equals(zjSpecialtyDTO.getSaveType())) {
                Map<String, Object> busiFormMap = JSON.parseObject(zjSpecialtyDTO.getBusiFormData(), new TypeReference<Map<String, Object>>() {
                });
                if (null == busiFormMap.get("id")) {
                    busiFormMap.put("id", zjSpecialty.getId());
                    zjSpecialtyDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
                }
                if (StringUtils.isNullOrEmpty(zjSpecialtyDTO.getId())) {
                    zjSpecialtyDTO.setId(zjSpecialty.getId());
                }
                Map<String, Object> result = workflowClient.sendOrSubmit(zjSpecialtyDTO);
                if ((boolean) result.get("success")) {
                    Optional<ZjProjectDTO> zjProjectDTOOptional = zjProjectService.findOne(zjSpecialty.getZjProjectId());
                    ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
                    ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();
                    String state = zjSpecialtyDTO.getState();
                    String flowState = zjSpecialtyDTO.getState();
                    // 如果是加签步骤
                    if (!StringUtils.isNullOrEmpty(zjSpecialtyDTO.getFlowId()) && zjSpecialtyDTO.getFlowId().startsWith("AddSign")) {

                    }
                    else {
                        if (zjSpecialtyDTO.getAuditResult().equals("1")) {
                            //提交成功的逻辑
                            if (state == null || "".equals(state) || state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ)) {

                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setTechnicalPost("编制人");
                                projectPersonDTO.setMainWork("自校");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"自校")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_ZJHZ;
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJHZ)) {

                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setMainWork("自校汇总");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"自校汇总")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH;
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH)) {
                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setTechnicalPost("复核人");
                                projectPersonDTO.setMainWork("二审");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"二审")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                boolean zjOver = true;
                                List<WfNoticeThingDTO> wfNoticeThingDTOList = workflowClient.findByBusinessIdAndStepName(zjSpecialtyDTO.getId(), Constants.ZJ_PROJECT_EJSH);
                                for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOList) {
                                    if (wfNoticeThingDTO.getIsFinish() != null && "0".equals(wfNoticeThingDTO.getIsFinish())) {
                                        zjOver = false;
                                    }
                                }
                                if (zjOver) {
                                    flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_SJSH;
                                }
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_SJSH)) {
                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setTechnicalPost("核定人");
                                projectPersonDTO.setMainWork("三审");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"三审")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                boolean zjOver = true;
                                List<WfNoticeThingDTO> wfNoticeThingDTOList = workflowClient.findByBusinessIdAndStepName(zjSpecialtyDTO.getId(), Constants.ZJ_PROJECT_SJSH);
                                for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOList) {
                                    if (wfNoticeThingDTO.getIsFinish() != null && "0".equals(wfNoticeThingDTO.getIsFinish())) {
                                        zjOver = false;
                                    }
                                }
                                if (zjOver) {
                                    flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_XMHZ;
                                    zjSpecialty.setActualFinishDate(Instant.now());
                                }
                            }
                        }
                        if (zjSpecialtyDTO.getAuditResult().equals("2")) {
                            if (!StringUtils.isNullOrEmpty(zjSpecialtyDTO.getNextStepName())) {
                                if (zjSpecialtyDTO.getNextStepName().equals(Constants.ZJ_PROJECT_ZJ)) {
                                    if (!StringUtils.isNullOrEmpty(flowState) && (flowState.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH) || flowState.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_SJSH))) {
                                        boolean zjOver = true;
                                        List<WfNoticeThingDTO> wfNoticeThingDTOList = workflowClient.findByBusinessIdAndStepName(zjSpecialtyDTO.getId(), flowState.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH)?Constants.ZJ_PROJECT_EJSH:Constants.ZJ_PROJECT_SJSH);
                                        for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOList) {
                                            if (wfNoticeThingDTO.getIsFinish() != null && "0".equals(wfNoticeThingDTO.getIsFinish())) {
                                                zjOver = false;
                                            }
                                        }
                                        if (zjOver) {
                                            flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ;
                                            zjCheckLogService.cleanCheckLog(zjSpecialty.getId());
                                        }
                                    }else {
                                        flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ;
                                        zjCheckLogService.cleanCheckLog(zjSpecialty.getId());
                                    }
                                } else if (zjSpecialtyDTO.getNextStepName().equals(Constants.ZJ_PROJECT_ZJHZ)) {
                                    flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_ZJHZ;
                                    zjCheckLogService.cleanCheckLogES(zjSpecialty.getId());
                                } else if (zjSpecialtyDTO.getNextStepName().equals(Constants.ZJ_PROJECT_EJSH)) {
                                    flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH;
                                    zjCheckLogService.cleanCheckLogES(zjSpecialty.getId());
                                } else if (zjSpecialtyDTO.getNextStepName().equals(Constants.ZJ_PROJECT_SJSH)) {
                                    flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_SJSH;
                                    zjCheckLogService.cleanCheckLogS(zjSpecialty.getId());
                                }
                                if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_XMHZ)) {
                                    zjSpecialty.setActualFinishDate(null);
                                }
                            }

                        }
                        if (zjSpecialtyDTO.getAuditResult().equals("3")) {

                        }
                    }
                    zjSpecialty.setState(flowState);
                    zjSpecialty = zjSpecialtyRepository.save(zjSpecialty);

                }
            }
        }
        return zjSpecialtyMapper.toDto(zjSpecialty);
    }

    /**
     * Get all the zjSpecialties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjSpecialties");
        return zjSpecialtyRepository.findAll(pageable)
            .map(zjSpecialtyMapper::toDto);
    }


    /**
     * Get one zjSpecialty by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjSpecialtyDTO> findOne(String id) {
        log.debug("Request to get ZjSpecialty : {}", id);
        return zjSpecialtyRepository.findById(id)
            .map(zjSpecialtyMapper::toDto);
    }

    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Map<String,Object> getPublishIdeas(String id){
        workflowClient.getAuditIdeaList(id);
        return null;
    }

    @ThsMultiTenancyFilter
    public List<ZjSpecialty> findAllByZjProjectId(String zjProjectId) {
        return zjSpecialtyRepository.findAllByZjProjectIdOrderByCreatedDateDesc(zjProjectId);
    }

    /**
     * Delete the zjSpecialty by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjSpecialty : {}", id);
        zjAssistantService.deleteByZjSpecialtyId(id);
        zjSpecialtyRepository.deleteById(id);
    }

    /**
     * 删除工作安排
     * @param id
     */
    public void deleteSpecialtyByZjProjectId(String id) {
        log.debug("Request to delete ZjSpecialty : {}", id);
        zjSpecialtyRepository.deleteZjSpecialtyByZjProjectId(id);
    }

    public void cleanBzr(String zjProjectId) {
        zjSpecialtyRepository.cleanBzr(zjProjectId);
    }

    public void cleanShr(String zjProjectId, String type) {
        // 根据项目id查找工程然后删除所有工程对应审批人员里的二级审核人信息
        List<ZjSpecialty> specialtyList = this.findAllByZjProjectId(zjProjectId);
        if (!specialtyList.isEmpty()) {
            for (ZjSpecialty zjSpecialty : specialtyList) {
                zjSpecialtyAuditerRepository.cleanShrByType(zjSpecialty.getId(), type);
            }
        }
    }

    public ZjSpecialtyDTO retDTO(ZjSpecialty zjSpecialty) {
        return zjSpecialtyMapper.toDto(zjSpecialty);
    }

    public List<ZjSpecialtyDTO> retListDTO(List<ZjSpecialty> zjSpecialtyList) {
        return zjSpecialtyMapper.toDto(zjSpecialtyList);
    }

    public List<ZjSpecialtyDTO> findAllByZjPublishId(String zjPublishId) {
        return zjSpecialtyMapper.toDto(zjSpecialtyRepository.findAllByZjPublishIdOrderByCreatedDateDesc(zjPublishId));
    }

    public ZjSpecialtyDTO updateSpecialty(ZjSpecialtyDTO zjSpecialtyDTO) {
        log.debug("Request to save ZjSpecialtyArchive : {}", zjSpecialtyDTO);
        ZjSpecialty zjSpecialty = zjSpecialtyMapper.toEntity(zjSpecialtyDTO);
        zjSpecialty = zjSpecialtyRepository.save(zjSpecialty);
        return zjSpecialtyMapper.toDto(zjSpecialty);
    }
}
