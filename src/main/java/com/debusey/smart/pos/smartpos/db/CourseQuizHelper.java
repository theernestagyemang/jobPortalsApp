package com.debusey.smart.pos.smartpos.db;



import java.util.List;

public class CourseQuizHelper {
    private Integer id;
    private String answer;
    private String question;
    private Integer courseId;
    private List<String> options;

    public CourseQuizHelper(Integer id, String answer, String question, Integer courseId, List<String> options) {
        this.id = id;
        this.answer = answer;
        this.question = question;
        this.courseId = courseId;
        this.options = options;
    }

    public CourseQuizHelper() {
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
