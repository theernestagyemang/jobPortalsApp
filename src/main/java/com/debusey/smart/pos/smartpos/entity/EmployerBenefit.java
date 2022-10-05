package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employer_benefits")
public class EmployerBenefit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String benefit;

    @JoinColumn(name = "employer_assessment", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private EmployerAssessment employerAssessment;

    public EmployerBenefit(Integer id, String benefit, EmployerAssessment employerAssessment) {
        this.id = id;
        this.benefit = benefit;
        this.employerAssessment = employerAssessment;
    }

    public EmployerBenefit() {
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

    public EmployerAssessment getEmployerAssessment() {
        return employerAssessment;
    }

    public void setEmployerAssessment(EmployerAssessment employerAssessment) {
        this.employerAssessment = employerAssessment;
    }
}
