/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.CourseCategory;
import com.debusey.smart.pos.smartpos.repo.CourseCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class CourseCategoryService {

    @Autowired
    public CourseCategoryRepo repository;


    public List<CourseCategory> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<CourseCategory> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public boolean save(CourseCategory t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<CourseCategory> t) {
        repository.saveAll(t);
    }

    public void delete(CourseCategory t) {
        repository.delete(t);
    }

    public Optional<CourseCategory> findById(Integer id) {
        return repository.findById(id);
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


    public Optional<CourseCategory> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<CourseCategory> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }

    public Optional<CourseCategory> findByName(String category) {
        return repository.findByName(category);
    }


}

