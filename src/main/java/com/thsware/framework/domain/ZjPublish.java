package com.thsware.framework.domain;

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
 * 造价咨询-项目变更记录表
 */
@ApiModel(description = "造价咨询-项目变更记录表")
@Entity
@Table(name = "zj_publish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjPublish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 500)
    @Column(name = "title", length = 500)
    private String title;

    @Size(max = 36)
    @Column(name = "zj_project_id", length = 36)
    private String zjProjectId;

    @Size(max = 50)
    @Column(name = "flow_state", length = 50)
    private String flowState;

    @Column(name = "publish_time")
    private Instant publishTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ZjPublish title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZjProjectId() {
        return zjProjectId;
    }

    public ZjPublish zjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
        return this;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getFlowState() {
        return flowState;
    }

    public ZjPublish flowState(String flowState) {
        this.flowState = flowState;
        return this;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public ZjPublish publishTime(Instant publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
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
        ZjPublish zjPublish = (ZjPublish) o;
        if (zjPublish.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjPublish.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjPublish{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", flowState='" + getFlowState() + "'" +
            ", publishTime='" + getPublishTime() + "'" +
            "}";
    }
}
