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
public class ShortlistedCandidate {
    private Integer id;
    private Integer jobId;
    private String jobtitle;
    private List<ShortlistCart> cart;

    public ShortlistedCandidate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public List<ShortlistCart> getCart() {
        return cart;
    }

    public void setCart(List<ShortlistCart> cart) {
        this.cart = cart;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }


}
