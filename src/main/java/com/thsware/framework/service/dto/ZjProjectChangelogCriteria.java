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
 * Criteria class for the ZjProjectChangelog entity. This class is used in ZjProjectChangelogResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-project-changelogs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjProjectChangelogCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter projectId;

    private StringFilter specialtyId;

    private StringFilter changeType;

    private IntegerFilter changeVersion;

    private StringFilter changeReason;

    private InstantFilter changeTime;

    private StringFilter oldFieldId;

    private StringFilter oldFieldText;

    private StringFilter newFieldId;

    private StringFilter newFieldText;

    private StringFilter currentBusinessState;

    private StringFilter flowState;

    private StringFilter remark;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter multiTenancyId;

    public ZjProjectChangelogCriteria() {
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(StringFilter projectId) {
        this.projectId = projectId;
    }

    public StringFilter getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(StringFilter specialtyId) {
        this.specialtyId = specialtyId;
    }

    public StringFilter getChangeType() {
        return changeType;
    }

    public void setChangeType(StringFilter changeType) {
        this.changeType = changeType;
    }

    public IntegerFilter getChangeVersion() {
        return changeVersion;
    }

    public void setChangeVersion(IntegerFilter changeVersion) {
        this.changeVersion = changeVersion;
    }

    public StringFilter getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(StringFilter changeReason) {
        this.changeReason = changeReason;
    }

    public InstantFilter getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(InstantFilter changeTime) {
        this.changeTime = changeTime;
    }

    public StringFilter getOldFieldId() {
        return oldFieldId;
    }

    public void setOldFieldId(StringFilter oldFieldId) {
        this.oldFieldId = oldFieldId;
    }

    public StringFilter getOldFieldText() {
        return oldFieldText;
    }

    public void setOldFieldText(StringFilter oldFieldText) {
        this.oldFieldText = oldFieldText;
    }

    public StringFilter getNewFieldId() {
        return newFieldId;
    }

    public void setNewFieldId(StringFilter newFieldId) {
        this.newFieldId = newFieldId;
    }

    public StringFilter getNewFieldText() {
        return newFieldText;
    }

    public void setNewFieldText(StringFilter newFieldText) {
        this.newFieldText = newFieldText;
    }

   public StringFilter getCurrentBusinessState() {
        return currentBusinessState;
    }

    public void setCurrentBusinessState(StringFilter currentBusinessState) {
        this.currentBusinessState = currentBusinessState;
    }

    public StringFilter getFlowState() {
        return flowState;
    }

    public void setFlowState(StringFilter flowState) {
        this.flowState = flowState;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(StringFilter multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    @Override
    public String toString() {
        return "ZjProjectChangelogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (specialtyId != null ? "specialtyId=" + specialtyId + ", " : "") +
                (changeType != null ? "changeType=" + changeType + ", " : "") +
                (changeVersion != null ? "changeVersion=" + changeVersion + ", " : "") +
                (changeReason != null ? "changeReason=" + changeReason + ", " : "") +
                (changeTime != null ? "changeTime=" + changeTime + ", " : "") +
                (oldFieldId != null ? "oldFieldId=" + oldFieldId + ", " : "") +
                (oldFieldText != null ? "oldFieldText=" + oldFieldText + ", " : "") +
                (newFieldId != null ? "newFieldId=" + newFieldId + ", " : "") +
                (newFieldText != null ? "newFieldText=" + newFieldText + ", " : "") +
                (currentBusinessState != null ? "currentBusinessState=" + currentBusinessState + ", " : "") +
                (flowState != null ? "flowState=" + flowState + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (multiTenancyId != null ? "multiTenancyId=" + multiTenancyId + ", " : "") +
            "}";
    }

}
