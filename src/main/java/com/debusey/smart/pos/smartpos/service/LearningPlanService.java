/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.LearningPlan;
import com.debusey.smart.pos.smartpos.repo.LearningPlanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class LearningPlanService {

    @Autowired
    public LearningPlanRepo repository;


    public List<LearningPlan> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public boolean save(LearningPlan t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<LearningPlan> t) {
        repository.saveAll(t);
    }

    public void delete(LearningPlan t) {
        repository.delete(t);
    }

    public Optional<LearningPlan> findById(Integer id) {
        return repository.findById(id);
    }


    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Optional<LearningPlan> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<LearningPlan> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }

    public Optional<LearningPlan> findByName(String name) {
        return repository.findByName(name);
    }


}


