/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.List;

/**
 * @author AlhassanHussein
 */
public class Profile {
    private Integer id;
    private String transactionId;
    private String profileSummary;
    private PersonalDetails personal;
    private String keySkills;
    private List<Skills> skills;
    private List<WorkEx> workEx;
    private List<Projects> projects;
    private List<JobSeekerSpecialization> specialization;
    private List<Education> education;
    private List<AwardsDb> awards;
    private List<ITSkillsDb> itSkills;

    public Profile() {
    }

    public Profile(Integer id, String transactionId, String profileSummary, PersonalDetails personal, String keySkills, List<Skills> skills, List<WorkEx> workEx, List<Projects> projects, List<JobSeekerSpecialization> specialization, List<Education> education, List<AwardsDb> awards) {
        this.id = id;
        this.transactionId = transactionId;
        this.profileSummary = profileSummary;
        this.personal = personal;
        this.keySkills = keySkills;
        this.skills = skills;
        this.workEx = workEx;
        this.projects = projects;
        this.specialization = specialization;
        this.education = education;
        this.awards = awards;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ITSkillsDb> getItSkills() {
        return itSkills;
    }

    public void setItSkills(List<ITSkillsDb> itSkills) {
        this.itSkills = itSkills;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public PersonalDetails getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalDetails personal) {
        this.personal = personal;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public List<WorkEx> getWorkEx() {
        return workEx;
    }

    public void setWorkEx(List<WorkEx> workEx) {
        this.workEx = workEx;
    }


    public List<Projects> getProjects() {
        return projects;
    }

    public void setProjects(List<Projects> projects) {
        this.projects = projects;
    }

    public List<JobSeekerSpecialization> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<JobSeekerSpecialization> specialization) {
        this.specialization = specialization;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<AwardsDb> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardsDb> awards) {
        this.awards = awards;
    }


}
