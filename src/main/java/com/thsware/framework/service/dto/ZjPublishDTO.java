package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the ZjPublish entity.
 */
public class ZjPublishDTO extends WfParamDTO implements Serializable {

    private String id;

    @Size(max = 500)
    private String title;

    @Size(max = 36)
    private String zjProjectId;

    @Size(max = 50)
    private String flowState;

    private Instant publishTime;

    private List<String> zjSpecialtyIds;

    private String saveType;

    private List<String> flowIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZjProjectId() {
        return zjProjectId;
    }

    public void setZjProjectId(String zjProjectId) {
        this.zjProjectId = zjProjectId;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
    }

    public List<String> getZjSpecialtyIds() {
        return zjSpecialtyIds;
    }

    public void setZjSpecialtyIds(List<String> zjSpecialtyIds) {
        this.zjSpecialtyIds = zjSpecialtyIds;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public List<String> getFlowIds() {
        return flowIds;
    }

    public void setFlowIds(List<String> flowIds) {
        this.flowIds = flowIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZjPublishDTO zjPublishDTO = (ZjPublishDTO) o;
        if (zjPublishDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjPublishDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjPublishDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", flowState='" + getFlowState() + "'" +
            ", publishTime='" + getPublishTime() + "'" +
            ", zjSpecialtyIds='" + getZjSpecialtyIds() + "'" +
            ", saveType='" + getSaveType() + "'" +
            ", flowIds='" + getFlowIds() + "'" +
            "}";
    }
}
