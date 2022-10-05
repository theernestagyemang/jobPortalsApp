/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

/**
 * @author AlhassanHussein
 */

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "learning_plan", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
public class LearningPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "entered_by", length = 50)
    private String enteredBy;
    private String name;
    private BigDecimal price;
    private BigDecimal cot;
    private String duration;
    private String description;
    @Column(name = "transaction_id")
    private String transactionId;
    private String color;
    private String btnText;

    public LearningPlan() {
    }

    public LearningPlan(Integer id, String enteredBy, String name, BigDecimal price, String duration, String description) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getCot() {
        return cot;
    }

    public void setCot(BigDecimal cot) {
        this.cot = cot;
    }


}
