/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ProfileViews;
import com.debusey.smart.pos.smartpos.repo.ProfileViewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ProfileViewService {

    @Autowired
    public ProfileViewRepo repository;


    public List<ProfileViews> findAll() {
        List<ProfileViews> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(ProfileViews t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<ProfileViews> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(ProfileViews t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<ProfileViews> findById(Integer id) {
        return repository.findById(id);
    }


    public Optional<ProfileViews> findByJobseekerAndJobs(JobSeeker jobseeker, Employer employer) {
        return repository.findByJobseekerAndEmployer(jobseeker, employer);
    }

    public List<ProfileViews> findByJobseeker(JobSeeker seeker) {
        return repository.findByJobseeker(seeker);
    }

}
 