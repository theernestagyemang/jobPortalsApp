/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.WorkExperience;
import com.debusey.smart.pos.smartpos.repo.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class WorkExperienceService {

    @Autowired
    public WorkExperienceRepository repository;


    public List<WorkExperience> findAll() {
        List<WorkExperience> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(WorkExperience t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<WorkExperience> t) {

        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(WorkExperience t) {

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

    public Optional<WorkExperience> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<List<Object[]>> findWorkExperience(Integer jobSeekerId) {
        return repository.findWorkExperience(jobSeekerId);
    }

    public List<WorkExperience> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }

    public Optional<WorkExperience> findCurrent(JobSeeker seeker) {

        List<WorkExperience> list = findByJobSeeker(seeker);
        WorkExperience p = list.stream()
                .filter(u -> u != null)
                .filter(u -> u.getStillWorkThere() != null)
                .filter(u -> u.getStillWorkThere().equalsIgnoreCase("Yes"))
                .findAny()
                .orElse(null);
//            if(p==null){
//               return Optional.ofNullable(p);
//            }

        Optional<WorkExperience> op = Optional.ofNullable(p);

        return op;

    }


    public Optional<List<Object[]>> findByWorkSpecialization() {
        return repository.findByWorkSpecialization();
    }

    public List<WorkExperience> findByDesignationIn(List<String> q) {
        return repository.findByDesignationIn(q);
    }

}
 