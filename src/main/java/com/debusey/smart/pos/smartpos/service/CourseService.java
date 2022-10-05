/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseCategory;
import com.debusey.smart.pos.smartpos.repo.CourseRepo;
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
public class CourseService {

    @Autowired
    public CourseRepo repository;


    public List<Course> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<Course> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<Course> findAllByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<Course> findFeaturedCourses() {
        return repository.findByFeaturedCourseAndStatus(true, "Active");
    }

    public boolean save(Course t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<Course> t) {
        repository.saveAll(t);
    }

    public void delete(Course t) {
        repository.delete(t);
    }

    public Optional<Course> findById(Integer id) {
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


    public Optional<Course> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<Course> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }


    public List<String> findByDistictCourse() {
        return repository.findByDistictCourse();
    }

    public List<Course> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Course> findByCourseCategory(CourseCategory category) {
        return repository.findByCourseCategory(category);
    }
}

