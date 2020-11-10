package com.thsware.framework.service.dto;

import com.thsware.framework.domain.ZjProject;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the ZjProject entity.
 */
public class ZjProjectDTO  extends  WfParamDTO implements Serializable {

    private String id;

    @Size(max = 500)
    private String implementDept;

    @Size(max = 50)
    private String projectNo;

    @Size(max = 36)
    private String contractId;

    @Size(max = 50)
    private String contractNo;

    @Size(max = 200)
    private String contractName;

    @Size(max = 200)
    private String projectName;

    private Instant registerDate;

    private Instant publishDate;

    @Size(max = 50)
    private String delegateUnit;

    private Instant delegateDate;

    @Size(max = 50)
    private String delegateDept;

    @Size(max = 50)
    private String delegateLinkman;

    private Instant requiredDate;

    @Size(max = 1)
    private String isGovernmentInvest;

    @Size(max = 20)
    private String projectType;

    @Size(max = 50)
    private String projectIndustry;

    @Size(max = 50)
    private String projectSource;

    @Size(max = 50)
    private String busiType;

    @Size(max = 500)
    private String specialty;

    @Size(max = 500)
    private String projectScale;

    private Double costMoney;

    private Double submitMoney;

    @Size(max = 50)
    private String projectManager;

    @Size(max = 50)
    private String projectManagerName;

    private Instant planFinishDate;

    @Size(max = 50)
    private String implementUnit;

    @Size(max = 200)
    private String contentScope;

    @Size(max = 1)
    private String isOutsource;

    @Size(max = 200)
    private String outsourceDesc;

    @Size(max = 200)
    private String projectProgress;

    @Size(max = 200)
    private String projectBudget;

    private Double needMoney;

    @Size(max = 50)
    private String flowState;

    @Size(max = 10)
    private String projectState;

    @Size(max = 500)
    private String remark;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @Size(max = 40)
    private String multiTenancyId;

    @Size(max = 36)
    private String parentId;

    /**
     * 新加字段20190419  --Start--
     */
    @Size(max = 50)
    private String linkageBranch;

    private Double contractMoney;

    private Double reportConclusion;

    private Double estimatedIncome;

    @Size(max = 1)
    private String isCcbClient;

    @Size(max = 20)
    private String attentionProjectType;

    @Size(max = 50)
    private String cityWhere;

    private Double totalProjectCost;

    @Size(max = 50)
    private String isCold;

    @Size(max = 50)
    private String isGood;

    private Double marketRate;

    /**
     * 新加字段  --End--
     */

    @Size(max = 100)
    private String cooperativeCompany;

    private List<ZjMemberDTO> zjMemberList;

    private List<ZjSpecialtyDTO> zjSpecialtyList;

    private List<ZjProjectDTO> zjProjectDTOList;

    private String saveType;

    private List<String> projectList;

    private Map<String,Object> auditersMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    public String getDelegateUnit() {
        return delegateUnit;
    }

