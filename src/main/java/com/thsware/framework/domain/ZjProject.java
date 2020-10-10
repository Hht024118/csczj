package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 造价咨询-项目登记
 */
@ApiModel(description = "造价咨询-项目登记")
@Entity
@Table(name = "zj_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 50)
    @Column(name = "project_no", length = 50)
    private String projectNo;

    @Size(max = 36)
    @Column(name = "contract_id", length = 36)
    private String contractId;

    @Size(max = 50)
    @Column(name = "contract_no", length = 50)
    private String contractNo;

    @Size(max = 200)
    @Column(name = "contract_name", length = 200)
    private String contractName;

    @Size(max = 200)
    @Column(name = "project_name", length = 200)
    private String projectName;

    @Column(name = "register_date")
    private Instant registerDate;

    @Column(name = "publish_date")
    private Instant publishDate;

    @Size(max = 50)
    @Column(name = "delegate_unit", length = 50)
    private String delegateUnit;

    @Column(name = "delegate_date")
    private Instant delegateDate;

    @Size(max = 50)
    @Column(name = "delegate_dept", length = 50)
    private String delegateDept;

    @Size(max = 50)
    @Column(name = "delegate_linkman", length = 50)
    private String delegateLinkman;

    @Column(name = "required_date")
    private Instant requiredDate;

    @Size(max = 1)
    @Column(name = "is_government_invest", length = 1)
    private String isGovernmentInvest;

    @Size(max = 20)
    @Column(name = "project_type", length = 20)
    private String projectType;

    @Size(max = 50)
    @Column(name = "project_industry", length = 50)
    private String projectIndustry;

    @Size(max = 50)
    @Column(name = "project_source", length = 50)
    private String projectSource;

    @Size(max = 50)
    @Column(name = "busi_type", length = 50)
    private String busiType;

    @Size(max = 500)
    @Column(name = "specialty", length = 500)
    private String specialty;

    @Size(max = 500)
    @Column(name = "project_scale", length = 500)
    private String projectScale;

    @Column(name = "cost_money")
    private Double costMoney;

    @Column(name = "submit_money")
    private Double submitMoney;

    @Size(max = 50)
    @Column(name = "project_manager", length = 50)
    private String projectManager;

    @Size(max = 50)
    @Column(name = "project_manager_name", length = 50)
    private String projectManagerName;

    @Column(name = "plan_finish_date")
    private Instant planFinishDate;

    @Size(max = 50)
    @Column(name = "implement_unit", length = 50)
    private String implementUnit;

    @Size(max = 200)
    @Column(name = "content_scope", length = 200)
    private String contentScope;

    @Size(max = 1)
    @Column(name = "is_outsource", length = 1)
    private String isOutsource;

    @Size(max = 200)
    @Column(name = "outsource_desc", length = 200)
    private String outsourceDesc;

    @Size(max = 200)
    @Column(name = "project_progress", length = 200)
    private String projectProgress;

    @Size(max = 200)
    @Column(name = "project_budget", length = 200)
    private String projectBudget;

    @Column(name = "need_money")
    private Double needMoney;

    @Size(max = 50)
    @Column(name = "flow_state", length = 50)
    private String flowState;

    @Size(max = 36)
    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Size(max = 10)
    @Column(name = "project_state", length = 10)
    private String projectState;

    @Size(max = 500)
    @Column(name = "remark", length = 500)
    private String remark;

    @CreatedBy
    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @LastModifiedBy
    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ThsMultiTenancyId
    @Size(max = 40)
    @Column(name="multi_tenancy_id",length = 40)
    private String multiTenancyId;

    /**
     * 新加字段20190419  --Start--
     */
    @Size(max = 50)
    @Column(name = "linkage_branch", length = 50)
    private String linkageBranch;

    @Column(name = "contract_money")
    private Double contractMoney;

    @Column(name = "report_conclusion")
    private Double reportConclusion;

    @Column(name = "estimated_income")
    private Double estimatedIncome;

    @Size(max = 1)
    @Column(name = "is_ccb_client", length = 1)
    private String isCcbClient;

    @Size(max = 20)
    @Column(name = "attention_project_type", length = 20)
    private String attentionProjectType;

    @Size(max = 50)
    @Column(name = "city_where", length = 50)
    private String cityWhere;

    @Column(name = "total_project_cost")
    private Double totalProjectCost;

    /**
     * 新加字段  --End--
     */

    @OneToOne(mappedBy = "zjProject")
    private ZjProjectArchive zjProjectArchive;

    @Size(max = 100)
    @Column(name = "cooperative_company", length = 100)
    private String cooperativeCompany;

    @Size(max = 50)
    @Column(name = "is_cold", length = 50)
    private String isCold;

    @Size(max = 50)
    @Column(name = "is_good", length = 50)
    private String isGood;

    @Column(name = "market_rate")
    private Double marketRate;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public ZjProject projectNo(String projectNo) {
        this.projectNo = projectNo;
        return this;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getContractId() {
        return contractId;
    }

    public ZjProject contractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public ZjProject contractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public ZjProject contractName(String contractName) {
        this.contractName = contractName;
        return this;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProjectName() {
        return projectName;
    }

    public ZjProject projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public ZjProject registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public ZjProject publishDate(Instant publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    public String getDelegateUnit() {
        return delegateUnit;
    }

    public ZjProject delegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
        return this;
    }

    public void setDelegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
    }

    public Instant getDelegateDate() {
        return delegateDate;
    }

    public ZjProject delegateDate(Instant delegateDate) {
        this.delegateDate = delegateDate;
        return this;
    }

    public void setDelegateDate(Instant delegateDate) {
        this.delegateDate = delegateDate;
    }

    public String getDelegateDept() {
        return delegateDept;
    }

    public ZjProject delegateDept(String delegateDept) {
        this.delegateDept = delegateDept;
        return this;
    }

    public void setDelegateDept(String delegateDept) {
        this.delegateDept = delegateDept;
    }

    public String getDelegateLinkman() {
        return delegateLinkman;
    }

    public ZjProject delegateLinkman(String delegateLinkman) {
        this.delegateLinkman = delegateLinkman;
        return this;
    }

    public void setDelegateLinkman(String delegateLinkman) {
        this.delegateLinkman = delegateLinkman;
    }

    public Instant getRequiredDate() {
        return requiredDate;
    }

    public ZjProject requiredDate(Instant requiredDate) {
        this.requiredDate = requiredDate;
        return this;
    }

    public void setRequiredDate(Instant requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getIsGovernmentInvest() {
        return isGovernmentInvest;
    }

    public ZjProject isGovernmentInvest(String isGovernmentInvest) {
        this.isGovernmentInvest = isGovernmentInvest;
        return this;
    }

    public void setIsGovernmentInvest(String isGovernmentInvest) {
        this.isGovernmentInvest = isGovernmentInvest;
    }

    public String getProjectType() {
        return projectType;
    }

    public ZjProject projectType(String projectType) {
        this.projectType = projectType;
        return this;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectIndustry() {
        return projectIndustry;
    }

    public ZjProject projectIndustry(String projectIndustry) {
        this.projectIndustry = projectIndustry;
        return this;
    }

    public void setProjectIndustry(String projectIndustry) {
        this.projectIndustry = projectIndustry;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public ZjProject projectSource(String projectSource) {
        this.projectSource = projectSource;
        return this;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getBusiType() {
        return busiType;
    }

    public ZjProject busiType(String busiType) {
        this.busiType = busiType;
        return this;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getSpecialty() {
        return specialty;
    }

    public ZjProject specialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getProjectScale() {
        return projectScale;
    }

    public ZjProject projectScale(String projectScale) {
        this.projectScale = projectScale;
        return this;
    }

    public void setProjectScale(String projectScale) {
        this.projectScale = projectScale;
    }

    public Double getCostMoney() {
        return costMoney;
    }

    public ZjProject costMoney(Double costMoney) {
        this.costMoney = costMoney;
        return this;
    }

    public void setCostMoney(Double costMoney) {
        this.costMoney = costMoney;
    }

    public Double getSubmitMoney() {
        return submitMoney;
    }

    public ZjProject submitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
        return this;
    }

    public void setSubmitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public ZjProject projectManager(String projectManager) {
        this.projectManager = projectManager;
        return this;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public ZjProject projectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
        return this;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public Instant getPlanFinishDate() {
        return planFinishDate;
    }

    public ZjProject planFinishDate(Instant planFinishDate) {
        this.planFinishDate = planFinishDate;
        return this;
    }

    public void setPlanFinishDate(Instant planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public String getImplementUnit() {
        return implementUnit;
    }

    public ZjProject implementUnit(String implementUnit) {
        this.implementUnit = implementUnit;
        return this;
    }

    public void setImplementUnit(String implementUnit) {
        this.implementUnit = implementUnit;
    }

    public String getContentScope() {
        return contentScope;
    }

    public ZjProject contentScope(String contentScope) {
        this.contentScope = contentScope;
        return this;
    }

    public void setContentScope(String contentScope) {
        this.contentScope = contentScope;
    }

    public String getIsOutsource() {
        return isOutsource;
    }

    public ZjProject isOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
        return this;
    }

    public void setIsOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
    }

    public String getOutsourceDesc() {
        return outsourceDesc;
    }

    public ZjProject outsourceDesc(String outsourceDesc) {
        this.outsourceDesc = outsourceDesc;
        return this;
    }

    public void setOutsourceDesc(String outsourceDesc) {
        this.outsourceDesc = outsourceDesc;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public ZjProject projectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
        return this;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getProjectBudget() {
        return projectBudget;
    }

    public ZjProject projectBudget(String projectBudget) {
        this.projectBudget = projectBudget;
        return this;
    }

    public void setProjectBudget(String projectBudget) {
        this.projectBudget = projectBudget;
    }

    public Double getNeedMoney() {
        return needMoney;
    }

    public ZjProject needMoney(Double needMoney) {
        this.needMoney = needMoney;
        return this;
    }

    public void setNeedMoney(Double needMoney) {
        this.needMoney = needMoney;
    }

    public String getFlowState() {
        return flowState;
    }

    public ZjProject flowState(String flowState) {
        this.flowState = flowState;
        return this;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getProjectState() {
        return projectState;
    }

    public ZjProject projectState(String projectState) {
        this.projectState = projectState;
        return this;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getRemark() {
        return remark;
    }

    public ZjProject remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZjProject createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ZjProject createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZjProject lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ZjProject lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public ZjProject multiTenancyId(String multiTenancyId){
        this.multiTenancyId = multiTenancyId;
        return this;
    }
    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjProjectArchive getZjProjectArchive() {
        return zjProjectArchive;
    }

    public void setZjProjectArchive(ZjProjectArchive zjProjectArchive) {
        this.zjProjectArchive = zjProjectArchive;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ZjProject parentId(String parentId){
        this.parentId = parentId;
        return this;
    }

    public String getAttentionProjectType() {
        return attentionProjectType;
    }

    public void setAttentionProjectType(String attentionProjectType) {
        this.attentionProjectType = attentionProjectType;
    }

    public ZjProject attentionProjectType(String attentionProjectType){
        this.attentionProjectType = attentionProjectType;
        return this;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public ZjProject contractMoney(Double contractMoney){
        this.contractMoney = contractMoney;
        return this;
    }

    public String getLinkageBranch() {
        return linkageBranch;
    }

    public void setLinkageBranch(String linkageBranch) {
        this.linkageBranch = linkageBranch;
    }

    public ZjProject linkageBranch(String linkageBranch){
        this.linkageBranch = linkageBranch;
        return this;
    }

    public Double getReportConclusion() {
        return reportConclusion;
    }

    public void setReportConclusion(Double reportConclusion) {
        this.reportConclusion = reportConclusion;
    }

    public ZjProject reportConclusion(Double reportConclusion){
        this.reportConclusion = reportConclusion;
        return this;
    }

    public Double getEstimatedIncome() {
        return estimatedIncome;
    }

    public void setEstimatedIncome(Double estimatedIncome) {
        this.estimatedIncome = estimatedIncome;
    }

    public String getIsCcbClient() {
        return isCcbClient;
    }

    public void setIsCcbClient(String isCcbClient) {
        this.isCcbClient = isCcbClient;
    }

    public String getCityWhere() {
        return cityWhere;
    }

    public void setCityWhere(String cityWhere) {
        this.cityWhere = cityWhere;
    }

    public ZjProject cityWhere(String cityWhere){
        this.cityWhere = cityWhere;
        return this;
    }

    public String getIsCold() {
        return isCold;
    }

    public ZjProject isCold(String isCold) {
        this.isCold = isCold;
        return this;
    }

    public void setIsCold(String isCold) {
        this.isCold = isCold;
    }

    public String getIsGood() {
        return isGood;
    }

    public ZjProject isGood(String isGood) {
        this.isGood = isGood;
        return this;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    public Double getMarketRate() {
        return marketRate;
    }

    public ZjProject marketRate(Double marketRate) {
        this.marketRate = marketRate;
        return this;
    }

    public void setMarketRate(Double marketRate) {
        this.marketRate = marketRate;
    }

    public Double getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(Double totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public ZjProject totalProjectCost(Double totalProjectCost){
        this.totalProjectCost = totalProjectCost;
        return this;
    }

    public String getCooperativeCompany() {
        return cooperativeCompany;
    }

    public ZjProject cooperativeCompany(String cooperativeCompany){
        this.cooperativeCompany = cooperativeCompany;
        return this;
    }

    public void setCooperativeCompany(String cooperativeCompany) {
        this.cooperativeCompany = cooperativeCompany;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZjProject zjProject = (ZjProject) o;
        if (zjProject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProject{" +
            "id=" + getId() +
            ", projectNo='" + getProjectNo() + "'" +
            ", contractId='" + getContractId() + "'" +
            ", contractNo='" + getContractNo() + "'" +
            ", contractName='" + getContractName() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", delegateUnit='" + getDelegateUnit() + "'" +
            ", delegateDate='" + getDelegateDate() + "'" +
            ", delegateDept='" + getDelegateDept() + "'" +
            ", delegateLinkman='" + getDelegateLinkman() + "'" +
            ", requiredDate='" + getRequiredDate() + "'" +
            ", isGovernmentInvest='" + getIsGovernmentInvest() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", projectIndustry='" + getProjectIndustry() + "'" +
            ", projectSource='" + getProjectSource() + "'" +
            ", busiType='" + getBusiType() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", projectScale='" + getProjectScale() + "'" +
            ", costMoney=" + getCostMoney() +
            ", submitMoney=" + getSubmitMoney() +
            ", projectManager='" + getProjectManager() + "'" +
            ", projectManagerName='" + getProjectManagerName() + "'" +
            ", planFinishDate='" + getPlanFinishDate() + "'" +
            ", implementUnit='" + getImplementUnit() + "'" +
            ", contentScope='" + getContentScope() + "'" +
            ", isOutsource='" + getIsOutsource() + "'" +
            ", outsourceDesc='" + getOutsourceDesc() + "'" +
            ", projectProgress='" + getProjectProgress() + "'" +
            ", projectBudget='" + getProjectBudget() + "'" +
            ", needMoney=" + getNeedMoney() +
            ", flowState='" + getFlowState() + "'" +
            ", projectState='" + getProjectState() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", reportConclusion=" + getReportConclusion() +
            ", linkageBranch='" + getLinkageBranch() + "'" +
            ", contractMoney=" + getContractMoney() +
            ", estimatedIncome=" + getEstimatedIncome() +
            ", isCcbClient='" + getIsCcbClient() + "'" +
            ", attentionProjectType='" + getAttentionProjectType() + "'" +
            ", totalProjectCost=" + getTotalProjectCost() +
            ", cityWhere='" + getCityWhere() + "'" +
            ", isCold='" + getIsCold() +"'" +
            ", isGood='" + getIsGood() +"'" +
            ", marketRate=" + getMarketRate() +
            ", cooperativeCompany=" + getCooperativeCompany() +"'" +
            "}";
    }
}
