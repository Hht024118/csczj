package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the ZjSpecialty entity.
 */
public class ZjSpecialtyDTO extends WfParamDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjProjectId;

    @Size(max = 36)
    private String zjPublishId;

    @Size(max = 200)
    private String engineeringName;

    @Size(max = 200)
    private String specialtyType;

    @Size(max = 36)
    private String establishmentPersonId;

    @Size(max = 20)
    private String establishmentPersonName;

    private Double workloadRate;

    private Double establishmentMoney;

    private Double approvalMoney;

    private Double creaseRate;

    private Double submitMoney;

    private Instant beginDate;

    private Instant endDate;

    private Instant actualFinishDate;

    private Integer planDuration;

    @Size(max = 20)
    private String state;

    @Size(max = 20)
    private String reject;

    private Double firstTrialMoney;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @Size(max = 40)
    private String multiTenancyId;

    private String saveType;

    private List<ZjSpecialtyAuditerDTO> zjSpecialtyAuditerDTOList;

    private List<ZjAssistantDTO> zjAssistantList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjProjectId() {
        return zjProjectId;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getZjPublishId() {
        return zjPublishId;
    }

    public void setZjPublishId(String zjPublishId) {
        this.zjPublishId = zjPublishId;
    }

    public String getEngineeringName() {
        return engineeringName;
    }

    public void setEngineeringName(String engineeringName) {
        this.engineeringName = engineeringName;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getEstablishmentPersonId() {
        return establishmentPersonId;
    }

    public void setEstablishmentPersonId(String establishmentPersonId) {
        this.establishmentPersonId = establishmentPersonId;
    }

    public String getEstablishmentPersonName() {
        return establishmentPersonName;
    }

    public void setEstablishmentPersonName(String establishmentPersonName) {
        this.establishmentPersonName = establishmentPersonName;
    }

    public Double getWorkloadRate() {
        return workloadRate;
    }

    public void setWorkloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
    }

    public Double getEstablishmentMoney() {
        return establishmentMoney;
    }

    public void setEstablishmentMoney(Double establishmentMoney) {
        this.establishmentMoney = establishmentMoney;
    }

    public Double getApprovalMoney() {
        return approvalMoney;
    }

    public void setApprovalMoney(Double approvalMoney) {
        this.approvalMoney = approvalMoney;
    }

    public Double getCreaseRate() {
        return creaseRate;
    }

    public void setCreaseRate(Double creaseRate) {
        this.creaseRate = creaseRate;
    }

    public Double getSubmitMoney() {
        return submitMoney;
    }

    public void setSubmitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Instant beginDate) {
        this.beginDate = beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getPlanDuration() {
        return planDuration;
    }

    public void setPlanDuration(Integer planDuration) {
        this.planDuration = planDuration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
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

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public Instant getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public List<ZjSpecialtyAuditerDTO> getZjSpecialtyAuditerDTOList() {
        return zjSpecialtyAuditerDTOList;
    }

    public void setZjSpecialtyAuditerDTOList(List<ZjSpecialtyAuditerDTO> zjSpecialtyAuditerDTOList) {
        this.zjSpecialtyAuditerDTOList = zjSpecialtyAuditerDTOList;
    }

    public Double getFirstTrialMoney() {
        return firstTrialMoney;
    }

    public void setFirstTrialMoney(Double firstTrialMoney) {
        this.firstTrialMoney = firstTrialMoney;
    }

    public List<ZjAssistantDTO> getZjAssistantList() {
        return zjAssistantList;
    }

    public void setZjAssistantList(List<ZjAssistantDTO> zjAssistantList) {
        this.zjAssistantList = zjAssistantList;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjSpecialtyDTO zjSpecialtyDTO = (ZjSpecialtyDTO) o;
        if (zjSpecialtyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialtyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialtyDTO{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", zjPublishId='" + getZjPublishId() + "'" +
            ", engineeringName='" + getEngineeringName() + "'" +
            ", specialtyType='" + getSpecialtyType() + "'" +
            ", establishmentPersonId='" + getEstablishmentPersonId() + "'" +
            ", establishmentPersonName='" + getEstablishmentPersonName() + "'" +
            ", workloadRate=" + getWorkloadRate() +
            ", establishmentMoney=" + getEstablishmentMoney() +
            ", approvalMoney=" + getApprovalMoney() +
            ", creaseRate=" + getCreaseRate() +
            ", submitMoney=" + getSubmitMoney() +
            ", beginDate='" + getBeginDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", planDuration=" + getPlanDuration() +
            ", state='" + getState() + "'" +
            ", reject='" + getReject() + "'" +
            ", firstTrialMoney=" + getFirstTrialMoney() +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate=" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate=" + getLastModifiedDate() + "'" +
            ", actualFinishDate='" + getActualFinishDate() + "'" +
            ", zjSpecialtyAuditerDTOList=" + getZjSpecialtyAuditerDTOList() +
            ", saveType=" + getSaveType() +
            "}";
    }
}
