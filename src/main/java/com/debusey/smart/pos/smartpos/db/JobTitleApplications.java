/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class JobTitleApplications {
    private String job;
    private Integer applications;

    public JobTitleApplications() {
    }

    public JobTitleApplications(String job, Integer applications) {
        this.job = job;
        this.applications = applications;
    }

    public JobTitleApplications(Integer applications, String job) {
        this.job = job;
        this.applications = applications;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getApplications() {
        return applications;
    }

    public void setApplications(Integer applications) {
        this.applications = applications;
    }


}
