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
 * @author AlhassanHussein
 */

@Entity
public class SeekerJobPrefrence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private JobSeeker jobseeker;


    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Positions positions;

    public SeekerJobPrefrence() {
    }

    public SeekerJobPrefrence(JobSeeker jobseeker, Positions positions) {
        this.jobseeker = jobseeker;
        this.positions = positions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobSeeker getJobseeker() {
        return jobseeker;
    }

    public void setJobseeker(JobSeeker jobseeker) {
        this.jobseeker = jobseeker;
    }

//    public Jobs getJobs() {
//        return jobs;
//    }
//
//    public void setJobs(Jobs jobs) {
//        this.jobs = jobs;
//    }

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
        final SeekerJobPrefrence other = (SeekerJobPrefrence) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        if (positions != null) {
            return positions.getName();
        }
        return String.valueOf(id);
    }

    public Positions getPositions() {
        return positions;
    }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }


}
