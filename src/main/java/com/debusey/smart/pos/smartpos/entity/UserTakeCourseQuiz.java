package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_take_quiz")
public class UserTakeCourseQuiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "test_date")
    private Date testDate;
    private Double score;
    private Double outOf;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Course course;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private Users users;

    public UserTakeCourseQuiz(Integer id, Date testDate, Double score, Double outOf,
                              String transactionId, Course course, Users users) {
        this.id = id;
        this.testDate = testDate;
        this.score = score;
        this.outOf = outOf;
        this.transactionId = transactionId;
        this.course = course;
        this.users = users;
    }

    public UserTakeCourseQuiz() {
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

    public Double getOutOf() {
        return outOf;
    }

    public void setOutOf(Double outOf) {
        this.outOf = outOf;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
