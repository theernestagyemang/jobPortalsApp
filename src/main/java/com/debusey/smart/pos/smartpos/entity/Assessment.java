/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
@Table(name = "assessment_transactions")
public class Assessment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comments;
    @Column(name = "entry_date")
    private Date entryDate;
    private Double amount;
    @Column(name = "payment_method")
    private String paymentMethode;
    @Column(name = "user_type")
    private String userType;
    private boolean status;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinColumn(name = "assessment_line", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;

    @JoinColumn(name = "assessment_round", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentRound assessmentRound;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    public Assessment(Integer id, String comments, Date entryDate, Double amount, String paymentMethode, boolean status,
                      AssessmentRound assessmentRound, String userType, String transactionId, AssessmentLines assessmentLines, Users users) {
        this.id = id;
        this.comments = comments;
        this.entryDate = entryDate;
        this.amount = amount;
        this.status = status;
        this.paymentMethode = paymentMethode;
        this.userType = userType;
        this.transactionId = transactionId;
        this.assessmentLines = assessmentLines;
        this.users = users;
        this.assessmentRound = assessmentRound;
    }

    public Assessment() {
    }

    public AssessmentRound getAssessmentRound() {
        return assessmentRound;
    }

    public void setAssessmentRound(AssessmentRound assessmentRound) {
        this.assessmentRound = assessmentRound;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethode() {
        return paymentMethode;
    }

    public void setPaymentMethode(String paymentMethode) {
        this.paymentMethode = paymentMethode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
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
        final Assessment other = (Assessment) obj;
        return Objects.equals(this.id, other.id);
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
