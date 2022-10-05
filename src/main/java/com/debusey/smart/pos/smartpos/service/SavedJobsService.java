/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.entity.SavedJobs;
import com.debusey.smart.pos.smartpos.repo.SavedJobsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class SavedJobsService {

    @Autowired
    public SavedJobsRepo repository;


    public List<SavedJobs> findAll() {
        List<SavedJobs> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(SavedJobs t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<SavedJobs> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(SavedJobs t) {
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

    public Optional<SavedJobs> findById(Integer id) {
        return repository.findById(id);
    }

    public List<SavedJobs> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }


    public List<SavedJobs> findByCategory(String category) {
        return repository.findByCategory(category);
    }


    public Optional<SavedJobs> findByJobSeekerAndJob(Jobs jobsId, JobSeeker seeker) {
        return repository.findByJobSeekerIdJobsId(jobsId, seeker);
    }
}
 