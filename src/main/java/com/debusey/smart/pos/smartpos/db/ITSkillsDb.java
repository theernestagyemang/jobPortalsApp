/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class ITSkillsDb {
    public Integer id;
    private String skill;
    private String version;
    private Integer lastUsed;
    private String monthExperience;
    private Integer yearExperience;
    private Integer proficiency;
    private String entryDate;
    private Integer jobSeekerId;

    public ITSkillsDb() {
    }

    public ITSkillsDb(Integer id, String skill, String version, Integer lastUsed, String monthExperience, Integer yearExperience, Integer proficiency, String entryDate, Integer jobSeekerId) {
        this.id = id;
        this.skill = skill;
        this.version = version;
        this.lastUsed = lastUsed;
        this.monthExperience = monthExperience;
        this.yearExperience = yearExperience;
        this.proficiency = proficiency;
        this.entryDate = entryDate;
        this.jobSeekerId = jobSeekerId;
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

    public Integer getProficiency() {
        return proficiency;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiency = proficiency;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }


}
