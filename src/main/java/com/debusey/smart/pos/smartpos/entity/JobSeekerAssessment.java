package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "job_seeker_assessments")
public class JobSeekerAssessment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "max_test")
    private Integer maxTest;

    @JoinColumn(name = "assessment_line", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;

    public JobSeekerAssessment(Integer id, Integer maxTest, AssessmentLines assessmentLines) {
        this.id = id;
        this.maxTest = maxTest;
        this.assessmentLines = assessmentLines;
    }

    public JobSeekerAssessment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
