/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api;

import com.debusey.smart.pos.smartpos.JsfUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author AlhassanHussein
 */
public class SubscriptionRetObject {

    private String seekerName;
    private String seekerEmail;
    private Date invoiceDate;
    private String invoiceStatus;
    private String invoiceNumber;
    private String description;
    private BigDecimal amt;
    private BigDecimal cot;
    private BigDecimal total;
    private Boolean isConfirmed;
    private String strInvoiceDate;

    public SubscriptionRetObject() {
    }


    public SubscriptionRetObject(String seekerName, String seekerEmail, Date invoiceDate,
                                 String invoiceStatus, String invoiceNumber, String description, BigDecimal amt, BigDecimal cot, BigDecimal total) {
        this.seekerName = seekerName;
        this.seekerEmail = seekerEmail;
        this.invoiceDate = invoiceDate;
        this.invoiceStatus = invoiceStatus;
        this.invoiceNumber = invoiceNumber;
        this.description = description;
        this.amt = amt;
        this.cot = cot;
        this.total = total;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getSeekerEmail() {
        return seekerEmail;
    }

    public void setSeekerEmail(String seekerEmail) {
        this.seekerEmail = seekerEmail;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getCot() {
        return cot;
    }

    public void setCot(BigDecimal cot) {
        this.cot = cot;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getStrInvoiceDate() {
        return strInvoiceDate;
    }

    public void setStrInvoiceDate(String strInvoiceDate) {
        this.strInvoiceDate = strInvoiceDate;
    }

    private String convertToString(Date invoiceDate) {
        return JsfUtil.convertFromDate(invoiceDate);
    }


}
