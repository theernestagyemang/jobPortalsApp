/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class CourseReponse {
    private Integer id;
    private String enteredBy;
    private String courseTitle;
    private String status;
    private String category;
    private String description;
    private boolean featured;
    private Double price;
    private Integer courseCategory;

    public CourseReponse() {
    }

    public CourseReponse(Integer id, String enteredBy, String courseTitle, String status, String category,
                         Integer courseCategory, String description, boolean featured, Double price) {
        this.id = id;
        this.enteredBy = enteredBy;
        this.courseTitle = courseTitle;
        this.status = status;
        this.category = category;
        this.courseCategory = courseCategory;
        this.description = description;
        this.featured = featured;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(Integer courseCategory) {
        this.courseCategory = courseCategory;
    }


}
