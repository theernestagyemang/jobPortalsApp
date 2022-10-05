/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author SL002
 */
@Entity
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "entered_by", nullable = false, length = 60)
    private String enteredBy;
    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @Column(name = "message", nullable = false, length = 60)
    private String message;
    @Column(name = "msg_read")
    private Boolean msgRead;

    @Column(name = "sender", length = 60)
    private String sender;

    @Column(name = "subject", length = 60)
    private String subject;

    @JoinColumn(name = "jobseeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobseekerId;

    @JoinColumn(name = "recruiter_id", referencedColumnName = "id")
    @ManyToOne
    private Recruiter recruiterId;

    public Notifications() {
    }

    public Notifications(Integer id) {
        this.id = id;
    }

    public Notifications(Integer id, String enteredBy, String message) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getMsgRead() {
        return msgRead;
    }

    public void setMsgRead(Boolean msgRead) {
        this.msgRead = msgRead;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public JobSeeker getJobseekerId() {
        return jobseekerId;
    }

    public void setJobseekerId(JobSeeker jobseekerId) {
        this.jobseekerId = jobseekerId;
    }


    public Recruiter getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Recruiter recruiterId) {
        this.recruiterId = recruiterId;
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
        if (!(object instanceof Notifications)) {
            return false;
        }
        Notifications other = (Notifications) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return message;
    }

}
