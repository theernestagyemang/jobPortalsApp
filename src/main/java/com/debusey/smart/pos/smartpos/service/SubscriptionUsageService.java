/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.SubscriptionUsage;
import com.debusey.smart.pos.smartpos.repo.SubscriptionUsageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class SubscriptionUsageService {

    @Autowired
    public SubscriptionUsageRepo repository;


    public List<SubscriptionUsage> findAll() {
        List<SubscriptionUsage> items = new ArrayList<>();
        repository.findAll().forEach(items::add);

        return items;
    }

    public boolean deleteById(Integer t) {
        try {
            repository.deleteById(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean save(SubscriptionUsage t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void saveAll(List<SubscriptionUsage> t) {
        repository.saveAll(t);
    }

    public void delete(SubscriptionUsage t) {
        repository.delete(t);
    }

    public Optional<SubscriptionUsage> findById(Integer id) {
        return repository.findById(id);
    }


    public List<SubscriptionUsage> findByJobSeekerId(Integer id) {
        return repository.findByJobSeekerId(id);
    }


    public List<SubscriptionUsage> findByEmployerId(Integer id) {
        return repository.findByEmployerId(id);
    }


    public Optional<SubscriptionUsage> findByEmployerAndJobSeeker(Integer seeker, Integer employer) {
        return repository.findByEmployerAndJobSeeker(seeker, employer);
    }


}
