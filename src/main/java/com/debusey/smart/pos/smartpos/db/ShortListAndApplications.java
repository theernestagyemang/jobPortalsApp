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
public class ShortListAndApplications {
    private List<Integer> shortlisted;
    private List<Integer> applications;

    public ShortListAndApplications() {
    }

    public ShortListAndApplications(List<Integer> shortlisted, List<Integer> applications) {
        this.shortlisted = shortlisted;
        this.applications = applications;
    }

    public List<Integer> getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(List<Integer> shortlisted) {
        this.shortlisted = shortlisted;
    }

    public List<Integer> getApplications() {
        return applications;
    }

    public void setApplications(List<Integer> applications) {
        this.applications = applications;
    }


}
