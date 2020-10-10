package com.thsware.framework.domain;

import com.thsware.framework.annotation.ThsMultiTenancyId;
import com.thsware.framework.listener.ThsMultiTenancyIdListener;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 造价咨询_质量检查记录
 */
@ApiModel(description = "造价咨询_质量检查记录")
@Entity
@Table(name = "zj_check_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name = "multiTenancyIdFilter", parameters = { @ParamDef(name = "multiTenancyId", type = "string") })
@Filters({ @Filter(name = "multiTenancyIdFilter",condition = "multi_tenancy_id like :multiTenancyId")})
@EntityListeners({AuditingEntityListener.class, ThsMultiTenancyIdListener.class})
public class ZjCheckLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(max = 36)
    @Column(name = "zj_specialty_id", length = 36)
    private String zjSpecialtyId;

    @Size(max = 20)
    @Column(name = "chekc_step", length = 20)
    private String chekcStep;

    @Column(name = "order_num")
    private Integer orderNum;

    @Size(max = 200)
    @Column(name = "check_item", length = 200)
    private String checkItem;

    @Size(max = 1)
    @Column(name = "is_pass", length = 1)
    private String isPass;

    @Size(max = 200)
    @Column(name = "check_audit", length = 200)
    private String checkAudit;

    @Size(max = 50)
    @Column(name = "checker", length = 50)
    private String checker;

    @Column(name = "check_time")
    private Instant checkTime;

    @ThsMultiTenancyId
    @Size(max = 40)
    @Column(name="multi_tenancy_id",length = 40)
    private String multiTenancyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZjSpecialtyId() {
        return zjSpecialtyId;
    }

    public ZjCheckLog zjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
        return this;
    }

    public void setZjSpecialtyId(String zjSpecialtyId) {
        this.zjSpecialtyId = zjSpecialtyId;
    }

    public String getChekcStep() {
        return chekcStep;
    }

    public ZjCheckLog chekcStep(String chekcStep) {
        this.chekcStep = chekcStep;
        return this;
    }

    public void setChekcStep(String chekcStep) {
        this.chekcStep = chekcStep;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public ZjCheckLog orderNum(Integer orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public ZjCheckLog checkItem(String checkItem) {
        this.checkItem = checkItem;
        return this;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getIsPass() {
        return isPass;
    }

    public ZjCheckLog isPass(String isPass) {
        this.isPass = isPass;
        return this;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getCheckAudit() {
        return checkAudit;
    }

    public ZjCheckLog checkAudit(String checkAudit) {
        this.checkAudit = checkAudit;
        return this;
    }

    public void setCheckAudit(String checkAudit) {
        this.checkAudit = checkAudit;
    }

    public String getChecker() {
        return checker;
    }

    public ZjCheckLog checker(String checker) {
        this.checker = checker;
        return this;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Instant getCheckTime() {
        return checkTime;
    }

    public ZjCheckLog checkTime(Instant checkTime) {
        this.checkTime = checkTime;
        return this;
    }

    public String getMultiTenancyId() {
        return multiTenancyId;
    }

    public void setMultiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
    }

    public ZjCheckLog multiTenancyId(String multiTenancyId) {
        this.multiTenancyId = multiTenancyId;
        return this;
    }

    public void setCheckTime(Instant checkTime) {
        this.checkTime = checkTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZjCheckLog zjCheckLog = (ZjCheckLog) o;
        if (zjCheckLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zjCheckLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZjCheckLog{" +
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
