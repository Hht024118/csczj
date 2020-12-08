package com.thsware.framework.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mysql.jdbc.StringUtils;
import com.netflix.discovery.converters.Auto;
import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.client.CcbWorkflowClient;
import com.thsware.framework.client.ThsAdminClient;
import com.thsware.framework.config.Constants;
import com.thsware.framework.consts.EnumProjectState;
import com.thsware.framework.domain.ZjMember;
import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.repository.ZjProjectRepository;
import com.thsware.framework.security.SecurityUtils;
import com.thsware.framework.security.ThsSecurityUtils;
import com.thsware.framework.service.dto.*;
import com.thsware.framework.service.mapper.ZjProjectMapper;
import com.thsware.framework.util.DateUtil;
import com.thsware.framework.util.MultiTenancyUtils;
import com.thsware.framework.web.client.CscggClient;
import com.thsware.framework.web.client.ThsUAAClient;
import com.thsware.framework.web.client.ThsadminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service Implementation for managing ZjProject.
 */
@Service
@Transactional
public class ZjProjectService {

    private final Logger log = LoggerFactory.getLogger(ZjProjectService.class);

    private final ZjProjectRepository zjProjectRepository;

    private final ZjProjectMapper zjProjectMapper;

    @Autowired
    private  CcbWorkflowClient workflowClient;

    @Autowired
    private ZjMemberService zjMemberService;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    @Autowired
    private ZjActorsService zjActorsService;

    @Autowired
    private CscggClient cscggClient;

    @Autowired
    private ThsadminClient thsadminClient;

    @Autowired
    private ZjSpecialtyIdeaService zjSpecialtyIdeaService;

    @Autowired
    private ZjCheckLogService zjCheckLogService;

    @Autowired
    private ZjAssistantService zjAssistantService;

    @Autowired
    private ZjPublishService zjPublishService;

    @Autowired
    private ThsUAAClient thsuaaClient;

    public ZjProjectService(ZjProjectRepository zjProjectRepository, ZjProjectMapper zjProjectMapper) {
        this.zjProjectRepository = zjProjectRepository;
        this.zjProjectMapper = zjProjectMapper;
    }

