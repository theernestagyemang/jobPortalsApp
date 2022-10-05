/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author AlhassanHussein
 */
public class SubscriptionDb {

    private Integer id;
    private BigDecimal amout;
    private BigDecimal cot;
    private String enteredBy;
    private Date entryDate;
    private String name;
    private Integer cvCount;
    private String message;
    private Integer jbCount;

    public SubscriptionDb() {
    }

    public SubscriptionDb(BigDecimal amout, BigDecimal cot, String enteredBy, Date entryDate, String name, Integer cvCount, String message) {
        this.amout = amout;
        this.cot = cot;
        this.enteredBy = enteredBy;
        this.entryDate = entryDate;
        this.name = name;
        this.cvCount = cvCount;
        this.message = message;
    }

    public SubscriptionDb(BigDecimal amout, BigDecimal cot, String enteredBy, Date entryDate, String name, Integer cvCount, String message, Integer jbCount, Integer id) {
        this.amout = amout;
        this.cot = cot;
        this.enteredBy = enteredBy;
        this.entryDate = entryDate;
        this.name = name;
        this.cvCount = cvCount;
        this.message = message;
        this.jbCount = jbCount;
        this.id = id;
    }

    public Integer getJbCount() {
        return jbCount;
    }

    public void setJbCount(Integer jbCount) {
        this.jbCount = jbCount;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    public BigDecimal getCot() {
        return cot;
    }

    public void setCot(BigDecimal cot) {
        this.cot = cot;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCvCount() {
        return cvCount;
    }

    public void setCvCount(Integer cvCount) {
        this.cvCount = cvCount;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
