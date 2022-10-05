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
public class AssessmentLinesResponse {
    private Integer id;

    private String name;
    private BigDecimal price;
    private String duration;
    private List<String> benefits;
    private Boolean active;
    private String transactionId;

    public AssessmentLinesResponse() {
    }

    public AssessmentLinesResponse(Integer id, String name, BigDecimal price, String duration, List<String> benefits, Boolean active, String transactionId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.benefits = benefits;
        this.active = active;
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
