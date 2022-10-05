/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class JobSeekerSpecialization {
    private String key;
    private Integer count;
    private String specialization;

    public JobSeekerSpecialization() {
    }

    public JobSeekerSpecialization(String key, Integer count, String specialization) {
        this.key = key;
        this.count = count;
        this.specialization = specialization;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


}
