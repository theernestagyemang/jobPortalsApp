/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Date;
import java.util.Objects;

/**
 * @author Admin
 */
public class Education {
    private Integer id;

    private String institutionName;
    private String qualificationReceived;
    private String programStudied;
    private String courseDescription;
    private Integer yearGraduated;
    private Integer yearStarted;
    private Date entryDate;
    private Integer jobSeekerId;
    private String transactionId;

    public Education() {
    }

    public Education(Integer id, String institutionName, String qualificationReceived, String programStudied, String courseDescription,
                     Integer yearGraduated, Integer yearStarted, Date entryDate, Integer jobSeekerId, String transactionId) {
        this.id = id;
        this.institutionName = institutionName;
        this.qualificationReceived = qualificationReceived;
        this.programStudied = programStudied;
        this.courseDescription = courseDescription;
        this.yearGraduated = yearGraduated;
        this.yearStarted = yearStarted;
        this.entryDate = entryDate;
        this.jobSeekerId = jobSeekerId;
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getQualificationReceived() {
        return qualificationReceived;
    }

    public void setQualificationReceived(String qualificationReceived) {
        this.qualificationReceived = qualificationReceived;
    }

    public String getProgramStudied() {
        return programStudied;
    }

    public void setProgramStudied(String programStudied) {
        this.programStudied = programStudied;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getYearGraduated() {
        return yearGraduated;
    }

    public void setYearGraduated(Integer yearGraduated) {
        this.yearGraduated = yearGraduated;
    }

    public Integer getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(Integer yearStarted) {
        this.yearStarted = yearStarted;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Education other = (Education) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return institutionName;
    }


}
