/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Admin
 */
public class InvoiceBd {
    private Integer id;
    private String orderId;
    private String invoiceType;
    private BigDecimal amount;
    private BigDecimal cot;
    private Date transactionDate;
    private String paymentMethod;
    private String paymentStatus;
    private Integer employerId;
    private String email, name;
    private String strDate;
    private BigDecimal total;
    private String description;

    public InvoiceBd() {
    }

    public InvoiceBd(String paymentStatus, Date transactionDate, BigDecimal amount, BigDecimal cot, String orderId,
                     Integer empId, String email, String name, BigDecimal total, String strDate, String description) {
        this.paymentStatus = paymentStatus;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.cot = cot;
        this.orderId = orderId;
        this.employerId = empId;
        this.email = email;
        this.name = name;
        this.total = total;
        this.strDate = strDate;
        this.description = description;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCot() {
        return cot;
    }

    public void setCot(BigDecimal cot) {
        this.cot = cot;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
