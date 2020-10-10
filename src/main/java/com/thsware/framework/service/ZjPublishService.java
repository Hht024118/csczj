package com.thsware.framework.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mysql.jdbc.StringUtils;
import com.thsware.framework.client.CcbWorkflowClient;
import com.thsware.framework.config.Constants;
import com.thsware.framework.domain.ZjPublish;
import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.repository.ZjPublishRepository;
import com.thsware.framework.security.ThsSecurityUtils;
import com.thsware.framework.service.dto.*;
import com.thsware.framework.service.mapper.ZjPublishMapper;
import com.thsware.framework.web.client.CscggClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Service Implementation for managing ZjPublish.
 */
@Service
@Transactional
public class ZjPublishService {

    private final Logger log = LoggerFactory.getLogger(ZjPublishService.class);

    private final ZjPublishRepository zjPublishRepository;

    private final ZjPublishMapper zjPublishMapper;

    @Autowired
    private CcbWorkflowClient workflowClient;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjProjectService zjProjectService;

    @Autowired
    private CscggClient cscggClient;

    public ZjPublishService(ZjPublishRepository zjPublishRepository, ZjPublishMapper zjPublishMapper) {
        this.zjPublishRepository = zjPublishRepository;
        this.zjPublishMapper = zjPublishMapper;
    }

