package com.thsware.framework.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SysBusiform entity.
 */
public class SysBusiformDTO implements Serializable {

    private String id;

    @Size(max = 40)
    private String applyNum;

    @Size(max = 10)
    private String busiType;

    private Integer year;

    private Integer month;

    @Size(max = 40)
    private String busiId;

    @Size(max = 50)
    private String busiNo;

    @Size(max = 200)
    private String busiName;

    @Size(max = 36)
    private String contractId;

    @Size(max = 50)
    private String contractNo;

    @Size(max = 200)
    private String contractName;

    private Double needMoney;

    private Instant applicantDate;

    @Size(max = 100)
    private String applicantDept;

    @Size(max = 50)
    private String applicantLinkman;

    @Size(max = 100)
    private String applicantName;

    @Size(max = 100)
    private String ownerDept;

    @Size(max = 100)
    private String applyName;

    private Instant applyTime;

    @Size(max = 40)
    private String flowId;

    @Size(max = 40)
    private String flowInstanceId;

    @Size(max = 40)
    private String stepInstanceId;

    @Size(max = 50)
    private String flowState;

    @Size(max = 8)
    private String busiState;

    private Instant suspendTime;

    private Instant unsuspendTime;

    private Integer timeLimit;

    private Integer remainDays;

    @Size(max = 8)
    private String isArchived;

    @Size(max = 8)
    private String isQuit;

    @Size(max = 40)
    private String multiTenancyId;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;


    @Size(max = 50)
    private String projectLeader;

    @Size(max = 50)
    private String projectLeaderName;

    private Instant requiredDate;

    @Size(max = 200)
    private String specialty;

    @Size(max = 1)
    private String isOutsource;

    @Size(max = 50)
    private String implementUnit;

    @Size(max = 100)
    private String busiDomain;

    private Instant publishDate;

    public String getProjectLeader() {return projectLeader;}

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public  String getProjectLeaderName() {
        return  projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiNo() {
        return busiNo;
    }

    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
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

    public Double getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(Double needMoney) {
        this.needMoney = needMoney;
    }

    public Instant getApplicantDate() {
        return applicantDate;
    }

    public void setApplicantDate(Instant applicantDate) {
        this.applicantDate = applicantDate;
    }

    public String getApplicantDept() {
        return applicantDept;
    }

    public void setApplicantDept(String applicantDept) {
        this.applicantDept = applicantDept;
    }

    public String getApplicantLinkman() {
        return applicantLinkman;
    }

    public void setApplicantLinkman(String applicantLinkman) {
        this.applicantLinkman = applicantLinkman;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Instant getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Instant applyTime) {
        this.applyTime = applyTime;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
    }

    public String getStepInstanceId() {
        return stepInstanceId;
    }

    public void setStepInstanceId(String stepInstanceId) {
        this.stepInstanceId = stepInstanceId;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getBusiState() {
        return busiState;
    }

    public void setBusiState(String busiState) {
        this.busiState = busiState;
    }

    public Instant getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(Instant suspendTime) {
        this.suspendTime = suspendTime;
    }

    public Instant getUnsuspendTime() {
        return unsuspendTime;
    }

    public void setUnsuspendTime(Instant unsuspendTime) {
        this.unsuspendTime = unsuspendTime;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }

    public String getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(String isArchived) {
        this.isArchived = isArchived;
    }

    public String getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(String isQuit) {
        this.isQuit = isQuit;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
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

    public Instant getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Instant requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
    }

    public String getImplementUnit() {
        return implementUnit;
    }

    public void setImplementUnit(String implementUnit) {
        this.implementUnit = implementUnit;
    }

    public String getBusiDomain() {
        return busiDomain;
    }

    public void setBusiDomain(String busiDomain) {
        this.busiDomain = busiDomain;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysBusiformDTO sysBusiformDTO = (SysBusiformDTO) o;
        if (sysBusiformDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysBusiformDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysBusiformDTO{" +
            "id=" + getId() +
            ", applyNum='" + getApplyNum() + "'" +
            ", busiType='" + getBusiType() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", busiId='" + getBusiId() + "'" +
            ", busiNo='" + getBusiNo() + "'" +
            ", busiName='" + getBusiName() + "'" +
            ", contractId='" + getContractId() + "'" +
            ", contractNo='" + getContractNo() + "'" +
            ", contractName='" + getContractName() + "'" +
            ", needMoney=" + getNeedMoney() +
            ", applicantDate='" + getApplicantDate() + "'" +
            ", applicantDept='" + getApplicantDept() + "'" +
            ", applicantLinkman='" + getApplicantLinkman() + "'" +
            ", applicantName='" + getApplicantName() + "'" +
            ", ownerDept='" + getOwnerDept() + "'" +
            ", applyName='" + getApplyName() + "'" +
            ", applyTime='" + getApplyTime() + "'" +
            ", flowId='" + getFlowId() + "'" +
            ", flowInstanceId='" + getFlowInstanceId() + "'" +
            ", stepInstanceId='" + getStepInstanceId() + "'" +
            ", flowState='" + getFlowState() + "'" +
            ", busiState='" + getBusiState() + "'" +
            ", suspendTime='" + getSuspendTime() + "'" +
            ", unsuspendTime='" + getUnsuspendTime() + "'" +
            ", timeLimit=" + getTimeLimit() +
            ", remainDays=" + getRemainDays() +
            ", isArchived='" + getIsArchived() + "'" +
            ", isQuit='" + getIsQuit() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", requiredDate='" + getRequiredDate() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", isOutsource='" + getIsOutsource() + "'" +
            ", implementUnit='" + getImplementUnit() + "'" +
            ", busiDomain='" + getBusiDomain() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            "}";
    }
}
