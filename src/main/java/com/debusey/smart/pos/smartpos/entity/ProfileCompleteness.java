/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
public class ProfileCompleteness implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobSeekerId;

    public ProfileCompleteness() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobSeeker getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(JobSeeker jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ProfileCompleteness other = (ProfileCompleteness) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ProfileCompleteness{" + "id=" + id + '}';
    }


}
