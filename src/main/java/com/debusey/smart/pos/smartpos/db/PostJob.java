/*
 * To change this license header ; choose License Headers in Project Properties.
 * To change this template file ; choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Jobs;

import java.math.BigDecimal;

/**
 * @author AlhassanHussein
 */
public class PostJob {
    private String jobTitle;
    private String jobTags;
    private String jobType;
    private String experience;
    private String category;
    private BigDecimal renumeration;
    private BigDecimal toRenumeration;
    private String region;
    private String jobCountry;
    private String expDate;
    private String city;
    private String howToApply;
    private String jobTown;
    private String minQualification;
    private Boolean showComp;
    private Boolean publish;
    private String jobDescription;
    private Integer slot;
    private Integer jobId;
    private String prefSkills;
    private String companyName;
    private String comDescription;
    private String compPhone;
    private String compEmail;
    private String compCity;
    private String compAddress;

    public PostJob() {
    }

    public PostJob(Jobs job) {
        this.jobTitle = job.getJobTitle();
        this.jobTags = job.getJobTags();
        this.jobType = job.getJobType();
        this.experience = job.getExperience();
        this.category = job.getCategory();
        this.renumeration = job.getRenumeration();
        this.toRenumeration = job.getToRenumeration();
        this.region = job.getRegion();
        this.jobCountry = job.getCountry();
        this.expDate = JsfUtil.convertFromDate(job.getExpireDate(), "dd/MM/yyyy");
        this.city = job.getJobCity();
        this.howToApply = job.getHowToApply();
        this.jobTown = job.getJobCity();
        this.minQualification = job.getMinQualification();
        this.showComp = job.getShowCompanyName();
        this.publish = job.getPublished();
        this.jobDescription = job.getJobDescription();
        this.slot = job.getNoNeeded();
        this.jobId = job.getId();
        Employer employer = job.getPostedBy();
        if (employer == null) {
            this.prefSkills = job.getPrefSkillsAttribute();
            this.companyName = "";
            this.comDescription = "";
            this.compPhone = "";
            this.compEmail = "";
            this.compCity = "";
            this.compAddress = "";
        } else {
            this.prefSkills = job.getPrefSkillsAttribute();
            this.companyName = employer.getCompanyName();
            this.comDescription = employer.getDescription();
            this.compPhone = employer.getPhoneNumber();
            this.compEmail = employer.getEmail();
            this.compCity = employer.getCity();
            this.compAddress = employer.getAddress();
        }

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTags() {
        return jobTags;
    }

    public void setJobTags(String jobTags) {
        this.jobTags = jobTags;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getRenumeration() {
        return renumeration;
    }

    public void setRenumeration(BigDecimal renumeration) {
        this.renumeration = renumeration;
    }

    public BigDecimal getToRenumeration() {
        return toRenumeration;
    }

    public void setToRenumeration(BigDecimal toRenumeration) {
        this.toRenumeration = toRenumeration;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getJobCountry() {
        return jobCountry;
    }

    public void setJobCountry(String jobCountry) {
        this.jobCountry = jobCountry;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    public String getJobTown() {
        return jobTown;
    }

    public void setJobTown(String jobTown) {
        this.jobTown = jobTown;
    }

    public String getMinQualification() {
        return minQualification;
    }

    public void setMinQualification(String minQualification) {
        this.minQualification = minQualification;
    }

    public Boolean getShowComp() {
        return showComp;
    }

    public void setShowComp(Boolean showComp) {
        this.showComp = showComp;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }


    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getPrefSkills() {
        return prefSkills;
    }

    public void setPrefSkills(String prefSkills) {
        this.prefSkills = prefSkills;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getComDescription() {
        return comDescription;
    }

    public void setComDescription(String comDescription) {
        this.comDescription = comDescription;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getCompEmail() {
        return compEmail;
    }

    public void setCompEmail(String compEmail) {
        this.compEmail = compEmail;
    }

    public String getCompCity() {
        return compCity;
    }

    public void setCompCity(String compCity) {
        this.compCity = compCity;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }


}
