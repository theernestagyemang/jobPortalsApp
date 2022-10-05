/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ProfileStrength;
import com.debusey.smart.pos.smartpos.repo.ProfileCompletenessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ProfileCompletenessService {

    @Autowired
    public ProfileCompletenessRepo repository;


    public List<ProfileStrength> findAll() {
        List<ProfileStrength> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }


    public void saveAll(List<ProfileStrength> t) {
        repository.saveAll(t);
    }

    public void delete(ProfileStrength t) {
        repository.delete(t);
    }

    public Optional<ProfileStrength> findById(Integer id) {
        return repository.findById(id);
    }

    public boolean save(ProfileStrength employer) {
        try {
            repository.save(employer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<ProfileStrength> findByJobSeekerId(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }

    public Integer findByCompletePctAndJobSeeker(Integer seeker) {
        return repository.findByCompletePctAndJobSeeker(seeker);
    }


    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}
