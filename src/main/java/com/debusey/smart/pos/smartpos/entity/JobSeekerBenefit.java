package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "job_seeker_benefits")
public class JobSeekerBenefit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String benefit;

    @JoinColumn(name = "job_seeker_assessment", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private JobSeekerAssessment jobSeekerAssessment;

    public JobSeekerBenefit(Integer id, String benefit, JobSeekerAssessment jobSeekerAssessment) {
        this.id = id;
        this.benefit = benefit;
        this.jobSeekerAssessment = jobSeekerAssessment;
    }

    public JobSeekerBenefit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public JobSeekerAssessment getJobSeekerAssessment() {
        return jobSeekerAssessment;
    }

    public void setJobSeekerAssessment(JobSeekerAssessment jobSeekerAssessment) {
        this.jobSeekerAssessment = jobSeekerAssessment;
    }
}
