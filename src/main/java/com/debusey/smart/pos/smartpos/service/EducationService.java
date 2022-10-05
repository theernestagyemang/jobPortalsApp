/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.EducationalExperience;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class EducationService {

    @Autowired
    public EducationRepository repository;


    public List<EducationalExperience> findAll() {
        List<EducationalExperience> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(EducationalExperience t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<EducationalExperience> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(EducationalExperience t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<EducationalExperience> findById(Integer id) {
        return repository.findById(id);
    }

    public List<EducationalExperience> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
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
 