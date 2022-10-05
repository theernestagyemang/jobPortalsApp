/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class AddEmployment {
    private String designation;
    private String org;
    private String curComp;
    private Integer startYear;
    private String startMonth;
    private Integer endYear;
    private String endMonth;
    private String jobProfile;
    private String stillWork;
    private Integer id;
    private Integer eduId;

    public AddEmployment() {
    }

    public AddEmployment(String designation, String org, String curComp, Integer startYear, String startMonth, Integer endYear, String endMonth, String jobProfile, String stillWork, Integer id, Integer eduId) {
        this.designation = designation;
        this.org = org;
        this.curComp = curComp;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.jobProfile = jobProfile;
        this.stillWork = stillWork;
        this.id = id;
        this.eduId = eduId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getCurComp() {
        return curComp;
    }

    public void setCurComp(String curComp) {
        this.curComp = curComp;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getStillWork() {
        return stillWork;
    }

    public void setStillWork(String stillWork) {
        this.stillWork = stillWork;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }


}
