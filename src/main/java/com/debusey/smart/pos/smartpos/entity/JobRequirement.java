/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
public class JobRequirement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private String requirement;
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Jobs jobId;

    public JobRequirement() {
    }

    public JobRequirement(String requirement, Jobs jobId) {
        this.requirement = requirement;
        this.jobId = jobId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Jobs getJobId() {
        return jobId;
    }

    public void setJobId(Jobs jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobRequirement other = (JobRequirement) obj;
        if (!Objects.equals(this.requirement, other.requirement)) {
            return false;
        }
        return Objects.equals(this.jobId, other.jobId);
    }

    @Override
    public String toString() {
        return requirement;
    }


}
