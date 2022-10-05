package com.debusey.smart.pos.smartpos.db;

public class CourseVideoResponse {
    private Integer id;
    private String name;
    private String url;
    private Integer displayOrder;
    private Integer courseId;

    public CourseVideoResponse(Integer id, String name, String url, Integer displayOrder, Integer courseId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.displayOrder = displayOrder;
        this.courseId = courseId;
    }

    public CourseVideoResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
