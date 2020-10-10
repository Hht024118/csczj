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
 * 造价咨询-参与人员
 */
@ApiModel(description = "造价咨询-参与人员")
@Entity
@Table(name = "zj_actors")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjActors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "project_id", length = 36)
    private String projectId;

    @Size(max = 50)
    @Column(name = "step", length = 50)
    private String step;

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

    public String getProjectId() {
        return projectId;
    }

    public ZjActors projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStep() {
        return step;
    }

    public ZjActors step(String step) {
        this.step = step;
        return this;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getPersonId() {
        return personId;
    }

    public ZjActors personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public ZjActors personName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public ZjActors multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
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
        ZjActors zjActors = (ZjActors) o;
        if (zjActors.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjActors.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjActors{" +
            "id=" + getId() +
            ", projectId='" + getProjectId() + "'" +
            ", step='" + getStep() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
