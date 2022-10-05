/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author Admin
 */
public class Skills {
    public Integer id;
    private String skill;
    private String version;
    private Integer lastUsed;
    private String monthExperience;
    private Integer yearExperience;
    private Integer jobSeekerId;
    private Integer proficiency;

    public Skills() {
    }

    public Skills(Integer id, String skill, String version, Integer lastUsed, String monthExperience, Integer yearExperience, Integer jobSeekerId, Integer proficiency) {
        this.id = id;
        this.skill = skill;
        this.version = version;
        this.lastUsed = lastUsed;
        this.monthExperience = monthExperience;
        this.yearExperience = yearExperience;
        this.jobSeekerId = jobSeekerId;
        this.proficiency = proficiency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Integer lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getMonthExperience() {
        return monthExperience;
    }

    public void setMonthExperience(String monthExperience) {
        this.monthExperience = monthExperience;
    }

    public Integer getYearExperience() {
        return yearExperience;
    }

    public void setYearExperience(Integer yearExperience) {
        this.yearExperience = yearExperience;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public Integer getProficiency() {
        return proficiency;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiency = proficiency;
    }


}
