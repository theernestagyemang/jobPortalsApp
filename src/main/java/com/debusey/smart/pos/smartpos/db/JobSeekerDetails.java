/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;

/**
 * @author AlhassanHussein
 */
public class JobSeekerDetails {
    private String traxId;
    private String fullName;
    private String email;
    private String telephone;
    private String location;
    private String fileName;

    public JobSeekerDetails() {
    }

    public JobSeekerDetails(String traxId, String fullName, String email, String telephone) {
        this.traxId = traxId;
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
    }


    public JobSeekerDetails(JobSeeker seeker) {
        this.traxId = seeker.getTransactionId();
        this.fullName = seeker.getFullName();
        this.email = seeker.getEmail();
        this.telephone = seeker.getTelephone();
        this.location = seeker.getCountryOfOrigin();
        this.fileName = JsfUtil.findfile(seeker.getPicFileName());
    }


    public String getTraxId() {
        return traxId;
    }

    public void setTraxId(String traxId) {
        this.traxId = traxId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
