package com.thsware.framework.service;

import com.mysql.jdbc.StringUtils;
import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.client.CcbWorkflowClient;
import com.thsware.framework.config.Constants;
import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.domain.ZjSpecialtyIdea;
import com.thsware.framework.repository.ZjSpecialtyIdeaRepository;
import com.thsware.framework.security.SecurityUtils;
import com.thsware.framework.security.ThsSecurityUtils;
import com.thsware.framework.service.dto.WfNoticeThingDTO;
import com.thsware.framework.service.dto.ZjProjectDTO;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import com.thsware.framework.service.dto.ZjSpecialtyIdeaDTO;
import com.thsware.framework.service.mapper.ZjSpecialtyIdeaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ZjSpecialtyIdea.
 */
@Service
@Transactional
public class ZjSpecialtyIdeaService {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyIdeaService.class);

    private final ZjSpecialtyIdeaRepository zjSpecialtyIdeaRepository;

    private final ZjSpecialtyIdeaMapper zjSpecialtyIdeaMapper;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    @Autowired
    private ZjProjectService zjProjectService;

    @Autowired
    private CcbWorkflowClient workflowClient;

    @Autowired
    private ZjActorsService zjActorsService;

    @Autowired
    private ZjCheckLogService zjCheckLogService;

    public ZjSpecialtyIdeaService(ZjSpecialtyIdeaRepository zjSpecialtyIdeaRepository, ZjSpecialtyIdeaMapper zjSpecialtyIdeaMapper) {
        this.zjSpecialtyIdeaRepository = zjSpecialtyIdeaRepository;
        this.zjSpecialtyIdeaMapper = zjSpecialtyIdeaMapper;
    }

    /**
     * Save a zjSpecialtyIdea.
     *
     * @param zjSpecialtyIdeaDTO the entity to save
     * @return the persisted entity
     */
    public ZjSpecialtyIdeaDTO save(ZjSpecialtyIdeaDTO zjSpecialtyIdeaDTO) {
        log.debug("Request to save ZjSpecialtyIdea : {}", zjSpecialtyIdeaDTO);

        String flowId = zjSpecialtyIdeaDTO.getFlowId();
        String haveId = "0";
        if (zjSpecialtyIdeaDTO.getHaveId() != null && !"".equals(zjSpecialtyIdeaDTO.getHaveId())) {
            haveId = "1";
        }
        ZjSpecialtyIdea zjSpecialtyIdea = zjSpecialtyIdeaMapper.toEntity(zjSpecialtyIdeaDTO);
        zjSpecialtyIdea.setAuditDate(Instant.now());
        zjSpecialtyIdea = zjSpecialtyIdeaRepository.save(zjSpecialtyIdea);

        String speId = zjSpecialtyIdea.getZjSpecialtyId();
        String proId = zjSpecialtyIdea.getZjProjectId();
        String auditType = zjSpecialtyIdea.getAuditType();// ejsh/sjsh/bzrxg

        Optional<ZjProjectDTO> zjProjectDTOOptional = zjProjectService.findOne(proId);
        ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
        Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(speId);
        ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();

        // 驳回
        if ("1".equals(zjSpecialtyIdea.getAuditResult())) {
            zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_WAIT_DBZRXG);
            zjSpecialtyService.save(zjSpecialtyDTO);
        }
        // 通过
        else if ("0".equals(zjSpecialtyIdea.getAuditResult())) {
            if ("bzrxg".equals(auditType)) {
                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.findAllByZjSpecialtyIdAndType(zjSpecialtyDTO.getId(), "ejshr");
                for (ZjSpecialtyAuditer zjSpecialtyAuditer : zjSpecialtyAuditerList) {
                    WfNoticeThingDTO wfNoticeThingDTO = new WfNoticeThingDTO();
                    wfNoticeThingDTO.setBusinessId(proId);
                    wfNoticeThingDTO.setStepName(Constants.ZJ_PROJECT_EJSH);
                    wfNoticeThingDTO.setReceiver(zjSpecialtyAuditer.getPersonId());
                    wfNoticeThingDTO.setThingUrl("/business/zj/project/projectEdit?id="+proId+"&zjSpecialtyId="+speId+"&auditerType=ejsh");
                    wfNoticeThingDTO.setIsFinish("0");
                    wfNoticeThingDTO.setIsVisible("1");
                    wfNoticeThingDTO.setThingType("Audit");
                    wfNoticeThingDTO.setThingTitle("【"+Constants.ZJ_BUSITYPE+"】"+zjProjectDTO.getProjectName());
                    wfNoticeThingDTO.setSender(SecurityUtils.getCurrentPersonId().get());
                    wfNoticeThingDTO.setSendTime(Instant.now());
                    workflowClient.createWfNoticeThing(wfNoticeThingDTO);
                }
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_DES);
                zjSpecialtyService.save(zjSpecialtyDTO);
            }
        }
        // 结束待办
        if ("0".equals(haveId)) {
            List<ZjSpecialtyAuditer> zjSpecialtyAuditers = zjSpecialtyAuditerService.findAllByZjProjectIdAndPersonIdAndType(proId, ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString(), "ejshr");
            List<ZjSpecialtyIdea> zjSpecialtyIdeas = this.zjSpecialtyIdeaRepository.findAllByZjProjectIdAndAuditerIdAndIsHistoryAndValidEjsh(proId, ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString(), "0", "1");
            if (zjSpecialtyAuditers.size()>0 && zjSpecialtyIdeas.size()>0) {
                if (zjSpecialtyAuditers.size() == zjSpecialtyIdeas.size()) {
                    workflowClient.setFinishById(flowId);
                }
            }
        } else {
            workflowClient.setFinishById(flowId);
        }
        List<ZjSpecialtyIdea> zjSpecialtyIdeaList = this.findAllByZjSpecialtyIdAndAuditTypeAndIsHistory(speId, auditType, "0");
        List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.findAllByZjSpecialtyIdAndType(speId, auditType + "r");// 审批人表存的type是ejshr或sjshr
