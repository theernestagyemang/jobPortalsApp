package com.debusey.smart.pos.smartpos.db;

import java.util.Date;

public class AssessmentTransactionResponse {
    private Integer id;
    private String comments;
    private Date entryDate;
    private Double amount;
    private String paymentMethode;
    private String userType;
    private String transactionId;
    private String userId;
    private String assessmentLine;

    private Integer participant;

    public AssessmentTransactionResponse(Integer id, String comments, Date entryDate, Double amount,
                                         String paymentMethode, String userType, String transactionId,
                                         String userId, String assessmentLine, Integer participant) {
        this.id = id;
        this.comments = comments;
        this.entryDate = entryDate;
        this.amount = amount;
        this.paymentMethode = paymentMethode;
        this.participant = participant;
        this.userType = userType;
        this.transactionId = transactionId;
        this.userId = userId;
        this.assessmentLine = assessmentLine;
    }

    public AssessmentTransactionResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Integer getParticipant() {
        return participant;
    }

    public void setParticipant(Integer participant) {
        this.participant = participant;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssessmentLine() {
        return assessmentLine;
    }

    public void setAssessmentLine(String assessmentLine) {
        this.assessmentLine = assessmentLine;
    }
}
