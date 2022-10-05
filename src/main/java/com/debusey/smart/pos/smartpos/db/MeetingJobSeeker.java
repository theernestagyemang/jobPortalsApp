/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class MeetingJobSeeker {
    private String tranx;
    private String name;

    public MeetingJobSeeker() {
    }

    public MeetingJobSeeker(String tranx, String name) {
        this.tranx = tranx;
        this.name = name;
    }

    public String getTranx() {
        return tranx;
    }

    public void setTranx(String tranx) {
        this.tranx = tranx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
