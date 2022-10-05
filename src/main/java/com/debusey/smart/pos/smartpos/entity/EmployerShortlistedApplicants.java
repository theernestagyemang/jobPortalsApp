/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author AlhassanHussein
 */

@Entity
@Table(name = "employer_shortlisted_applicants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_seeker_id", "job_title"})})
public class EmployerShortlistedApplicants implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private JobSeeker jobSeekerId;
    @Column(name = "job_title")
    private String jobTitle;
    private LocalDate entryDate;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Employer shortListedBy;
    private LocalDate transactionDate;
    private Boolean approved;
    private Boolean processed;
    private String transactionNo;

    public EmployerShortlistedApplicants() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobSeeker getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(JobSeeker jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Employer getShortListedBy() {
        return shortListedBy;
    }

    public void setShortListedBy(Employer shortListedBy) {
        this.shortListedBy = shortListedBy;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }


}
