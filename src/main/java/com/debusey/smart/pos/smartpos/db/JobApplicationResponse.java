/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class JobApplicationResponse {
    private String id;
    private JobSeekerDetails jobSeeker;
    private String appliedDate;
    private String company;
    private String companyFileName;
    private String jobType;
    private String position;
    private String jobStatus;
    private String jobTransactionId;

    public JobApplicationResponse() {
    }

    public JobApplicationResponse(String id, JobSeekerDetails jobSeeker, String appliedDate, String company, String companyFileName, String jobType, String position, String jobStatus, String jobTransactionId) {
        this.id = id;
        this.jobSeeker = jobSeeker;
        this.appliedDate = appliedDate;
        this.company = company;
        this.companyFileName = companyFileName;
        this.jobType = jobType;
        this.position = position;
        this.jobStatus = jobStatus;
        this.jobTransactionId = jobTransactionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobSeekerDetails getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekerDetails jobSeeker) {
        this.jobSeeker = jobSeeker;
    }


    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyFileName() {
        return companyFileName;
    }

    public void setCompanyFileName(String companyFileName) {
        this.companyFileName = companyFileName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobTransactionId() {
        return jobTransactionId;
    }

    public void setJobTransactionId(String jobTransactionId) {
        this.jobTransactionId = jobTransactionId;
    }


}
