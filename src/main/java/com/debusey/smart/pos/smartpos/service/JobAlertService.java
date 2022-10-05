/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobAlert;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.JobAlertRepo;
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
public class JobAlertService {

    @Autowired
    public JobAlertRepo repository;


    public List<JobAlert> findAll() {
        List<JobAlert> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(JobAlert t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Page<JobAlert> findAll(Integer page) {
        return repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "entryDate")));
    }


    public boolean saveAll(List<JobAlert> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(JobAlert t) {
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

    public boolean deleteAll(List<JobAlert> list) {
        try {
            repository.deleteAll(list);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<JobAlert> findById(Integer id) {
        return repository.findById(id);
    }

    public List<JobAlert> findByEmail(String id) {
        return repository.findByEmail(id);
    }


    public List<JobAlert> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }


    public List<String> findByDistictEmails() {
        return repository.findByDistictEmails();
    }

    public List<String> findByDistictEmailsAndCategory(String category) {
        return repository.findByDistictEmailsAndCategory(category);
    }


    public List<JobAlert> findByJobCategory(String category) {
        return repository.findByJobCategory(category);
    }


}
 