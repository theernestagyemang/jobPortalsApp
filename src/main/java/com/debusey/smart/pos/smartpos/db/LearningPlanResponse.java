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
public class LearningPlanResponse {
    private Integer id;
    private String enteredBy;
    private String name;
    private BigDecimal price;
    private String duration;
    private List<String> description;
    private String transactionId;
    private String desc;
    private String color;
    private String btnText;

    public LearningPlanResponse() {
    }

    public LearningPlanResponse(Integer id, String enteredBy, String name, BigDecimal price, String duration, List<String> description, String transactionId, String color, String btnText) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.transactionId = transactionId;
        this.btnText = btnText;
        this.color = color;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }


}
