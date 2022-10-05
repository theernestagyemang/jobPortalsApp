/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.Jobs;

import java.util.List;

/**
 * @author Admin
 */
public class CategoryLocation {
    private Integer id;
    private String category;
    private List<Jobs> listOfJobs;

    public CategoryLocation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Jobs> getListOfJobs() {
        return listOfJobs;
    }

    public void setListOfJobs(List<Jobs> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }


}
