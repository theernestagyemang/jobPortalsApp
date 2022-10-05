/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
public class WorkSample implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private String worktitle;
    private String strUrl;
    private String frmMonth;
    private Integer frmYear;
    private String toMonth;
    private Integer toYear;
    private String currentlyOnProject;
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobSeekerId;

    public WorkSample() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorktitle() {
        return worktitle;
    }

    public void setWorktitle(String worktitle) {
        this.worktitle = worktitle;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public String getFrmMonth() {
        return frmMonth;
    }

    public void setFrmMonth(String frmMonth) {
        this.frmMonth = frmMonth;
    }

    public Integer getFrmYear() {
        return frmYear;
    }

    public void setFrmYear(Integer frmYear) {
        this.frmYear = frmYear;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public String getCurrentlyOnProject() {
        return currentlyOnProject;
    }

    public void setCurrentlyOnProject(String currentlyOnProject) {
        this.currentlyOnProject = currentlyOnProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
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
        final WorkSample other = (WorkSample) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return worktitle;
    }


}
