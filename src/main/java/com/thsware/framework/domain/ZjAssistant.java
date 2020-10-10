package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A ZjAssistant.
 */
@Entity
@Table(name = "zj_assistant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjAssistant extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_specialty_id", length = 36)
    private String zjSpecialtyId;

    @Size(max = 36)
    @Column(name = "person_id", length = 36)
    private String personId;

    @Size(max = 50)
    @Column(name = "person_name", length = 50)
    private String personName;

    @Column(name = "workload_rate")
    private Double workloadRate;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public ZjAssistant zjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
        return this;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getPersonId() {
        return personId;
    }

    public ZjAssistant personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public ZjAssistant personName(String personName) {
        this.personName = personName;
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Double getWorkloadRate() {
        return workloadRate;
    }

    public ZjAssistant workloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
        return this;
    }

    public void setWorkloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
    }

    public String getRemark() {
        return remark;
    }

    public ZjAssistant remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZjAssistant createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ZjAssistant createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZjAssistant lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ZjAssistant lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public ZjAssistant multiTenancyId(String multiTenancyId) {
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
        ZjAssistant zjAssistant = (ZjAssistant) o;
        if (zjAssistant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjAssistant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjAssistant{" +
            "id=" + getId() +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", workloadRate=" + getWorkloadRate() +
            ", remark='" + getRemark() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
