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
 * Criteria class for the ZjSpecialtyAuditer entity. This class is used in ZjSpecialtyAuditerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /zj-specialty-auditers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZjSpecialtyAuditerCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private StringFilter id;

    private StringFilter zjProjectId;

    private StringFilter zjSpecialtyId;

    private StringFilter type;

    private StringFilter personId;

    private StringFilter personName;

    public ZjSpecialtyAuditerCriteria() {
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

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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
        return "ZjSpecialtyAuditerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zjProjectId != null ? "zjProjectId=" + zjProjectId + ", " : "") +
                (zjSpecialtyId != null ? "zjSpecialtyId=" + zjSpecialtyId + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (personName != null ? "personName=" + personName + ", " : "") +
            "}";
    }

}
