/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.ReportProblem;
import com.debusey.smart.pos.smartpos.repo.ReportProblemRepo;
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
public class ReportProblemService {

    @Autowired
    public ReportProblemRepo repository;


    public Page<ReportProblem> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<ReportProblem> findAll() {
        List<ReportProblem> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(ReportProblem t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<ReportProblem> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(ReportProblem t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteById(Integer t) {
        try {
            repository.deleteById(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Optional<ReportProblem> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<ReportProblem> findByTransactionId(String tx) {
        return repository.findByTransactionId(tx);
    }


    public List<ReportProblem> findByResponded(boolean tx) {
        return repository.findByResponded(tx);
    }


}
 