/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;


import com.debusey.smart.pos.smartpos.JsfUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amout", nullable = false, precision = 8, scale = 2)
    private BigDecimal amout;

    @Basic(optional = false)
    @Column(name = "cot", precision = 8, scale = 2)
    private BigDecimal cot;
    private String caption;


    @Column(name = "entered_by", length = 100)
    private String enteredBy;
    @Column(name = "entry_date")
    @Temporal(TemporalType.DATE)
    private Date entryDate;
    @Column(name = "name", length = 150)
    private String name;
    @OneToMany(mappedBy = "subscriptionId")
    private List<Employer> employerList;

    @Column(columnDefinition = "integer default 0")
    private Integer cvCount;

    @Column(columnDefinition = "integer default 0")
    private Integer jobprefCount;

    @Column(columnDefinition = "boolean default 1")
    private Boolean unlimited;

    @Temporal(TemporalType.DATE)
    @Column(name = "expire_date")
    private Date expireDate;
    private String transactionId;

    private String message;

    public Subscription() {
    }

    public Subscription(Integer id, BigDecimal amount) {
        this.id = id;
        this.amout = amount;
        this.transactionId = JsfUtil.generateSerial();
    }

    public Subscription(String name, String message, BigDecimal amout) {
        this.amout = amout;
        this.name = name;
        this.message = message;
        this.transactionId = JsfUtil.generateSerial();
    }

    public Subscription(Integer id, String name, String message, BigDecimal amout) {
        this.amout = amout;
        this.name = name;
        this.message = message;
        this.id = id;
        this.cvCount = 0;
        this.transactionId = JsfUtil.generateSerial();
    }

    public Subscription(String name, String message, String caption, BigDecimal amout) {
        this.amout = amout;
        this.name = name;
        this.message = message;
        this.caption = caption;
        this.jobprefCount = 0;
        this.transactionId = JsfUtil.generateSerial();
    }


    public Integer getId() {
        return id;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
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

    public List<Employer> getEmployerList() {
        return employerList;
    }

    public void setEmployerList(List<Employer> employerList) {
        this.employerList = employerList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Subscription other = (Subscription) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getCvCount() {
        return cvCount;
    }

    public void setCvCount(Integer cvCount) {
        this.cvCount = cvCount;
    }

    public BigDecimal getCot() {
        return cot;
    }

    public void setCot(BigDecimal cot) {
        this.cot = cot;
    }

    public Integer getJobprefCount() {
        return jobprefCount;
    }

    public void setJobprefCount(Integer jobprefCount) {
        this.jobprefCount = jobprefCount;
    }

    public Boolean getUnlimited() {
        return unlimited;
    }

    public void setUnlimited(Boolean unlimited) {
        this.unlimited = unlimited;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
