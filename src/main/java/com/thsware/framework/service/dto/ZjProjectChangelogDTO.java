package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjProjectChangelog entity.
 */
public class ZjProjectChangelogDTO   extends  WfParamDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String projectId;

    @Size(max = 36)
    private String specialtyId;

    @Size(max = 20)
    private String changeType;

    private Integer changeVersion;

    @Size(max = 1000)
    private String changeReason;

    private Instant changeTime;

    @Size(max = 36)
    private String oldFieldId;

    @Size(max = 200)
    private String oldFieldText;

    @Size(max = 36)
    private String newFieldId;

    @Size(max = 200)
    private String newFieldText;

    @Size(max = 50)
    private String currentBusinessState;

    @Size(max = 50)
    private String flowState;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getChangeVersion() {
        return changeVersion;
    }

    public void setChangeVersion(Integer changeVersion) {
        this.changeVersion = changeVersion;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Instant getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Instant changeTime) {
        this.changeTime = changeTime;
    }

    public String getOldFieldId() {
        return oldFieldId;
    }

    public void setOldFieldId(String oldFieldId) {
        this.oldFieldId = oldFieldId;
    }

    public String getOldFieldText() {
        return oldFieldText;
    }

    public void setOldFieldText(String oldFieldText) {
        this.oldFieldText = oldFieldText;
    }

    public String getNewFieldId() {
        return newFieldId;
    }

    public void setNewFieldId(String newFieldId) {
        this.newFieldId = newFieldId;
    }

    public String getNewFieldText() {
        return newFieldText;
    }

    public void setNewFieldText(String newFieldText) {
        this.newFieldText = newFieldText;
    }

   
   public String getCurrentBusinessState() {
        return currentBusinessState;
    }

    public void setCurrentBusinessState(String currentBusinessState) {
        this.currentBusinessState = currentBusinessState;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
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

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjProjectChangelogDTO zjProjectChangelogDTO = (ZjProjectChangelogDTO) o;
        if (zjProjectChangelogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProjectChangelogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProjectChangelogDTO{" +
            "id=" + getId() +
            ", projectId='" + getProjectId() + "'" +
            ", specialtyId='" + getSpecialtyId() + "'" +
            ", changeType='" + getChangeType() + "'" +
            ", changeVersion=" + getChangeVersion() +
            ", changeReason='" + getChangeReason() + "'" +
            ", changeTime='" + getChangeTime() + "'" +
            ", oldFieldId='" + getOldFieldId() + "'" +
            ", oldFieldText='" + getOldFieldText() + "'" +
            ", newFieldId='" + getNewFieldId() + "'" +
            ", newFieldText='" + getNewFieldText() + "'" +
            ", currentBusinessState='" + getCurrentBusinessState() + "'" +
            ", flowState='" + getFlowState() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
