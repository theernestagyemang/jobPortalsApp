package com.debusey.smart.pos.smartpos.db;

public class AssessmentQuestionResponse {
    private Integer id;
    private String question;
    private String answer;
    private Integer displayOrder;

    private String assessmentRoundId;
    private String assessmentLineId;

    public AssessmentQuestionResponse(Integer id, String question, String answer, String assessmentRoundId,
                                      String assessmentLineId, Integer displayOrder) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.displayOrder = displayOrder;
        this.assessmentLineId = assessmentLineId;
        this.assessmentRoundId = assessmentRoundId;
    }

    public AssessmentQuestionResponse() {
    }

    public String getAssessmentRoundId() {
        return assessmentRoundId;
    }

    public void setAssessmentRoundId(String assessmentRoundId) {
        this.assessmentRoundId = assessmentRoundId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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

    public String getAssessmentLineId() {
        return assessmentLineId;
    }

    public void setAssessmentLineId(String assessmentLineId) {
        this.assessmentLineId = assessmentLineId;
    }
}
