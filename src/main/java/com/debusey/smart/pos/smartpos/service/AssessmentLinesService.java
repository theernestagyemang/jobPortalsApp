/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.repo.AssessmentLinesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class AssessmentLinesService {

    @Autowired
    public AssessmentLinesRepo repository;


    public List<AssessmentLines> findAll() {
        List<AssessmentLines> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public List<AssessmentLines> findAllActive(boolean status) {
        List<AssessmentLines> items = new ArrayList<>();
        repository.findByActive(status).forEach(items::add);
        return items;
    }

    public boolean save(AssessmentLines t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<AssessmentLines> t) {
        repository.saveAll(t);
    }

    public void delete(AssessmentLines t) {
        repository.delete(t);
    }

    public Optional<AssessmentLines> findById(Integer id) {
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


    public Optional<AssessmentLines> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<AssessmentLines> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }


}

