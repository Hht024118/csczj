package com.thsware.framework.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjSpecialtyAuditer entity.
 */
public class ZjSpecialtyAuditerDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjProjectId;

    @Size(max = 36)
    private String zjSpecialtyId;

    @Size(max = 10)
    private String type;

    @Size(max = 36)
    private String personId;

    @Size(max = 50)
    private String personName;

    @Size(max = 40)
    private String multiTenancyId;

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

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjSpecialtyAuditerDTO zjSpecialtyAuditerDTO = (ZjSpecialtyAuditerDTO) o;
        if (zjSpecialtyAuditerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialtyAuditerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialtyAuditerDTO{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", type='" + getType() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
