/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Subscription;
import com.debusey.smart.pos.smartpos.repo.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class SubscriptionService {

    @Autowired
    public SubscriptionRepo repository;


    public List<Subscription> findAll() {
        List<Subscription> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(Subscription t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<Subscription> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(Subscription t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<Subscription> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<Subscription> findByTransactionId(String transactionId) {
        return repository.findByTransactionId(transactionId);
    }


    public Optional<Subscription> findByName(String name) {
        return repository.findByName(name);
    }

}
 