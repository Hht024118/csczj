package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjSpecialtyIdea entity.
 */
public class ZjSpecialtyIdeaDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjProjectId;

    @Size(max = 200)
    private String zjSpecialtyId;

    @Size(max = 50)
    private String auditType;

    @Size(max = 36)
    private String auditerId;

    @Size(max = 200)
    private String auditerName;

    @Size(max = 1)
    private String auditResult;

    @Size(max = 200)
    private String auditIdea;

    private Instant auditDate;

    @Size(max = 10)
    private String isHistory;

    @Size(max = 1)
    private String validEjsh;

    @Size(max = 40)
    private String multiTenancyId;

    private String flowId; // 暂放待办id,审批判断后用来结束待办

    private String haveId; // 判断是否是自校汇总人发送的二审，是则haveId为空，否则有值

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

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditerId() {
        return auditerId;
    }

    public void setAuditerId(String auditerId) {
        this.auditerId = auditerId;
    }

    public String getAuditerName() {
        return auditerName;
    }

    public void setAuditerName(String auditerName) {
        this.auditerName = auditerName;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Instant getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Instant auditDate) {
        this.auditDate = auditDate;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }

    public String getValidEjsh() {
        return validEjsh;
    }

    public void setValidEjsh(String validEjsh) {
        this.validEjsh = validEjsh;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getHaveId() {
        return haveId;
    }

    public void setHaveId(String haveId) {
        this.haveId = haveId;
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

        ZjSpecialtyIdeaDTO zjSpecialtyIdeaDTO = (ZjSpecialtyIdeaDTO) o;
        if (zjSpecialtyIdeaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjSpecialtyIdeaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjSpecialtyIdeaDTO{" +
            "id=" + getId() +
            ", zjProjectId='" + getZjProjectId() + "'" +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", auditType='" + getAuditType() + "'" +
            ", auditerId='" + getAuditerId() + "'" +
            ", auditerName='" + getAuditerName() + "'" +
            ", auditResult='" + getAuditResult() + "'" +
            ", auditIdea='" + getAuditIdea() + "'" +
            ", auditDate='" + getAuditDate() + "'" +
            ", isHistory='" + getIsHistory() + "'" +
            ", validEjsh='" + getValidEjsh() + "'" +
            ", flowId='" + getFlowId() + "'" +
            ", haveId='" + getHaveId() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
