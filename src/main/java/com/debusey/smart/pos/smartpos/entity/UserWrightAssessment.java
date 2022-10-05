package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserWrightAssessment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date testDate;
    private Double score;
    private Integer NumberOfQuestionAnswered;
    private Integer NumberOfCorrectAnswer;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    @JoinColumn(name = "assessment_line", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;

    public UserWrightAssessment(Integer id, Date testDate, Double score, Integer numberOfQuestionAnswered, Integer numberOfCorrectAnswer, Users users, AssessmentLines assessmentLines) {
        this.id = id;
        this.testDate = testDate;
        this.score = score;
        NumberOfQuestionAnswered = numberOfQuestionAnswered;
        NumberOfCorrectAnswer = numberOfCorrectAnswer;
        this.users = users;
        this.assessmentLines = assessmentLines;
    }

    public UserWrightAssessment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getNumberOfQuestionAnswered() {
        return NumberOfQuestionAnswered;
    }

    public void setNumberOfQuestionAnswered(Integer numberOfQuestionAnswered) {
        NumberOfQuestionAnswered = numberOfQuestionAnswered;
    }

    public Integer getNumberOfCorrectAnswer() {
        return NumberOfCorrectAnswer;
    }

    public void setNumberOfCorrectAnswer(Integer numberOfCorrectAnswer) {
        NumberOfCorrectAnswer = numberOfCorrectAnswer;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
    }
}
