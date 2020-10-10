package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ZjProjectChangelog.
 */
@Entity
@Table(name = "zj_project_changelog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjProjectChangelog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "project_id", length = 36)
    private String projectId;

    @Size(max = 36)
    @Column(name = "specialty_id", length = 36)
    private String specialtyId;

    @Size(max = 20)
    @Column(name = "change_type", length = 20)
    private String changeType;

    @Column(name = "change_version")
    private Integer changeVersion;

    @Size(max = 1000)
    @Column(name = "change_reason", length = 1000)
    private String changeReason;

    @Column(name = "change_time")
    private Instant changeTime;

    @Size(max = 36)
    @Column(name = "old_field_id", length = 36)
    private String oldFieldId;

    @Size(max = 200)
    @Column(name = "old_field_text", length = 200)
    private String oldFieldText;

    @Size(max = 36)
    @Column(name = "new_field_id", length = 36)
    private String newFieldId;

    @Size(max = 200)
    @Column(name = "new_field_text", length = 200)
    private String newFieldText;

    @Size(max = 200)
    @Column(name = "current_business_state", length = 50)
    private String currentBusinessState;

    @Size(max = 50)
    @Column(name = "flow_state", length = 50)
    private String flowState;

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
    @Column(name = "multi_tenancy_id", length = 40)
    private String multiTenancyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public ZjProjectChangelog projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSpecialtyId() {
        return specialtyId;
    }

    public ZjProjectChangelog specialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
        return this;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getChangeType() {
        return changeType;
    }

    public ZjProjectChangelog changeType(String changeType) {
        this.changeType = changeType;
        return this;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getChangeVersion() {
        return changeVersion;
    }

    public ZjProjectChangelog changeVersion(Integer changeVersion) {
        this.changeVersion = changeVersion;
        return this;
    }

    public void setChangeVersion(Integer changeVersion) {
        this.changeVersion = changeVersion;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public ZjProjectChangelog changeReason(String changeReason) {
        this.changeReason = changeReason;
        return this;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Instant getChangeTime() {
        return changeTime;
    }

    public ZjProjectChangelog changeTime(Instant changeTime) {
        this.changeTime = changeTime;
        return this;
    }

    public void setChangeTime(Instant changeTime) {
        this.changeTime = changeTime;
    }

    public String getOldFieldId() {
        return oldFieldId;
    }

    public ZjProjectChangelog oldFieldId(String oldFieldId) {
        this.oldFieldId = oldFieldId;
        return this;
    }

    public void setOldFieldId(String oldFieldId) {
        this.oldFieldId = oldFieldId;
    }

    public String getOldFieldText() {
        return oldFieldText;
    }

    public ZjProjectChangelog oldFieldText(String oldFieldText) {
        this.oldFieldText = oldFieldText;
        return this;
    }

    public void setOldFieldText(String oldFieldText) {
        this.oldFieldText = oldFieldText;
    }

    public String getNewFieldId() {
        return newFieldId;
    }

    public ZjProjectChangelog newFieldId(String newFieldId) {
        this.newFieldId = newFieldId;
        return this;
    }

    public void setNewFieldId(String newFieldId) {
        this.newFieldId = newFieldId;
    }

    public String getNewFieldText() {
        return newFieldText;
    }

    public ZjProjectChangelog newFieldText(String newFieldText) {
        this.newFieldText = newFieldText;
        return this;
    }

    public void setNewFieldText(String newFieldText) {
        this.newFieldText = newFieldText;
    }

    public String getCurrentBusinessState() {
        return currentBusinessState;
    }

    public ZjProjectChangelog currentBusinessState(String currentBusinessState) {
        this.currentBusinessState = currentBusinessState;
        return this;
    }

    public void setCurrentBusinessState(String currentBusinessState) {
        this.currentBusinessState = currentBusinessState;
    }

    public String getFlowState() {
        return flowState;
    }

    public ZjProjectChangelog flowState(String flowState) {
        this.flowState = flowState;
        return this;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getRemark() {
        return remark;
    }

    public ZjProjectChangelog remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZjProjectChangelog createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ZjProjectChangelog createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZjProjectChangelog lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ZjProjectChangelog lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public ZjProjectChangelog multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
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
        ZjProjectChangelog zjProjectChangelog = (ZjProjectChangelog) o;
        if (zjProjectChangelog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProjectChangelog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProjectChangelog{" +
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
