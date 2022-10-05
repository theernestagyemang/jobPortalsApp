/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author SL002
 */
@Entity
@Indexed
public class WorkExperience implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @FullTextField
    @Column(name = "company_name", length = 100)
    private String companyName;

    @Column(name = "descriptions", length = 100)
    private String descriptions;
    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @FullTextField
    private String designation;

    @Column(name = "job_profile", length = 1000)
    private String jobProfile;

    @FullTextField
    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Column(name = "month_started_work", length = 60)
    private String monthStartedWork;

    @Column(name = "month_stoped_work", length = 60)
    private String monthStopedWork;

    @Column(name = "still_work_there", length = 60)
    private String stillWorkThere;
    @Column(name = "year_started_work")
    private Integer yearStartedWork;
    @Column(name = "year_stoped_work")
    private Integer yearStopedWork;
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
    @JoinColumn(name = "position_id", referencedColumnName = "ID")
    @ManyToOne
    private Positions positionId;
    private String industry;
    private String transactionId;
    private String region;
    private String city;
    private String country;
    private BigDecimal currentSalary;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobSeekerId;
    private String highestPosition;

    public WorkExperience() {
    }

    public WorkExperience(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Positions getPositionId() {
        return positionId;
    }

    public void setPositionId(Positions positionId) {
        this.positionId = positionId;
    }


    public JobSeeker getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(JobSeeker jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkExperience)) {
            return false;
        }
        WorkExperience other = (WorkExperience) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return companyName + " " + yearsOfExperience;
    }

    public String getHighestPosition() {
        return highestPosition;
    }

    public void setHighestPosition(String highestPosition) {
        this.highestPosition = highestPosition;
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

}
