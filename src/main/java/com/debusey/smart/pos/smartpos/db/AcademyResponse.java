/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.Facilitators;
import com.debusey.smart.pos.smartpos.entity.LearningPlan;

import java.util.List;

/**
 * @author AlhassanHussein
 */
public class AcademyResponse {
    private Integer id;
    private List<Facilitators> facilitators;
    private List<Course> courses;
    private List<TrainingScheduleResponse> training;
    private List<LearningPlan> learningPlan;

    public AcademyResponse() {
    }

    public AcademyResponse(Integer id, List<Facilitators> facilitators, List<Course> courses, List<TrainingScheduleResponse> training, List<LearningPlan> learningPlan) {
        this.id = id;
        this.facilitators = facilitators;
        this.courses = courses;
        this.training = training;
        this.learningPlan = learningPlan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Facilitators> getFacilitators() {
        return facilitators;
    }

    public void setFacilitators(List<Facilitators> facilitators) {
        this.facilitators = facilitators;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<TrainingScheduleResponse> getTraining() {
        return training;
    }

    public void setTraining(List<TrainingScheduleResponse> training) {
        this.training = training;
    }

    public List<LearningPlan> getLearningPlan() {
        return learningPlan;
    }

    public void setLearningPlan(List<LearningPlan> learningPlan) {
        this.learningPlan = learningPlan;
    }


}
