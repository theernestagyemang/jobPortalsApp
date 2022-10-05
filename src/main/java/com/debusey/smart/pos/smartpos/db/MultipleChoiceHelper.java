package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.AssessmentRound;

import java.util.List;

public class MultipleChoiceHelper {

    private Integer id;
    private String answer;
    private String question;
    private Integer displayOrder;
    private Integer assessmentLineId;

    private AssessmentRound assessmentRound;
    private List<String> options;

    public MultipleChoiceHelper(Integer id, String answer, String question, Integer assessmentLineId,
                                AssessmentRound assessmentRound,Integer displayOrder, List<String> options) {
        this.id = id;
        this.answer = answer;
        this.question = question;
        this.displayOrder = displayOrder;
        this.options = options;
        this.assessmentLineId = assessmentLineId;
        this.assessmentRound = assessmentRound;
    }

    public MultipleChoiceHelper() {
    }

    public AssessmentRound getAssessmentRound() {
        return assessmentRound;
    }

    public void setAssessmentRound(AssessmentRound assessmentRound) {
        this.assessmentRound = assessmentRound;
    }

    public Integer getAssessmentLineId() {
        return assessmentLineId;
    }

    public void setAssessmentLineId(Integer assessmentLineId) {
        this.assessmentLineId = assessmentLineId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
