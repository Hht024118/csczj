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
 * Criteria class for the ZjPublish entity. This class is used in ZjPublishResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-publishes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjPublishCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter title;

    private StringFilter zjProjectId;

    private StringFilter flowState;

    private InstantFilter publishTime;

    public ZjPublishCriteria() {
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getZjProjectId() {
        return zjProjectId;
    }

    public void setZjProjectId(StringFilter zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public StringFilter getFlowState() {
        return flowState;
    }

    public void setFlowState(StringFilter flowState) {
        this.flowState = flowState;
    }

    public InstantFilter getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(InstantFilter publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "ZjPublishCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (zjProjectId != null ? "zjProjectId=" + zjProjectId + ", " : "") +
                (flowState != null ? "flowState=" + flowState + ", " : "") +
                (publishTime != null ? "publishTime=" + publishTime + ", " : "") +
            "}";
    }

}
