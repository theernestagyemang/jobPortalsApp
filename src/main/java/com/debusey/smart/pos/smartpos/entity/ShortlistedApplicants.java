/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
@Table(name = "shortlisted_applicants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_seeker_id", "job_id"})})
public class ShortlistedApplicants implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private JobSeeker jobSeekerId;

    @JoinColumn(name = "job_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Jobs jobId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Employer employer;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    private Boolean approved;

    private String status;
    private Boolean processed;

    private String transactionNo;

    public ShortlistedApplicants() {
    }

    public ShortlistedApplicants(Integer id, JobSeeker jobSeekerId, Jobs jobId, Date entryDate, Employer employer,
                                String status, Date transactionDate, Boolean approved, Boolean processed, String transactionNo) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.jobId = jobId;
        this.entryDate = entryDate;
        this.employer = employer;
        this.status = status;
        this.transactionDate = transactionDate;
        this.approved = approved;
        this.processed = processed;
        this.transactionNo = transactionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Jobs getJobId() {
        return jobId;
    }

    public void setJobId(Jobs jobId) {
        this.jobId = jobId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
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
        final ShortlistedApplicants other = (ShortlistedApplicants) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return transactionNo;
    }


}
