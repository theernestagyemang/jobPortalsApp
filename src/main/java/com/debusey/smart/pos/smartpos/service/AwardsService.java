/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Awards;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.AwardsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class AwardsService {

    @Autowired
    public AwardsRepo repository;


    public List<Awards> findAll() {
        List<Awards> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(Awards t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<Awards> t) {
        repository.saveAll(t);
    }

    public void delete(Awards t) {
        repository.delete(t);
    }

    public Optional<Awards> findById(Integer id) {
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


    public Optional<Awards> findOne(Integer id) {
        return repository.findById(id);
    }

    public List<Awards> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobseeker(seeker);
    }

}

