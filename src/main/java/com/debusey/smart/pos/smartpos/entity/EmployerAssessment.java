package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employer_assessments")
public class EmployerAssessment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "max_test_by_employee")
    private Integer maxTestByEmployee; // maximum test done by an employee
    @Column(name = "max_employee")
    private Integer maxEmployee; //maximum of employee to pass the test based on the package

    @JoinColumn(name = "assessment_line", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne()
    private AssessmentLines assessmentLines;

    public EmployerAssessment(Integer id, Integer maxTestByEmployee, Integer maxEmployee, AssessmentLines assessmentLines) {
        this.id = id;
        this.maxTestByEmployee = maxTestByEmployee;
        this.maxEmployee = maxEmployee;
        this.assessmentLines = assessmentLines;
    }

    public EmployerAssessment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxTestByEmployee() {
        return maxTestByEmployee;
    }

    public void setMaxTestByEmployee(Integer maxTestByEmployee) {
        this.maxTestByEmployee = maxTestByEmployee;
    }

    public Integer getMaxEmployee() {
        return maxEmployee;
    }

    public void setMaxEmployee(Integer maxEmployee) {
        this.maxEmployee = maxEmployee;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
    }
}
