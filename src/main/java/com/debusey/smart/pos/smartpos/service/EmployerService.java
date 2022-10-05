/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.repo.EmployerRepository;
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
public class EmployerService {

    @Autowired
    public EmployerRepository repository;


    public List<Employer> findAll() {
        List<Employer> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public Page<Employer> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page, max, Sort.by(Sort.Direction.ASC, "companyName")));
    }


    public void saveAll(List<Employer> t) {
        repository.saveAll(t);
    }

    public void delete(Employer t) {
        repository.delete(t);
    }

    public Optional<Employer> findById(Integer id) {
        return repository.findById(id);
    }

    public boolean save(Employer employer) {
        try {
            repository.save(employer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Employer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Employer> findByName(String name) {
        return repository.findByCompanyName(name);
    }

    public List<Employer> findDuplicates(String email) {
        return repository.findDuplicates(email);
    }

    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Page<Employer> findAll(Integer page) {
        return repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "companyName")));
    }

    public List<Employer> findByPopular(boolean b) {
        return repository.findByPopular(b);
        //return list.size();
    }

    public Integer getCompaniesCount() {
        return repository.getCompaniesCount();
    }


}
