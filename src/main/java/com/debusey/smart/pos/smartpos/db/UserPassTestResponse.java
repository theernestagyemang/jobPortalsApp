package com.debusey.smart.pos.smartpos.db;

import java.util.Date;

public class UserPassTestResponse {
    private Integer id;

    private Date testDate;
    private Double score;
    private Double outOf;

    private Integer duration;

    private String transactionId;

    private String assessmentLines;

    private String users;

    public UserPassTestResponse(Integer id, Date testDate, Double score, Double outOf, String transactionId,
                                Integer duration,String assessmentLines, String users) {
        this.id = id;
        this.testDate = testDate;
        this.score = score;
        this.outOf = outOf;
        this.duration = duration;
        this.transactionId = transactionId;
        this.assessmentLines = assessmentLines;
        this.users = users;
    }

    public UserPassTestResponse() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getOutOf() {
        return outOf;
    }

    public void setOutOf(Double outOf) {
        this.outOf = outOf;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(String assessmentLines) {
        this.assessmentLines = assessmentLines;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
