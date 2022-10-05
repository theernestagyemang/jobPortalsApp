/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.EmployerSubscription;

import java.util.List;

/**
 * @author AlhassanHussein
 */
public class EmployerProfile {
    private String name;
    private EmployerSubscription subscription;
    private List<EmployerShortlisted> candidates;

    public EmployerProfile() {
    }

    public EmployerProfile(String name, EmployerSubscription subscription, List<EmployerShortlisted> candidates) {
        this.name = name;
        this.subscription = subscription;
        this.candidates = candidates;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployerSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(EmployerSubscription subscription) {
        this.subscription = subscription;
    }

    public List<EmployerShortlisted> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<EmployerShortlisted> candidates) {
        this.candidates = candidates;
    }


}
