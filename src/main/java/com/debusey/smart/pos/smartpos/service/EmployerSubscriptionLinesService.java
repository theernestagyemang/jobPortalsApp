/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


/**
 * @author Admin
 */


import com.debusey.smart.pos.smartpos.entity.EmployerSubscription;
import com.debusey.smart.pos.smartpos.entity.EmployerSubscriptionLines;
import com.debusey.smart.pos.smartpos.repo.EmployerSubscriptionLinesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class EmployerSubscriptionLinesService {

    @Autowired
    public EmployerSubscriptionLinesRepo repository;


    public List<EmployerSubscriptionLines> findAll() {
        List<EmployerSubscriptionLines> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(EmployerSubscriptionLines t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<EmployerSubscriptionLines> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(EmployerSubscriptionLines t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<EmployerSubscriptionLines> findById(Integer id) {
        return repository.findById(id);
    }

    public List<EmployerSubscriptionLines> findBySubscription(EmployerSubscription seeker) {
        return repository.findBySubscription(seeker);
    }


}
 