/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobApplications;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.repo.JobApplicationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class JobApplicationsService {

    @Autowired
    public JobApplicationsRepo repository;


    public List<JobApplications> findAll() {
        List<JobApplications> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public Page<JobApplications> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<JobApplications> findByApplicationStatus(String appStatus, Integer page, Integer max) {
        return repository.findByApplicationStatus(appStatus, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<JobApplications> findByApplicationStatusAndDates(String appStatus, LocalDate start, LocalDate end, Integer page, Integer max) {
        return repository.findByApplicationStatusAndDates(appStatus, start, end, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<JobApplications> findByJob(Jobs job, Integer page, Integer max) {
        return repository.findByJob(job, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Integer findByJobCount(Jobs job) {
        return repository.findByJobCount(job);
    }

    public Integer findByJobAndApplicationStatusCount(Jobs job, String applicationStatus) {
        return repository.findByJobAndApplicationStatusCount(job, applicationStatus);
    }

    public Page<JobApplications> findByJobseeker(JobSeeker job, Integer page, Integer max) {
        return repository.findByJobseeker(job, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public void saveAll(List<JobApplications> t) {
        repository.saveAll(t);
    }

    public void delete(JobApplications t) {
        repository.delete(t);
    }

    public Optional<JobApplications> findById(Integer id) {
        return repository.findById(id);
    }

    public boolean save(JobApplications employer) {
        try {
            repository.save(employer);
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
            e.printStackTrace();
        }

        return false;

    }


    public Optional<JobApplications> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }

    public Integer findDailyTotalJobApplications() {
        return repository.findDailyTotalJobApplications();
    }

    public Integer findDailyTotalJobApplications(LocalDate start, LocalDate end) {
        return repository.findDailyTotalJobApplications(start, end);
    }

    public List<JobApplications> findDailyJobApplications(LocalDate start, LocalDate end) {
        return repository.findDailyJobApplications(start, end);
    }

    public Integer findDailyTotalJobApplications(String status) {
        return repository.findDailyTotalJobApplications(status);
    }

    public Integer findDailyTotalJobApplications(String status, LocalDate start, LocalDate end) {
        return repository.findDailyTotalJobApplications(status, start, end);
    }


    public List<JobApplications> findByDailyApplied() {
        return repository.findByEntryDate(LocalDate.now());
    }

    public List<Object[]> findMonthlySalesByYear(String appliedStatus) {
        return repository.findMonthlySalesByYear(appliedStatus);
    }

    public List<Object[]> findMonthlySalesByYear(Integer year) {
        return repository.findMonthlySalesByYear(year);
    }


    public List<Object[]> findMonthlyApplication(Integer year) {
        return repository.findMonthlyApplication(year);
    }

    public Page<JobApplications> findByJobAndApplicationStatus(Jobs job, String type, Integer page, Integer max) {
        return repository.findByJobAndApplicationStatus(job, type, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<JobApplications> lastFiveApplications() {
        return repository.lastFiveApplications();
    }

    public List<Object[]> findApplicationsByJobCategory() {
        return repository.findApplicationsByJobCategory();
    }

    public List<Object[]> findApplicationsByJobCategory(LocalDate start, LocalDate end) {
        return repository.findApplicationsByJobCategory(start, end);
    }


}
