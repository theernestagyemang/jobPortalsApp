package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "multiple_choices")
public class MultipleChoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String choice;
    @Column(name = "is_right_answer")
    private boolean isRightAnswer;
    private String assessmentLine;
    @JoinColumn(name = "assessment_question", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentQuestion assessmentQuestion;

    public MultipleChoice(Integer id, String choice, boolean isRightAnswer,
                          String assessmentLine, AssessmentQuestion assessmentQuestion) {
        this.id = id;
        this.choice = choice;
        this.assessmentLine = assessmentLine;
        this.isRightAnswer = isRightAnswer;
        this.assessmentQuestion = assessmentQuestion;
    }

    public MultipleChoice() {
    }

    public MultipleChoice(String choice) {
        this.choice = choice;
    }

    public String getAssessmentLine() {
        return assessmentLine;
    }

    public void setAssessmentLine(String assessmentLine) {
        this.assessmentLine = assessmentLine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public AssessmentQuestion getAssessmentQuestion() {
        return assessmentQuestion;
    }

    public void setAssessmentQuestion(AssessmentQuestion assessmentQuestion) {
        this.assessmentQuestion = assessmentQuestion;
    }
}
