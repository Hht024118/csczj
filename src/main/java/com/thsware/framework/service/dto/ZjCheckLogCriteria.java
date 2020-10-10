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
 * Criteria class for the ZjCheckLog entity. This class is used in ZjCheckLogResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-check-logs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjCheckLogCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjSpecialtyId;

    private StringFilter chekcStep;

    private IntegerFilter orderNum;

    private StringFilter checkItem;

    private StringFilter isPass;

    private StringFilter checkAudit;

    private StringFilter checker;

    private InstantFilter checkTime;

    public ZjCheckLogCriteria() {
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

    public StringFilter getChekcStep() {
        return chekcStep;
    }

    public void setChekcStep(StringFilter chekcStep) {
        this.chekcStep = chekcStep;
    }

    public IntegerFilter getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(IntegerFilter orderNum) {
        this.orderNum = orderNum;
    }

    public StringFilter getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(StringFilter checkItem) {
        this.checkItem = checkItem;
    }

    public StringFilter getIsPass() {
        return isPass;
    }

    public void setIsPass(StringFilter isPass) {
        this.isPass = isPass;
    }

    public StringFilter getCheckAudit() {
        return checkAudit;
    }

    public void setCheckAudit(StringFilter checkAudit) {
        this.checkAudit = checkAudit;
    }

    public StringFilter getChecker() {
        return checker;
    }

    public void setChecker(StringFilter checker) {
        this.checker = checker;
    }

    public InstantFilter getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(InstantFilter checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public String toString() {
        return "ZjCheckLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjSpecialtyId != null ? "zjSpecialtyId=" + zjSpecialtyId + ", " : "") +
                (chekcStep != null ? "chekcStep=" + chekcStep + ", " : "") +
                (orderNum != null ? "orderNum=" + orderNum + ", " : "") +
                (checkItem != null ? "checkItem=" + checkItem + ", " : "") +
                (isPass != null ? "isPass=" + isPass + ", " : "") +
                (checkAudit != null ? "checkAudit=" + checkAudit + ", " : "") +
                (checker != null ? "checker=" + checker + ", " : "") +
                (checkTime != null ? "checkTime=" + checkTime + ", " : "") +
            "}";
    }

}
