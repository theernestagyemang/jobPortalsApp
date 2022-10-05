/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author AlhassanHussein
 */


@Entity
@Table(name = "employer_subscription_lines")
public class EmployerSubscriptionLines implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "transaction_id")
    private String transactionId;

    private Integer createdByEmployeeId; //Employee Id

    private LocalDateTime entryDate;

    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    @ManyToOne
    private EmployerSubscription subscription;

    public EmployerSubscriptionLines() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public EmployerSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(EmployerSubscription subscription) {
        this.subscription = subscription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setCreatedByEmployeeId(Integer createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }


}