//        if (zjSpecialtyIdeaList.size() > 0 && zjSpecialtyAuditerList.size() > 0) {
//            if (zjSpecialtyAuditerList.size() == zjSpecialtyIdeaList.size()) {
//                // 如果工程状态不为待编制人修改  工程状态一共有:des/dss/dbzrxg/sptg/waitDbzrxg
//                if (zjSpecialtyDTO.getState() != null && !Constants.ZJ_SPECIALTY_AUDIT_STATE_WAIT_DBZRXG.equals(zjSpecialtyDTO.getState())) {
//                    if (auditType.equals(Constants.ZJ_FLOW_STATE_EJSH)) {
//                        List<ZjSpecialtyAuditer> zjSpecialtyAuditerSjshList = zjSpecialtyAuditerService.findAllByZjSpecialtyIdAndType(zjSpecialtyDTO.getId(), "sjshr");
//                        for (ZjSpecialtyAuditer zjSpecialtyAuditer : zjSpecialtyAuditerSjshList) {
//                            WfNoticeThingDTO wfNoticeThingDTO = new WfNoticeThingDTO();
//                            wfNoticeThingDTO.setBusinessId(proId);
//                            wfNoticeThingDTO.setStepName(Constants.ZJ_PROJECT_SJSH);
//                            wfNoticeThingDTO.setReceiver(zjSpecialtyAuditer.getPersonId());
//                            wfNoticeThingDTO.setThingUrl("/business/zj/project/projectEdit?id="+proId+"&zjSpecialtyId="+speId+"&auditerType=sjsh");
//                            wfNoticeThingDTO.setIsFinish("0");
//                            wfNoticeThingDTO.setIsVisible("1");
//                            wfNoticeThingDTO.setThingType("Audit");
//                            wfNoticeThingDTO.setThingTitle("【"+Constants.ZJ_BUSITYPE+"】"+zjProjectDTO.getProjectName());
//                            wfNoticeThingDTO.setSender(SecurityUtils.getCurrentPersonId().get());
//                            wfNoticeThingDTO.setSendTime(Instant.now());
//                            workflowClient.createWfNoticeThing(wfNoticeThingDTO);
//                        }
//                        zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_DSS);
//                    } else if (auditType.equals(Constants.ZJ_FLOW_STATE_SJSH)) {
//                        zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_SPTG);
//                    }
//                    zjSpecialtyService.save(zjSpecialtyDTO);
//                } else if (Constants.ZJ_SPECIALTY_AUDIT_STATE_WAIT_DBZRXG.equals(zjSpecialtyDTO.getState())) {
//                    WfNoticeThingDTO wfNoticeThingDTO = new WfNoticeThingDTO();
//                    wfNoticeThingDTO.setBusinessId(proId);
//                    wfNoticeThingDTO.setStepName("编制人修改");
//                    wfNoticeThingDTO.setReceiver(zjSpecialtyDTO.getEstablishmentPersonId());
//                    wfNoticeThingDTO.setThingUrl("/business/zj/project/projectEdit?id="+proId+"&zjSpecialtyId="+zjSpecialtyDTO.getId()+"&auditerType=bzrxg");
//                    wfNoticeThingDTO.setIsFinish("0");
//                    wfNoticeThingDTO.setIsVisible("1");
//                    wfNoticeThingDTO.setThingType("Audit");
//                    wfNoticeThingDTO.setThingTitle("【"+Constants.ZJ_BUSITYPE+"】"+zjProjectDTO.getProjectName());
//                    wfNoticeThingDTO.setSender(SecurityUtils.getCurrentPersonId().get());
//                    wfNoticeThingDTO.setSendTime(Instant.now());
//                    workflowClient.createWfNoticeThing(wfNoticeThingDTO);
//                    zjSpecialtyIdeaRepository.updateIsHistory(zjSpecialtyDTO.getId());
//                    // 发送后现在可以将工程状态改为dbzrxg
//                    zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_DBZRXG);
//                    zjSpecialtyService.save(zjSpecialtyDTO);
//                    // 驳回清空检查记录
//                    zjCheckLogService.cleanCheckLog(zjSpecialtyDTO.getId());
//                }
//            }
//        }
        List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(proId);
        int specialtys = zjSpecialtyList.size();
        int dbzrxgs = 0;
        int waitDbzrxgs = 0;
        int des = 0;
        int dss = 0;
        int sptg = 0;
        for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
            if (zjSpecialty.getState() != null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_AUDIT_STATE_DBZRXG)) {
                dbzrxgs++;
            } else if (zjSpecialty.getState() != null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_AUDIT_STATE_DES)) {
                des++;
            } else if (zjSpecialty.getState() != null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_AUDIT_STATE_DSS)) {
                dss++;
            } else if (zjSpecialty.getState() != null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_AUDIT_STATE_SPTG)) {
                sptg++;
            } else if (zjSpecialty.getState() != null && zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_AUDIT_STATE_WAIT_DBZRXG)) {
                waitDbzrxgs++;
            }
        }
