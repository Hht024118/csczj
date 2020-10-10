package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.domain.ZjPublish;
import com.thsware.framework.repository.ZjProjectRepository;
import com.thsware.framework.service.*;
import com.thsware.framework.service.dto.*;
import com.thsware.framework.service.mapper.ZjProjectMapper;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.ResponseUtil;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST controller for managing ZjProject.
 */
@RestController
@RequestMapping("/api")
public class  ZjProjectResource {

    private final Logger log = LoggerFactory.getLogger(ZjProjectResource.class);

    private static final String ENTITY_NAME = "zjProject";

    private final ZjProjectService zjProjectService;

    private final ZjProjectQueryService zjProjectQueryService;

    private final ZjProjectRepository zjProjectRepository;

    private final ZjProjectMapper zjProjectMapper;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjPublishService zjPublishService;

    public ZjProjectResource(ZjProjectService zjProjectService, ZjProjectQueryService zjProjectQueryService, ZjProjectRepository zjProjectRepository, ZjProjectMapper zjProjectMapper) {
        this.zjProjectService = zjProjectService;
        this.zjProjectQueryService = zjProjectQueryService;
        this.zjProjectRepository = zjProjectRepository;
        this.zjProjectMapper = zjProjectMapper;
    }

    /**
     * POST  /zj-projects : Create a new zjProject.
     *
     * @param zjProjectDTO the zjProjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjProjectDTO, or with status 400 (Bad Request) if the zjProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-projects")
    @Timed
    public ResponseEntity<ZjProjectDTO> createZjProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to save ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zjProjectDTO.setProjectNo(zjProjectService.createProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getImplementUnit()));
        ZjProjectDTO result = zjProjectService.save(zjProjectDTO);
        return ResponseEntity.created(new URI("/api/zj-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * 添加全过程项目
     * @param zjProjectDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/zj-projects/allProcess")
    @Timed
    public ResponseEntity<ZjProjectDTO> createAllProcessProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to save ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zjProjectDTO.setProjectNo(zjProjectService.createProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getImplementUnit()));
        ZjProjectDTO result = zjProjectService.saveAllProcessProject(zjProjectDTO);
        return ResponseEntity.created(new URI("/api/zj-projects/allProcess" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * Post添加子项目
     * @param zjProjectDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/zj-projects/childProject")
    @Timed
    public ResponseEntity<ZjProjectDTO> createChildProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to save ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zjProjectDTO.setProjectNo(zjProjectService.createZxmProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getParentId()));
        ZjProjectDTO result = zjProjectService.saveChildProject(zjProjectDTO);
        return ResponseEntity.created(new URI("/api/zj-projects/childProject" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-projects : Updates an existing zjProject.
     *
     * @param zjProjectDTO the zjProjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjProjectDTO,
     * or with status 400 (Bad Request) if the zjProjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjProjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-projects")
    @Timed
    public ResponseEntity<ZjProjectDTO> updateZjProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(zjProjectDTO.getProjectNo().length() < 10){
            zjProjectDTO.setProjectNo(zjProjectService.createProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getImplementUnit()));
        }
        ZjProjectDTO result = zjProjectService.save(zjProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * Update修改全过程项目数据
     * @param zjProjectDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/zj-projects/allProcess")
    @Timed
    public ResponseEntity<ZjProjectDTO> updateAllProcessProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(zjProjectDTO.getProjectNo().length() < 10){
            zjProjectDTO.setProjectNo(zjProjectService.createProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getImplementUnit()));
        }
        ZjProjectDTO result = zjProjectService.saveAllProcessProject(zjProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * Update修改子项目数据
     * @param zjProjectDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/zj-projects/childProject")
    @Timed
    public ResponseEntity<ZjProjectDTO> updateChildProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(zjProjectDTO.getProjectNo().length() < 10){
            zjProjectDTO.setProjectNo(zjProjectService.createZxmProjectNo(zjProjectDTO.getProjectNo(),zjProjectDTO.getParentId()));
        }
        ZjProjectDTO result = zjProjectService.saveChildProject(zjProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectDTO.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /zj-projects/updateProject :getGGthById Updates an existing zjProject.
     *
     * @param zjProjectDTO the zjProjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjProjectDTO,
     * or with status 400 (Bad Request) if the zjProjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjProjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-projects/updateProject")
    @Timed
    public ResponseEntity<ZjProjectDTO> contractUpdateZjProject(@Valid @RequestBody ZjProjectDTO zjProjectDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProject : {}", zjProjectDTO);
        if (zjProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjProjectDTO result = zjProjectService.updateProject(zjProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-projects : get all the zjProjects.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjProjects in body
     */
    @GetMapping("/zj-projects")
    @Timed
    public ResponseEntity<List<ZjProjectDTO>> getAllZjProjects(ZjProjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjProjects by criteria: {}", criteria);
        if(criteria.getProjectState() != null ){
            if(criteria.getProjectState().getContains().equals("1")){
                criteria.setProjectState((new StringFilter().setContains("待归档")));
            }else if(criteria.getProjectState().getContains().equals("0")){
                criteria.setProjectState((new StringFilter().setContains("已归档")));
            }
        }
        Page<ZjProjectDTO> page = zjProjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET查询所有正常项目
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping("/zj-projects/alls")
    @Timed
    public ResponseEntity<List<Map<String, Object>>> getAllNormalZjProject(
        @RequestParam(value = "contractNo.contains", required = false) String contractNo,
        @RequestParam(value = "projectNo.contains", required = false) String projectNo,
        @RequestParam(value = "projectName.contains", required = false) String projectName,
        @RequestParam(value = "projectManagerName.contains", required = false) String projectManagerName,
        @RequestParam(value = "implementUnit.contains", required = false) String implementUnit,
        @RequestParam(value = "projectState.contains", required = false) String projectState,
        @RequestParam(value = "busiType.contains", required = false) String busiType,
        @RequestParam(value = "delegateUnit.contains", required = false) String delegateUnit,
        @RequestParam(value = "flowState.contains", required = false) String flowState
        ,Pageable pageable) {
        if (contractNo == null) {
            contractNo = "";
        }
        if (projectNo == null) {
            projectNo = "";
        }
        if (projectName == null) {
            projectName = "";
        }
        if (projectManagerName == null) {
            projectManagerName = "";
        }
        if (implementUnit == null) {
            implementUnit = "";
        }
        if (projectState == null) {
            projectState = "";
        }
        if (busiType == null) {
            busiType = "";
        }
        if (delegateUnit == null) {
            delegateUnit = "";
        }
        if (flowState == null) {
            flowState = "";
        }
        Page<Map<String,Object>> page = zjProjectService.findAllNormal(contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,busiType,delegateUnit,flowState,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects/alls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET查询所有全过程项目及子项目
     */
    @GetMapping("/zj-projects/allProcess")
    @Timed
    public ResponseEntity<List<Map<String, Object>>> getAllProject(
        @RequestParam(value = "contractNo.contains", required = false) String contractNo,
        @RequestParam(value = "projectNo.contains", required = false) String projectNo,
        @RequestParam(value = "projectName.contains", required = false) String projectName,
        @RequestParam(value = "projectManagerName.contains", required = false) String projectManagerName,
        @RequestParam(value = "implementUnit.contains", required = false) String implementUnit,
        @RequestParam(value = "projectState.contains", required = false) String projectState,
        @RequestParam(value = "delegateUnit.contains", required = false) String delegateUnit,
        @RequestParam(value = "busiType.contains", required = false) String busiType
        , Pageable pageable) {
        if (contractNo == null) {
            contractNo = "";
        }
        if (projectNo == null) {
            projectNo = "";
        }
        if (projectName == null) {
            projectName = "";
        }
        if (projectManagerName == null) {
            projectManagerName = "";
        }
        if (implementUnit == null) {
            implementUnit = "";
        }
        if (projectState == null) {
            projectState = "";
        }
        if (delegateUnit == null) {
            delegateUnit = "";
        }
        if (busiType == null) {
            busiType = "";
        }
        Page<Map<String,Object>> page = zjProjectService.findAllProcess(contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,delegateUnit,busiType,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects/allProcess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/zj-projects/allChildProject")
    @Timed
    public List<Map<String, Object>> allChildProject(
        @RequestParam(value = "contractNo.contains", required = false) String contractNo,
        @RequestParam(value = "projectNo.contains", required = false) String projectNo,
        @RequestParam(value = "projectName.contains", required = false) String projectName,
        @RequestParam(value = "projectManagerName.contains", required = false) String projectManagerName,
        @RequestParam(value = "implementUnit.contains", required = false) String implementUnit,
        @RequestParam(value = "projectState.contains", required = false) String projectState,
        @RequestParam(value = "delegateUnit.contains", required = false) String delegateUnit,
        @RequestParam(value = "parentId.in", required = false) List parentIdList
    ) {
        if (contractNo == null) {
            contractNo = "";
        }
        if (projectNo == null) {
            projectNo = "";
        }
        if (projectName == null) {
            projectName = "";
        }
        if (projectManagerName == null) {
            projectManagerName = "";
        }
        if (implementUnit == null) {
            implementUnit = "";
        }
        if (projectState == null) {
            projectState = "";
        }
        if (delegateUnit == null) {
            delegateUnit = "";
        }
        List<Map<String,Object>> zjProjectList = zjProjectService.findAllByParentIds(parentIdList,contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,delegateUnit);
        return  zjProjectList;
    }


    /**
     * GET  /zj-projects/:id : get the "id" zjProject.
     *
     * @param id the id of the zjProjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjProjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-projects/{id}")
    @Timed
    public ResponseEntity<ZjProjectDTO> getZjProject(@PathVariable String id) {
        log.debug("REST request to get ZjProject : {}", id);
        Optional<ZjProjectDTO> zjProjectDTO = zjProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjProjectDTO);
    }

    /**
     * DELETE  /zj-projects/:id : delete the "id" zjProject.
     *
     * @param ids the id of the zjProjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-projects/allProcess/{ids}")
    @Timed
    public ResponseEntity<Void> deleteAllProcess(@PathVariable String ids) {
        log.debug("REST request to delete ZjProject : {}", ids);
        if (StringUtils.isNotEmpty(ids)) {
            String[] idList = ids.split(",");
            for (String id : idList) {
                zjProjectService.deleteChild(id);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, ids.toString())).build();
    }

    /**
     * DELETE  /zj-projects/:id : delete the "id" zjProject.
     *
     * @param ids the id of the zjProjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-projects/{ids}")
    @Timed
    public ResponseEntity<Void> deleteZjProject(@PathVariable String ids) {
        log.debug("REST request to delete ZjProject : {}", ids);
        if (StringUtils.isNotEmpty(ids)) {
            String[] idList = ids.split(",");
            for (String id : idList) {
                zjProjectService.delete(id);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, ids.toString())).build();
    }
    /**
     * GET 根据项目id,获取项目和归档的基本信息
     *
     * @param id
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/zj-projects/getArchiveById/{id}")
    @Timed
    public Map<String, Object> getArchiveById(@PathVariable String id) {
        return zjProjectRepository.getArchiveById(id);
    }

    /**
     * GET  /zj-projects/xmgz : get all the zjProjects.
     */
    @GetMapping("/zj-projects/xmgz")
    @Timed
    public ResponseEntity<List<ZjProjectDTO>> getAllXmgzZjProjects(ZjProjectCriteria criteria, Pageable pageable) {
        Page<ZjProjectDTO> page = zjProjectQueryService.findByCriteria(criteria, pageable);
        List<String> parentIdList = new ArrayList<>();
        for (ZjProjectDTO zjProjectDTO: page) {
            parentIdList.add(zjProjectDTO.getId());
        }
        List<ZjProjectDTO> zjProjectList = zjProjectService.findAllByParentId(parentIdList);
        for (ZjProjectDTO zjProjectDTO: page) {
            List<ZjProjectDTO> childList = new ArrayList<>();
            for (ZjProjectDTO zjProjectChild:zjProjectList) {
                if (zjProjectChild.getParentId().equals(zjProjectDTO.getId())){
                    childList.add(zjProjectChild);
                }
            }
            zjProjectDTO.setZjProjectDTOList(childList);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects/xmgz");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-projects/:id : get the "id" zjProject.
     */
    @GetMapping("/zj-projects/xmgz/{id}")
    @Timed
    public ResponseEntity<ZjProjectDTO> getXmgzZjProject(@PathVariable String id) {
        return getZjProject(id);
    }

    /**
     * GET  /zj-projects/xmxgbb : get all the zjProjects.
     */
    @GetMapping("/zj-projects/xmxgbb")
    @Timed
    public ResponseEntity<List<ZjProjectDTO>> getAllXmxgbbZjProjects(ZjProjectCriteria criteria, Pageable pageable) {
        return getAllXmgzZjProjects(criteria,pageable);
    }

    /**
     * GET项目登记归档
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping("/zj-projects/xmgd")
    @Timed
    public ResponseEntity<List> getAllXmgdZjProjects(ZjProjectCriteria criteria,@RequestParam(value = "personId", required = false) String personId,
                                                                   @RequestParam(value = "personName", required = false) String personName, Pageable pageable) {
        Page<ZjProjectDTO> page = null;
        Page<Map<String,Object>> page2 = null;
        List<String> parentIdList = new ArrayList<>();
        if(criteria.getProjectState() != null ){
            if(criteria.getProjectState().getContains().equals("1")){
                criteria.setProjectState((new StringFilter().setContains("待归档")));
                page = zjProjectQueryService.findByCriteria(criteria, pageable);
                for (ZjProjectDTO zjProjectDTO: page) {
                    parentIdList.add(zjProjectDTO.getId());
                }
                List<ZjProjectDTO> zjProjectList = zjProjectService.findAllByParentIdAndProjectState(parentIdList,criteria.getProjectState().getContains());
                for (ZjProjectDTO zjProjectDTO: page) {
                    List<ZjProjectDTO> childList = new ArrayList<>();
                    for (ZjProjectDTO zjProjectChild:zjProjectList) {
                        if (zjProjectChild.getParentId().equals(zjProjectDTO.getId())){
                            childList.add(zjProjectChild);
                        }
                    }
                    zjProjectDTO.setZjProjectDTOList(childList);
                }
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects/xmgz");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }else if(criteria.getProjectState().getContains().equals("0")){
                criteria.setProjectState((new StringFilter().setContains("已归档")));
                page2 = zjProjectService.findAllProjectArchiveOK(
                    criteria.getProjectState().getContains(),criteria.getContractNo().getContains(),criteria.getProjectNo().getContains(),
                    criteria.getProjectName().getContains(),criteria.getProjectManagerName().getContains(),criteria.getImplementUnit().getContains(),
                    criteria.getBusiType().getContains(),criteria.getDelegateUnit().getContains(),pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page2, "/api/zj-projects/xmgz");
                return new ResponseEntity<>(page2.getContent(), headers, HttpStatus.OK);
//                for (Map<String,Object> zjProjectMap: page2) {
//                    parentIdList.add(zjProjectMap.get("id").toString());
//                }
//                List<ZjProjectDTO> zjProjectList = zjProjectService.findAllByParentIdAndProjectState(parentIdList,criteria.getProjectState().getContains());
//                for (Map<String,Object> zjProjectMap: page2.getContent()) {
//                    List<ZjProjectDTO> childList = new ArrayList<>();
//                    for (ZjProjectDTO zjProjectChild:zjProjectList) {
//                        if (zjProjectChild.getParentId().equals(zjProjectMap.get("id").toString())){
//                            childList.add(zjProjectChild);
//                        }
//                    }
//                    zjProjectMap.setZjProjectDTOList(childList);
//                }
            }
        }
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects/xmgz");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return null;
    }

    /**
     * GET  /zj-projects/in-actors : get all the zjProjects.
     */
    @GetMapping("/zj-projects/in-actors")
    @Timed
    public ResponseEntity<List<ZjProjectDTO>> getZjProjects(
        @RequestParam(value = "contractNo", required = false) String contractNo,
        @RequestParam(value = "projectNo", required = false) String projectNo,
        @RequestParam(value = "projectName", required = false) String projectName,
        @RequestParam(value = "projectManagerName", required = false) String projectManagerName,
        @RequestParam(value = "implementUnit", required = false) String implementUnit,
        @RequestParam(value = "projectState", required = false) String projectState,
        @RequestParam(value = "flowState", required = false) String flowState,
        @RequestParam(value = "busiType", required = false) String busiType,
        @RequestParam(value = "delegateDept", required = false) String delegateDept,
        @RequestParam(value = "personId", required = false) String personId,
        @RequestParam(value = "personName", required = false) String personName,
        Pageable pageable) {
        if (StringUtils.isNotEmpty(personName) && personName.equals("admin")) {
            personId = null;
        }
        Page<ZjProjectDTO> page = zjProjectService.getAllProjects(personId,contractNo,projectNo,projectName,projectManagerName,implementUnit,projectState,flowState,busiType,delegateDept,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/zj-projects/in-actors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/zj-projects/xmywbg")
    @Timed
    public ResponseEntity<List<ZjProjectDTO>> getAllXmywbgZjProjects(ZjProjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjProjects by criteria: {}", criteria);
        Page<ZjProjectDTO> page = zjProjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/zj-projects/childProject/{id}")
    @Timed
    public List<ZjProjectDTO> findAllChild(@PathVariable String id){
        List<String> parentIdList = new ArrayList<>();
        parentIdList.add(id);
        return zjProjectService.findAllByParentId(parentIdList);
    }


    /**
     * GET  /zj-projects/:id : get the "id" zjProject.
     */
    @GetMapping("/zj-projects/get-ideas-by-publish/{id}")
    @Timed
    public List getIdeasByPublish(@PathVariable String id) {
        return zjProjectService.getIdeasByPublish(id);
}

    /**
     * GET  /zj-projects/:id : get the "id" zjProject.
     */
    @GetMapping("/zj-projects/get-ideas-by-project/{id}")
    @Timed
    public List getIdeasByProject(@PathVariable String id) {
        return zjProjectService.getIdeasByProject(id);
    }

    /**
     * GET  /zj-projects/:id : get the "id" zjProject.
     */
    @GetMapping("/zj-projects/get-ideas-by-specialty/{id}")
    @Timed
    public List getIdeasBySpecialty(@PathVariable String id) {
        return zjProjectService.getIdeasBySpecialty(id);
    }

    /**
     * POST  /zj-projects : Create a new zjProject.
     */
    @PostMapping("/zj-projects/revoke-zjwdxm/{id}/{type}")
    @Timed
    public Map<String, Object> revokeZJWDXM(@PathVariable String id, @PathVariable String type) throws URISyntaxException {
        Map<String, Object> result = zjProjectService.revoke(id, type);
        return result;
    }

    /**
     * 一般项目-步骤条页面-初始化
     * @param recordId
     * @param extField2
     * @param comeInType
     * @param extField1
     * @param stepName
     * @return
     */
    @GetMapping("/zj-projects/get-all-about-project")
    @Timed
    public Map<String,Object> getAllAboutProject(
        @RequestParam(value = "recordId", required = false) String recordId,//projectId或者待办businessId
        @RequestParam(value = "extField2", required = false) String extField2,//待办projectId
        @RequestParam(value = "comeInType", required = false) String comeInType,// 待办or项目列表进入
        @RequestParam(value = "extField1", required = false) String extField1,// 待办-流程类型
        @RequestParam(value = "stepName", required = false) String stepName//待办步骤
    ) {
        /**
         * 根据来源（待办、列表）获取项目id
         * 签发出版需要提前获取工作安排ids，进入页面后用来初始化文件
         */
        Map<String,Object> map = new HashMap<>();
        String zjProjectId = null;
        if(comeInType.equals("notice")){
            if(StringUtils.isEmpty(extField1) || extField1.equals("project")){
                zjProjectId = recordId;
            }
            else if(extField1.equals("specialty")){
                zjProjectId = extField2;
            }
            else if(extField1.equals("publish")){
                zjProjectId = extField2;
                String specialtyIds = "";
                List<ZjSpecialtyDTO> zjSpecialtyDTOList = zjSpecialtyService.findAllByZjPublishId(recordId);
                for(ZjSpecialtyDTO zjSpecialtyDTO : zjSpecialtyDTOList){
                    specialtyIds += zjSpecialtyDTO.getId()+",";
                }
                map.put("specialtyIds",StringUtils.isNotEmpty(specialtyIds)&&specialtyIds.endsWith(",")
                    ?specialtyIds.substring(0,specialtyIds.length()-1):"");
            }
        }
        else if(comeInType.equals("edit")){
            zjProjectId = recordId;
        }
        /**
         * 1、获取项目信息
         * 2、拼接此工程的签发出版流程审核意见
         * 3、获取工程信息
         * 4、获取项目+工作安排的审批意见
         * 5、获取签发出版信息
         * 6、获取工程+签发出版审批意见
         */
        Optional<ZjProjectDTO> zjProjectDTO = zjProjectService.findOne(zjProjectId);
        map.put("zjProjectDTO",zjProjectDTO);
        List list = new ArrayList();
        if(comeInType.equals("notice") && extField1.equals("specialty") && !stepName.equals("归档")) {
            list = zjProjectService.getIdeasBySpecialty(recordId);
            Optional<ZjSpecialtyDTO> zjSpecialtyDTO = zjSpecialtyService.findOne(recordId);
            map.put("ideaList", list);
            map.put("zjSpecialtyDTO", zjSpecialtyDTO);
        }
        else if(comeInType.equals("notice") && extField1.equals("publish")){
            list = zjProjectService.getIdeasByPublish(recordId);
            Optional<ZjPublishDTO> zjPublishDTO = zjPublishService.findOne(recordId);
            map.put("ideaList",list);
            map.put("zjPublishDTO",zjPublishDTO);
        }
        else{
            list = zjProjectService.getIdeasByProject(recordId);
            map.put("ideaList",list);
        }
        return map;
    }

    @GetMapping("/zj-projects/get-all-with-step-in-xmch-or-chsh")
    @Timed
    public Map<String,Object> getAllWithStepInXmchOrChsh(
        @RequestParam(value = "delegateUnit", required = false) String delegateUnit,// 委托单位id
        @RequestParam(value = "projectId", required = false) String projectId,// 项目id
        @RequestParam(value = "stepName", required = false) String stepName,// 步骤名称
        @RequestParam(value = "dicts", required = false) String dicts,// 所有字典名称
        @RequestParam(value = "roleName1", required = false) String roleName1,// 角色名称
        @RequestParam(value = "roleName2", required = false) String roleName2,// 角色名称
        @RequestParam(value = "projectManager", required = false) String projectManager// 项目负责人id
    ) {
        Map<String,Object> map = this.zjProjectService.getAllWithStepInXmchOrChsh(delegateUnit,projectId,stepName,dicts,roleName1,roleName2,projectManager);
        return map;
    }
}
