/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.List;

/**
 * @author AlhassanHussein
 */
public class CategoryCourse {
    private Integer id;
    private String category;
    private String description;
    private String fileName;
    private List<CourseReponse> listOfCourses;

    public CategoryCourse() {
    }

    public CategoryCourse(Integer id, String category, List<CourseReponse> listOfCourses, String des, String fileName) {
        this.id = id;
        this.category = category;
        this.listOfCourses = listOfCourses;
        this.description = des;
        this.fileName = fileName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CourseReponse> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(List<CourseReponse> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
