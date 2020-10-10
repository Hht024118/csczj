package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProjectPerson entity.
 */
public class ProjectPersonDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String personId;

    @Size(max = 100)
    private String personType;

    @Size(max = 50)
    private String personName;

    @Size(max = 36)
    private String projectId;

    @Size(max = 50)
    private String projectNo;

    @Size(max = 100)
    private String projectName;

    @Size(max = 50)
    private String projectType;

    private Instant startTime;

    private Instant endTime;

    @Size(max = 500)
    private String scale;

    @Size(max = 50)
    private String mainWork;

    @Size(max = 500)
    private String resultsForm;

    @Size(max = 500)
    private String awards;

    @Size(max = 50)
    private String technicalPost;

    @Size(max = 40)
    private String multiTenancyId;

    @Size(max = 50)
    private String extField1;

    @Size(max = 100)
    private String extField2;

    @Size(max = 100)
    private String extField3;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getMainWork() {
        return mainWork;
    }

    public void setMainWork(String mainWork) {
        this.mainWork = mainWork;
    }

    public String getResultsForm() {
        return resultsForm;
    }

    public void setResultsForm(String resultsForm) {
        this.resultsForm = resultsForm;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getTechnicalPost() {
        return technicalPost;
    }

    public void setTechnicalPost(String technicalPost) {
        this.technicalPost = technicalPost;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1;
    }

    public String getExtField2() {
        return extField2;
    }

    public void setExtField2(String extField2) {
        this.extField2 = extField2;
    }

    public String getExtField3() {
        return extField3;
    }

    public void setExtField3(String extField3) {
        this.extField3 = extField3;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectPersonDTO projectPersonDTO = (ProjectPersonDTO) o;
        if (projectPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectPersonDTO{" +
            "id=" + getId() +
            ", personId='" + getPersonId() + "'" +
            ", personType='" + getPersonType() + "'" +
            ", personName='" + getPersonName() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectNo='" + getProjectNo() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", scale='" + getScale() + "'" +
            ", mainWork='" + getMainWork() + "'" +
            ", resultsForm='" + getResultsForm() + "'" +
            ", awards='" + getAwards() + "'" +
            ", technicalPost='" + getTechnicalPost() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            ", extField1='" + getExtField1() + "'" +
            ", extField2='" + getExtField2() + "'" +
            ", extField3='" + getExtField3() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