    /**
     * 生成项目编号
     * @param implementUnit 项目实施单位
     * @return
     */
    public String createProjectNo(String projectNo,String projectYear,String implementUnit){
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            String projectNo_ = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            String year = format.format(y);
            if (!StringUtils.isNullOrEmpty(projectNo)) {
                Object obj = new Object();
                //判断本年度和上 年度1：本年度 0：上年度
                if (!StringUtils.isNullOrEmpty(projectYear) && "1".equals(projectYear)) {
                    obj = zjProjectRepository.getMaxProjectNo(projectNo + "-" + DateUtil.getYear(new Date()));
                    projectNo_ = "JYZX-ZJ-" + projectNo + "-" + DateUtil.getYear(new Date());
                }else if(!StringUtils.isNullOrEmpty(projectYear) && "0".equals(projectYear)){
                    obj = zjProjectRepository.getMaxProjectNo(projectNo + "-" + year);
                    projectNo_ = "JYZX-ZJ-" + projectNo + "-" + year;
                }else{
                    obj = zjProjectRepository.getMaxProjectNo(projectNo + "-" + DateUtil.getYear(new Date()));
                    projectNo_ = "JYZX-ZJ-" + projectNo + "-" + DateUtil.getYear(new Date());
                }
                //新需求根据前台项目木年度生成项目编号

                int projectBh = 1;
                if (obj != null) {
                    projectBh = Integer.parseInt(obj.toString()) + 1;
                }
                DecimalFormat df = new DecimalFormat("00000");
                projectNo_ = projectNo_ + "-" + df.format(projectBh);
            }
            return projectNo_;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 生成子项目编号
     * @param
     * @return
     */
    public String createZxmProjectNo(String projectNo,String parentId){
        String zxmProjectNo = "";
        Object obj = new Object();
        obj = zjProjectRepository.getMaxZxmProjectNo(parentId);
        zxmProjectNo = projectNo + "Q" ;
        int projectBh = 1;
        if(obj != null){
            projectBh = Integer.parseInt(obj.toString()) + 1;
        }
        DecimalFormat df = new DecimalFormat("000");
        zxmProjectNo = zxmProjectNo + df.format(projectBh);
        return zxmProjectNo;
    }

    /**
     * Save a zjProject.
     *
     * @param zjProjectDTO the entity to save
     * @return the persisted entity
     */
    public ZjProjectDTO save(ZjProjectDTO zjProjectDTO) {
        log.debug("Request to save ZjProject : {}", zjProjectDTO);
        ZjProject zjProject = zjProjectMapper.toEntity(zjProjectDTO);
        ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();

        if (Constants.ZJ_FLOW_STATE_XMCH.equals(zjProjectDTO.getFlowState()) || Constants.ZJ_FLOW_STATE_CHSH.equals(zjProjectDTO.getFlowState())) {
            if (null!=zjProjectDTO.getZjMemberList() && zjProjectDTO.getZjMemberList().size() > 0) {
                zjMemberService.deleteMemberByZjProjectId(zjProjectDTO.getId());
                for (ZjMemberDTO zjMemberDTO: zjProjectDTO.getZjMemberList()) {
                    zjMemberService.save(zjMemberDTO);
                }
            }
        }

        zjProject = zjProjectRepository.save(zjProject);
        String personId = ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString();
        // 修改暂存状态后，设置项目登记的参与人员
        if (!StringUtils.isNullOrEmpty(zjProject.getProjectState()) && zjProject.getProjectState().equals(Constants.ZJ_PROJECT_STATE_ZC)) {
            zjActorsService.saveActors(zjProject.getId(), Constants.ZJ_FLOW_STATE_XMDJ, personId);
        }

        if(null!=zjProject){
            // 前台是save or submit
            if (zjProjectDTO.getSaveType()!=null && "submit".equals(zjProjectDTO.getSaveType())) {
                Map<String, Object> busiFormMap = JSON.parseObject(zjProjectDTO.getBusiFormData(),new TypeReference<Map<String, Object>>(){} );
                if(null==busiFormMap.get("id")){
                    busiFormMap.put("id",zjProject.getId());
                    zjProjectDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
                }
                if(StringUtils.isNullOrEmpty(zjProjectDTO.getId())){
                    zjProjectDTO.setId(zjProject.getId());
                }
                Map<String,Object> result=workflowClient.sendOrSubmit(zjProjectDTO);
                if((boolean)result.get("success")){
                    String state = zjProjectDTO.getFlowState();
                    String flowState = zjProjectDTO.getFlowState();
                    // 如果是加签步骤
                    if (!StringUtils.isNullOrEmpty(zjProjectDTO.getFlowId()) && zjProjectDTO.getFlowId().startsWith("AddSign")) {

                    }
                    else {
                        if(zjProjectDTO.getAuditResult().equals("1")){
                            //提交成功的逻辑
                            if (state == null || "".equals(state) || state.equals(Constants.ZJ_FLOW_STATE_XMDJ)) {
                                projectPersonDTO.setProjectName(zjProject.getProjectName());
                                projectPersonDTO.setProjectNo(zjProject.getProjectNo());
                                projectPersonDTO.setProjectId(zjProject.getId());
                                projectPersonDTO.setPersonName(zjProject.getProjectManagerName());
                                projectPersonDTO.setPersonId(zjProject.getProjectManager());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setMainWork("项目登记");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),zjProject.getProjectManagerName(),"项目登记")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_FLOW_STATE_XMCH;
                                zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_YTJ);

                            }
                            else if (state.equals(Constants.ZJ_FLOW_STATE_XMCH)) {
                                projectPersonDTO.setProjectName(zjProject.getProjectName());
                                projectPersonDTO.setProjectNo(zjProject.getProjectNo());
                                projectPersonDTO.setProjectId(zjProject.getId());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setMainWork("项目策划");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"项目策划")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_FLOW_STATE_CHSH;
                                zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_SPZ);
                            }
                            else if (state.equals(Constants.ZJ_FLOW_STATE_CHSH)) {
                                // 项目由策划审核到实施中
                                // 衔接工程流程，修改工程状态为自校状态
                                flowState = Constants.ZJ_FLOW_STATE_SSZ;
                                List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProject.getId());
                                // 更新这些工程的状态
                                if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
                                    for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                                        ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyService.retDTO(zjSpecialty);
                                        zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ);
                                        zjSpecialtyService.save(zjSpecialtyDTO);
                                    }
                                }
                            }
                            else if (state.equals(Constants.ZJ_FLOW_STATE_DGD)) {
                                projectPersonDTO.setProjectName(zjProject.getProjectName());
                                projectPersonDTO.setProjectNo(zjProject.getProjectNo());
                                projectPersonDTO.setProjectId(zjProject.getId());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setMainWork("待归档");
                                projectPersonDTO.setProjectType("zjzx");
                                projectPersonDTO.setResultsForm(zjProjectDTO.getBusiType().concat("报告"));
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"待归档")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_FLOW_STATE_JS;
                                zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_DGD);
                            }
                        }
                        if(zjProjectDTO.getAuditResult().equals("2")){
                            if (state.equals(Constants.ZJ_FLOW_STATE_CHSH) || state.equals(Constants.ZJ_FLOW_STATE_XMCH)) {
                                if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMCH)) {
                                    flowState = Constants.ZJ_FLOW_STATE_XMCH;
                                }
                                else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMDJ)) {
                                    flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                                }
                            }
                            else if (state.equals(Constants.ZJ_FLOW_STATE_DGD)) {
                                flowState = Constants.ZJ_FLOW_STATE_CHSH;
                                List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProject.getId());
                                List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.retListDTO(zjSpecialtyList);
                                for (ZjSpecialtyDTO zjSpecialtyDTO : zjSpecialtyDTOList) {
                                    zjCheckLogService.cleanCheckLog(zjSpecialtyDTO.getId());
                                    zjSpecialtyDTO.setState(null);
                                    zjSpecialtyDTO.setActualFinishDate(null);
                                    zjSpecialtyService.save(zjSpecialtyDTO);
                                }
                                zjProject.setPublishDate(null);

                            }
                        }
                        if(zjProjectDTO.getAuditResult().equals("3")){
                            //转办成功的逻辑

                        }
                        if(zjProjectDTO.getAuditResult().equals("6")){
                            //项目登记步骤作废中止
                            flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                            zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_ZC);
                        }
                    }
                    zjActorsService.saveActors(zjProject.getId(), state, personId);
                    zjProject.setFlowState(flowState);
                    zjProject = zjProjectRepository.save(zjProject);

                }
            }
        }
        return zjProjectMapper.toDto(zjProject);
    }


    /**
     * Save a AllProcessProject.
     *
     * @param zjProjectDTO the entity to save
     * @return the persisted entity
     */
    public ZjProjectDTO saveChildProject(ZjProjectDTO zjProjectDTO) {
        log.debug("Request to save ZjProject : {}", zjProjectDTO);
        ZjProject zjProject = zjProjectMapper.toEntity(zjProjectDTO);
        zjProject = zjProjectRepository.save(zjProject);
        ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();
        if (Constants.ZJ_FLOW_STATE_XMDJ.equals(zjProjectDTO.getFlowState())) {
            if (null!=zjProjectDTO.getZjMemberList() && zjProjectDTO.getZjMemberList().size() > 0) {
                zjMemberService.deleteMemberByZjProjectId(zjProjectDTO.getId());
                for (ZjMemberDTO zjMemberDTO: zjProjectDTO.getZjMemberList()) {
                    zjMemberDTO.setZjProjectId(zjProject.getId());
                    zjMemberService.save(zjMemberDTO);
                }
            }
        }


        if (StringUtils.isNullOrEmpty(zjProjectDTO.getId())) {
            if (null != zjProjectDTO.getZjSpecialtyList() && zjProjectDTO.getZjSpecialtyList().size() > 0) {
                for (ZjSpecialtyDTO zjSpecialtyDTO: zjProjectDTO.getZjSpecialtyList()) {
                    zjSpecialtyDTO.setZjProjectId(zjProject.getId());
                    zjSpecialtyService.save(zjSpecialtyDTO);
                }
            }
        }

        String personId = ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString();
        // 修改暂存状态后，设置项目登记的参与人员
        if (!StringUtils.isNullOrEmpty(zjProject.getProjectState()) && zjProject.getProjectState().equals(Constants.ZJ_PROJECT_STATE_ZC)) {
            zjActorsService.saveActors(zjProject.getId(), Constants.ZJ_FLOW_STATE_XMDJ, personId);
        }

        if(null!=zjProject){
            // 前台是save or submit
            if (zjProjectDTO.getSaveType()!=null && "submit".equals(zjProjectDTO.getSaveType())) {
                Map<String, Object> busiFormMap = JSON.parseObject(zjProjectDTO.getBusiFormData(),new TypeReference<Map<String, Object>>(){} );
                if (zjProjectDTO.getFlowState().equals(Constants.ZJ_FLOW_STATE_XMDJ)) {
                    Map<String,String> auditersMap = new HashMap();
                    List<String> projectList = new ArrayList<>();
                    Map<String,Object> planFinishDateMap = new HashMap<>();
                    List<ZjSpecialty> zjSpecialtyList =  zjSpecialtyService.findAllByZjProjectId(zjProject.getId());
                    for(ZjSpecialty zjSpecialty : zjSpecialtyList) {
                        auditersMap.put(zjSpecialty.getId(),zjSpecialty.getEstablishmentPersonId());
                        projectList.add(zjSpecialty.getId());
                        Date tmpDate=Date.from(zjSpecialty.getEndDate());
                        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        String endDateStr=formatter.format(tmpDate);
                        planFinishDateMap.put(zjSpecialty.getId(), endDateStr);
                    }
                    busiFormMap.put("auditersMap",auditersMap);
                    busiFormMap.put("projectList",projectList);
                    busiFormMap.put("planFinishDateMap",planFinishDateMap);
                    zjProjectDTO.setExtField1("specialty");
                    zjProjectDTO.setExtField2(zjProject.getId());
                }
                if(null==busiFormMap.get("id")){
                    busiFormMap.put("id",zjProject.getId());
                }
                zjProjectDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
                if(StringUtils.isNullOrEmpty(zjProjectDTO.getId())){
                    zjProjectDTO.setId(zjProject.getId());
                }
                Map<String,Object> result=workflowClient.sendOrSubmit(zjProjectDTO);
                if((boolean)result.get("success")){
                    String state = zjProjectDTO.getFlowState();
                    String flowState = zjProjectDTO.getFlowState();
                    // 如果是加签步骤
                    if (!StringUtils.isNullOrEmpty(zjProjectDTO.getFlowId()) && zjProjectDTO.getFlowId().startsWith("AddSign")) {

                    }
                    else {
                        if (zjProjectDTO.getAuditResult().equals("1")) {
                            //提交成功的逻辑
                            if (state == null || "".equals(state) || state.equals(Constants.ZJ_FLOW_STATE_XMDJ)) {
                                flowState = Constants.ZJ_FLOW_STATE_SSZ;
                                zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_SPZ);
                                List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProject.getId());
                                // 更新这些工程的状态
                                if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
                                    for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                                        ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyService.retDTO(zjSpecialty);
                                        zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ);
                                        zjSpecialtyService.save(zjSpecialtyDTO);
                                    }
                                }
                            } else if (state.equals(Constants.ZJ_FLOW_STATE_DGD)) {
                                projectPersonDTO.setProjectName(zjProject.getProjectName());
                                projectPersonDTO.setProjectNo(zjProject.getProjectNo());
                                projectPersonDTO.setProjectId(zjProject.getId());
                                projectPersonDTO.setStartTime(zjProjectDTO.getDelegateDate());
                                projectPersonDTO.setEndTime(zjProjectDTO.getPlanFinishDate());
                                projectPersonDTO.setScale(zjProjectDTO.getProjectScale());
                                projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                                projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                                projectPersonDTO.setTechnicalPost("项目负责人");
                                projectPersonDTO.setMainWork("待归档");
                                projectPersonDTO.setProjectType("zjzx");
                                if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"待归档")){
                                    cscggClient.createProjectPerson(projectPersonDTO);
                                }
                                flowState = Constants.ZJ_FLOW_STATE_JS;
                                zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_DGD);
                            }
                        }
                        if (zjProjectDTO.getAuditResult().equals("2")) {
                            if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMDJ)) {
                                flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                                List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProject.getId());
                                List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.retListDTO(zjSpecialtyList);
                                for (ZjSpecialtyDTO zjSpecialtyDTO : zjSpecialtyDTOList) {
                                    zjCheckLogService.cleanCheckLog(zjSpecialtyDTO.getId());
                                    zjSpecialtyDTO.setState(null);
                                    zjSpecialtyService.save(zjSpecialtyDTO);

                                }
                            }

                        }
                        if (zjProjectDTO.getAuditResult().equals("3")) {
                            //转办成功的逻辑

                        }
                    }
                    zjActorsService.saveActors(zjProject.getId(), state, personId);
                    zjProject.setFlowState(flowState);
                    zjProject = zjProjectRepository.save(zjProject);

                }
            }
        }
        return zjProjectMapper.toDto(zjProject);
    }

    /**
     * Save a AllProcessProject.
     *
     * @param zjProjectDTO the entity to save
     * @return the persisted entity
     */
    public ZjProjectDTO saveAllProcessProject(ZjProjectDTO zjProjectDTO) {
        log.debug("Request to save ZjProject : {}", zjProjectDTO);
        ZjProject zjProject = zjProjectMapper.toEntity(zjProjectDTO);
        ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();
        if (Constants.ZJ_FLOW_STATE_XMCH.equals(zjProjectDTO.getFlowState()) || Constants.ZJ_FLOW_STATE_CHSH.equals(zjProjectDTO.getFlowState())) {
            if (null!=zjProjectDTO.getZjMemberList() && zjProjectDTO.getZjMemberList().size() > 0) {
                zjMemberService.deleteMemberByZjProjectId(zjProjectDTO.getId());
                for (ZjMemberDTO zjMemberDTO: zjProjectDTO.getZjMemberList()) {
                    zjMemberService.save(zjMemberDTO);
                }
            }
        }

        zjProject = zjProjectRepository.save(zjProject);
        String personId = ThsSecurityUtils.getDecodedDetails().get().get("entity_id").toString();
        // 修改暂存状态后，设置项目登记的参与人员
        if (!StringUtils.isNullOrEmpty(zjProject.getProjectState()) && zjProject.getProjectState().equals(Constants.ZJ_PROJECT_STATE_ZC)) {
            zjActorsService.saveActors(zjProject.getId(), Constants.ZJ_FLOW_STATE_XMDJ, personId);
        }

        if(null!=zjProject){
            // 前台是save or submit
            if (zjProjectDTO.getSaveType()!=null && "submit".equals(zjProjectDTO.getSaveType())) {
                Map<String, Object> busiFormMap = JSON.parseObject(zjProjectDTO.getBusiFormData(),new TypeReference<Map<String, Object>>(){} );
                if(null==busiFormMap.get("id")){
                    busiFormMap.put("id",zjProject.getId());
                    zjProjectDTO.setBusiFormData(JSON.toJSONString(busiFormMap));
                }
                if(StringUtils.isNullOrEmpty(zjProjectDTO.getId())){
                    zjProjectDTO.setId(zjProject.getId());
                }
                Map<String,Object> result=workflowClient.sendOrSubmit(zjProjectDTO);
                if((boolean)result.get("success")){
                    String state = zjProjectDTO.getFlowState();
                    String flowState = zjProjectDTO.getFlowState();
                    if(zjProjectDTO.getAuditResult().equals("1")){
                        //提交成功的逻辑
                        if (state == null || "".equals(state) || state.equals(Constants.ZJ_FLOW_STATE_XMDJ)) {
                            flowState = Constants.ZJ_FLOW_STATE_XMCH;
                            zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_YTJ);
                        }
                        else if (state.equals(Constants.ZJ_FLOW_STATE_XMCH)) {
                            projectPersonDTO.setProjectName(zjProject.getProjectName());
                            projectPersonDTO.setProjectNo(zjProject.getProjectNo());
                            projectPersonDTO.setProjectId(zjProject.getId());
                            projectPersonDTO.setPersonName(ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get());
                            projectPersonDTO.setPersonId(ThsSecurityUtils.getDecodedDetailsVaule("entity_id").get());
                            projectPersonDTO.setStartTime(zjProject.getDelegateDate());
                            projectPersonDTO.setEndTime(zjProject.getPlanFinishDate());
                            projectPersonDTO.setScale(zjProject.getProjectScale());
                            projectPersonDTO.setTechnicalPost("项目负责人");
                            projectPersonDTO.setMainWork("项目策划");
                            projectPersonDTO.setProjectType("zjzx");
                            if (!cscggClient.getIsPerInfos(zjProjectDTO.getProjectNo(),ThsSecurityUtils.getDecodedDetailsVaule("entity_name").get(),"项目策划")){
                                cscggClient.createProjectPerson(projectPersonDTO);
                            }
                            flowState = Constants.ZJ_FLOW_STATE_CHSH;
                            zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_SPZ);
                        }
                        else if (state.equals(Constants.ZJ_FLOW_STATE_CHSH)) {
                            flowState = Constants.ZJ_FLOW_STATE_JS;
                            zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_DGD);
                        }
                        /*else if (state.equals(Constants.ZJ_FLOW_STATE_ZJ)) {
                            boolean zjOver = true;
                            List<WfNoticeThingDTO> wfNoticeThingDTOList = workflowClient.findByBusinessIdAndStepName(zjProjectDTO.getId(), Constants.ZJ_PROJECT_ZJ);
                            for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOList) {
                                if (wfNoticeThingDTO.getIsFinish() != null && "0".equals(wfNoticeThingDTO.getIsFinish())) {
                                    zjOver = false;
                                }
                            }
                            if (zjOver) {
                                flowState = Constants.ZJ_FLOW_STATE_ZJHZ;
                            }
                        }*/
                        /*else if (state.equals(Constants.ZJ_FLOW_STATE_ZJHZ)) {
                            flowState = Constants.ZJ_FLOW_STATE_EJSH;
                            List<ZjSpecialtyAuditer> zjSpecialtyAuditers = zjSpecialtyAuditerService.findAllByZjProjectIdAndType(zjProjectDTO.getId(), "ejshr");
                            String receivers = ",";
                            for (ZjSpecialtyAuditer zjSpecialtyAuditer : zjSpecialtyAuditers) {
                                if (!receivers.contains(zjSpecialtyAuditer.getPersonId())) {
                                    receivers += zjSpecialtyAuditer.getPersonId()+",";
                                    WfNoticeThingDTO wfNoticeThingDTO = new WfNoticeThingDTO();
                                    wfNoticeThingDTO.setBusinessId(zjProjectDTO.getId());
                                    wfNoticeThingDTO.setStepName(Constants.ZJ_PROJECT_EJSH);
                                    wfNoticeThingDTO.setReceiver(zjSpecialtyAuditer.getPersonId());
                                    wfNoticeThingDTO.setThingUrl("/business/zj/project/projectEdit?id="+zjProjectDTO.getId()+"&auditerType=ejsh");
                                    wfNoticeThingDTO.setIsFinish("0");
                                    wfNoticeThingDTO.setIsVisible("1");
                                    wfNoticeThingDTO.setThingType("Audit");
                                    wfNoticeThingDTO.setThingTitle("【"+Constants.ZJ_BUSITYPE+"】"+zjProject.getProjectName());
                                    wfNoticeThingDTO.setSender(SecurityUtils.getCurrentPersonId().get());
                                    wfNoticeThingDTO.setSendTime(Instant.now());
                                    workflowClient.createWfNoticeThing(wfNoticeThingDTO);
                                }
                            }
                            // 发送待办成功后将工程state改为待二审des
                            List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProjectDTO.getId());
                            for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(zjSpecialty.getId());
                                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_AUDIT_STATE_DES);
                                zjSpecialtyService.save(zjSpecialtyDTO);
                            }
                            // 隐藏项目汇总人待办
                            List<WfNoticeThingDTO> wfNoticeThingDTOS = workflowClient.findByBusinessIdAndStepName(zjProjectDTO.getId(), Constants.ZJ_PROJECT_XMHZ);
                            if (wfNoticeThingDTOS != null && wfNoticeThingDTOS.size() > 0) {
                                for (WfNoticeThingDTO wfNoticeThingDTO : wfNoticeThingDTOS) {
                                    if (!StringUtils.isNullOrEmpty(wfNoticeThingDTO.getIsFinish()) && wfNoticeThingDTO.getIsFinish().equals("0")) {
                                        workflowClient.showOrHideThing(wfNoticeThingDTO.getId(), false);
                                    }
                                }
                            }
                        }
                        else if (state.equals(Constants.ZJ_FLOW_STATE_XMHZ)) {
                            flowState = Constants.ZJ_FLOW_STATE_QF;
                        }
                        else if (state.equals(Constants.ZJ_FLOW_STATE_QF)) {
                            flowState = Constants.ZJ_FLOW_STATE_CB;
                        }
                        else if (state.equals(Constants.ZJ_FLOW_STATE_CB)) {
                            flowState = Constants.ZJ_FLOW_STATE_JS;
                            zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_DGD);
                            zjProject.setPublishDate(Instant.now());
                            //发送给项目负责人待归档消息
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("content",zjProject.getProjectName()+"待归档!");
                            map.put("priority",1);
                            map.put("receiverId",zjProject.getProjectManager());
                            map.put("receiverName",zjProject.getProjectManagerName());
                            map.put("senderId",SecurityUtils.getCurrentPersonId().get());
                            map.put("senderName",SecurityUtils.getCurrentPersonName().get());
                            thsadminClient.createSysNotification(map);
                        }*/
                    }
                    if(zjProjectDTO.getAuditResult().equals("2")){
                        if (state.equals(Constants.ZJ_FLOW_STATE_CHSH) || state.equals(Constants.ZJ_FLOW_STATE_XMCH)) {
                            if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMCH)) {
                                flowState = Constants.ZJ_FLOW_STATE_XMCH;
                            }
                            else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMDJ)) {
                                flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                            }
                        }
