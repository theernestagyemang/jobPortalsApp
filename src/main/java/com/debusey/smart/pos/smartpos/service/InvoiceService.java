/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Invoice;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class InvoiceService {

    @Autowired
    public InvoiceRepo repository;


    public List<Invoice> findAll() {
        List<Invoice> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }


    public void saveAll(List<Invoice> t) {
        repository.saveAll(t);
    }

    public void delete(Invoice t) {
        repository.delete(t);
    }

    public Optional<Invoice> findById(Integer id) {
        return repository.findById(id);
    }

    public boolean save(Invoice employer) {
        try {
            repository.save(employer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Invoice> findByEmployer(Employer email) {
        return repository.findByEmployerId(email);
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


    public Optional<Invoice> findByEmployerAndSub(Employer employer, String sub) {
        return repository.findByEmployerAndSub(employer, sub);
    }

    public Optional<Invoice> findByJobSeekerAndSub(JobSeeker seeker, String sub) {
        return repository.findByJobSeekerAndSub(seeker, sub);
    }
}
