/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class Shortlisted {
    private Integer application;
    private Integer maonth;
    private String month;

    public Shortlisted() {
    }

    public Shortlisted(Integer application, Integer maonth) {
        this.application = application;
        this.maonth = maonth;
    }

    public Shortlisted(String month, Integer application) {
        this.application = application;
        this.month = month;
    }


    public Integer getApplication() {
        return application;
    }

    public void setApplication(Integer application) {
        this.application = application;
    }

    public Integer getMaonth() {
        return maonth;
    }

    public void setMaonth(Integer maonth) {
        this.maonth = maonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
