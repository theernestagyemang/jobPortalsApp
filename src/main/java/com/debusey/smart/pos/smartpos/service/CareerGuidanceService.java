/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.CareerGuidance;
import com.debusey.smart.pos.smartpos.repo.CareerGuidanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class CareerGuidanceService {

    @Autowired
    public CareerGuidanceRepo repository;


    public List<CareerGuidance> findAll() {
        List<CareerGuidance> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(CareerGuidance t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<CareerGuidance> t) {
        repository.saveAll(t);
    }

    public boolean delete(CareerGuidance t) {
        try {
            repository.delete(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<CareerGuidance> findById(Integer id) {
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


    public Optional<CareerGuidance> findOne(Integer id) {
        return repository.findById(id);
    }

    public List<CareerGuidance> findThreeEvents() {
        return repository.findThreeEvents();
    }
}

