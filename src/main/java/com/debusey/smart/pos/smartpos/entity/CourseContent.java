package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "course_content")
public class CourseContent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @Lob
    private String content;

    @Lob
    private String introduction;

    @Column(name = "attached_file_type")
    private String attachedFileType;
    @Column(name = "attached_file_name")
    private String attachedFileName;

    @Column(name = "display_order")
    private String displayOrder;
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Course course;

    public CourseContent() {
    }

    public CourseContent(Integer id, String title, String content, String attachedFileName,
                         String introduction, String attachedFileType, String displayOrder, Course course) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.displayOrder = displayOrder;
        this.course = course;
        this.attachedFileName = attachedFileName;
        this.attachedFileType = attachedFileType;
        this.introduction = introduction;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAttachedFileType() {
        return attachedFileType;
    }

    public void setAttachedFileType(String attachedFileType) {
        this.attachedFileType = attachedFileType;
    }

    public String getAttachedFileName() {
        return attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
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
}
