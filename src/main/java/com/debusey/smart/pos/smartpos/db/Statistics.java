/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;


/**
 * @author AlhassanHussein
 */
public class Statistics {
    private Integer posted;
    private Integer companies;
    private Integer filled;
    private Integer members;

    public Statistics() {
    }

    public Statistics(Integer posted, Integer companies, Integer filled, Integer members) {
        this.posted = posted;
        this.companies = companies;
        this.filled = filled;
        this.members = members;
    }

    public Integer getPosted() {
        return posted;
    }

    public void setPosted(Integer posted) {
        this.posted = posted;
    }

    public Integer getCompanies() {
        return companies;
    }

    public void setCompanies(Integer companies) {
        this.companies = companies;
    }

    public Integer getFilled() {
        return filled;
    }

    public void setFilled(Integer filled) {
        this.filled = filled;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }


}
