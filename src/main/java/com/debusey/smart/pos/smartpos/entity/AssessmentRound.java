package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assessment_round")
public class AssessmentRound implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String roundName;

    @Column(unique = true)
    private Integer roundPriority;

    public Integer getRoundPriority() {
        return roundPriority;
    }

    public void setRoundPriority(Integer roundPriority) {
        this.roundPriority = roundPriority;
    }

    public AssessmentRound() {
    }

    public AssessmentRound(Integer id, String roundName, Integer roundPriority) {
        this.id = id;
        this.roundName = roundName;
        this.roundPriority = roundPriority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }
}
