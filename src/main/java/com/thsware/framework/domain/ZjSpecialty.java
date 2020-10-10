package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
import io.swagger.annotations.ApiModel;
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

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * 造价咨询_专业工程
 */
@ApiModel(description = "造价咨询_专业工程")
@Entity
@Table(name = "zj_specialty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjSpecialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_project_id", length = 36)
    private String zjProjectId;

    @Size(max = 36)
    @Column(name = "zj_publish_id", length = 36)
    private String zjPublishId;

    @Size(max = 200)
    @Column(name = "engineering_name", length = 200)
    private String engineeringName;

    @Size(max = 200)
    @Column(name = "specialty_type", length = 200)
    private String specialtyType;

    @Size(max = 36)
    @Column(name = "establishment_person_id", length = 20)
    private String establishmentPersonId;

    @Size(max = 20)
    @Column(name = "establishment_person_name", length = 20)
    private String establishmentPersonName;

    @Column(name = "workload_rate")
    private Double workloadRate;

    @Column(name = "establishment_money")
    private Double establishmentMoney;

    @Column(name = "approval_money")
    private Double approvalMoney;

    @Column(name = "crease_rate")
    private Double creaseRate;

    @Column(name = "submit_money")
    private Double submitMoney;

    @Column(name = "begin_date")
    private Instant beginDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "actual_finish_date")
    private Instant actualFinishDate;

    @Column(name = "plan_duration")
    private Integer planDuration;

    @Size(max = 20)
    @Column(name = "state", length = 20)
    private String state;

    @Size(max = 20)
    @Column(name = "reject", length = 20)
    private String reject;

    @Column(name = "first_trial_money")
    private Double firstTrialMoney;

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

    public String getZjProjectId() {
        return zjProjectId;
    }

    public ZjSpecialty zjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
        return this;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getZjPublishId() {
        return zjPublishId;
    }

    public ZjSpecialty zjPublishId(String zjPublishId) {
        this.zjPublishId = zjPublishId;
        return this;
    }

    public void setZjPublishId(String zjPublishId) {
        this.zjPublishId = zjPublishId;
    }

    public String getEngineeringName() {
        return engineeringName;
    }

    public ZjSpecialty engineeringName(String engineeringName) {
        this.engineeringName = engineeringName;
        return this;
    }

    public void setEngineeringName(String engineeringName) {
        this.engineeringName = engineeringName;
    }

    public String getSpecialtyType() {
        return specialtyType;
    }

    public ZjSpecialty specialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
        return this;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getEstablishmentPersonId() {
        return establishmentPersonId;
    }

    public ZjSpecialty establishmentPersonId(String establishmentPersonId) {
        this.establishmentPersonId = establishmentPersonId;
        return this;
    }

    public void setEstablishmentPersonId(String establishmentPersonId) {
        this.establishmentPersonId = establishmentPersonId;
    }

    public String getEstablishmentPersonName() {
        return establishmentPersonName;
    }

    public ZjSpecialty establishmentPersonName(String establishmentPersonName) {
        this.establishmentPersonName = establishmentPersonName;
        return this;
    }

    public void setEstablishmentPersonName(String establishmentPersonName) {
        this.establishmentPersonName = establishmentPersonName;
    }

    public Double getWorkloadRate() {
        return workloadRate;
    }

    public ZjSpecialty workloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
        return this;
    }

    public void setWorkloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
    }

    public Double getEstablishmentMoney() {
        return establishmentMoney;
    }

    public ZjSpecialty establishmentMoney(Double establishmentMoney) {
        this.establishmentMoney = establishmentMoney;
        return this;
    }

    public void setEstablishmentMoney(Double establishmentMoney) {
        this.establishmentMoney = establishmentMoney;
    }

    public Double getApprovalMoney() {
        return approvalMoney;
    }

    public ZjSpecialty approvalMoney(Double approvalMoney) {
        this.approvalMoney = approvalMoney;
        return this;
    }

    public void setApprovalMoney(Double approvalMoney) {
        this.approvalMoney = approvalMoney;
    }

    public Double getCreaseRate() {
        return creaseRate;
    }

    public ZjSpecialty creaseRate(Double creaseRate) {
        this.creaseRate = creaseRate;
        return this;
    }

    public void setCreaseRate(Double creaseRate) {
        this.creaseRate = creaseRate;
    }

    public Double getSubmitMoney() {
        return submitMoney;
    }

    public ZjSpecialty submitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
        return this;
    }

    public void setSubmitMoney(Double submitMoney) {
        this.submitMoney = submitMoney;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public ZjSpecialty beginDate(Instant beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public void setBeginDate(Instant beginDate) {
        this.beginDate = beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ZjSpecialty endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getPlanDuration() {
        return planDuration;
    }

    public ZjSpecialty planDuration(Integer planDuration) {
        this.planDuration = planDuration;
        return this;
    }

    public void setPlanDuration(Integer planDuration) {
        this.planDuration = planDuration;
    }

    public String getState() {
        return state;
    }

    public ZjSpecialty state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReject() {
        return reject;
    }

    public ZjSpecialty reject(String reject) {
        this.reject = reject;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjSpecialty multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZjSpecialty createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ZjSpecialty createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZjSpecialty lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ZjSpecialty lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public ZjSpecialty actualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
        return this;
    }

    public Double getFirstTrialMoney() {
        return firstTrialMoney;
    }

    public void setFirstTrialMoney(Double firstTrialMoney) {
        this.firstTrialMoney = firstTrialMoney;
    }

    public ZjSpecialty firstTrialMoney(Double firstTrialMoney) {
        this.firstTrialMoney = firstTrialMoney;
        return this;
    }

    public void setReject(String reject) {
        this.reject = reject;
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
        ZjSpecialty zjSpecialty = (ZjSpecialty) o;
        if (zjSpecialty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialty{" +
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
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", actualFinishDate='" + getActualFinishDate() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