//                        if (state.equals(Constants.ZJ_FLOW_STATE_CHSH)) {
//                            flowState = Constants.ZJ_FLOW_STATE_XMCH;
//                        }
                        /*else if (state.equals(Constants.ZJ_FLOW_STATE_ZJHZ)) {
                            flowState = Constants.ZJ_FLOW_STATE_ZJ;
                            this.clearCheckLogByChekcStep(zjProject.getId(), Constants.ZJ_FLOW_STATE_ZJ, "");
                        }*/
                        /*else if (state.equals(Constants.ZJ_FLOW_STATE_QF)) {
                            if (!StringUtils.isNullOrEmpty(zjProjectDTO.getNextStepName())) {
                                boolean changeEjshAndSjshHistory = false;
                                if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMDJ)) {
                                    changeEjshAndSjshHistory = true;
                                    flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                                    this.clearCheckLog(zjProject.getId());
                                }
                                else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMCH)) {
                                    changeEjshAndSjshHistory = true;
                                    flowState = Constants.ZJ_FLOW_STATE_XMCH;
                                    this.clearCheckLog(zjProject.getId());
                                }
                                else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_CHSH)) {
                                    changeEjshAndSjshHistory = true;
                                    flowState = Constants.ZJ_FLOW_STATE_CHSH;
                                    this.clearCheckLog(zjProject.getId());
                                }
                                else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_ZJHZ)) {
                                    changeEjshAndSjshHistory = true;
                                    flowState = Constants.ZJ_FLOW_STATE_ZJHZ;
                                    this.clearCheckLogByChekcStep(zjProject.getId(), Constants.ZJ_FLOW_STATE_EJSH, Constants.ZJ_FLOW_STATE_SJSH);
                                }
                                else if (zjProjectDTO.getNextStepName().equals(Constants.ZJ_PROJECT_XMHZ)) {
                                    flowState = Constants.ZJ_FLOW_STATE_XMHZ;
                                }
                                if (changeEjshAndSjshHistory) {
                                    zjSpecialtyIdeaService.updateIsHistoryByProjectId(zjProject.getId());
                                }
                            }
                        }*/
                    }
                    /*if(zjProjectDTO.getAuditResult().equals("3")){
                        //转办成功的逻辑

                    }*/
                    if(zjProjectDTO.getAuditResult().equals("6")){
                        //项目登记步骤作废中止
                        flowState = Constants.ZJ_FLOW_STATE_XMDJ;
                        zjProject.setProjectState(Constants.ZJ_PROJECT_STATE_ZC);
                    }
                    zjActorsService.saveActors(zjProject.getId(), state, personId);
                    zjProject.setFlowState(flowState);
                    zjProject = zjProjectRepository.save(zjProject);
                }
            }
        }
        return zjProjectMapper.toDto(zjProject);
    }

    /**
     * Get all the zjProjects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjProjects");
        return zjProjectRepository.findAll(pageable)
            .map(zjProjectMapper::toDto);
    }


    /**
     * Get one zjProject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjProjectDTO> findOne(String id) {
        log.debug("Request to get ZjProject : {}", id);
        return zjProjectRepository.findById(id)
            .map(zjProjectMapper::toDto);
    }

    /**
     * Delete the zjProject by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjProject : {}", id);
        cscggClient.deleteSysBusiform(id);
        zjProjectRepository.deleteById(id);
    }

    /**
     * Delete the zjProject by id.
     *
     * @param id the id of the entity
     */
    public void deleteChild(String id) {
        log.debug("Request to delete ZjProject : {}", id);
        cscggClient.deleteSysBusiform(id);
        zjMemberService.deleteMemberByZjProjectId(id);
        zjSpecialtyService.deleteSpecialtyByZjProjectId(id);
        zjProjectRepository.deleteById(id);
    }

    public ZjProjectDTO updateProject(ZjProjectDTO zjProjectDTO) {
        log.debug("Request to save ZjProjectArchive : {}", zjProjectDTO);
        ZjProject zjProject = zjProjectMapper.toEntity(zjProjectDTO);
        zjProject = zjProjectRepository.save(zjProject);
        return zjProjectMapper.toDto(zjProject);
    }

    public void clearCheckLog(String zjProjectId) {
        List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProjectId);
        if (zjSpecialtyList!=null) {
            for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                zjCheckLogService.cleanCheckLog(zjSpecialty.getId());
            }
        }
    }

    public void clearCheckLogByChekcStep(String zjProjectId, String chekcStepOne, String chekcStepTwo) {
        List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(zjProjectId);
        if (zjSpecialtyList!=null) {
            for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
                zjCheckLogService.cleanCheckLogByZjSpecialtyId(zjSpecialty.getId(),chekcStepOne, chekcStepTwo);
            }
        }
    }

    @ThsMultiTenancyFilter
    public Page<ZjProjectDTO> getAllProjects(
        String personId,
        String contractNo,
        String projectNo,
        String projectName,
        String projectManagerName,
        String implementUnit,
        String projectState,
        String flowState,
        String busiType,
        String delegateDept,
        Pageable pageable
    ){
        return zjProjectRepository.getAllProjects
            (personId,contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,flowState,busiType,delegateDept,pageable).map(zjProjectMapper::toDto);
    }

    public List<ZjProjectDTO> findAllByParentId(List<String> parentIdList){
        return zjProjectMapper.toDto(zjProjectRepository.findAllByParentIdInOrderByCreatedDateDesc(parentIdList));
    }

    @ThsMultiTenancyFilter
    public List<Map<String,Object>> findAllByParentIds(
                                            List<String> parentIdList,
                                            String contractNo,
                                            String projectNo,
                                            String projectName,
                                            String projectManagerName,
                                            String implementUnit,
                                            String projectState,
                                            String delegateUnit){
        String multiId = MultiTenancyUtils.getMultiTenancyFilterId();
        if(StringUtils.isNullOrEmpty(multiId)) {
            return zjProjectRepository.findAllByParentId(parentIdList,contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,delegateUnit);
        } else {
            return zjProjectRepository.findAllByParentIdByMultiTenancyId(parentIdList,contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,delegateUnit,multiId);
        }
    }

    /**
     * 签发出版环节需要获取项目及关联工程流程拼接
     * @param id
     * @return
     */
    public List getIdeasByPublish(String id) {
        List ideas = new ArrayList();
        List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.findAllByZjPublishId(id);
        String zjProjectId = null;
        for (ZjSpecialtyDTO zjSpecialtyDTO : zjSpecialtyDTOList) {
            zjProjectId = zjSpecialtyDTO.getZjProjectId();
            if (workflowClient.getAuditIdeaList(zjSpecialtyDTO.getId()) != null) {
                ideas.add(workflowClient.getAuditIdeaList(zjSpecialtyDTO.getId()));
            }
        }
        if (!StringUtils.isNullOrEmpty(zjProjectId)) {
            if (workflowClient.getAuditIdeaList(zjProjectId) != null) {
                ideas.add(workflowClient.getAuditIdeaList(zjProjectId));
            }
        }
        return ideas;
    }

    /**
     * 获取项目下所有签发出版流程审批信息
     * @param id
     * @return
     */
    public List getIdeasByProject(String id) {
        List ideas = new ArrayList();
        List<ZjSpecialty> zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(id);
        List<String> list = new ArrayList();
        for (ZjSpecialty zjSpecialty : zjSpecialtyList) {
            if (!StringUtils.isNullOrEmpty(zjSpecialty.getZjPublishId()) && !list.contains(zjSpecialty.getZjPublishId())) {
                list.add(zjSpecialty.getZjPublishId());
            }
        }
        if (list.size() > 0) {
            for (String publishId : list) {
                if (workflowClient.getAuditIdeaList(publishId) != null) {
                    ideas.add(workflowClient.getAuditIdeaList(publishId));
                }
            }
        }
        return ideas;
    }

    /**
     * 工程流程需要获取关联签发出版的流程审批信息
     * @param id
     * @return
     */
    public List getIdeasBySpecialty(String id) {
        List ideas = new ArrayList();
        Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
        ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
        if (!StringUtils.isNullOrEmpty(zjSpecialtyDTO.getZjPublishId())) {
            if (workflowClient.getAuditIdeaList(zjSpecialtyDTO.getZjPublishId()) != null) {
                ideas.add(workflowClient.getAuditIdeaList(zjSpecialtyDTO.getZjPublishId()));
            }
        }
        return ideas;
    }

    public List<ZjProjectDTO> findAllByParentIdAndProjectState(List<String> parentIdList,String projectState){
        return zjProjectMapper.toDto(zjProjectRepository.findAllByParentIdInAndProjectStateOrderByCreatedDateDesc(parentIdList,projectState));
    }

