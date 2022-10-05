/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class AddSchool {
    private String nameOfSchool;
    private String course;
    private String educationLevel;
    private Integer gradYear;
    private Integer jobSeekerId;
    private Integer schooId;
    private Integer startYear;

    public AddSchool() {
    }

    public AddSchool(String nameOfSchool, String course, String educationLevel, Integer gradYear, Integer jobSeekerId, Integer schooId, Integer startYear) {
        this.nameOfSchool = nameOfSchool;
        this.course = course;
        this.educationLevel = educationLevel;
        this.gradYear = gradYear;
        this.jobSeekerId = jobSeekerId;
        this.schooId = schooId;
        this.startYear = startYear;
    }


    public String getNameOfSchool() {
        return nameOfSchool;
    }

    public void setNameOfSchool(String nameOfSchool) {
        this.nameOfSchool = nameOfSchool;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Integer getGradYear() {
        return gradYear;
    }

    public void setGradYear(Integer gradYear) {
        this.gradYear = gradYear;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }


    public Integer getSchooId() {
        return schooId;
    }

    public void setSchooId(Integer schooId) {
        this.schooId = schooId;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }


}
