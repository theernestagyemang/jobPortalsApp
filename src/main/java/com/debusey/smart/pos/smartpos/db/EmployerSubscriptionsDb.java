/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author AlhassanHussein
 */
public class EmployerSubscriptionsDb {
    private Integer id;
    private String category;
    private String message;
    private String caption;
    private String transactionId;
    private BigDecimal amount;
    private String headerColor;
    private String bodyColor;
    private List<EmployerSubscriptionsLinesDb> lines;

    public EmployerSubscriptionsDb() {
    }

    public EmployerSubscriptionsDb(Integer id, String category, String message, String caption, String transactionId, BigDecimal amount, List<EmployerSubscriptionsLinesDb> lines, String headerColor, String bodyColor) {
        this.id = id;
        this.category = category;
        this.transactionId = transactionId;
        this.amount = amount;
        this.lines = lines;
        this.message = message;
        this.caption = caption;
        this.bodyColor = bodyColor;
        this.headerColor = headerColor;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<EmployerSubscriptionsLinesDb> getLines() {
        return lines;
    }

    public void setLines(List<EmployerSubscriptionsLinesDb> lines) {
        this.lines = lines;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }


}
