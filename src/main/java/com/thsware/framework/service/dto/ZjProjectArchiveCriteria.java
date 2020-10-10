package com.thsware.framework.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ZjProjectArchive entity. This class is used in ZjProjectArchiveResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-project-archives?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjProjectArchiveCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjProjectId;

    private StringFilter archiveNo;

    private StringFilter isComplete;

    private StringFilter archivedBy;

    private InstantFilter archiveDate;

    private StringFilter remark;

    private StringFilter paperArchiveBy;

    private InstantFilter paperArchiveDate;

    public ZjProjectArchiveCriteria() {
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getZjProjectId() {
        return zjProjectId;
    }

    public void setZjProjectId(StringFilter zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public StringFilter getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(StringFilter archiveNo) {
        this.archiveNo = archiveNo;
    }

    public StringFilter getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(StringFilter isComplete) {
        this.isComplete = isComplete;
    }

    public StringFilter getArchivedBy() {
        return archivedBy;
    }

    public void setArchivedBy(StringFilter archivedBy) {
        this.archivedBy = archivedBy;
    }

    public InstantFilter getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(InstantFilter archiveDate) {
        this.archiveDate = archiveDate;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getPaperArchiveBy() {
        return paperArchiveBy;
    }

    public void setPaperArchiveBy(StringFilter paperArchiveBy) {
        this.paperArchiveBy = paperArchiveBy;
    }

    public InstantFilter getPaperArchiveDate() {
        return paperArchiveDate;
    }

    public void setPaperArchiveDate(InstantFilter paperArchiveDate) {
        this.paperArchiveDate = paperArchiveDate;
    }

    @Override
    public String toString() {
        return "ZjProjectArchiveCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjProjectId != null ? "zjProjectId=" + zjProjectId + ", " : "") +
                (archiveNo != null ? "archiveNo=" + archiveNo + ", " : "") +
                (isComplete != null ? "isComplete=" + isComplete + ", " : "") +
                (archivedBy != null ? "archivedBy=" + archivedBy + ", " : "") +
                (archiveDate != null ? "archiveDate=" + archiveDate + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (paperArchiveBy != null ? "paperArchiveBy=" + paperArchiveBy + ", " : "") +
                (paperArchiveDate != null ? "paperArchiveDate=" + paperArchiveDate + ", " : "") +
            "}";
    }

}
