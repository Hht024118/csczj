package com.thsware.framework.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ZjProject entity. This class is used in ZjProjectResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-projects?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjProjectCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter projectNo;

    private StringFilter contractId;

    private StringFilter contractNo;

    private StringFilter contractName;

    private StringFilter projectName;

    private InstantFilter registerDate;

    private InstantFilter publishDate;

    private StringFilter delegateUnit;

    private InstantFilter delegateDate;

    private StringFilter delegateDept;

    private StringFilter delegateLinkman;

    private InstantFilter requiredDate;

    private StringFilter isGovernmentInvest;

    private StringFilter projectType;

    private StringFilter projectIndustry;

    private StringFilter projectSource;

    private StringFilter busiType;

    private StringFilter specialty;

    private StringFilter projectScale;

    private DoubleFilter costMoney;

    private DoubleFilter submitMoney;

    private StringFilter projectManager;

    private StringFilter projectManagerName;

    private InstantFilter planFinishDate;

    private StringFilter implementUnit;

    private StringFilter contentScope;

    private StringFilter isOutsource;

    private StringFilter outsourceDesc;

    private StringFilter projectProgress;

    private StringFilter projectBudget;

    private DoubleFilter needMoney;

    private StringFilter flowState;

    private StringFilter projectState;

    private StringFilter remark;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter parentId;

    /**
     * 新加字段20190419  --Start--
     */
    private StringFilter linkageBranch;

    private DoubleFilter contractMoney;

    private DoubleFilter reportConclusion;

    private DoubleFilter estimatedIncome;

    private StringFilter isCcbClient;

    private StringFilter attentionProjectType;

    private StringFilter cityWhere;

    private DoubleFilter totalProjectCost;

    private StringFilter cooperativeCompany;

    private StringFilter isCold;

    private StringFilter isGood;

    private DoubleFilter marketRate;

    /**
     * 新加字段  --End--
     */


    public ZjProjectCriteria() {
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(StringFilter projectNo) {
        this.projectNo = projectNo;
    }

    public StringFilter getContractId() {
        return contractId;
    }

    public void setContractId(StringFilter contractId) {
        this.contractId = contractId;
    }

    public StringFilter getContractNo() {
        return contractNo;
    }

    public void setContractNo(StringFilter contractNo) {
        this.contractNo = contractNo;
    }

    public StringFilter getContractName() {
        return contractName;
    }

    public void setContractName(StringFilter contractName) {
        this.contractName = contractName;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public InstantFilter getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(InstantFilter registerDate) {
        this.registerDate = registerDate;
    }

    public InstantFilter getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(InstantFilter publishDate) {
        this.publishDate = publishDate;
    }

    public StringFilter getDelegateUnit() {
        return delegateUnit;
    }

    public void setDelegateUnit(StringFilter delegateUnit) {
        this.delegateUnit = delegateUnit;
    }

    public InstantFilter getDelegateDate() {
        return delegateDate;
    }

    public void setDelegateDate(InstantFilter delegateDate) {
        this.delegateDate = delegateDate;
    }

    public StringFilter getDelegateDept() {
        return delegateDept;
    }

    public void setDelegateDept(StringFilter delegateDept) {
        this.delegateDept = delegateDept;
    }

    public StringFilter getDelegateLinkman() {
        return delegateLinkman;
    }

    public void setDelegateLinkman(StringFilter delegateLinkman) {
        this.delegateLinkman = delegateLinkman;
    }

    public InstantFilter getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(InstantFilter requiredDate) {
        this.requiredDate = requiredDate;
    }

    public StringFilter getIsGovernmentInvest() {
        return isGovernmentInvest;
    }

    public void setIsGovernmentInvest(StringFilter isGovernmentInvest) {
        this.isGovernmentInvest = isGovernmentInvest;
    }

    public StringFilter getProjectType() {
        return projectType;
    }

    public void setProjectType(StringFilter projectType) {
        this.projectType = projectType;
    }

    public StringFilter getProjectIndustry() {
        return projectIndustry;
    }

    public void setProjectIndustry(StringFilter projectIndustry) {
        this.projectIndustry = projectIndustry;
    }

    public StringFilter getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(StringFilter projectSource) {
        this.projectSource = projectSource;
    }

    public StringFilter getBusiType() {
        return busiType;
    }

    public void setBusiType(StringFilter busiType) {
        this.busiType = busiType;
    }

    public StringFilter getSpecialty() {
        return specialty;
    }

    public void setSpecialty(StringFilter specialty) {
        this.specialty = specialty;
    }

    public StringFilter getProjectScale() {
        return projectScale;
    }

    public void setProjectScale(StringFilter projectScale) {
        this.projectScale = projectScale;
    }

    public DoubleFilter getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(DoubleFilter costMoney) {
        this.costMoney = costMoney;
    }

    public DoubleFilter getSubmitMoney() {
        return submitMoney;
    }

    public void setSubmitMoney(DoubleFilter submitMoney) {
        this.submitMoney = submitMoney;
    }

    public StringFilter getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(StringFilter projectManager) {
        this.projectManager = projectManager;
    }

    public StringFilter getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(StringFilter projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public InstantFilter getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(InstantFilter planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public StringFilter getImplementUnit() {
        return implementUnit;
    }

    public void setImplementUnit(StringFilter implementUnit) {
        this.implementUnit = implementUnit;
    }

    public StringFilter getContentScope() {
        return contentScope;
    }

    public void setContentScope(StringFilter contentScope) {
        this.contentScope = contentScope;
    }

    public StringFilter getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(StringFilter isOutsource) {
        this.isOutsource = isOutsource;
    }

    public StringFilter getOutsourceDesc() {
        return outsourceDesc;
    }

    public void setOutsourceDesc(StringFilter outsourceDesc) {
        this.outsourceDesc = outsourceDesc;
    }

    public StringFilter getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(StringFilter projectProgress) {
        this.projectProgress = projectProgress;
    }

    public StringFilter getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(StringFilter projectBudget) {
        this.projectBudget = projectBudget;
    }

    public DoubleFilter getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(DoubleFilter needMoney) {
        this.needMoney = needMoney;
    }

    public StringFilter getFlowState() {
        return flowState;
    }

    public void setFlowState(StringFilter flowState) {
        this.flowState = flowState;
    }

    public StringFilter getProjectState() {
        return projectState;
    }

    public void setProjectState(StringFilter projectState) {
        this.projectState = projectState;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getParentId() {
        return parentId;
    }

    public void setParentId(StringFilter parentId) {
        this.parentId = parentId;
    }

    public StringFilter getLinkageBranch() {
        return linkageBranch;
    }

    public void setLinkageBranch(StringFilter linkageBranch) {
        this.linkageBranch = linkageBranch;
    }

    public DoubleFilter getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(DoubleFilter contractMoney) {
        this.contractMoney = contractMoney;
    }

    public DoubleFilter getReportConclusion() {
        return reportConclusion;
    }

    public void setReportConclusion(DoubleFilter reportConclusion) {
        this.reportConclusion = reportConclusion;
    }

    public DoubleFilter getEstimatedIncome() {
        return estimatedIncome;
    }

    public void setEstimatedIncome(DoubleFilter estimatedIncome) {
        this.estimatedIncome = estimatedIncome;
    }

    public StringFilter getIsCcbClient() {
        return isCcbClient;
    }

    public void setIsCcbClient(StringFilter isCcbClient) {
        this.isCcbClient = isCcbClient;
    }

    public StringFilter getAttentionProjectType() {
        return attentionProjectType;
    }

    public void setAttentionProjectType(StringFilter attentionProjectType) {
        this.attentionProjectType = attentionProjectType;
    }

    public StringFilter getCityWhere() {
        return cityWhere;
    }

    public void setCityWhere(StringFilter cityWhere) {
        this.cityWhere = cityWhere;
    }

    public DoubleFilter getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(DoubleFilter totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public StringFilter getCooperativeCompany() {
        return cooperativeCompany;
    }

    public void setCooperativeCompany(StringFilter cooperativeCompany) {
        this.cooperativeCompany = cooperativeCompany;
    }

    public StringFilter getIsCold() {
        return isCold;
    }

    public void setIsCold(StringFilter isCold) {
        this.isCold = isCold;
    }

    public StringFilter getIsGood() {
        return isGood;
    }

    public void setIsGood(StringFilter isGood) {
        this.isGood = isGood;
    }

    public DoubleFilter getMarketRate() {
        return marketRate;
    }

    public void setMarketRate(DoubleFilter marketRate) {
        this.marketRate = marketRate;
    }

    @Override
    public String toString() {
        return "ZjProjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectNo != null ? "projectNo=" + projectNo + ", " : "") +
                (contractId != null ? "contractId=" + contractId + ", " : "") +
                (contractNo != null ? "contractNo=" + contractNo + ", " : "") +
                (contractName != null ? "contractName=" + contractName + ", " : "") +
                (projectName != null ? "projectName=" + projectName + ", " : "") +
                (registerDate != null ? "registerDate=" + registerDate + ", " : "") +
                (publishDate != null ? "publishDate=" + publishDate + ", " : "") +
                (delegateUnit != null ? "delegateUnit=" + delegateUnit + ", " : "") +
                (delegateDate != null ? "delegateDate=" + delegateDate + ", " : "") +
                (delegateDept != null ? "delegateDept=" + delegateDept + ", " : "") +
                (delegateLinkman != null ? "delegateLinkman=" + delegateLinkman + ", " : "") +
                (requiredDate != null ? "requiredDate=" + requiredDate + ", " : "") +
                (isGovernmentInvest != null ? "isGovernmentInvest=" + isGovernmentInvest + ", " : "") +
                (projectType != null ? "projectType=" + projectType + ", " : "") +
                (projectIndustry != null ? "projectIndustry=" + projectIndustry + ", " : "") +
                (projectSource != null ? "projectSource=" + projectSource + ", " : "") +
                (busiType != null ? "busiType=" + busiType + ", " : "") +
                (specialty != null ? "specialty=" + specialty + ", " : "") +
                (projectScale != null ? "projectScale=" + projectScale + ", " : "") +
                (costMoney != null ? "costMoney=" + costMoney + ", " : "") +
                (submitMoney != null ? "submitMoney=" + submitMoney + ", " : "") +
                (projectManager != null ? "projectManager=" + projectManager + ", " : "") +
                (projectManagerName != null ? "projectManagerName=" + projectManagerName + ", " : "") +
                (planFinishDate != null ? "planFinishDate=" + planFinishDate + ", " : "") +
                (implementUnit != null ? "implementUnit=" + implementUnit + ", " : "") +
                (contentScope != null ? "contentScope=" + contentScope + ", " : "") +
                (isOutsource != null ? "isOutsource=" + isOutsource + ", " : "") +
                (outsourceDesc != null ? "outsourceDesc=" + outsourceDesc + ", " : "") +
                (projectProgress != null ? "projectProgress=" + projectProgress + ", " : "") +
                (projectBudget != null ? "projectBudget=" + projectBudget + ", " : "") +
                (needMoney != null ? "needMoney=" + needMoney + ", " : "") +
                (flowState != null ? "flowState=" + flowState + ", " : "") +
                (projectState != null ? "projectState=" + projectState + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (linkageBranch != null ? "linkageBranch=" + linkageBranch + ", " : "") +
                (contractMoney != null ? "contractMoney=" + contractMoney + ", " : "") +
                (reportConclusion != null ? "reportConclusion=" + reportConclusion + ", " : "") +
                (estimatedIncome != null ? "estimatedIncome=" + estimatedIncome + ", " : "") +
                (isCcbClient != null ? "isCcbClient=" + isCcbClient + ", " : "") +
                (attentionProjectType != null ? "attentionProjectType=" + attentionProjectType + ", " : "") +
                (cityWhere != null ? "cityWhere=" + cityWhere + ", " : "") +
                (totalProjectCost != null ? "totalProjectCost=" + totalProjectCost + ", " : "") +
                (cooperativeCompany != null ? "cooperativeCompany=" + cooperativeCompany + ", " : "") +
            (isCold != null ? "isCold=" + isCold + ", " : "") +
            (isGood != null ? "isGood=" + isGood + ", " : "") +
            (marketRate != null ? "marketRate=" + marketRate + ", " : "") +
            "}";
    }

}
