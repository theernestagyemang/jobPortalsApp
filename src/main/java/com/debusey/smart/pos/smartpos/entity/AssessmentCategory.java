package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "assessment_category")
public class AssessmentCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "entry_date")
    private Date entryDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "max_question")
    private Integer maxQuestion; //maximum question to be set by an employer
    @Column(name = "max_test")
    private Integer maxTest; // maximum test to be taken by a jobseeker
    @JoinColumn(name = "assessment_line", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;

    public AssessmentCategory(Integer id, String name, Date entryDate, String createdBy, Integer maxQuestion, Integer maxTest, AssessmentLines assessmentLines) {
        this.id = id;
        this.name = name;
        this.entryDate = entryDate;
        this.createdBy = createdBy;
        this.maxQuestion = maxQuestion;
        this.maxTest = maxTest;
        this.assessmentLines = assessmentLines;
    }

    public AssessmentCategory() {
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

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getMaxQuestion() {
        return maxQuestion;
    }

    public void setMaxQuestion(Integer maxQuestion) {
        this.maxQuestion = maxQuestion;
    }

    public Integer getMaxTest() {
        return maxTest;
    }

    public void setMaxTest(Integer maxTest) {
        this.maxTest = maxTest;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
    }
}
