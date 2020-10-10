package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ZjAssistant entity.
 */
public class ZjAssistantDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjSpecialtyId;

    @Size(max = 36)
    private String personId;

    @Size(max = 50)
    private String personName;

    private Double workloadRate;

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

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Double getWorkloadRate() {
        return workloadRate;
    }

    public void setWorkloadRate(Double workloadRate) {
        this.workloadRate = workloadRate;
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

        ZjAssistantDTO zjAssistantDTO = (ZjAssistantDTO) o;
        if (zjAssistantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjAssistantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjAssistantDTO{" +
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
