package com.debusey.smart.pos.smartpos.db;

public class CourseContentResponse {
    private Integer id;
    private String title;
    private String content;
    private String introduction;
    private String displayOrder;
    private Integer courseId;

    public CourseContentResponse(Integer id, String title, String content, String introduction,
                                 String displayOrder, Integer courseId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.displayOrder = displayOrder;
        this.courseId = courseId;
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
