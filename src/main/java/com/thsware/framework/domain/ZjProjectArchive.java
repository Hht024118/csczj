package com.thsware.framework.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 造价咨询_项目归档
 */
@ApiModel(description = "造价咨询_项目归档")
@Entity
@Table(name = "zj_project_archive")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjProjectArchive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JsonIgnoreProperties("")
    private ZjProject zjProject;

    @Size(max = 50)
    @Column(name = "archive_no", length = 36)
    private String archiveNo;

    @Size(max = 1)
    @Column(name = "is_complete", length = 1)
    private String isComplete;

    @Size(max = 50)
    @Column(name = "archived_by", length = 50)
    private String archivedBy;

    @Column(name = "archive_date")
    private Instant archiveDate;

    @Size(max = 500)
    @Column(name = "remark", length = 500)
    private String remark;

    @ThsMultiTenancyId
    @Size(max = 40)
    @Column(name="multi_tenancy_id",length = 40)
    private String multiTenancyId;

    @Size(max = 50)
    @Column(name = "paper_archive_by", length = 50)
    private String paperArchiveBy;

    @Column(name = "paper_archive_date")
    private Instant paperArchiveDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZjProject getZjProject() {
        return zjProject;
    }

    public void setZjProject(ZjProject zjProject) {
        this.zjProject = zjProject;
    }

    public String getArchiveNo() {
        return archiveNo;
    }

    public ZjProjectArchive archiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
        return this;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
    }


    public String getIsComplete() {
        return isComplete;
    }

    public ZjProjectArchive isComplete(String isComplete) {
        this.isComplete = isComplete;
        return this;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getArchivedBy() {
        return archivedBy;
    }

    public ZjProjectArchive archivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
        return this;
    }

    public void setArchivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
    }

    public Instant getArchiveDate() {
        return archiveDate;
    }

    public ZjProjectArchive archiveDate(Instant archiveDate) {
        this.archiveDate = archiveDate;
        return this;
    }

    public void setArchiveDate(Instant archiveDate) {
        this.archiveDate = archiveDate;
    }

    public String getRemark() {
        return remark;
    }

    public ZjProjectArchive remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjProjectArchive multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public String getPaperArchiveBy() {
        return paperArchiveBy;
    }

    public ZjProjectArchive paperArchiveBy(String paperArchiveBy) {
        this.paperArchiveBy = paperArchiveBy;
        return this;
    }

    public void setPaperArchiveBy(String paperArchiveBy) {
        this.paperArchiveBy = paperArchiveBy;
    }

    public Instant getPaperArchiveDate() {
        return paperArchiveDate;
    }

    public ZjProjectArchive paperArchiveDate(Instant paperArchiveDate) {
        this.paperArchiveDate = paperArchiveDate;
        return this;
    }

    public void setPaperArchiveDate(Instant paperArchiveDate) {
        this.paperArchiveDate = paperArchiveDate;
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
        ZjProjectArchive zjProjectArchive = (ZjProjectArchive) o;
        if (zjProjectArchive.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProjectArchive.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProjectArchive{" +
            "id=" + getId() +
            ", archiveNo='" + getArchiveNo() + "'" +
            ", isComplete='" + getIsComplete() + "'" +
            ", archivedBy='" + getArchivedBy() + "'" +
            ", archiveDate='" + getArchiveDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            ", paperArchiveBy='" + getPaperArchiveBy() + "'" +
            ", paperArchiveDate='" + getPaperArchiveDate() + "'" +
            "}";
    }
}