    /**
     * Save a zjPublish.
     *
     * @param zjPublishDTO the entity to save
     * @return the persisted entity
     */
    public ZjPublishDTO save(ZjPublishDTO zjPublishDTO) {
        log.debug("Request to save ZjPublish : {}", zjPublishDTO);
        ZjPublish zjPublish = zjPublishMapper.toEntity(zjPublishDTO);
        zjPublish = zjPublishRepository.save(zjPublish);
        Optional<ZjProjectDTO> zjProjectDTOOptional = zjProjectService.findOne(zjPublish.getZjProjectId());
        ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
        ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();



        // 保存完成后出版信息关联工程
        if (zjPublishDTO.getZjSpecialtyIds() != null ) {
            for (String zjSpecialtyId : zjPublishDTO.getZjSpecialtyIds()) {
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(zjSpecialtyId);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setZjPublishId(zjPublish.getId());
                zjSpecialtyService.save(zjSpecialtyDTO);
            }
        }

        if(null!=zjPublishDTO) {
            // 前台是save or submit
            if (zjPublishDTO.getSaveType() != null && "submit".equals(zjPublishDTO.getSaveType())) {
                Map<String, Object> busiFormMap = JSON.parseObject(zjPublishDTO.getBusiFormData(), new TypeReference<Map<String, Object>>() {});
                if (null == busiFormMap.get("id")) {
                    busiFormMap.put("id", zjPublish.getId());
                    zjPublishDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
                }
                if (StringUtils.isNullOrEmpty(zjPublishDTO.getId())) {
                    zjPublishDTO.setId(zjPublish.getId());
                }
                Map<String, Object> result = workflowClient.sendOrSubmit(zjPublishDTO);
                if ((boolean) result.get("success")) {
                    String state = zjPublishDTO.getFlowState();
                    String flowState = zjPublishDTO.getFlowState();
                    // 如果是加签步骤
                    if (!StringUtils.isNullOrEmpty(zjPublishDTO.getFlowId()) && zjPublishDTO.getFlowId().startsWith("AddSign")) {

                    }
                    else {
                        if (zjPublishDTO.getAuditResult().equals("1")) {
                            //提交成功的逻辑
                            if (state == null || "".equals(state)) {
                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setMainWork("项目汇总");
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setProjectType("zjzx");
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"项目汇总")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_QF;
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_QF);
                                // 隐藏项目汇总工程的状态
                                if (zjPublishDTO.getFlowIds() != null) {
                                    List<String> flowIds = zjPublishDTO.getFlowIds();
                                    for (String flowId : flowIds) {
                                        workflowClient.showOrHideThing(flowId, false);
                                    }
                                }
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_QF)) {
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_CB;
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_CB);
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_CB)) {
                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setMainWork("出版");
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setProjectType("zjzx");
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"出版")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_CBWC;
                                zjPublish.setPublishTime(Instant.now());
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_CBWC);
                            } else if (state.equals(Constants.ZJ_SPECIALTY_FLOW_STATE_CBWC)) {
                                projectPersonDTO.setProjectName(zjProjectDTO.getProjectName());
                                projectPersonDTO.setProjectNo(zjProjectDTO.getProjectNo());
                                projectPersonDTO.setProjectId(zjProjectDTO.getId());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setMainWork("出版完成");
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setProjectType("zjzx");
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"出版完成")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_CBJS;
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_CBJS);
                                updateProjectFlowState(zjPublishDTO.getId());

                            }
                        }
                        if (zjPublishDTO.getAuditResult().equals("2")) {
                            if (zjPublishDTO.getNextStepName().equals(Constants.ZJ_PROJECT_QF)) {
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_QF;
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_QF);
                            } else if (zjPublishDTO.getNextStepName().equals(Constants.ZJ_PROJECT_CB)) {
                                flowState = Constants.ZJ_SPECIALTY_FLOW_STATE_CB;
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_CB);
                            } else if (zjPublishDTO.getNextStepName().equals(Constants.ZJ_SPECIALTY_STEP_NAME_XMHZ)) {
                                updateSpecialtyState(zjPublish.getId(), Constants.ZJ_SPECIALTY_FLOW_STATE_XMHZ);
                                updateProjectFlowState(zjPublishDTO.getId());
                                if (zjPublishDTO.getFlowIds() != null) {
                                    List<String> flowIds = zjPublishDTO.getFlowIds();
                                    // flowIds里是工程ID
                                    for (String flowId : flowIds) {
                                        List<WfNoticeThingDTO> wfNoticeThingDTOS = workflowClient.findByBusinessIdAndStepName(flowId, Constants.ZJ_SPECIALTY_STEP_NAME_XMHZ);
                                        if (wfNoticeThingDTOS != null && wfNoticeThingDTOS.size() > 0) {
                                            for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOS) {
                                                if (!StringUtils.isNullOrEmpty(wfNoticeThingDTO.getIsFinish()) && wfNoticeThingDTO.getIsFinish().equals("0")) {
                                                    workflowClient.showOrHideThing(wfNoticeThingDTO.getId(), true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (zjPublishDTO.getAuditResult().equals("3")) {

                        }
                        if (zjPublishDTO.getAuditResult().equals("6")) {
                            flowState = null;
                            // 因为是在发起步骤废弃，退回或者撤回到发起时，关联的工程状态其实都是项目汇总
                        }
                    }
                    zjPublish.setFlowState(flowState);
                    zjPublish = zjPublishRepository.save(zjPublish);

                }
            }
        }

        return zjPublishMapper.toDto(zjPublish);
    }

    /**
     * Get all the zjPublishes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ZjPublishDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjPublishes");
        return zjPublishRepository.findAll(pageable)
            .map(zjPublishMapper::toDto);
    }


    /**
     * Get one zjPublish by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ZjPublishDTO> findOne(String id) {
        log.debug("Request to get ZjPublish : {}", id);
        return zjPublishRepository.findById(id)
            .map(zjPublishMapper::toDto);
    }

    /**
     * Delete the zjPublish by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjPublish : {}", id);
        zjPublishRepository.deleteById(id);
    }

    /**
     * 更新工程状态
     * @param zjPublishId
     * @param flowState
     */
    public void updateSpecialtyState(String zjPublishId, String flowState) {
        List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.findAllByZjPublishId(zjPublishId);
        for (ZjSpecialtyDTO zjSpecialtyDTO : zjSpecialtyDTOList) {
            zjSpecialtyDTO.setState(flowState);
            zjSpecialtyService.save(zjSpecialtyDTO);
        }
    }

    /**
     * 更新项目状态
     * @param zjPublishId
     */
    public void updateProjectFlowState(String zjPublishId) {
        boolean allOver = true;
        List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.findAllByZjPublishId(zjPublishId);
        if (zjSpecialtyDTOList != null) {
            String zjProjectId = zjSpecialtyDTOList.get(0).getZjProjectId();
            List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProjectId);
            for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                if (StringUtils.isNullOrEmpty(zjSpecialty.getState()) || !zjSpecialty.getState().equals(Constants.ZJ_SPECIALTY_FLOW_STATE_CBJS)) {
                    allOver = false;
                }
            }
            if (allOver) {
                Optional<ZjProjectDTO> zjProjectDTOOptional = zjProjectService.findOne(zjProjectId);
                ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
                zjProjectDTO.setPublishDate(Instant.now());
                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_DGD);
                zjProjectService.save(zjProjectDTO);
                if(StringUtils.isNullOrEmpty(zjProjectDTO.getParentId())){
                    ResponseEntity<SysBusiformDTO> sysBusiformDTO =  cscggClient.getBusiforms(zjProjectId);
                    sysBusiformDTO.getBody().setPublishDate(Instant.now());
                    cscggClient.updateBusiforms(sysBusiformDTO.getBody());
                }
            }
        }
    }

    public List<ZjPublish> findAllByZjProjectId(String zjProjectId){
        return zjPublishRepository.findAllByZjProjectId(zjProjectId);
    }
}
