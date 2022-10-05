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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author SL002
 */
@Entity
@Table(name = "service_request")
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "entry_date")
    @Temporal(TemporalType.DATE)
    private Date entryDate;


    private String transactionId;
    private BigDecimal Amount;
    private String comment;
    private String status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Users requestedBy;


    @ManyToOne
    @JoinColumn(name = "service_type_id",referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ServiceType serviceType;
    private String workStatus;  //pending/started/completed

    private String fileCopy;   //null

    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upload_date;  //null

    private Long processedBy;  //userId reu null

    public ServiceRequest() {
    }

    public ServiceRequest(
            Integer id, Date entryDate, String transactionId, BigDecimal amount, String comment, Users requestedBy,
            String workStatus, String fileCopy, Date upload_date, Long processedBy, ServiceType serviceType) {
        this.id = id;
        this.entryDate = entryDate;
        this.transactionId = transactionId;
        Amount = amount;
        this.comment = comment;
        this.requestedBy = requestedBy;
        this.workStatus = workStatus;
        this.fileCopy = fileCopy;
        this.upload_date = upload_date;
        this.processedBy = processedBy;
        this.serviceType = serviceType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Users getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Users requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getFileCopy() {
        return fileCopy;
    }

    public void setFileCopy(String fileCopy) {
        this.fileCopy = fileCopy;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public Long getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Long processedBy) {
        this.processedBy = processedBy;
    }


    @Override
    public String toString() {
        return "ResumeRequest{" +
                "id=" + id +
                ", entryDate=" + entryDate +
                ", transactionId='" + transactionId + '\'' +
                ", Amount=" + Amount +
                ", comment='" + comment + '\'' +
                ", requestedBy=" + requestedBy +
                ", workStatus='" + workStatus + '\'' +
                ", fileCopy='" + fileCopy + '\'' +
                ", upload_date=" + upload_date +
                ", processedBy=" + processedBy +
                '}';
    }
}
