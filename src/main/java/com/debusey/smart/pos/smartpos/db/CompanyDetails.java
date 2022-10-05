/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class CompanyDetails {
    private String logo;
    private String name;
    private String message;
    private String industry;
    private Integer jobs;
    private String location;
    private Integer id;

    public CompanyDetails() {
    }

    public CompanyDetails(String logo, String name, String message, String industry, Integer jobs, String location, Integer id) {
        this.logo = logo;
        this.name = name;
        this.message = message;
        this.industry = industry;
        this.jobs = jobs;
        this.location = location;
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getJobs() {
        return jobs;
    }

    public void setJobs(Integer jobs) {
        this.jobs = jobs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
