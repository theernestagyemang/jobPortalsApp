/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class EmployerSubscriptionsLinesDb {
    private Integer id;
    private String message;
    private String transactionId;
    private String entryDate;
    private Integer subscription;

    public EmployerSubscriptionsLinesDb() {
    }


    public EmployerSubscriptionsLinesDb(Integer id, String message, String transactionId, String entryDate, Integer subscription) {
        this.id = id;
        this.message = message;
        this.transactionId = transactionId;
        this.entryDate = entryDate;
        this.subscription = subscription;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getSubscription() {
        return subscription;
    }

    public void setSubscription(Integer subscription) {
        this.subscription = subscription;
    }


}
