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
 * Criteria class for the ZjSpecialty entity. This class is used in ZjSpecialtyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-specialties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjSpecialtyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjProjectId;

    private StringFilter zjPublishId;

    private StringFilter engineeringName;

    private StringFilter specialtyType;

    private StringFilter establishmentPersonId;

    private StringFilter establishmentPersonName;

    private DoubleFilter workloadRate;

    private DoubleFilter establishmentMoney;

    private DoubleFilter approvalMoney;

    private DoubleFilter creaseRate;

    private DoubleFilter submitMoney;

    private InstantFilter beginDate;

    private InstantFilter endDate;

    private InstantFilter actualFinishDate;

    private IntegerFilter planDuration;

    private StringFilter state;

    private StringFilter reject;

    private DoubleFilter firstTrialMoney;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    public ZjSpecialtyCriteria() {
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

    public StringFilter getZjPublishId() {
        return zjPublishId;
    }

    public void setZjPublishId(StringFilter zjPublishId) {
        this.zjPublishId = zjPublishId;
    }

    public StringFilter getEngineeringName() {
        return engineeringName;
    }

    public void setEngineeringName(StringFilter engineeringName) {
        this.engineeringName = engineeringName;
    }

    public StringFilter getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(StringFilter specialtyType) {
        this.specialtyType = specialtyType;
    }

    public StringFilter getEstablishmentPersonId() {
        return establishmentPersonId;
    }

    public void setEstablishmentPersonId(StringFilter establishmentPersonId) {
        this.establishmentPersonId = establishmentPersonId;
    }

    public StringFilter getEstablishmentPersonName() {
        return establishmentPersonName;
    }

    public void setEstablishmentPersonName(StringFilter establishmentPersonName) {
        this.establishmentPersonName = establishmentPersonName;
    }

    public DoubleFilter getWorkloadRate() {
        return workloadRate;
    }

    public void setWorkloadRate(DoubleFilter workloadRate) {
        this.workloadRate = workloadRate;
    }

    public DoubleFilter getEstablishmentMoney() {
        return establishmentMoney;
    }

    public void setEstablishmentMoney(DoubleFilter establishmentMoney) {
        this.establishmentMoney = establishmentMoney;
    }

    public DoubleFilter getApprovalMoney() {
        return approvalMoney;
    }

    public void setApprovalMoney(DoubleFilter approvalMoney) {
        this.approvalMoney = approvalMoney;
    }

    public DoubleFilter getCreaseRate() {
        return creaseRate;
    }

    public void setCreaseRate(DoubleFilter creaseRate) {
        this.creaseRate = creaseRate;
    }

    public DoubleFilter getSubmitMoney() {
        return submitMoney;
    }

    public void setSubmitMoney(DoubleFilter submitMoney) {
        this.submitMoney = submitMoney;
    }

    public InstantFilter getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(InstantFilter beginDate) {
        this.beginDate = beginDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public IntegerFilter getPlanDuration() {
        return planDuration;
    }

    public void setPlanDuration(IntegerFilter planDuration) {
        this.planDuration = planDuration;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getReject() {
        return reject;
    }

    public void setReject(StringFilter reject) {
        this.reject = reject;
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

    public InstantFilter getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(InstantFilter actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public DoubleFilter getFirstTrialMoney() {
        return firstTrialMoney;
    }

    public void setFirstTrialMoney(DoubleFilter firstTrialMoney) {
        this.firstTrialMoney = firstTrialMoney;
    }

    @Override
    public String toString() {
        return "ZjSpecialtyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjProjectId != null ? "zjProjectId=" + zjProjectId + ", " : "") +
                (zjPublishId != null ? "zjPublishId=" + zjPublishId + ", " : "") +
                (engineeringName != null ? "engineeringName=" + engineeringName + ", " : "") +
                (specialtyType != null ? "specialtyType=" + specialtyType + ", " : "") +
                (establishmentPersonId != null ? "establishmentPersonId=" + establishmentPersonId + ", " : "") +
                (establishmentPersonName != null ? "establishmentPersonName=" + establishmentPersonName + ", " : "") +
                (workloadRate != null ? "workloadRate=" + workloadRate + ", " : "") +
                (establishmentMoney != null ? "establishmentMoney=" + establishmentMoney + ", " : "") +
                (approvalMoney != null ? "approvalMoney=" + approvalMoney + ", " : "") +
                (creaseRate != null ? "creaseRate=" + creaseRate + ", " : "") +
                (submitMoney != null ? "submitMoney=" + submitMoney + ", " : "") +
                (beginDate != null ? "beginDate=" + beginDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (actualFinishDate != null ? "actualFinishDate=" + actualFinishDate + ", " : "") +
                (planDuration != null ? "planDuration=" + planDuration + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (reject != null ? "reject=" + reject + ", " : "") +
                (firstTrialMoney != null ? "firstTrialMoney=" + firstTrialMoney + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            "}";
    }

}
