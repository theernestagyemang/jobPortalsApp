package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "assessment_questions", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
public class AssessmentQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private String answer;
    private Double score;
    private Date startDate;
    private Date endDate;
    private Integer displayOrder;
    private String enteredBy;
    @Column(name = "transaction_id")
    private String transactionId;

    @JoinColumn(name = "assessment_round", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentRound assessmentRound;
    @JoinColumn(name = "assessment_lines", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    public AssessmentQuestion(Integer id, String question, String answer, Double score, Date startDate,
                              Date endDate, String enteredBy, String transactionId, AssessmentLines assessmentLines,
                              AssessmentRound assessmentRound, Users users, Integer displayOrder) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.transactionId = transactionId;
        this.score = score;
        this.enteredBy = enteredBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.displayOrder = displayOrder;
        this.assessmentLines = assessmentLines;
        this.users = users;
        this.assessmentRound = assessmentRound;
    }

    public AssessmentQuestion() {
    }

    public AssessmentRound getAssessmentRound() {
        return assessmentRound;
    }

    public void setAssessmentRound(AssessmentRound assessmentRound) {
        this.assessmentRound = assessmentRound;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
