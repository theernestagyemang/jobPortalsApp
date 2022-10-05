/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Date;

/**
 * @author Admin
 */
public class Projects {

    public Integer id;
    private String title;
    private String client;
    private String projectStatus;
    private Integer startYear;
    private Integer endYear;
    private String startMonth;
    private String endMonth;
    private double projectCost;
    private String fundedBy;
    private Date entryDate;
    private Integer jobSeekerId;
    private String details;

    public Projects() {
    }

    public Projects(Integer id, String title, String client, String projectStatus, Integer startYear,
                    Integer endYear, String startMonth, String endMonth, double projectCost,
                    String fundedBy, Date entryDate, Integer jobSeekerId, String details) {
        this.id = id;
        this.title = title;
        this.client = client;
        this.projectStatus = projectStatus;
        this.startYear = startYear;
        this.endYear = endYear;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.projectCost = projectCost;
        this.fundedBy = fundedBy;
        this.entryDate = entryDate;
        this.jobSeekerId = jobSeekerId;
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public double getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(double projectCost) {
        this.projectCost = projectCost;
    }

    public String getFundedBy() {
        return fundedBy;
    }

    public void setFundedBy(String fundedBy) {
        this.fundedBy = fundedBy;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
