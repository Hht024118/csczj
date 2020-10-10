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
import java.util.Objects;

/**
 * 造价咨询-工程审批人员表
 */
@ApiModel(description = "造价咨询-工程审批人员表")
@Entity
@Table(name = "zj_specialty_auditer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjSpecialtyAuditer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_project_id", length = 36)
    private String zjProjectId;

    @Size(max = 36)
    @Column(name = "zj_specialty_id", length = 36)
    private String zjSpecialtyId;

    @Size(max = 10)
    @Column(name = "jhi_type", length = 10)
    private String type;

    @Size(max = 36)
    @Column(name = "person_id", length = 36)
    private String personId;

    @Size(max = 50)
    @Column(name = "person_name", length = 50)
    private String personName;

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

    public ZjSpecialtyAuditer zjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
        return this;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public ZjSpecialtyAuditer zjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
        return this;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getType() {
        return type;
    }

    public ZjSpecialtyAuditer type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public ZjSpecialtyAuditer personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public ZjSpecialtyAuditer personName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjSpecialtyAuditer multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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
        ZjSpecialtyAuditer zjSpecialtyAuditer = (ZjSpecialtyAuditer) o;
        if (zjSpecialtyAuditer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialtyAuditer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialtyAuditer{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", type='" + getType() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
