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
import com.debusey.smart.pos.smartpos.repo.EmployerSubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class EmployerSubscriptionService {

    @Autowired
    public EmployerSubscriptionRepo repository;


    public List<EmployerSubscription> findAll() {
        List<EmployerSubscription> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(EmployerSubscription t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<EmployerSubscription> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(EmployerSubscription t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<EmployerSubscription> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<EmployerSubscription> findByCategory(String category) {
        return repository.findByCategory(category);
    }


}
 