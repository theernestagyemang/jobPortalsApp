/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author Admin
 */


public class AwardsDb implements Serializable {

    private Integer id;
    private String title;
    private Integer fromYear;
    private Integer toYear;
    private Integer jobseeker;

    public AwardsDb() {
    }

    public AwardsDb(Integer id, String title, Integer fromYear, Integer toYear, Integer jobseeker) {
        this.id = id;
        this.title = title;
        this.fromYear = fromYear;
        this.toYear = toYear;
        this.jobseeker = jobseeker;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public Integer getJobseeker() {
        return jobseeker;
    }

    public void setJobseeker(Integer jobseeker) {
        this.jobseeker = jobseeker;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final AwardsDb other = (AwardsDb) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return title;
    }


}
