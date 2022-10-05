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
import java.util.Date;

/**
 * @author SL002
 */
@Entity
@Indexed
public class EducationalExperience implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @FullTextField
    private String institutionName;
    private String qualificationReceived;
    private String programStudied;
    private String courseDescription;
    @Column(name = "year_graduated")
    private Integer yearGraduated;

    private Integer yearStarted;

    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobSeekerId;
    private String transactionId;


    public EducationalExperience() {
    }

    public EducationalExperience(Integer id, String institutionName,
                                 String qualificationReceived, String programStudied,
                                 String courseDescription, Integer yearGraduated,
                                 Integer yearStarted, Date entryDate) {
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


    public EducationalExperience(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
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

    public Integer getYearGraduated() {
        return yearGraduated;
    }

    public void setYearGraduated(Integer yearGraduated) {
        this.yearGraduated = yearGraduated;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public JobSeeker getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(JobSeeker jobSeekerId) {
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EducationalExperience)) {
            return false;
        }
        EducationalExperience other = (EducationalExperience) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return institutionName;
    }

    public Integer getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(Integer yearStarted) {
        this.yearStarted = yearStarted;
    }


}
