package com.thsware.framework.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ZjActors entity. This class is used in ZjActorsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-actors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjActorsCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter projectId;

    private StringFilter step;

    private StringFilter personId;

    private StringFilter personName;

    public ZjActorsCriteria() {
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

    public StringFilter getStep() {
        return step;
    }

    public void setStep(StringFilter step) {
        this.step = step;
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

    @Override
    public String toString() {
        return "ZjActorsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (step != null ? "step=" + step + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (personName != null ? "personName=" + personName + ", " : "") +
            "}";
    }

}
