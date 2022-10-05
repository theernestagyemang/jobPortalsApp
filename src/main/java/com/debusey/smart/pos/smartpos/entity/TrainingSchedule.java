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
import java.time.LocalDate;

@Entity
@Table(name = "training_schedule", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
public class TrainingSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "entered_by", length = 50)
    private String enteredBy;

    //    @NotFound(action = NotFoundAction.IGNORE)
//    @JoinColumn(name = "course_id", referencedColumnName = "id")
//    @ManyToOne
//    private Course course;
    private String location;
    private LocalDate eventDate;
    private LocalDate entryDate;
    private Integer year;
    private String courseScheduled;
    private String trainingStatus;
    @Column(name = "transaction_id")
    private String transactionId;
    private String eventMonth;
    private String timeTo;
    private String timeFrom;
    private Double training_cost;
    private String picture;
    @Lob
    private String description;

    public TrainingSchedule() {
    }

    public TrainingSchedule(Integer id, String enteredBy, Course course, String location, LocalDate eventDate, LocalDate entryDate,
                            String trainingStatus, Double training_cost, String description, String courseScheduled, String picture) {
        this.id = id;
        this.enteredBy = enteredBy;
//        this.course = course;
        this.location = location;
        this.eventDate = eventDate;
        this.entryDate = entryDate;
        this.trainingStatus = trainingStatus;
        this.training_cost = training_cost;
        this.description = description;
        this.courseScheduled = courseScheduled;
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTraining_cost() {
        return training_cost;
    }

    public void setTraining_cost(Double training_cost) {
        this.training_cost = training_cost;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCourseScheduled() {
        return courseScheduled;
    }

    public void setCourseScheduled(String courseScheduled) {
        this.courseScheduled = courseScheduled;
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

//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }


}
