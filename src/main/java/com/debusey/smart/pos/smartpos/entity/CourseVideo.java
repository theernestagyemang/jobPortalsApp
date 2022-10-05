package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "course_video")
public class CourseVideo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
    @Column(name = "display_order")
    private Integer displayOrder;
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Course course;

    public CourseVideo() {
    }

    public CourseVideo(Integer id, String name, String url, Integer displayOrder, Course course) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.displayOrder = displayOrder;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
