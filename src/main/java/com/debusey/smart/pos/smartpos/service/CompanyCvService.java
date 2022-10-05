/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.CompanyCv;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.CompanyCvRepo;
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
public class CompanyCvService {

    @Autowired
    public CompanyCvRepo repository;


    public List<CompanyCv> findAll() {
        List<CompanyCv> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(CompanyCv t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<CompanyCv> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(CompanyCv t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<CompanyCv> findById(Integer id) {
        return repository.findById(id);
    }

    public List<CompanyCv> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }

//    public List<CompanyCv> findByEmployerId(Employer employer,Integer page){
//        return repository.findByEmployerId(employer);
//    }

    public Page<CompanyCv> findByEmployerId(Employer employer, Integer page, Integer max) {
        return repository.findByEmployerId(employer, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "entryDate")));
    }

    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
 