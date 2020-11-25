package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.service.dto.ZjProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Spring Data  repository for the ZjProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjProjectRepository extends JpaRepository<ZjProject, String>, JpaSpecificationExecutor<ZjProject> {

    Page<ZjProject> findAllByZjProjectArchive_IsCompleteAndContractNoAndProjectNoAndProjectNameAndProjectManagerAndImplementUnitAndFlowStateAndBusiTypeAndDelegateDept
        (@Size(max = 1) String zjProjectArchive_isComplete, @Size(max = 50) String contractNo, @Size(max = 50) String projectNo, @Size(max = 200) String projectName, @Size(max = 50) String projectManager, @Size(max = 50) String implementUnit, @Size(max = 50) String flowState, @Size(max = 50) String busiType, @Size(max = 50) String delegateDept, Pageable pageable);

    Page<ZjProject> findAllByZjProjectArchive_IsCompleteIsNullAndContractNoAndProjectNoAndProjectNameAndProjectManagerAndImplementUnitAndFlowStateAndBusiTypeAndDelegateDept
        (@Size(max = 50) String contractNo, @Size(max = 50) String projectNo, @Size(max = 200) String projectName, @Size(max = 50) String projectManager, @Size(max = 50) String implementUnit, @Size(max = 50) String flowState, @Size(max = 50) String busiType, @Size(max = 50) String delegateDept, Pageable pageable);

    Page<ZjProjectDTO> findAllByParentIdIsNullAndFlowStateAndBusiTypeNot
        (@Size(max = 50) String busiType, @Size(max = 50) String flowState, Pageable pageable);

    @Query(value = "SELECT zp.id as id,zp.project_no as projectNo,zp.project_name as projectName,zp.project_manager as projectManager,zp.delegate_unit as delegateUnit,zp.busi_type as busiType," +
        "zp.project_type as projectType,zp.project_industry as projectIndustry,zp.specialty,zpa.archive_no as archiveNo,zpa.archived_by as archivedBy,zpa.archive_date as archiveDate," +
        "zpa.remark as remark,zp.report_conclusion as reportConclusion," +
        "zpa.paper_archive_by as paperArchiveBy,zpa.paper_archive_date as paperArchiveDate" +
        " FROM zj_project zp left join zj_project_archive zpa on zp.id = zpa.zj_project_id WHERE zp.id = :id " ,nativeQuery = true)
    Map<String,Object> getArchiveById(@Param("id") String id);

    @Query(value = "SELECT * FROM zj_project WHERE id in (SELECT project_id FROM zj_actors WHERE person_id = :personId) " +
        " and contract_no like %:contractNo% and project_no like %:projectNo% " +
        " and project_name like %:projectName% and project_manager_name like %:projectManagerName% and implement_unit like %:implementUnit%" +
        " and project_state like %:projectState% " +
        " and flow_state like %:flowState% and busi_type like %:busiType% and delegate_dept like %:delegateDept%  " ,
        countQuery = "SELECT count(*) FROM zj_project WHERE id in (SELECT project_id FROM zj_actors WHERE person_id = :personId) " +
        " and contract_no like %:contractNo% and project_no like %:projectNo% " +
        " and project_name like %:projectName% and project_manager_name like %:projectManagerName% and implement_unit like %:implementUnit%" +
        " and project_state like %:projectState% " +
        " and flow_state like %:flowState% and busi_type like %:busiType% and delegate_dept like %:delegateDept%  " ,
        nativeQuery = true)
    Page<ZjProject> getAllProjects
        (@Param("personId") String personId, @Param("contractNo") String contractNo, @Param("projectNo") String projectNo,
         @Param("projectName") String projectName, @Param("projectManagerName") String projectManager, @Param("implementUnit") String implementUnit,
         @Param("projectState") String projectState, @Param("flowState") String flowState, @Param("busiType") String busiType, @Param("delegateDept") String delegateDept,
         Pageable page);


    @Query(value = "SELECT id as id,project_no as projectNo,contract_id as contractId,contract_no as contractNo,contract_name as contractName," +
            " project_name as projectName,register_date as registerDate,publish_date as publishDate,delegate_unit as delegateUnit,delegate_date as delegateDate," +
            " delegate_dept as delegateDept,delegate_linkman as delegateLinkman,required_date as requiredDate,is_government_invest as isGovernmentInvest," +
            " project_type as projectType,project_industry as projectIndustry,project_source as projectSource,busi_type as busiType,specialty as specialty," +
            " project_scale as projectScale,cost_money as costMoney,submit_money as submitMoney,project_manager as projectManager,project_manager_name as projectManagerName," +
            " plan_finish_date as planFinishDate,implement_unit as implementUnit,content_scope as contentScope,is_outsource as isOutsource,outsource_desc as outsourceDesc," +
            " project_progress as projectProgress,project_budget as projectBudget,need_money as needMoney,flow_state as flowState,project_state as projectState,remark as remark," +
            " multi_tenancy_id as multiTenancyId,created_by as createdBy,created_date as createdDate,last_modified_by as lastModifiedBy,last_modified_date as lastModifiedDate," +
            " parent_id as parentId,linkage_branch as linkageBranch,contract_money as contractMoney,report_conclusion as reportConclusion,estimated_income as estimatedIncome,is_ccb_client as isCcbClient," +
            " attention_project_type as attentionProjectType,city_where as cityWhere,total_project_cost as totalProjectCost,is_good as isGood,is_cold as isCold " +
            " FROM zj_project WHERE busi_type <> '全过程造价类'" +
            " and parent_id IS NULL" +
            " and IFNULL(contract_no,0) like %:contractNo%" +
            " and IFNULL(project_no,0) like %:projectNo% " +
            " and IFNULL(project_name,0) like %:projectName%" +
            " and IFNULL(project_manager_name,0) like %:projectManagerName%" +
            " and IFNULL(implement_unit,0) like %:implementUnit%" +
            " and IFNULL(project_state,0) like %:projectState% " +
            " and IFNULL(busi_type,0) like %:busiType%" +
            " and IFNULL(delegate_unit,0) like %:delegateUnit%  " +
            " and IFNULL(flow_state,0) like %:flowState%"+
            " and if(:isGood != '', is_good=:isGood, 1=1)"+
            " and if(:isCold != '', is_cold=:isCold, 1=1)"+
            " and IFNULL(attention_project_type,0) like %:attentionProjectType%",

        countQuery = "SELECT count(*) FROM zj_project WHERE busi_type <> '全过程造价类'" +
            " and parent_id IS NULL" +
            " and IFNULL(contract_no,0) like %:contractNo%" +
            " and IFNULL(project_no,0) like %:projectNo% " +
            " and IFNULL(project_name,0) like %:projectName%" +
            " and IFNULL(project_manager_name,0) like %:projectManagerName%" +
            " and IFNULL(implement_unit,0) like %:implementUnit%" +
            " and IFNULL(project_state,0) like %:projectState% " +
            " and IFNULL(busi_type,0) like %:busiType%" +
            " and IFNULL(delegate_unit,0) like %:delegateUnit%  " +
            " and IFNULL(flow_state,0) like %:flowState%"+
            " and if(:isGood != '', is_good=:isGood, 1=1)"+
            " and if(:isCold != '', is_cold=:isCold, 1=1)"+
            " and IFNULL(attention_project_type,0) like %:attentionProjectType%"
        ,nativeQuery = true)
    Page<Map<String,Object>> findAllNormal(@Param("contractNo") String contractNo,
                                           @Param("projectNo") String projectNo,
                                           @Param("projectName") String projectName,
                                           @Param("projectManagerName") String projectManagerName,
                                           @Param("implementUnit") String implementUnit,
                                           @Param("projectState") String projectState,
                                           @Param("busiType") String busiType,
                                           @Param("delegateUnit") String delegateUnit,
                                           @Param("flowState") String flowState,
                                           Pageable page,
                                           @Param("isGood") String isGood,
                                           @Param("isCold") String isCold,
                                           @Param("attentionProjectType") String attentionProjectType);

    @Query(value = "SELECT id as id,project_no as projectNo,contract_id as contractId,contract_no as contractNo,contract_name as contractName," +
        " project_name as projectName,register_date as registerDate,publish_date as publishDate,delegate_unit as delegateUnit,delegate_date as delegateDate," +
        " delegate_dept as delegateDept,delegate_linkman as delegateLinkman,required_date as requiredDate,is_government_invest as isGovernmentInvest," +
        " project_type as projectType,project_industry as projectIndustry,project_source as projectSource,busi_type as busiType,specialty as specialty," +
        " project_scale as projectScale,cost_money as costMoney,submit_money as submitMoney,project_manager as projectManager,project_manager_name as projectManagerName," +
        " plan_finish_date as planFinishDate,implement_unit as implementUnit,content_scope as contentScope,is_outsource as isOutsource,outsource_desc as outsourceDesc," +
        " project_progress as projectProgress,project_budget as projectBudget,need_money as needMoney,flow_state as flowState,project_state as projectState,remark as remark," +
        " multi_tenancy_id as multiTenancyId,created_by as createdBy,created_date as createdDate,last_modified_by as lastModifiedBy,last_modified_date as lastModifiedDate," +
        " parent_id as parentId,linkage_branch as linkageBranch,contract_money as contractMoney,report_conclusion as reportConclusion,estimated_income as estimatedIncome,is_ccb_client as isCcbClient," +
        " attention_project_type as attentionProjectType,city_where as cityWhere,total_project_cost as totalProjectCost,is_good as isGood,is_cold as isCold " +
        " FROM zj_project WHERE busi_type <> '全过程造价类'" +
        " and parent_id IS NULL" +
        " and IFNULL(contract_no,0) like %:contractNo%" +
        " and IFNULL(project_no,0) like %:projectNo% " +
        " and IFNULL(project_name,0) like %:projectName%" +
        " and IFNULL(project_manager_name,0) like %:projectManagerName%" +
        " and IFNULL(implement_unit,0) like %:implementUnit%" +
        " and IFNULL(project_state,0) like %:projectState% " +
        " and IFNULL(busi_type,0) like %:busiType%" +
        " and IFNULL(delegate_unit,0) like %:delegateUnit%  " +
        " and IFNULL(flow_state,0) like %:flowState%" +
        " and multi_tenancy_id like :multiId"+
        " and if(:isGood != '', is_good=:isGood, 1=1)"+
        " and if(:isCold != '', is_cold=:isCold, 1=1)"+
        " and IFNULL(attention_project_type,0) like %:attentionProjectType%",
        countQuery = "SELECT count(*) FROM zj_project WHERE busi_type <> '全过程造价类'" +
            " and parent_id IS NULL" +
            " and IFNULL(contract_no,0) like %:contractNo%" +
            " and IFNULL(project_no,0) like %:projectNo% " +
            " and IFNULL(project_name,0) like %:projectName%" +
            " and IFNULL(project_manager_name,0) like %:projectManagerName%" +
            " and IFNULL(implement_unit,0) like %:implementUnit%" +
            " and IFNULL(project_state,0) like %:projectState% " +
            " and IFNULL(busi_type,0) like %:busiType%" +
            " and IFNULL(delegate_unit,0) like %:delegateUnit%  " +
            " and IFNULL(flow_state,0) like %:flowState%" +
            " and multi_tenancy_id like :multiId"+
            " and if(:isGood != '', is_good=:isGood, 1=1)"+
            " and if(:isCold != '', is_cold=:isCold, 1=1)"+
            " and IFNULL(attention_project_type,0) like %:attentionProjectType%"
        ,nativeQuery = true)
    Page<Map<String,Object>> findAllNormalByMultiTenancyId(@Param("contractNo") String contractNo,
                                                           @Param("projectNo") String projectNo,
                                                           @Param("projectName") String projectName,
                                                           @Param("projectManagerName") String projectManagerName,
                                                           @Param("implementUnit") String implementUnit,
                                                           @Param("projectState") String projectState,
                                                           @Param("busiType") String busiType,
                                                           @Param("delegateUnit") String delegateUnit,
                                                           @Param("flowState") String flowState,
                                                           @Param("multiId") String multiId,
                                                           Pageable page,
                                                           @Param("isGood") String isGood,
                                                           @Param("isCold") String isCold,
                                                           @Param("attentionProjectType") String attentionProjectType);


    /**
     * 获取最大项目编号
     * @param projectNo 项目实施单位
     * @return
     */
//    @Query(value = "SELECT max(CONVERT(right(project_no,4),SIGNED)) from zj_project " +
//        "where year(created_date) = year(CURRENT_DATE) and implement_unit = :implementUnit"
//        ,nativeQuery = true)
//    Object getMaxProjectNo(@Param("implementUnit") String implementUnit);
    @Query(value = "SELECT max(CONVERT(right(project_no,5),SIGNED)) from zj_project " +
        "where IFNULL(project_no,0) like %:projectNo% "
        ,nativeQuery = true)
    Object getMaxProjectNo(@Param("projectNo") String projectNo);

    /**
     * 获取最大子项目编号
     * @param parentId
     * @return
     */
    @Query(value = "SELECT max(CONVERT(right(project_no,3),SIGNED)) from zj_project " +
        "where parent_id = :parentId"
        ,nativeQuery = true)
    Object getMaxZxmProjectNo(@Param("parentId") String parentId);

    List<ZjProject> findAllByParentIdInOrderByCreatedDateDesc(List<String> parentIdList);

    @Modifying()
    @Query( value = "Delete from ZjProject WHERE parentId=?1")
    void delAllByParentId(String parentId);

    Page<ZjProject> findAllByParentIdIsNullAndBusiTypeNot(@Size(max = 50) String busiType,Pageable page);

//    @Query(value = "SELECT * FROM zj_project WHERE parent_id IS NULL AND project_state = :projectState",nativeQuery = true)
//    Page<ZjProject> findAllProjectArchiveNO(@Param("projectState") String projectState,Pageable page);
//    select d.parent_id from zj_project d where d.parent_id is not null and d.project_state =:projectState
//    @Query(value = "SELECT c.id as id, " +
//        " c.parentId as parentId, " +
//        " c.projectName as projectName, " +
//        " c.busiType as busiType, " +
//        " c.contractNo as contractNo, " +
//        " c.delegateUnit as delegateUnit, " +
//        " c.projectManagerName as projectManagerName, " +
//        " c.implementUnit as implementUnit, " +
//        " c.planFinishDate as planFinishDate, " +
//        " c.flowState as flowState "+
    @Query(value = "SELECT c.id as id, " +
        " c.parentId as parentId, " +
        " c.projectName as projectName, " +
        " c.busiType as busiType, " +
        " c.contractNo as contractNo, " +
        " c.delegateUnit as delegateUnit, " +
        " c.projectManagerName as projectManagerName, " +
        " c.implementUnit as implementUnit, " +
        " c.planFinishDate as planFinishDate, " +
        " c.projectNo as projectNo, " +
        " c.createdDate as createdDate, " +
        " c.flowState as flowState " +
        " from ( " +
        " select a.id as id, " +
        " a.parent_id as parentId, " +
        " a.project_name as projectName, " +
        " a.busi_type as busiType, " +
        " a.contract_no as contractNo, " +
        " a.delegate_unit as delegateUnit, " +
        " a.project_manager_name as projectManagerName, " +
        " a.implement_unit as implementUnit, " +
        " a.plan_finish_date as planFinishDate," +
        " a.project_no as projectNo, " +
        " a.created_date as createdDate, " +
        " a.flow_state as flowState "+
        " from zj_project a " +
        " where a.id in (select d.parent_id from zj_project d where d.parent_id is not null and d.project_state =:projectState) " +
        " UNION " +
        " select b.id as id, " +
        " b.parent_id as parentId, " +
        " b.project_name as projectName, " +
        " b.busi_type as busiType, " +
        " b.contract_no as contractNo, " +
        " b.delegate_unit as delegateUnit, " +
        " b.project_manager_name as projectManagerName, " +
        " b.implement_unit as implementUnit, " +
        " b.plan_finish_date as planFinishDate, " +
        " b.project_no as projectNo, " +
        " b.created_date as createdDate, " +
        " b.flow_state as flowState " +
        " from zj_project b where b.parent_id is null and b.project_state =:projectState" +
        " ) as c where 1=1 " +
        " AND ifnull( c.contractNo, '' ) LIKE %:contractNo% " +
        " AND ifnull( c.projectNo, '' ) LIKE %:projectNo% " +
        " AND ifnull( c.projectName, '' ) LIKE %:projectName% " +
        " AND ifnull( c.projectManagerName, '' ) LIKE %:projectManagerName% " +
        " AND ifnull( c.implementUnit, '' ) LIKE %:implementUnit% " +
        " AND ifnull( c.busiType, '' ) LIKE %:busiType% " +
        " AND ifnull( c.delegateUnit, '' ) LIKE %:delegateUnit%" +
        " order by c.createdDate desc ",
        countQuery = "SELECT count(c.id) " +
            " from ( " +
            " select a.id as id, " +
            " a.parent_id as parentId, " +
            " a.project_name as projectName, " +
            " a.busi_type as busiType, " +
            " a.contract_no as contractNo, " +
            " a.delegate_unit as delegateUnit, " +
            " a.project_manager_name as projectManagerName, " +
            " a.implement_unit as implementUnit, " +
            " a.plan_finish_date as planFinishDate, " +
            " a.project_no as projectNo, " +
            " a.created_date as createdDate, " +
            " a.flow_state as flowState "+
            " from zj_project a " +
            " where a.id in (select d.parent_id from zj_project d where d.parent_id is not null and d.project_state =:projectState) " +
            " UNION " +
            " select b.id as id, " +
            " b.parent_id as parentId, " +
            " b.project_name as projectName, " +
            " b.busi_type as busiType, " +
            " b.contract_no as contractNo, " +
            " b.delegate_unit as delegateUnit, " +
            " b.project_manager_name as projectManagerName, " +
            " b.implement_unit as implementUnit, " +
            " b.plan_finish_date as planFinishDate, " +
            " b.project_no as projectNo, " +
            " b.created_date as createdDate, " +
            " b.flow_state as flowState " +
            " from zj_project b where b.parent_id is null and b.project_state =:projectState" +
            " ) as c where 1=1 " +
            " AND ifnull( c.contractNo, '' ) LIKE %:contractNo% " +
            " AND ifnull( c.projectNo, '' ) LIKE %:projectNo% " +
            " AND ifnull( c.projectName, '' ) LIKE %:projectName% " +
            " AND ifnull( c.projectManagerName, '' ) LIKE %:projectManagerName% " +
            " AND ifnull( c.implementUnit, '' ) LIKE %:implementUnit% " +
            " AND ifnull( c.busiType, '' ) LIKE %:busiType% " +
            " AND ifnull( c.delegateUnit, '' ) LIKE %:delegateUnit%"
        ,nativeQuery = true)
//    Page<Map<String,Object>> findAllProjectArchiveOK(@Param("projectState") String projectState, Pageable page);
    Page<Map<String,Object>> findAllProjectArchiveOK(@Param("projectState") String projectState,@Param("contractNo") String contractNo,@Param("projectNo") String projectNo,
        @Param("projectName") String projectName,@Param("projectManagerName") String projectManagerName,@Param("implementUnit") String implementUnit,
        @Param("busiType") String busiType,@Param("delegateUnit") String delegateUnit, Pageable page);

    @Query(value = "SELECT c.id as id, " +
        " c.parentId as parentId, " +
        " c.projectName as projectName, " +
        " c.busiType as busiType, " +
        " c.contractNo as contractNo, " +
        " c.delegateUnit as delegateUnit, " +
        " c.projectManagerName as projectManagerName, " +
        " c.implementUnit as implementUnit, " +
        " c.planFinishDate as planFinishDate, " +
        " c.projectNo as projectNo, " +
        " c.createdDate as createdDate, " +
        " c.multiTenancyId as multiTenancyId, " +
        " c.flowState as flowState " +
        " from ( " +
        " select a.id as id, " +
        " a.parent_id as parentId, " +
        " a.project_name as projectName, " +
        " a.busi_type as busiType, " +
        " a.contract_no as contractNo, " +
        " a.delegate_unit as delegateUnit, " +
        " a.project_manager_name as projectManagerName, " +
        " a.implement_unit as implementUnit, " +
        " a.plan_finish_date as planFinishDate," +
        " a.project_no as projectNo, " +
        " a.created_date as createdDate, " +
        " a.multi_tenancy_id as multiTenancyId, " +
        " a.flow_state as flowState "+
        " from zj_project a " +
        " where a.id in (select d.parent_id from zj_project d where d.parent_id is not null and d.project_state =:projectState) " +
        " UNION " +
        " select b.id as id, " +
        " b.parent_id as parentId, " +
        " b.project_name as projectName, " +
        " b.busi_type as busiType, " +
        " b.contract_no as contractNo, " +
        " b.delegate_unit as delegateUnit, " +
        " b.project_manager_name as projectManagerName, " +
        " b.implement_unit as implementUnit, " +
        " b.plan_finish_date as planFinishDate, " +
        " b.project_no as projectNo, " +
        " b.created_date as createdDate, " +
        " b.multi_tenancy_id as multiTenancyId, " +
        " b.flow_state as flowState " +
        " from zj_project b where b.parent_id is null and b.project_state =:projectState" +
        " ) as c where 1=1 " +
        " AND ifnull( c.contractNo, '' ) LIKE %:contractNo% " +
        " AND ifnull( c.projectNo, '' ) LIKE %:projectNo% " +
        " AND ifnull( c.projectName, '' ) LIKE %:projectName% " +
        " AND ifnull( c.projectManagerName, '' ) LIKE %:projectManagerName% " +
        " AND ifnull( c.implementUnit, '' ) LIKE %:implementUnit% " +
        " AND ifnull( c.busiType, '' ) LIKE %:busiType% " +
        " AND ifnull( c.delegateUnit, '' ) LIKE %:delegateUnit%" +
        " AND c.multiTenancyId like :multiId order by c.createdDate desc ",
        countQuery = "SELECT count(c.id) " +
            " from ( " +
            " select a.id as id, " +
            " a.parent_id as parentId, " +
            " a.project_name as projectName, " +
            " a.busi_type as busiType, " +
            " a.contract_no as contractNo, " +
            " a.delegate_unit as delegateUnit, " +
            " a.project_manager_name as projectManagerName, " +
            " a.implement_unit as implementUnit, " +
            " a.plan_finish_date as planFinishDate, " +
            " a.project_no as projectNo, " +
            " a.created_date as createdDate, " +
            " a.multi_tenancy_id as multiTenancyId, " +
            " a.flow_state as flowState "+
            " from zj_project a " +
            " where a.id in (select d.parent_id from zj_project d where d.parent_id is not null and d.project_state =:projectState) " +
            " UNION " +
            " select b.id as id, " +
            " b.parent_id as parentId, " +
            " b.project_name as projectName, " +
            " b.busi_type as busiType, " +
            " b.contract_no as contractNo, " +
            " b.delegate_unit as delegateUnit, " +
            " b.project_manager_name as projectManagerName, " +
            " b.implement_unit as implementUnit, " +
            " b.plan_finish_date as planFinishDate, " +
            " b.project_no as projectNo, " +
            " b.created_date as createdDate, " +
            " b.multi_tenancy_id as multiTenancyId, " +
            " b.flow_state as flowState " +
            " from zj_project b where b.parent_id is null and b.project_state =:projectState" +
            " ) as c where 1=1 " +
            " AND ifnull( c.contractNo, '' ) LIKE %:contractNo% " +
            " AND ifnull( c.projectNo, '' ) LIKE %:projectNo% " +
            " AND ifnull( c.projectName, '' ) LIKE %:projectName% " +
            " AND ifnull( c.projectManagerName, '' ) LIKE %:projectManagerName% " +
            " AND ifnull( c.implementUnit, '' ) LIKE %:implementUnit% " +
            " AND ifnull( c.busiType, '' ) LIKE %:busiType% " +
            " AND ifnull( c.delegateUnit, '' ) LIKE %:delegateUnit%" +
            " AND c.multiTenancyId like :multiId "
        ,nativeQuery = true)
//    Page<Map<String,Object>> findAllProjectArchiveOK(@Param("projectState") String projectState, Pageable page);
    Page<Map<String,Object>> findAllProjectArchiveOKByMultiTenancyId(@Param("projectState") String projectState,@Param("contractNo") String contractNo,@Param("projectNo") String projectNo,
                                                     @Param("projectName") String projectName,@Param("projectManagerName") String projectManagerName,@Param("implementUnit") String implementUnit,
                                                     @Param("busiType") String busiType,@Param("delegateUnit") String delegateUnit, @Param("multiId") String multiId, Pageable page);

    List<ZjProject> findAllByParentIdInAndProjectStateOrderByCreatedDateDesc(List<String> parentIdList,@Size(max = 50) String projectState);


    @Query(
        value = "SELECT  " +
        "oa.id AS id,  "+
        "oa.parentId AS parentId,  " +
        "oa.projectName AS projectName,  " +
        "oa.busiType AS busiType,  " +
        "oa.contractNo AS contractNo,  " +
        "oa.delegateUnit AS delegateUnit,  " +
        "oa.projectManagerName AS projectManagerName,  " +
        "oa.implementUnit AS implementUnit,  " +
        "oa.planFinishDate AS planFinishDate,  " +
        "oa.projectNo AS projectNo,  " +
        "oa.createdDate AS createdDate,  " +
        "oa.flowState AS flowState,  " +
        "oa.projectState AS projectState,  " +
        "oa.multiTenancyId AS multiTenancyId, " +
        "oa.isGood,   " +
        "oa.isCold,   " +
        "oa.attentionProjectType   " +
        "FROM  " +
        "(  " +
        "SELECT  " +
        "ia.id AS id,  " +
        "ia.parent_id AS parentId,  " +
        "ia.project_name AS projectName,  " +
        "ia.busi_type AS busiType,  " +
        "ia.contract_no AS contractNo,  " +
        "ia.delegate_unit AS delegateUnit,  " +
        "ia.project_manager_name AS projectManagerName,  " +
        "ia.implement_unit AS implementUnit,  " +
        "ia.plan_finish_date AS planFinishDate,  " +
        "ia.project_no AS projectNo,  " +
        "ia.created_date AS createdDate,  " +
        "ia.flow_state AS flowState,  " +
        "ia.project_state AS projectState,  " +
        "ia.multi_tenancy_id AS multiTenancyId,   " +
        "ia.is_good AS isGood,   " +
        "ia.is_cold AS isCold,   " +
        "ia.attention_project_type AS attentionProjectType   " +
        "FROM  " +
        "zj_project AS ia   " +
        "WHERE  " +
        "1 = 1   " +
        "AND IFNULL( ia.contract_no, '' ) LIKE %:contractNo%  " +
        "AND IFNULL( ia.project_no, '' ) LIKE %:projectNo%  " +
        "AND IFNULL( ia.project_name, '' ) LIKE %:projectName%   " +
        "AND IFNULL( ia.project_manager_name, '' ) LIKE %:projectManagerName%  " +
        "AND IFNULL( ia.implement_unit, '' ) LIKE %:implementUnit%   " +
        "AND IFNULL( ia.project_state, '' ) LIKE %:projectState%   " +
        "AND IFNULL( ia.delegate_unit, '' ) LIKE %:delegateUnit%   " +
        "AND IFNULL( ia.busi_type, '' ) LIKE %:busiType% " +
        "AND if(:isGood != '', ia.is_good=:isGood, 1=1)"+
        "AND if(:isCold != '', ia.is_cold=:isCold, 1=1)"+
        "AND IFNULL(ia.attention_project_type,'') like %:attentionProjectType% "+
        "UNION  " +
        "SELECT  " +
        "ib.id AS id,  " +
        "ib.parent_id AS parentId,  " +
        "ib.project_name AS projectName,  " +
        "ib.busi_type AS busiType,  " +
        "ib.contract_no AS contractNo,  " +
        "ib.delegate_unit AS delegateUnit,  " +
        "ib.project_manager_name AS projectManagerName,  " +
        "ib.implement_unit AS implementUnit,  " +
        "ib.plan_finish_date AS planFinishDate,  " +
        "ib.project_no AS projectNo,  " +
        "ib.created_date AS createdDate,  " +
        "ib.flow_state AS flowState,  " +
        "ib.project_state AS projectState,  " +
        "ib.multi_tenancy_id AS multiTenancyId,  " +
        "ib.is_good AS isGood,   " +
        "ib.is_cold AS isCold,   " +
        "ib.attention_project_type AS attentionProjectType   " +
        "FROM  " +
        "zj_project AS ib   " +
        "WHERE " +
        "1 = 1  " +
        "AND IFNULL( ib.busi_type, '' ) LIKE %:busiType%  " +
        "AND ib.id IN ( " +
        "SELECT " +
        "ic.parent_id AS parentId " +
        "FROM " +
        "zj_project AS ic " +
        "WHERE  " +
        "ic.parent_id IS NOT NULL   " +
        "AND ib.id = ic.parent_id   " +
        "AND IFNULL( ic.contract_no, '' ) LIKE %:contractNo%  " +
        "AND IFNULL( ic.project_no, '' ) LIKE %:projectNo%  " +
        "AND IFNULL( ic.project_name, '' ) LIKE %:projectName%   " +
        "AND IFNULL( ic.project_manager_name, '' ) LIKE %:projectManagerName%  " +
        "AND IFNULL( ic.implement_unit, '' ) LIKE %:implementUnit%   " +
        "AND IFNULL( ic.project_state, '' ) LIKE %:projectState%   " +
        "AND IFNULL( ic.delegate_unit, '' ) LIKE %:delegateUnit%   " +
        "AND if(:isGood != '', ic.is_good=:isGood, 1=1)"+
        "AND if(:isCold != '', ic.is_cold=:isCold, 1=1)"+
        "AND IFNULL(ic.attention_project_type,'') like %:attentionProjectType% "+
        ")   " +
        ") AS oa   " +
        "ORDER BY  " +
        "oa.createdDate DESC",
        countQuery = "SELECT " +
            "count(oa.id) " +
            "FROM  " +
            "(  " +
            "SELECT  " +
            "ia.id AS id,  " +
            "ia.parent_id AS parentId,  " +
            "ia.project_name AS projectName,  " +
            "ia.busi_type AS busiType,  " +
            "ia.contract_no AS contractNo,  " +
            "ia.delegate_unit AS delegateUnit,  " +
            "ia.project_manager_name AS projectManagerName,  " +
            "ia.implement_unit AS implementUnit,  " +
            "ia.plan_finish_date AS planFinishDate,  " +
            "ia.project_no AS projectNo,  " +
            "ia.created_date AS createdDate,  " +
            "ia.flow_state AS flowState,  " +
            "ia.project_state AS projectState,  " +
            "ia.multi_tenancy_id AS multiTenancyId,   " +
            "ia.is_good AS isGood,   " +
            "ia.is_cold AS isCold,   " +
            "ia.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ia   " +
            "WHERE  " +
            "1 = 1   " +
            "AND IFNULL( ia.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ia.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ia.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ia.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ia.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ia.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ia.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND IFNULL( ia.busi_type, '' ) LIKE %:busiType% " +
            "AND if(:isGood != '', ia.is_good=:isGood, 1=1)"+
            "AND if(:isCold != '', ia.is_cold=:isCold, 1=1)"+
            "AND IFNULL(ia.attention_project_type,'') like %:attentionProjectType% "+
            "UNION  " +
            "SELECT  " +
            "ib.id AS id,  " +
            "ib.parent_id AS parentId,  " +
            "ib.project_name AS projectName,  " +
            "ib.busi_type AS busiType,  " +
            "ib.contract_no AS contractNo,  " +
            "ib.delegate_unit AS delegateUnit,  " +
            "ib.project_manager_name AS projectManagerName,  " +
            "ib.implement_unit AS implementUnit,  " +
            "ib.plan_finish_date AS planFinishDate,  " +
            "ib.project_no AS projectNo,  " +
            "ib.created_date AS createdDate,  " +
            "ib.flow_state AS flowState,  " +
            "ib.project_state AS projectState,  " +
            "ib.multi_tenancy_id AS multiTenancyId,  " +
            "ib.is_good AS isGood,   " +
            "ib.is_cold AS isCold,   " +
            "ib.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ib   " +
            "WHERE  " +
            "1 = 1 " +
            "AND IFNULL( ib.busi_type, '' ) LIKE %:busiType%  " +
            "AND ib.id IN ( " +
            "SELECT " +
            "ic.parent_id AS parentId " +
            "FROM " +
            "zj_project AS ic " +
            "WHERE  " +
            "ic.parent_id IS NOT NULL   " +
            "AND ib.id = ic.parent_id   " +
            "AND IFNULL( ic.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ic.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ic.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ic.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ic.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ic.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ic.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND if(:isGood != '', ic.is_good=:isGood, 1=1)"+
            "AND if(:isCold != '', ic.is_cold=:isCold, 1=1)"+
            "AND IFNULL(ic.attention_project_type,'') like %:attentionProjectType% "+
            ")   " +
            ") AS oa   ",
        nativeQuery = true
    )
    Page<Map<String,Object>> findAllProcess(
                                            @Param("contractNo") String contractNo,
                                            @Param("projectNo") String projectNo,
                                            @Param("projectName") String projectName,
                                            @Param("projectManagerName") String projectManagerName,
                                            @Param("implementUnit") String implementUnit,
                                            @Param("projectState") String projectState,
                                            @Param("delegateUnit") String delegateUnit,
                                            @Param("busiType") String busiType,
                                            Pageable page,
                                            @Param("isGood") String isGood,
                                            @Param("isCold") String isCold,
                                            @Param("attentionProjectType") String attentionProjectType);
    @Query(
        value = "SELECT " +
            "oa.id AS id,  " +
            "oa.parentId AS parentId,  " +
            "oa.projectName AS projectName,  " +
            "oa.busiType AS busiType,  " +
            "oa.contractNo AS contractNo,  " +
            "oa.delegateUnit AS delegateUnit,  " +
            "oa.projectManagerName AS projectManagerName,  " +
            "oa.implementUnit AS implementUnit,  " +
            "oa.planFinishDate AS planFinishDate,  " +
            "oa.projectNo AS projectNo,  " +
            "oa.createdDate AS createdDate,  " +
            "oa.flowState AS flowState,  " +
            "oa.projectState AS projectState,  " +
            "oa.multiTenancyId AS multiTenancyId,   " +
            "oa.isGood,   " +
            "oa.isCold,   " +
            "oa.attentionProjectType   " +
            "FROM  " +
            "(  " +
            "SELECT  " +
            "ia.id AS id,  " +
            "ia.parent_id AS parentId,  " +
            "ia.project_name AS projectName,  " +
            "ia.busi_type AS busiType,  " +
            "ia.contract_no AS contractNo,  " +
            "ia.delegate_unit AS delegateUnit,  " +
            "ia.project_manager_name AS projectManagerName,  " +
            "ia.implement_unit AS implementUnit,  " +
            "ia.plan_finish_date AS planFinishDate,  " +
            "ia.project_no AS projectNo,  " +
            "ia.created_date AS createdDate,  " +
            "ia.flow_state AS flowState,  " +
            "ia.project_state AS projectState,  " +
            "ia.multi_tenancy_id AS multiTenancyId,   " +
            "ia.is_good AS isGood,   " +
            "ia.is_cold AS isCold,   " +
            "ia.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ia   " +
            "WHERE  " +
            "1 = 1   " +
            "AND IFNULL( ia.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ia.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ia.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ia.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ia.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ia.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ia.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND IFNULL( ia.busi_type, '' ) LIKE %:busiType% " +
            "AND ia.multi_tenancy_id LIKE :multiId " +
            "AND if(:isGood != '', ia.is_good=:isGood, 1=1)"+
            "AND if(:isCold != '', ia.is_cold=:isCold, 1=1)"+
            "AND IFNULL(ia.attention_project_type,'') like %:attentionProjectType%  "+
            "UNION  " +
            "SELECT  " +
            "ib.id AS id,  " +
            "ib.parent_id AS parentId,  " +
            "ib.project_name AS projectName,  " +
            "ib.busi_type AS busiType,  " +
            "ib.contract_no AS contractNo,  " +
            "ib.delegate_unit AS delegateUnit,  " +
            "ib.project_manager_name AS projectManagerName,  " +
            "ib.implement_unit AS implementUnit,  " +
            "ib.plan_finish_date AS planFinishDate,  " +
            "ib.project_no AS projectNo,  " +
            "ib.created_date AS createdDate,  " +
            "ib.flow_state AS flowState,  " +
            "ib.project_state AS projectState,  " +
            "ib.multi_tenancy_id AS multiTenancyId,   " +
            "ib.is_good AS isGood,   " +
            "ib.is_cold AS isCold,   " +
            "ib.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ib   " +
            "WHERE  " +
            "1 = 1 " +
            "AND IFNULL( ib.busi_type, '' ) LIKE %:busiType%  " +
            "AND ib.id IN ( " +
            "SELECT " +
            "ic.parent_id AS parentId " +
            "FROM " +
            "zj_project AS ic " +
            "WHERE  " +
            "ic.parent_id IS NOT NULL   " +
            "AND ib.id = ic.parent_id   " +
            "AND IFNULL( ic.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ic.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ic.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ic.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ic.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ic.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ic.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND ic.multi_tenancy_id LIKE :multiId " +
            "AND if(:isGood != '', ic.is_good=:isGood, 1=1)"+
            "AND if(:isCold != '', ic.is_cold=:isCold, 1=1)"+
            "AND IFNULL(ic.attention_project_type,'') like %:attentionProjectType%  "+
            ")   " +
            ") AS oa   " +
            "ORDER BY  " +
            "oa.createdDate DESC",
        countQuery = "SELECT " +
            "count(oa.id) " +
            "FROM  " +
            "(  " +
            "SELECT  " +
            "ia.id AS id,  " +
            "ia.parent_id AS parentId,  " +
            "ia.project_name AS projectName,  " +
            "ia.busi_type AS busiType,  " +
            "ia.contract_no AS contractNo,  " +
            "ia.delegate_unit AS delegateUnit,  " +
            "ia.project_manager_name AS projectManagerName,  " +
            "ia.implement_unit AS implementUnit,  " +
            "ia.plan_finish_date AS planFinishDate,  " +
            "ia.project_no AS projectNo,  " +
            "ia.created_date AS createdDate,  " +
            "ia.flow_state AS flowState,  " +
            "ia.project_state AS projectState,  " +
            "ia.multi_tenancy_id AS multiTenancyId,   " +
            "ia.is_good AS isGood,   " +
            "ia.is_cold AS isCold,   " +
            "ia.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ia   " +
            "WHERE  " +
            "1 = 1   " +
            "AND IFNULL( ia.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ia.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ia.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ia.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ia.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ia.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ia.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND IFNULL( ia.busi_type, '' ) LIKE %:busiType% " +
            "AND ia.multi_tenancy_id LIKE :multiId " +
            "AND if(:isGood != '', ia.is_good=:isGood, 1=1)"+
            "AND if(:isCold != '', ia.is_cold=:isCold, 1=1)"+
            "AND IFNULL(ia.attention_project_type,'') like %:attentionProjectType%  "+
            "UNION  " +
            "SELECT  " +
            "ib.id AS id,  " +
            "ib.parent_id AS parentId,  " +
            "ib.project_name AS projectName,  " +
            "ib.busi_type AS busiType,  " +
            "ib.contract_no AS contractNo,  " +
            "ib.delegate_unit AS delegateUnit,  " +
            "ib.project_manager_name AS projectManagerName,  " +
            "ib.implement_unit AS implementUnit,  " +
            "ib.plan_finish_date AS planFinishDate,  " +
            "ib.project_no AS projectNo,  " +
            "ib.created_date AS createdDate,  " +
            "ib.flow_state AS flowState,  " +
            "ib.project_state AS projectState,  " +
            "ib.multi_tenancy_id AS multiTenancyId,   " +
            "ib.is_good AS isGood,   " +
            "ib.is_cold AS isCold,   " +
            "ib.attention_project_type AS attentionProjectType   " +
            "FROM  " +
            "zj_project AS ib   " +
            "WHERE  " +
            "1 = 1 " +
            "AND IFNULL( ib.busi_type, '' ) LIKE %:busiType%  " +
            "AND ib.id IN ( " +
            "SELECT " +
            "ic.parent_id AS parentId " +
            "FROM " +
            "zj_project AS ic " +
            "WHERE  " +
            "ic.parent_id IS NOT NULL   " +
            "AND ib.id = ic.parent_id   " +
            "AND IFNULL( ic.contract_no, '' ) LIKE %:contractNo%  " +
            "AND IFNULL( ic.project_no, '' ) LIKE %:projectNo%  " +
            "AND IFNULL( ic.project_name, '' ) LIKE %:projectName%   " +
            "AND IFNULL( ic.project_manager_name, '' ) LIKE %:projectManagerName%  " +
            "AND IFNULL( ic.implement_unit, '' ) LIKE %:implementUnit%   " +
            "AND IFNULL( ic.project_state, '' ) LIKE %:projectState%   " +
            "AND IFNULL( ic.delegate_unit, '' ) LIKE %:delegateUnit%   " +
            "AND ic.multi_tenancy_id LIKE :multiId " +
            "AND if(:isGood != '', ic.is_good=:isGood, 1=1)  "+
            "AND if(:isCold != '', ic.is_cold=:isCold, 1=1)  "+
            "AND IFNULL(ic.attention_project_type,'') like %:attentionProjectType%  "+
            ")   " +
            ") AS oa   ",
        nativeQuery = true
    )
    Page<Map<String,Object>> findAllProcessByMultiTenancyId(
                                            @Param("contractNo") String contractNo,
                                            @Param("projectNo") String projectNo,
                                            @Param("projectName") String projectName,
                                            @Param("projectManagerName") String projectManagerName,
                                            @Param("implementUnit") String implementUnit,
                                            @Param("projectState") String projectState,
                                            @Param("delegateUnit") String delegateUnit,
                                            @Param("busiType") String busiType,
                                            @Param("multiId") String multiId,
                                            Pageable page,
                                            @Param("isGood") String isGood,
                                            @Param("isCold") String isCold,
                                            @Param("attentionProjectType") String attentionProjectType);

    @Query(
        value = "SELECT  " +
            " a.id AS id,  " +
            " a.parent_id AS parentId,  " +
            " a.project_name AS projectName,  " +
            " a.busi_type AS busiType,  " +
            " a.contract_no AS contractNo,  " +
            " a.delegate_unit AS delegateUnit,  " +
            " a.project_manager_name AS projectManagerName,  " +
            " a.implement_unit AS implementUnit,  " +
            " a.plan_finish_date AS planFinishDate,  " +
            " a.project_no AS projectNo,  " +
            " a.created_date AS createdDate,  " +
            " a.flow_state AS flowState,  " +
            " a.project_state AS projectState  " +
            "FROM  " +
            " zj_project a  " +
            "WHERE " +
            "1 = 1 " +
            " AND a.parent_id IS NOT NULL " +
            " AND a.parent_id IN (:parentIdList) " +
            " AND IFNULL( a.contract_no, '' ) LIKE %:contractNo%   " +
            " AND IFNULL( a.project_no, '' ) LIKE %:projectNo%   " +
            " AND IFNULL( a.project_name, '' ) LIKE %:projectName%   " +
            " AND IFNULL( a.project_manager_name, '' ) LIKE %:projectManagerName%   " +
            " AND IFNULL( a.implement_unit, '' ) LIKE %:implementUnit%   " +
            " AND IFNULL( a.project_state, '' ) LIKE %:projectState%   " +
            " AND IFNULL( a.delegate_unit, '' ) LIKE %:delegateUnit%" +
            " order by a.project_no ASC",
        countQuery = "SELECT  " +
        "count(*) " +
        "FROM  " +
        " zj_project a  " +
        "WHERE " +
        "1 = 1 " +
        " AND a.parent_id IS NOT NULL " +
        " AND a.parent_id IN (:parentIdList) " +
        " AND IFNULL( a.contract_no, '' ) LIKE %:contractNo%   " +
        " AND IFNULL( a.project_no, '' ) LIKE %:projectNo%   " +
        " AND IFNULL( a.project_name, '' ) LIKE %:projectName%   " +
        " AND IFNULL( a.project_manager_name, '' ) LIKE %:projectManagerName%   " +
        " AND IFNULL( a.implement_unit, '' ) LIKE %:implementUnit%   " +
        " AND IFNULL( a.project_state, '' ) LIKE %:projectState%   " +
        " AND IFNULL( a.delegate_unit, '' ) LIKE %:delegateUnit%",
        nativeQuery = true
    )
    List<Map<String,Object>> findAllByParentId(
                                            @Param("parentIdList") List<String> parentIdList,
                                            @Param("contractNo") String contractNo,
                                            @Param("projectNo") String projectNo,
                                            @Param("projectName") String projectName,
                                            @Param("projectManagerName") String projectManagerName,
                                            @Param("implementUnit") String implementUnit,
                                            @Param("projectState") String projectState,
                                            @Param("delegateUnit") String delegateUnit
    );

    @Query(
        value = "SELECT  " +
            " a.id AS id,  " +
            " a.parent_id AS parentId,  " +
            " a.project_name AS projectName,  " +
            " a.busi_type AS busiType,  " +
            " a.contract_no AS contractNo,  " +
            " a.delegate_unit AS delegateUnit,  " +
            " a.project_manager_name AS projectManagerName,  " +
            " a.implement_unit AS implementUnit,  " +
            " a.plan_finish_date AS planFinishDate,  " +
            " a.project_no AS projectNo,  " +
            " a.created_date AS createdDate,  " +
            " a.flow_state AS flowState,  " +
            " a.project_state AS projectState, " +
            " a.multi_tenancy_id AS multiTenancyId " +
            "FROM  " +
            " zj_project a  " +
            "WHERE " +
            "1 = 1 " +
            " AND a.parent_id IS NOT NULL " +
            " AND a.parent_id IN (:parentIdList)  " +
            " AND IFNULL( a.contract_no, '' ) LIKE %:contractNo%   " +
            " AND IFNULL( a.project_no, '' ) LIKE %:projectNo%   " +
            " AND IFNULL( a.project_name, '' ) LIKE %:projectName%   " +
            " AND IFNULL( a.project_manager_name, '' ) LIKE %:projectManagerName%   " +
            " AND IFNULL( a.implement_unit, '' ) LIKE %:implementUnit%   " +
            " AND IFNULL( a.project_state, '' ) LIKE %:projectState%   " +
            " AND IFNULL( a.delegate_unit, '' ) LIKE %:delegateUnit% " +
            " AND IFNULL( a.multi_tenancy_id, '' ) LIKE :multiId " +
            " order by a.project_no ASC",
        countQuery = "SELECT " +
        "count(*) " +
        "FROM  " +
        " zj_project a  " +
        "WHERE " +
        "1 = 1 " +
        " AND a.parent_id IS NOT NULL " +
        " AND a.parent_id IN (:parentIdList)  " +
        " AND IFNULL( a.contract_no, '' ) LIKE %:contractNo%   " +
        " AND IFNULL( a.project_no, '' ) LIKE %:projectNo%   " +
        " AND IFNULL( a.project_name, '' ) LIKE %:projectName%   " +
        " AND IFNULL( a.project_manager_name, '' ) LIKE %:projectManagerName%   " +
        " AND IFNULL( a.implement_unit, '' ) LIKE %:implementUnit%   " +
        " AND IFNULL( a.project_state, '' ) LIKE %:projectState%   " +
        " AND IFNULL( a.delegate_unit, '' ) LIKE %:delegateUnit% " +
        " AND IFNULL( a.multi_tenancy_id, '' ) LIKE :multiId " ,
        nativeQuery = true
    )
    List<Map<String,Object>> findAllByParentIdByMultiTenancyId(
        @Param("parentIdList") List<String> parentIdList,
        @Param("contractNo") String contractNo,
        @Param("projectNo") String projectNo,
        @Param("projectName") String projectName,
        @Param("projectManagerName") String projectManagerName,
        @Param("implementUnit") String implementUnit,
        @Param("projectState") String projectState,
        @Param("delegateUnit") String delegateUnit,
        @Param("multiId") String multiId
    );
}

