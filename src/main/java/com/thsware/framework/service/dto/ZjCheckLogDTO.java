package com.thsware.framework.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZjCheckLog entity.
 */
public class ZjCheckLogDTO implements Serializable {

    private String id;

    @Size(max = 36)
    private String zjSpecialtyId;

    @Size(max = 20)
    private String chekcStep;

    private Integer orderNum;

    @Size(max = 200)
    private String checkItem;

    @Size(max = 1)
    private String isPass;

    @Size(max = 200)
    private String checkAudit;

    @Size(max = 50)
    private String checker;

    private Instant checkTime;

    @Size(max = 40)
    private String multiTenancyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getChekcStep() {
        return chekcStep;
    }

    public void setChekcStep(String chekcStep) {
        this.chekcStep = chekcStep;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getCheckAudit() {
        return checkAudit;
    }

    public void setCheckAudit(String checkAudit) {
        this.checkAudit = checkAudit;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Instant getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Instant checkTime) {
        this.checkTime = checkTime;
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

        ZjCheckLogDTO zjCheckLogDTO = (ZjCheckLogDTO) o;
        if (zjCheckLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjCheckLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjCheckLogDTO{" +
            "id=" + getId() +
            ", zjSpecialtyId='" + getZjSpecialtyId() + "'" +
            ", chekcStep='" + getChekcStep() + "'" +
            ", orderNum=" + getOrderNum() +
            ", checkItem='" + getCheckItem() + "'" +
            ", isPass='" + getIsPass() + "'" +
            ", checkAudit='" + getCheckAudit() + "'" +
            ", checker='" + getChecker() + "'" +
            ", checkTime='" + getCheckTime() + "'" +
            ", multiTenancyId='" + getMultiTenancyId() + "'" +
            "}";
    }
}
