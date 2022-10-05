package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "quiz_options")
public class QuizOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "quiz_option")
    private String quizOption;

    private String course;
    @JoinColumn(name = "course_quiz_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private CourseQuiz courseQuiz;

    public QuizOption() {
    }

    public QuizOption(String quizOption, String course) {
        this.course = course;
        this.quizOption = quizOption;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public QuizOption(Integer id, String option, CourseQuiz courseQuiz) {
        this.id = id;
        this.quizOption = option;
        this.courseQuiz = courseQuiz;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuizOption() {
        return quizOption;
    }

    public void setQuizOption(String option) {
        this.quizOption = option;
    }

    public CourseQuiz getCourseQuiz() {
        return courseQuiz;
    }

    public void setCourseQuiz(CourseQuiz courseQuiz) {
        this.courseQuiz = courseQuiz;
    }
}
