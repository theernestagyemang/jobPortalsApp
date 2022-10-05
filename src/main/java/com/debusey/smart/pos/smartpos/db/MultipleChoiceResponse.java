package com.debusey.smart.pos.smartpos.db;

public class MultipleChoiceResponse {
    private Integer id;
    private String choice;
    private String assessmentQuestionId;
    private String assessmentLine;

    public MultipleChoiceResponse(Integer id, String choice, String assessmentLine, String assessmentQuestionId) {
        this.id = id;
        this.choice = choice;
        this.assessmentQuestionId = assessmentQuestionId;
        this.assessmentLine = assessmentLine;
    }

    public MultipleChoiceResponse() {
    }

    public MultipleChoiceResponse(String choice) {
        this.choice = choice;
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

    public String getAssessmentQuestionId() {
        return assessmentQuestionId;
    }

    public void setAssessmentQuestionId(String assessmentQuestionId) {
        this.assessmentQuestionId = assessmentQuestionId;
    }
}
