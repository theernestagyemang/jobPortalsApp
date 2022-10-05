/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobRequirement;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.repo.JobRequirementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class JobRequirementService {

    @Autowired
    public JobRequirementRepo repository;


    public List<JobRequirement> findAll() {
        List<JobRequirement> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public void save(JobRequirement t) {
        repository.save(t);
    }

    public void saveAll(List<JobRequirement> t) {
        repository.saveAll(t);
    }

    public void delete(JobRequirement t) {
        repository.delete(t);
    }

    public Optional<JobRequirement> findById(Integer id) {
        return repository.findById(id);
    }


    public List<JobRequirement> findByJobId(Jobs id) {
        return repository.findByJobId(id);
    }

    public boolean deleteAll(List<JobRequirement> t) {
        try {
            t.stream().forEach((item) -> {
                System.out.println("item " + item.getId());
                repository.deleteById(item.getId());
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
