/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class SeekerJob {

    private Integer id;
    private String profession;
    private String location;
    private String fileName;
    private String postedBy;
    private String natureOfContract;
    private String duration;
    private String transactionId;


    public SeekerJob() {
    }

    public SeekerJob(Integer id, String profession, String location, String fileName, String postedBy, String natureOfContract, String duration, String transactionId) {
        this.id = id;
        this.profession = profession;
        this.location = location;
        this.fileName = fileName;
        this.postedBy = postedBy;
        this.natureOfContract = natureOfContract;
        this.duration = duration;
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getNatureOfContract() {
        return natureOfContract;
    }

    public void setNatureOfContract(String natureOfContract) {
        this.natureOfContract = natureOfContract;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
