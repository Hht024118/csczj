package com.thsware.framework.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjActors entity.
 */
public class ZjActorsDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String projectId;

    @Size(max = 50)
    private String step;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
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

        ZjActorsDTO zjActorsDTO = (ZjActorsDTO) o;
        if (zjActorsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjActorsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjActorsDTO{" +
            "id=" + getId() +
            ", projectId='" + getProjectId() + "'" +
            ", step='" + getStep() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
