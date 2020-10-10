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
 * 造价咨询-团队成员
 */
@ApiModel(description = "造价咨询-团队成员")
@Entity
@Table(name = "zj_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_project_id", length = 36)
    private String zjProjectId;

    @Size(max = 10)
    @Column(name = "jhi_type", length = 10)
    private String type;

    @Column(name = "order_num")
    private Integer orderNum;

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

    public ZjMember zjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
        return this;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getType() {
        return type;
    }

    public ZjMember type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public ZjMember orderNum(Integer orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPersonId() {
        return personId;
    }

    public ZjMember personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public ZjMember personName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjMember multiTenancyId(String multiTenancyId) {
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
        ZjMember zjMember = (ZjMember) o;
        if (zjMember.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjMember.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjMember{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", type='" + getType() + "'" +
            ", orderNum=" + getOrderNum() +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
