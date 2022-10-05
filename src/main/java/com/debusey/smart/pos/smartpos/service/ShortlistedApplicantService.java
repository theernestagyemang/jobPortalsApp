/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.entity.ShortlistedApplicants;
import com.debusey.smart.pos.smartpos.repo.ShortlistedApplicantsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ShortlistedApplicantService {

    @Autowired
    public ShortlistedApplicantsRepo repository;

    public Page<ShortlistedApplicants> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<ShortlistedApplicants> findAll() {
        List<ShortlistedApplicants> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public List<ShortlistedApplicants> findByEmployer(Employer employer) {
        try {
            return repository.findByEmployer(employer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ShortlistedApplicants findBySeekerAndJobAndEmployer(JobSeeker jobSeeker, Jobs jobs, Employer employer){
        try {
            return repository.findByJobSeekerIdAndJobIdAndEmployer(jobSeeker,jobs,employer);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean save(ShortlistedApplicants t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<ShortlistedApplicants> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(ShortlistedApplicants t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<ShortlistedApplicants> findById(Integer id) {
        return repository.findById(id);
    }

    public List<ShortlistedApplicants> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }

    public Optional<ShortlistedApplicants> findByJobSeekerAndJobId(JobSeeker seeker, Jobs jobs) {
        return repository.findByJobSeekerIdAndJobId(seeker, jobs);
    }

    public List<ShortlistedApplicants> findByJobId(Jobs jobId) {
        return repository.findByJobId(jobId);
    }


}
 