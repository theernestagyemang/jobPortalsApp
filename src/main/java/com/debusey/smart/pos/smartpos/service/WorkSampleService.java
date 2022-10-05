/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.WorkSample;
import com.debusey.smart.pos.smartpos.repo.WorkSampleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class WorkSampleService {

    @Autowired
    public WorkSampleRepo repository;


    public List<WorkSample> findAll() {
        List<WorkSample> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(WorkSample t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<WorkSample> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(WorkSample t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<WorkSample> findById(Integer id) {
        return repository.findById(id);
    }

    public List<WorkSample> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }


}
 