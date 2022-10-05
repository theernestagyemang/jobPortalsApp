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
public class WorkEx {

    private String companyName;
    private String descriptions;
    private Date entryDate;
    private String designation;
    private String jobProfile;
    private String jobTitle;
    private String monthStartedWork;
    private String monthStopedWork;
    private String stillWorkThere;
    private Integer yearStartedWork;
    private Integer yearStopedWork;
    private Integer yearsOfExperience;
    private String industry;
    private String transactionId;
    private String region;
    private String city;
    private String country;
    private BigDecimal currentSalary;
    private Integer jobSeekerId;
    private String highestPosition;
    private Integer id;

    public WorkEx() {
    }

    public WorkEx(String companyName, String descriptions, Date entryDate, String designation,
                  String jobProfile, String jobTitle, String monthStartedWork, String monthStopedWork,
                  String stillWorkThere, Integer yearStartedWork, Integer yearStopedWork, Integer yearsOfExperience,
                  String industry, String transactionId, String region, String city, String country,
                  BigDecimal currentSalary, Integer jobSeekerId, String highestPosition, Integer id) {
        this.companyName = companyName;
        this.descriptions = descriptions;
        this.entryDate = entryDate;
        this.designation = designation;
        this.jobProfile = jobProfile;
        this.jobTitle = jobTitle;
        this.monthStartedWork = monthStartedWork;
        this.monthStopedWork = monthStopedWork;
        this.stillWorkThere = stillWorkThere;
        this.yearStartedWork = yearStartedWork;
        this.yearStopedWork = yearStopedWork;
        this.yearsOfExperience = yearsOfExperience;
        this.industry = industry;
        this.transactionId = transactionId;
        this.region = region;
        this.city = city;
        this.country = country;
        this.currentSalary = currentSalary;
        this.jobSeekerId = jobSeekerId;
        this.highestPosition = highestPosition;
        this.id = id;


    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMonthStartedWork() {
        return monthStartedWork;
    }

    public void setMonthStartedWork(String monthStartedWork) {
        this.monthStartedWork = monthStartedWork;
    }

    public String getMonthStopedWork() {
        return monthStopedWork;
    }

    public void setMonthStopedWork(String monthStopedWork) {
        this.monthStopedWork = monthStopedWork;
    }

    public String getStillWorkThere() {
        return stillWorkThere;
    }

    public void setStillWorkThere(String stillWorkThere) {
        this.stillWorkThere = stillWorkThere;
    }

    public Integer getYearStartedWork() {
        return yearStartedWork;
    }

    public void setYearStartedWork(Integer yearStartedWork) {
        this.yearStartedWork = yearStartedWork;
    }

    public Integer getYearStopedWork() {
        return yearStopedWork;
    }

    public void setYearStopedWork(Integer yearStopedWork) {
        this.yearStopedWork = yearStopedWork;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getHighestPosition() {
        return highestPosition;
    }

    public void setHighestPosition(String highestPosition) {
        this.highestPosition = highestPosition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
