/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class ActiveJob {
    private String month;
    private Integer monthNumber;
    private Integer shortlisted;
    private Integer applications;

    public ActiveJob() {
    }


    public ActiveJob(String month, Integer monthNumber, Integer shortlisted, Integer applications) {
        this.month = month;
        this.monthNumber = monthNumber;
        this.shortlisted = shortlisted;
        this.applications = applications;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(Integer shortlisted) {
        this.shortlisted = shortlisted;
    }

    public Integer getApplications() {
        return applications;
    }

    public void setApplications(Integer applications) {
        this.applications = applications;
    }


}
