/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Admin
 */
public class Alerts {


    public Integer id;
    private String jobKeywors;
    private String jobLocation;
    private String jobWorkExp;
    private String jobCategory;
    private String name;
    private BigDecimal expSalary;
    private String email;
    private Boolean approved;
    private Date entryDate;
    private Integer jobSeekerId;

    public Alerts() {
    }

    public Alerts(Integer id, String jobKeywors, String jobLocation, String jobWorkExp, String jobCategory,
                  String name, BigDecimal expSalary, String email, Boolean approved, Date entryDate, Integer jobSeekerId) {
        this.id = id;
        this.jobKeywors = jobKeywors;
        this.jobLocation = jobLocation;
        this.jobWorkExp = jobWorkExp;
        this.jobCategory = jobCategory;
        this.name = name;
        this.expSalary = expSalary;
        this.email = email;
        this.approved = approved;
        this.entryDate = entryDate;
        this.jobSeekerId = jobSeekerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobKeywors() {
        return jobKeywors;
    }

    public void setJobKeywors(String jobKeywors) {
        this.jobKeywors = jobKeywors;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobWorkExp() {
        return jobWorkExp;
    }

    public void setJobWorkExp(String jobWorkExp) {
        this.jobWorkExp = jobWorkExp;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getExpSalary() {
        return expSalary;
    }

    public void setExpSalary(BigDecimal expSalary) {
        this.expSalary = expSalary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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


}
