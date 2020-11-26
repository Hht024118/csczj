package com.thsware.framework.domain.bo;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: jianghaolin
 * @date: 2020/11/25
 * @version: 1.0
 */
public class ZjProjectBo {

    private String contractNo;

    private String projectNo;

    private String projectName;

    private String projectManagerName;

    private String implementUnit;

    private String projectState;

    private String delegateUnit;

    private List parentIdList;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getImplementUnit() {
        return implementUnit;
    }

    public void setImplementUnit(String implementUnit) {
        this.implementUnit = implementUnit;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getDelegateUnit() {
        return delegateUnit;
    }

    public void setDelegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
    }

    public List getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(List parentIdList) {
        this.parentIdList = parentIdList;
    }
}
