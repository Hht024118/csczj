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
 * Criteria class for the ZjSpecialtyIdea entity. This class is used in ZjSpecialtyIdeaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-specialty-ideas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjSpecialtyIdeaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjProjectId;

    private StringFilter zjSpecialtyId;

    private StringFilter auditType;

    private StringFilter auditerId;

    private StringFilter auditerName;

    private StringFilter auditResult;

    private StringFilter auditIdea;

    private InstantFilter auditDate;

    private StringFilter isHistory;

    private StringFilter validEjsh;

    public ZjSpecialtyIdeaCriteria() {
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

    public StringFilter getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public void setZjSpecialtyId(StringFilter zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public StringFilter getAuditType() {
        return auditType;
    }

    public void setAuditType(StringFilter auditType) {
        this.auditType = auditType;
    }

    public StringFilter getAuditerId() {
        return auditerId;
    }

    public void setAuditerId(StringFilter auditerId) {
        this.auditerId = auditerId;
    }

    public StringFilter getAuditerName() {
        return auditerName;
    }

    public void setAuditerName(StringFilter auditerName) {
        this.auditerName = auditerName;
    }

    public StringFilter getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(StringFilter auditResult) {
        this.auditResult = auditResult;
    }

    public StringFilter getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(StringFilter auditIdea) {
        this.auditIdea = auditIdea;
    }

    public InstantFilter getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(InstantFilter auditDate) {
        this.auditDate = auditDate;
    }

    public StringFilter getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(StringFilter isHistory) {
        this.isHistory = isHistory;
    }

    public StringFilter getValidEjsh() {
        return validEjsh;
    }

    public void setValidEjsh(StringFilter validEjsh) {
        this.validEjsh = validEjsh;
    }

    @Override
    public String toString() {
        return "ZjSpecialtyIdeaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjProjectId != null ? "zjProjectId=" + zjProjectId + ", " : "") +
                (zjSpecialtyId != null ? "zjSpecialtyId=" + zjSpecialtyId + ", " : "") +
                (auditType != null ? "auditType=" + auditType + ", " : "") +
                (auditerId != null ? "auditerId=" + auditerId + ", " : "") +
                (auditerName != null ? "auditerName=" + auditerName + ", " : "") +
                (auditResult != null ? "auditResult=" + auditResult + ", " : "") +
                (auditIdea != null ? "auditIdea=" + auditIdea + ", " : "") +
                (auditDate != null ? "auditDate=" + auditDate + ", " : "") +
                (isHistory != null ? "isHistory=" + isHistory + ", " : "") +
                (validEjsh != null ? "validEjsh=" + validEjsh + ", " : "") +
            "}";
    }

}
