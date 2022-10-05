/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
@Table(name = "assessment_lines", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
public class AssessmentLines implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;
    //    private BigDecimal cot;
    private String duration;
    //    private String userType;
    @Lob
    private String benefits;
    private Boolean active;
    private LocalDate entryDate;
    @Column(name = "transaction_id")
    private String transactionId;

    public AssessmentLines() {
    }

    public AssessmentLines(
            Integer id, String name, BigDecimal price, String duration,
            Boolean active, LocalDate entryDate, String benefits, String transactionId) {
        this.id = id;
        this.name = name;
        this.price = price;
//        this.cot = cot;
        this.duration = duration;
//        this.userType = userType;
        this.active = active;
        this.benefits = benefits;
        this.entryDate = entryDate;
        this.transactionId = transactionId;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefit) {
        this.benefits = benefit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final AssessmentLines other = (AssessmentLines) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return name;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
