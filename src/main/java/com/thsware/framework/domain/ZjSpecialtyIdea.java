package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 造价咨询-工程审批意见表
 */
@ApiModel(description = "造价咨询-工程审批意见表")
@Entity
@Table(name = "zj_specialty_idea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjSpecialtyIdea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_project_id", length = 36)
    private String zjProjectId;

    @Size(max = 200)
    @Column(name = "zj_specialty_id", length = 200)
    private String zjSpecialtyId;

    @Size(max = 50)
    @Column(name = "audit_type", length = 50)
    private String auditType;

    @Size(max = 36)
    @Column(name = "auditer_id", length = 36)
    private String auditerId;

    @Size(max = 200)
    @Column(name = "auditer_name", length = 200)
    private String auditerName;

    @Size(max = 1)
    @Column(name = "audit_result", length = 1)
    private String auditResult;

    @Size(max = 200)
    @Column(name = "audit_idea", length = 200)
    private String auditIdea;

    @Column(name = "audit_date")
    private Instant auditDate;

    @Size(max = 10)
    @Column(name = "is_history", length = 10)
    private String isHistory;

    @Size(max = 1)
    @Column(name = "valid_ejsh", length = 1)
    private String validEjsh;

    @ThsMultiTenancyId
    @Size(max = 40)
    @Column(name="multi_tenancy_id",length = 40)
    private String multiTenancyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjProjectId() {
        return zjProjectId;
    }

    public ZjSpecialtyIdea zjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
        return this;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public ZjSpecialtyIdea zjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
        return this;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getAuditType() {
        return auditType;
    }

    public ZjSpecialtyIdea auditType(String auditType) {
        this.auditType = auditType;
        return this;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditerId() {
        return auditerId;
    }

    public ZjSpecialtyIdea auditerId(String auditerId) {
        this.auditerId = auditerId;
        return this;
    }

    public void setAuditerId(String auditerId) {
        this.auditerId = auditerId;
    }

    public String getAuditerName() {
        return auditerName;
    }

    public ZjSpecialtyIdea auditerName(String auditerName) {
        this.auditerName = auditerName;
        return this;
    }

    public void setAuditerName(String auditerName) {
        this.auditerName = auditerName;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public ZjSpecialtyIdea auditResult(String auditResult) {
        this.auditResult = auditResult;
        return this;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public ZjSpecialtyIdea auditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
        return this;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Instant getAuditDate() {
        return auditDate;
    }

    public ZjSpecialtyIdea auditDate(Instant auditDate) {
        this.auditDate = auditDate;
        return this;
    }

    public void setAuditDate(Instant auditDate) {
        this.auditDate = auditDate;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public ZjSpecialtyIdea isHistory(String isHistory) {
        this.isHistory = isHistory;
        return this;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }

    public String getValidEjsh() {
        return validEjsh;
    }

    public ZjSpecialtyIdea validEjsh(String validEjsh) {
        this.validEjsh = validEjsh;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjSpecialtyIdea multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public void setValidEjsh(String validEjsh) {
        this.validEjsh = validEjsh;
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
        ZjSpecialtyIdea zjSpecialtyIdea = (ZjSpecialtyIdea) o;
        if (zjSpecialtyIdea.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialtyIdea.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialtyIdea{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", auditType='" + getAuditType() + "'" +
            ", auditerId='" + getAuditerId() + "'" +
            ", auditerName='" + getAuditerName() + "'" +
            ", auditResult='" + getAuditResult() + "'" +
            ", auditIdea='" + getAuditIdea() + "'" +
            ", auditDate='" + getAuditDate() + "'" +
            ", isHistory='" + getIsHistory() + "'" +
            ", validEjsh='" + getValidEjsh() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
