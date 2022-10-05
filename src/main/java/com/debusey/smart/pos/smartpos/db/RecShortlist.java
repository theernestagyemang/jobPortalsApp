/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class RecShortlist {
    private String seekerTrx;
    private String jobTrx;
    private String jobAppTrx;
    private String type;

    public RecShortlist() {
    }

    public RecShortlist(String seekerTrx, String jobTrx, String jobAppTrx, String type) {
        this.seekerTrx = seekerTrx;
        this.jobTrx = jobTrx;
        this.jobAppTrx = jobAppTrx;
        this.type = type;
    }

    public String getSeekerTrx() {
        return seekerTrx;
    }

    public void setSeekerTrx(String seekerTrx) {
        this.seekerTrx = seekerTrx;
    }

    public String getJobTrx() {
        return jobTrx;
    }

    public void setJobTrx(String jobTrx) {
        this.jobTrx = jobTrx;
    }

    public String getJobAppTrx() {
        return jobAppTrx;
    }

    public void setJobAppTrx(String jobAppTrx) {
        this.jobAppTrx = jobAppTrx;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
