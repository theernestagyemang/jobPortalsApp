/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Positions;
import com.debusey.smart.pos.smartpos.entity.SeekerJobPrefrence;
import com.debusey.smart.pos.smartpos.repo.JobPreferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class JobPreferenceService {

    @Autowired
    public JobPreferenceRepo repository;


    public List<SeekerJobPrefrence> findAll() {
        List<SeekerJobPrefrence> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(SeekerJobPrefrence t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean saveAll(List<SeekerJobPrefrence> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(SeekerJobPrefrence t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteAll(List<SeekerJobPrefrence> list) {
        try {
            repository.deleteAll(list);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<SeekerJobPrefrence> findById(Integer id) {
        return repository.findById(id);
    }

    public List<SeekerJobPrefrence> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobseeker(seeker);
    }

    public List<JobSeeker> findByJobSeekerPrefrence(Positions jobs) {
        return repository.findByJobSeekerPrefrence(jobs);
    }


}
 