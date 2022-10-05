/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author AlhassanHussein
 */
public class JobsResponse {
    private Integer id;
    private String postedDate;
    private Integer applied;
    private Integer shortlisted;
    private String publishedDate;
    private String expireDate;
    private String location;
    private String company;
    private String companyFileName;
    private String jobType;
    private String position;
    private String jobStatus;
    private String summary;
    private String transactionId;
    private BigDecimal minSal;
    private BigDecimal maxSal;
    private Integer views;


    public JobsResponse() {
    }

    public JobsResponse(Integer id, String postedDate, Integer applied, Integer shortlisted, String publishedDate,
                        String expireDate, String location, String company, String companyFileName, String jobType,
                        String position, String jobStatus, String summary, String transactionId,
                        BigDecimal minSal, BigDecimal maxSal, Integer views) {
        this.id = id;
        this.postedDate = postedDate;
        this.applied = applied;
        this.shortlisted = shortlisted;
        this.publishedDate = publishedDate;
        this.expireDate = expireDate;
        this.location = location;
        this.company = company;
        this.companyFileName = companyFileName;
        this.jobType = jobType;
        this.position = position;
        this.jobStatus = jobStatus;
        this.summary = summary;
        this.transactionId = transactionId;
        this.minSal = minSal;
        this.maxSal = maxSal;
        this.views = views;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getApplied() {
        return applied;
    }

    public void setApplied(Integer applied) {
        this.applied = applied;
    }

    public Integer getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(Integer shortlisted) {
        this.shortlisted = shortlisted;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getMinSal() {
        return minSal;
    }

    public void setMinSal(BigDecimal minSal) {
        this.minSal = minSal;
    }

    public BigDecimal getMaxSal() {
        return maxSal;
    }

    public void setMaxSal(BigDecimal maxSal) {
        this.maxSal = maxSal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobsResponse other = (JobsResponse) obj;
        return Objects.equals(this.id, other.id);
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }


}