    public void setDelegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
    }

    public Instant getDelegateDate() {
        return delegateDate;
    }

    public void setDelegateDate(Instant delegateDate) {
        this.delegateDate = delegateDate;
    }

    public String getDelegateDept() {
        return delegateDept;
    }

    public void setDelegateDept(String delegateDept) {
        this.delegateDept = delegateDept;
    }

    public String getDelegateLinkman() {
        return delegateLinkman;
    }

    public void setDelegateLinkman(String delegateLinkman) {
        this.delegateLinkman = delegateLinkman;
    }

    public Instant getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Instant requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getIsGovernmentInvest() {
        return isGovernmentInvest;
    }

    public void setIsGovernmentInvest(String isGovernmentInvest) {
        this.isGovernmentInvest = isGovernmentInvest;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectIndustry() {
        return projectIndustry;
    }

    public void setProjectIndustry(String projectIndustry) {
        this.projectIndustry = projectIndustry;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getProjectScale() {
        return projectScale;
    }

    public void setProjectScale(String projectScale) {
        this.projectScale = projectScale;
    }

    public Double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Double costMoney) {
        this.costMoney = costMoney;
    }

    public Double getSubmitMoney() {
        return submitMoney;
    }

    public void setSubmitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public Instant getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(Instant planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public String getImplementUnit() {
        return implementUnit;
    }

    public void setImplementUnit(String implementUnit) {
        this.implementUnit = implementUnit;
    }

    public String getContentScope() {
        return contentScope;
    }

    public void setContentScope(String contentScope) {
        this.contentScope = contentScope;
    }

    public String getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
    }

    public String getOutsourceDesc() {
        return outsourceDesc;
    }

    public void setOutsourceDesc(String outsourceDesc) {
        this.outsourceDesc = outsourceDesc;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(String projectBudget) {
        this.projectBudget = projectBudget;
    }

    public Double getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(Double needMoney) {
        this.needMoney = needMoney;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<ZjMemberDTO> getZjMemberList() {
        return zjMemberList;
    }

    public void setZjMemberList( List<ZjMemberDTO> zjMemberList) {
        this.zjMemberList = zjMemberList;
    }

    public List<ZjSpecialtyDTO> getZjSpecialtyList() {
        return zjSpecialtyList;
    }

    public void setZjSpecialtyList( List<ZjSpecialtyDTO> zjSpecialtyList) {
        this.zjSpecialtyList = zjSpecialtyList;
    }

    public List<ZjProjectDTO> getZjProjectDTOList() {
        return zjProjectDTOList;
    }

    public void setZjProjectDTOList(List<ZjProjectDTO> zjProjectDTOList) {
        this.zjProjectDTOList = zjProjectDTOList;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLinkageBranch() {
        return linkageBranch;
    }

    public void setLinkageBranch(String linkageBranch) {
        this.linkageBranch = linkageBranch;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public Double getReportConclusion() {
        return reportConclusion;
    }

    public void setReportConclusion(Double reportConclusion) {
        this.reportConclusion = reportConclusion;
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

    public String getAttentionProjectType() {
        return attentionProjectType;
    }

    public void setAttentionProjectType(String attentionProjectType) {
        this.attentionProjectType = attentionProjectType;
    }

    public String getCityWhere() {
        return cityWhere;
    }

    public void setCityWhere(String cityWhere) {
        this.cityWhere = cityWhere;
    }

    public Double getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(Double totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public List<String> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<String> projectList) {
        this.projectList = projectList;
    }

    public Map<String,Object> getAuditersMap() {
        return auditersMap;
    }

    public void setAuditersMap(Map<String,Object> auditersMap) {
        this.auditersMap = auditersMap;
    }

    public String getCooperativeCompany() {
        return cooperativeCompany;
    }

    public void setCooperativeCompany(String cooperativeCompany) {
        this.cooperativeCompany = cooperativeCompany;
    }

    public String getIsCold() {
        return isCold;
    }

    public void setIsCold(String isCold) {
        this.isCold = isCold;
    }

    public String getIsGood() {
        return isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    public Double getMarketRate() {
        return marketRate;
    }

    public void setMarketRate(Double marketRate) {
        this.marketRate = marketRate;
    }

    public String getImplementDept() {
        return implementDept;
    }

    public void setImplementDept(String implementDept) {
        this.implementDept = implementDept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjProjectDTO zjProjectDTO = (ZjProjectDTO) o;
        if (zjProjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProjectDTO{" +
            "id='" + id + '\'' +
            ", projectNo='" + projectNo + '\'' +
            ", contractId='" + contractId + '\'' +
            ", contractNo='" + contractNo + '\'' +
            ", contractName='" + contractName + '\'' +
            ", projectName='" + projectName + '\'' +
            ", registerDate=" + registerDate +
            ", publishDate=" + publishDate +
            ", delegateUnit='" + delegateUnit + '\'' +
            ", delegateDate=" + delegateDate +
            ", delegateDept='" + delegateDept + '\'' +
            ", delegateLinkman='" + delegateLinkman + '\'' +
            ", requiredDate=" + requiredDate +
            ", isGovernmentInvest='" + isGovernmentInvest + '\'' +
            ", projectType='" + projectType + '\'' +
            ", projectIndustry='" + projectIndustry + '\'' +
            ", projectSource='" + projectSource + '\'' +
            ", busiType='" + busiType + '\'' +
            ", specialty='" + specialty + '\'' +
            ", projectScale='" + projectScale + '\'' +
            ", costMoney=" + costMoney +
            ", submitMoney=" + submitMoney +
            ", projectManager='" + projectManager + '\'' +
            ", projectManagerName='" + projectManagerName + '\'' +
            ", planFinishDate=" + planFinishDate +
            ", implementUnit='" + implementUnit + '\'' +
            ", contentScope='" + contentScope + '\'' +
            ", isOutsource='" + isOutsource + '\'' +
            ", outsourceDesc='" + outsourceDesc + '\'' +
            ", projectProgress='" + projectProgress + '\'' +
            ", projectBudget='" + projectBudget + '\'' +
            ", needMoney=" + needMoney +
            ", flowState='" + flowState + '\'' +
            ", projectState='" + projectState + '\'' +
            ", remark='" + remark + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", zjMemberList=" + zjMemberList +
            ", zjSpecialtyList=" + zjSpecialtyList +
            ", zjProjectDTOList=" + zjProjectDTOList +
            ", saveType='" + saveType + '\'' +
            ", totalProjectCost=" + totalProjectCost +
            ", cityWhere='" + cityWhere +  '\'' +
            ", multiTenancyId='" + multiTenancyId +  '\'' +
            ", parentId='" + parentId +  '\'' +
            ", linkageBranch='" + linkageBranch +  '\'' +
            ", contractMoney=" + contractMoney +
            ", reportConclusion=" + reportConclusion +
            ", estimatedIncome=" + estimatedIncome +
            ", isCcbClient='" + isCcbClient +  '\'' +
            ", attentionProjectType='" + attentionProjectType +  '\'' +
            ", projectList='" + projectList +  '\'' +
            ", auditersMap='" + auditersMap +  '\'' +
            ", isCold='" + isCold +  '\'' +
            ", isGood='" + isGood +  '\'' +
            ", marketRate=" + marketRate +
            ", cooperativeCompany='" + cooperativeCompany +  '\'' +
            ", implementDept='" + implementDept +  '\'' +
            '}';
    }
}
