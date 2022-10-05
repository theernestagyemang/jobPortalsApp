/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
public class ProfileStrength implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private Integer profileSummary;
    private Integer resumeHeadline;
    private Integer keySkills;
    private Integer employment;
    private Integer education;
    private Integer itSkills;
    private Integer accomplishment;
    private Integer projects;
    private Integer desiredCareerProfile;
    private Integer personalDetails;
    private Integer attachedResume;
    private Integer coverLetter;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id")
    @ManyToOne
    private JobSeeker jobSeekerId;

    public ProfileStrength() {
    }

    public ProfileStrength(Integer id) {
        this.id = id;
    }

    public ProfileStrength(JobSeeker jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(Integer profileSummary) {
        this.profileSummary = profileSummary;
    }

    public Integer getResumeHeadline() {
        return resumeHeadline;
    }

    public void setResumeHeadline(Integer resumeHeadline) {
        this.resumeHeadline = resumeHeadline;
    }

    public Integer getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(Integer keySkills) {
        this.keySkills = keySkills;
    }

    public Integer getEmployment() {
        return employment;
    }

    public void setEmployment(Integer employment) {
        this.employment = employment;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getItSkills() {
        return itSkills;
    }

    public void setItSkills(Integer itSkills) {
        this.itSkills = itSkills;
    }

    public Integer getAccomplishment() {
        return accomplishment;
    }

    public void setAccomplishment(Integer accomplishment) {
        this.accomplishment = accomplishment;
    }

    public Integer getProjects() {
        return projects;
    }

    public void setProjects(Integer projects) {
        this.projects = projects;
    }

    public Integer getDesiredCareerProfile() {
        return desiredCareerProfile;
    }

    public void setDesiredCareerProfile(Integer desiredCareerProfile) {
        this.desiredCareerProfile = desiredCareerProfile;
    }

    public Integer getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(Integer personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Integer getAttachedResume() {
        return attachedResume;
    }

    public void setAttachedResume(Integer attachedResume) {
        this.attachedResume = attachedResume;
    }

    public Integer getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(Integer coverLetter) {
        this.coverLetter = coverLetter;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
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
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ProfileStrength other = (ProfileStrength) obj;
        return Objects.equals(this.id, other.id);
    }


    @Override
    public String toString() {
        return "ProfileStrength{" + "jobSeekerId=" + jobSeekerId + '}';
    }


}
