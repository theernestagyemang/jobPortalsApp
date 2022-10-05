/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.JsfUtil;

import java.time.LocalDate;

/**
 * @author AlhassanHussein
 */
public class Meeting {
    private Integer id;
    private String enteredBy;
    private String eventDate;
    private String title;
    private String timeFrom;
    private String timeTo;
    private String transactionId;
    private String workStatus;
    private String comment;
    private Integer month;
    private String day;
    private Integer dayOfMonth;

    public Meeting() {
    }

    public Meeting(Integer id, String enteredBy, LocalDate eventDate, String title, String timeFrom, String timeTo, String transactionId, String workStatus, String comment) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.eventDate = JsfUtil.convertToString(eventDate, "dd/MM/yyyy");
        this.title = title;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.transactionId = transactionId;
        this.workStatus = workStatus;
        this.comment = comment;
        if (eventDate != null) {
            this.month = eventDate.getMonthValue();
            this.day = eventDate.getDayOfWeek().name();
            this.dayOfMonth = eventDate.getDayOfMonth();
        }


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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


}
