/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class TrainingScheduleResponse {
    private Integer id;
    private String enteredBy;
    //    private Integer courseId;
    private String location;
    private String eventDate;
    private String entryDate;
    private String trainingStatus;
    private String transactionId;
    private String month;
    private Integer year;
    private String course;
    private String timeFrom;
    private String timeTo;
    private Double trainingCost;
    private String description;

    public TrainingScheduleResponse() {
    }

    public TrainingScheduleResponse(Integer id, String enteredBy, String location, String eventDate,
                                    String entryDate, String trainingStatus, String transactionId, String month, Integer year,
                                    String course, String timeFrom, String timeTo, Double trainingCost, String description) {
        this.id = id;
        this.enteredBy = enteredBy;
//        this.courseId = courseId;
        this.location = location;
        this.eventDate = eventDate;
        this.entryDate = entryDate;
        this.trainingStatus = trainingStatus;
        this.transactionId = transactionId;
        this.month = month;
        this.year = year;
        this.course = course;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.trainingCost = trainingCost;
        this.description = description;
    }

    public TrainingScheduleResponse(Integer id, String enteredBy, String course, String location, String eventDate, String entryDate, String trainingStatus, String transactionId) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.course = course;
        this.location = location;
        this.eventDate = eventDate;
        this.entryDate = entryDate;
        this.trainingStatus = trainingStatus;
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTrainingCost() {
        return trainingCost;
    }

    public void setTrainingCost(Double trainingCost) {
        this.trainingCost = trainingCost;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

//    public Integer getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Integer courseId) {
//        this.courseId = courseId;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

//    public String getCourse() {
//        return course;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }

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


}