//        if (Constants.ZJ_FLOW_STATE_EJSH.equals(zjProjectDTO.getFlowState())) {
//            if (dbzrxgs == 0 && des == 0 && waitDbzrxgs == 0 && dss > 0) {
//                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_SJSH);
//            }
//        }
//        else if (Constants.ZJ_FLOW_STATE_SJSH.equals(zjProjectDTO.getFlowState())) {
//            if (specialtys == sptg) {
//                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_XMHZ);
//                // 展示项目汇总人待办
//                List<WfNoticeThingDTO> wfNoticeThingDTOS = workflowClient.findByBusinessIdAndStepName(zjProjectDTO.getId(), Constants.ZJ_PROJECT_XMHZ);
//                if (wfNoticeThingDTOS != null && wfNoticeThingDTOS.size() > 0) {
//                    for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOS) {
//                        if (!StringUtils.isNullOrEmpty(wfNoticeThingDTO.getIsFinish()) && wfNoticeThingDTO.getIsFinish().equals("0")) {
//                            workflowClient.showOrHideThing(wfNoticeThingDTO.getId(), true);
//                        }
//                    }
//                }
//            } else if (sptg == 0 && dss == 0) {
//                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_EJSH);
//            }
//        }
//        zjProjectService.save(zjProjectDTO);
//        zjActorsService.saveActors(proId, auditType.equals(Constants.ZJ_FLOW_STATE_BZRXG)?Constants.ZJ_FLOW_STATE_ZJ:auditType, ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString());
        return zjSpecialtyIdeaMapper.toDto(zjSpecialtyIdea);
    }

    /**
     * Get all the zjSpecialtyIdeas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyIdeaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjSpecialtyIdeas");
        return zjSpecialtyIdeaRepository.findAll(pageable)
            .map(zjSpecialtyIdeaMapper::toDto);
    }


    /**
     * Get one zjSpecialtyIdea by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjSpecialtyIdeaDTO> findOne(String id) {
        log.debug("Request to get ZjSpecialtyIdea : {}", id);
        return zjSpecialtyIdeaRepository.findById(id)
            .map(zjSpecialtyIdeaMapper::toDto);
    }

    /**
     * Delete the zjSpecialtyIdea by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjSpecialtyIdea : {}", id);
        zjSpecialtyIdeaRepository.deleteById(id);
    }

    public List<ZjSpecialtyIdea> findAllByZjSpecialtyIdAndAuditTypeAndIsHistory(String zjSpecialtyId, String auditType, String isHistory) {
        return zjSpecialtyIdeaRepository.findAllByZjSpecialtyIdAndAuditTypeAndIsHistory(zjSpecialtyId, auditType, isHistory);
    }

    public List<ZjSpecialtyIdea> findAllByZjProjectIdAndAuditTypeAndIsHistory(String zjProjectId, String auditType, String isHistory) {
        return zjSpecialtyIdeaRepository.findAllByZjProjectIdAndAuditTypeAndIsHistory(zjProjectId, auditType, isHistory);
    }

    public void updateIsHistoryByProjectId(String zjProjectId) {
        zjSpecialtyIdeaRepository.updateIsHistoryByProjectId(zjProjectId);
    }
}
