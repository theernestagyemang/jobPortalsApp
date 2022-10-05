package com.debusey.smart.pos.smartpos.db;

public class QuizOptionResponse {
    private Integer id;
    private String option;
    private Integer quizQuestionId;

    public QuizOptionResponse(Integer id, String option, Integer quizQuestionId) {
        this.id = id;
        this.option = option;
        this.quizQuestionId = quizQuestionId;
    }

    public QuizOptionResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getQuizQuestionId() {
        return quizQuestionId;
    }

    public void setQuizQuestionId(Integer quizQuestionId) {
        this.quizQuestionId = quizQuestionId;
    }
}