//    public Page<ZjProjectDTO> findAllProjectArchiveNO(String projectState,Pageable pageable){
//        return zjProjectRepository.findAllProjectArchiveNO(projectState,pageable).map(zjProjectMapper::toDto);
//    }
//
    @ThsMultiTenancyFilter
    public Page<Map<String,Object>> findAllProjectArchiveOK(String projectState,
                                                      String contractNo,
                                                      String projectNo,
                                                      String projectName,
                                                      String projectManagerName,
                                                      String implementUnit,
                                                      String implementUnitEq,
                                                      String busiType,
                                                      String delegateUnit,
                                                      Pageable pageable){
        String multiId = MultiTenancyUtils.getMultiTenancyFilterId();
        if (StringUtils.isNullOrEmpty(multiId)) {
            return zjProjectRepository.findAllProjectArchiveOK(projectState,contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,busiType,delegateUnit,pageable);
        } else {
            return zjProjectRepository.findAllProjectArchiveOKByMultiTenancyId(projectState,contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,busiType,delegateUnit,multiId,pageable);
        }
//        return zjProjectRepository.findAllProjectArchiveOK(projectState,pageable).map(zjProjectMapper::toDto);
//        return zjProjectRepository.findAllProjectArchiveOK(projectState,contractNo,projectNo,projectName,projectManagerName,implementUnit,busiType,delegateUnit,pageable).map(zjProjectMapper::toDto);
    }

    public Page<ZjProjectDTO> findAllByParentIdIsNullAndBusiTypeNot(String busiType, Pageable pageable){
        return zjProjectRepository.findAllByParentIdIsNullAndBusiTypeNot(busiType,pageable).map(zjProjectMapper::toDto);
    }

    public void delAllByParentId(String parentId){
        zjProjectRepository.delAllByParentId(parentId);
    }

    @ThsMultiTenancyFilter
    public Page<Map<String,Object>> findAllNormal(
                                            String contractNo,
                                            String projectNo,
                                            String projectName,
                                            String projectManagerName,
                                            String implementUnit,
                                            String implementUnitEq,
                                            String projectState,
                                            String busiType,
                                            String delegateUnit,
                                            String flowState,
                                            Pageable pageable,
                                            String isGood,
                                            String isCold,
                                            String attentionProjectType,
                                            String projectNameEqu) {
        String multiId = MultiTenancyUtils.getMultiTenancyFilterId();
        if(StringUtils.isNullOrEmpty(multiId)) {
            return zjProjectRepository.findAllNormal
                (contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,projectState,busiType,delegateUnit,flowState,pageable,isGood,isCold,attentionProjectType, projectNameEqu);
        } else {
            return zjProjectRepository.findAllNormalByMultiTenancyId
                (contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,projectState,busiType,delegateUnit,flowState,multiId,pageable,isGood,isCold,attentionProjectType, projectNameEqu);
        }
    }

    public Map<String, Object> revoke(String id ,String type) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(!StringUtils.isNullOrEmpty(type)){
            if("项目登记".equals(type)){
                Optional<ZjProjectDTO> zjProjectDTOOptional = this.findOne(id);
                ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_XMDJ);
                zjProjectDTO.setProjectState(Constants.ZJ_PROJECT_STATE_ZC);
                this.updateProject(zjProjectDTO);
            }else if("项目策划".equals(type)){
                Optional<ZjProjectDTO> zjProjectDTOOptional = this.findOne(id);
                ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_XMCH);
                this.updateProject(zjProjectDTO);
            }else if("策划审核".equals(type)){
                Optional<ZjProjectDTO> zjProjectDTOOptional = this.findOne(id);
                ZjProjectDTO zjProjectDTO = zjProjectDTOOptional.get();
                zjProjectDTO.setFlowState(Constants.ZJ_FLOW_STATE_CHSH);
                this.updateProject(zjProjectDTO);
            }else if("自校".equals(type)){
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJ);
                zjSpecialtyService.updateSpecialty(zjSpecialtyDTO);
            }else if("自校汇总".equals(type)){
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_ZJHZ);
                zjSpecialtyService.updateSpecialty(zjSpecialtyDTO);
            }else if("二级审核".equals(type)){
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_EJSH);
                zjSpecialtyService.updateSpecialty(zjSpecialtyDTO);
            }else if("三级审核".equals(type)){
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_SJSH);
                zjSpecialtyService.updateSpecialty(zjSpecialtyDTO);
            }else if("项目汇总".equals(type)){
                Optional<ZjSpecialtyDTO> zjSpecialtyDTOOptional = zjSpecialtyService.findOne(id);
                ZjSpecialtyDTO zjSpecialtyDTO = zjSpecialtyDTOOptional.get();
                zjSpecialtyDTO.setState(Constants.ZJ_SPECIALTY_FLOW_STATE_XMHZ);
                zjSpecialtyService.updateSpecialty(zjSpecialtyDTO);
            }else if("发起".equals(type)){
                Optional<ZjPublishDTO> zjPublishDTOOptional = zjPublishService.findOne(id);
                ZjPublishDTO zjPublishDTO = zjPublishDTOOptional.get();
                zjPublishDTO.setFlowState(null);
                zjPublishService.updateSpecialtyState(id, Constants.ZJ_SPECIALTY_FLOW_STATE_XMHZ);
                zjPublishService.save(zjPublishDTO);
            }else if("签发".equals(type)){
                Optional<ZjPublishDTO> zjPublishDTOOptional = zjPublishService.findOne(id);
                ZjPublishDTO zjPublishDTO = zjPublishDTOOptional.get();
                zjPublishDTO.setFlowState(Constants.ZJ_SPECIALTY_FLOW_STATE_QF);
                zjPublishService.updateSpecialtyState(id, Constants.ZJ_SPECIALTY_FLOW_STATE_QF);
                zjPublishService.save(zjPublishDTO);
            }else if("出版".equals(type)){
                Optional<ZjPublishDTO> zjPublishDTOOptional = zjPublishService.findOne(id);
                ZjPublishDTO zjPublishDTO = zjPublishDTOOptional.get();
                zjPublishDTO.setFlowState(Constants.ZJ_SPECIALTY_FLOW_STATE_CB);
                zjPublishService.updateSpecialtyState(id, Constants.ZJ_SPECIALTY_FLOW_STATE_CB);
                zjPublishService.save(zjPublishDTO);
            }else if("出版完成".equals(type)){
                Optional<ZjPublishDTO> zjPublishDTOOptional = zjPublishService.findOne(id);
                ZjPublishDTO zjPublishDTO = zjPublishDTOOptional.get();
                zjPublishDTO.setFlowState(Constants.ZJ_SPECIALTY_FLOW_STATE_CBWC);
                zjPublishService.updateSpecialtyState(id, Constants.ZJ_SPECIALTY_FLOW_STATE_CBWC);
                zjPublishService.save(zjPublishDTO);
            }else if("归档".equals(type)){

            }
        }
        result.put("success", false);
        result.put("result", "");
        result.put("message", "撤回成功");
        return result;
    }

    @ThsMultiTenancyFilter
    public Page<Map<String,Object>> findAllProcess(String contractNo,
                                                   String projectNo,
                                                   String projectName,
                                                   String projectManagerName,
                                                   String implementUnit,
                                                   String implementUnitEq,
                                                   String projectState,
                                                   String delegateUnit,
                                                   String busiType,
                                                   Pageable pageable,
                                                   String isGood,
                                                   String isCold,
                                                   String attentionProjectType,
                                                   String projectNameEqu) {
        String multiId = MultiTenancyUtils.getMultiTenancyFilterId();
        if(StringUtils.isNullOrEmpty(multiId)) {
            Page<Map<String,Object>> pages = zjProjectRepository.findAllProcess(
                contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,projectState,delegateUnit,busiType,pageable,isGood,isCold,attentionProjectType,projectNameEqu
            );
            return pages;
        } else {
            return zjProjectRepository.findAllProcessByMultiTenancyId(
                contractNo,projectNo,projectName,projectManagerName,implementUnit,implementUnitEq,projectState,delegateUnit,busiType,multiId,pageable,isGood,isCold,attentionProjectType,projectNameEqu
            );
        }
    }

    public Map<String,Object> getAllWithStepInXmchOrChsh(String delegateUnit,
                                                         String projectId,
                                                         String stepName,
                                                         String dicts,
                                                         String roleName1,
                                                         String roleName2,
                                                         String projectManager) {
        /**
         * 1、获取委托单位（单个）
         * 2、获取团队成员
         * 3、获取工作安排
         * 4、获取审批人
         * 5、获取字典
         * 6、获取签发人
         */
        /**
         * 页面加载耗时长
         * 1、单页面请求多
         * 2、整合页面互相多调用
         */
        Map<String,Object> map = new HashMap<>();

        map.put("wtdwList",cscggClient.getWtdwById(delegateUnit).getBody());

        List<ZjMember> zjMemberList = zjMemberService.findAllByZjProjectId(projectId);
        map.put("memberList",zjMemberList);
        // 处理各个团队成员
        List bzrData = new ArrayList();
        List ejshrData = new ArrayList();
        List sjshrData = new ArrayList();
        List qfrData = new ArrayList();
        String bzrIds = "";
        String ejshrIds = "";
        String sjshrIds = "";
        String qfrIds = "";
        String wxjgNames = "";
        for(ZjMember zjMember : zjMemberList){
            Map<String,String> typeMap = new HashMap<>();
            typeMap.put("id",zjMember.getPersonId());
            typeMap.put("name",zjMember.getPersonName());
            if(zjMember.getType().equals("bzr")){
                bzrData.add(typeMap);
                bzrIds += zjMember.getPersonId()+",";
            }
            else if(zjMember.getType().equals("ejshr")){
                ejshrData.add(typeMap);
                ejshrIds += zjMember.getPersonId()+",";
            }
            else if(zjMember.getType().equals("sjshr")){
                sjshrData.add(typeMap);
                sjshrIds += zjMember.getPersonId()+",";
            }
            else if(zjMember.getType().equals("qfr")){
                qfrData.add(typeMap);
                qfrIds += zjMember.getPersonId()+",";
            }
            else if(zjMember.getType().equals("wxjg")){
                wxjgNames = zjMember.getPersonName();
            }
        }
        map.put("bzrData",bzrData);
        map.put("ejshrData",ejshrData);
        map.put("sjshrData",sjshrData);
        map.put("qfrData",qfrData);
        map.put("bzrIds",!StringUtils.isNullOrEmpty(bzrIds)&&bzrIds.endsWith(",")?bzrIds.substring(0,bzrIds.length()-1):"");
        map.put("ejshrIds",!StringUtils.isNullOrEmpty(ejshrIds)&&ejshrIds.endsWith(",")?ejshrIds.substring(0,ejshrIds.length()-1):"");
        map.put("sjshrIds",!StringUtils.isNullOrEmpty(sjshrIds)&&sjshrIds.endsWith(",")?sjshrIds.substring(0,sjshrIds.length()-1):"");
        map.put("qfrIds",!StringUtils.isNullOrEmpty(qfrIds)&&qfrIds.endsWith(",")?qfrIds.substring(0,qfrIds.length()-1):"");
        map.put("wxjgNames",wxjgNames);

        map.put("specialtyList",zjSpecialtyService.findAllByZjProjectId(projectId));

        Map<String,String> dictValues = new HashMap<>();
        Map<String,String> dictDetailValues = new HashMap<>();
        String sysDictIds = "";
        dictValues.put("dictNo.in",dicts);
        List<Map<String,Object>> dictList =  thsadminClient.getDicts(dictValues).getBody();
        map.put("dictList",dictList);
        for(Map<String,Object> map1: dictList){
            sysDictIds += map1.get("id").toString()+",";
        }
        if(!StringUtils.isNullOrEmpty(sysDictIds) && sysDictIds.endsWith(",")){
            dictDetailValues.put("sysDictId.in",sysDictIds.substring(0,sysDictIds.length()-1));
            List<Map<String,Object>> dictDetailList =  thsadminClient.getDictDetails(dictDetailValues).getBody();
            // 获取字典移植到公共方法
            // 后续需要把组装字典（树）移植到这里
            map.put("dictDetailList",dictDetailList);
        }

        Map<String,String> projectManagerMap = thsadminClient.getRyById(projectManager).getBody();
        if(!projectManagerMap.isEmpty()&& !StringUtils.isNullOrEmpty(projectManagerMap.get("multiTenancyId"))){
            String mul = projectManagerMap.get("multiTenancyId").substring(0,5);
            if(stepName.equals("xmch")){
                // 获取当前登陆人的multiTenancyId
                map.put("auditerList",thsuaaClient.findListByMultiTenancyIdAndRoleName(mul,roleName1).getBody());
            }
            map.put("qfrList",thsuaaClient.findListByMultiTenancyIdAndRoleName(mul,roleName2));
        }
        return map;
    }


}
