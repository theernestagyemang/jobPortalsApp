/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.EmployerShortlistedApplicants;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.EmployerShortlistedApplicantsRepo;
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
public class EmployerShortlistedApplicantsService {

    @Autowired
    public EmployerShortlistedApplicantsRepo repository;

    public Page<EmployerShortlistedApplicants> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<EmployerShortlistedApplicants> findAll() {
        List<EmployerShortlistedApplicants> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(EmployerShortlistedApplicants t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<EmployerShortlistedApplicants> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(EmployerShortlistedApplicants t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<EmployerShortlistedApplicants> findById(Integer id) {
        return repository.findById(id);
    }

    public List<EmployerShortlistedApplicants> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }

    public Optional<EmployerShortlistedApplicants> findByJobSeekerAndJobId(JobSeeker seeker, String jobTitle) {
        return repository.findByJobSeekerIdAndJobTitle(seeker, jobTitle);
    }

    public List<EmployerShortlistedApplicants> findByJobTitle(String jobTitle) {
        return repository.findByJobTitle(jobTitle);
    }

    public List<EmployerShortlistedApplicants> findByEmployer(Employer employer) {
        return repository.findByShortListedBy(employer);
    }


}
 