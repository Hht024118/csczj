package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjProjectArchive entity.
 */
public class ZjProjectArchiveDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjProjectId;

    @Size(max = 50)
    private String archiveNo;

    @Size(max = 1)
    private String isComplete;

    @Size(max = 50)
    private String archivedBy;

    private Instant archiveDate;

    @Size(max = 500)
    private String remark;

    @Size(max = 40)
    private String multiTenancyId;

    @Size(max = 50)
    private String paperArchiveBy;

    private Instant paperArchiveDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjProjectId() {
        return zjProjectId;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getArchivedBy() {
        return archivedBy;
    }

    public void setArchivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
    }

    public Instant getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Instant archiveDate) {
        this.archiveDate = archiveDate;
    }

    public String getRemark() {
        return remark;
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

    public String getPaperArchiveBy() {
        return paperArchiveBy;
    }

    public void setPaperArchiveBy(String paperArchiveBy) {
        this.paperArchiveBy = paperArchiveBy;
    }

    public Instant getPaperArchiveDate() {
        return paperArchiveDate;
    }

    public void setPaperArchiveDate(Instant paperArchiveDate) {
        this.paperArchiveDate = paperArchiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjProjectArchiveDTO zjProjectArchiveDTO = (ZjProjectArchiveDTO) o;
        if (zjProjectArchiveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjProjectArchiveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjProjectArchiveDTO{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
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
