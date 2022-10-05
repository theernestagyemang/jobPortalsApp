package com.debusey.smart.pos.smartpos.db;

public class CourseQuizResponse {
    private Integer id;
    private String question;
    private String answer;
    private Integer courseId;

    public CourseQuizResponse(Integer id, String question, String answer, Integer courseId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.courseId = courseId;
    }

    public CourseQuizResponse() {
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
