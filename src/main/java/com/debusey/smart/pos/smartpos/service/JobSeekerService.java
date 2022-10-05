/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.JobSeekerRepository;
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
public class JobSeekerService {

    @Autowired
    public JobSeekerRepository repository;

    public Page<JobSeeker> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "fullName")));
    }

    public List<JobSeeker> findAll() {
        List<JobSeeker> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public Page<JobSeeker> findAll(Integer page) {
        return repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "fullName")));
    }

    public boolean save(JobSeeker t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<JobSeeker> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void delete(JobSeeker t) {
        repository.delete(t);
    }

    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<JobSeeker> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<JobSeeker> findByTransactionId(String id) {
        return repository.findByTransactionId(id);
    }

    public Optional<JobSeeker> findByEmail(String id) {
        return repository.findByEmail(id);
    }

    public List<JobSeeker> findDuplicates(String id) {
        return repository.findDuplicates(id);
    }

    public Optional<JobSeeker> findByUniqueId(String token) {
        return repository.findByUniqueId(token);
    }

    public Integer getJobSeekersCount() {
        return repository.getJobSeekersCount();
    }

    public Optional<List<Object[]>> findBySeekerSpecialization() {
        return repository.findBySeekerSpecialization();
    }

    public List<JobSeeker> findJobSeekerBySpecialization(List<String> q) {
        return repository.findByProffesionalTitileIn(q);
    }

    public List<JobSeeker> findNullTransactions() {
        return repository.findNullTransactions();
    }


}

