/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.JobViews;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.repo.JobViewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class JobViewService {

    @Autowired
    public JobViewsRepo repository;


    public List<JobViews> findAll() {
        List<JobViews> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(JobViews t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<JobViews> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(JobViews t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<JobViews> findById(Integer id) {
        return repository.findById(id);
    }


    public Optional<JobViews> findByJobseekerAndJobs(JobSeeker jobseeker, Jobs jobs) {
        return repository.findByJobseekerAndJobs(jobseeker, jobs);
    }

    public List<JobViews> findByJobseeker(JobSeeker jobseeker) {
        return repository.findByJobseeker(jobseeker);
    }

    public List<JobViews> findByJobs(Jobs jobs) {
        return repository.findByJobs(jobs);
    }

    public Integer findByJobCount(Jobs jobs) {
        return repository.findByJobCount(jobs);
    }

}
 