package com.thsware.framework.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ZjAssistant entity. This class is used in ZjAssistantResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-assistants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjAssistantCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjSpecialtyId;

    private StringFilter personId;

    private StringFilter personName;

    private DoubleFilter workloadRate;

    private StringFilter remark;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter multiTenancyId;

    public ZjAssistantCriteria() {
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public void setZjSpecialtyId(StringFilter zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public StringFilter getPersonId() {
        return personId;
    }

    public void setPersonId(StringFilter personId) {
        this.personId = personId;
    }

    public StringFilter getPersonName() {
        return personName;
    }

    public void setPersonName(StringFilter personName) {
        this.personName = personName;
    }

    public DoubleFilter getWorkloadRate() {
        return workloadRate;
    }

    public void setWorkloadRate(DoubleFilter workloadRate) {
        this.workloadRate = workloadRate;
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
        return "ZjAssistantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjSpecialtyId != null ? "zjSpecialtyId=" + zjSpecialtyId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (personName != null ? "personName=" + personName + ", " : "") +
                (workloadRate != null ? "workloadRate=" + workloadRate + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (multiTenancyId != null ? "multiTenancyId=" + multiTenancyId + ", " : "") +
            "}";
    }

}
