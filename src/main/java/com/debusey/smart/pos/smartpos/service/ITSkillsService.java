/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.ITSkills;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.repo.ITSkillsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ITSkillsService {

    @Autowired
    public ITSkillsRepo repository;


    public List<ITSkills> findAll() {
        List<ITSkills> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(ITSkills t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<ITSkills> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(ITSkills t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<ITSkills> findById(Integer id) {
        return repository.findById(id);
    }

    public List<ITSkills> findByJobSeeker(JobSeeker seeker) {
        return repository.findByJobSeekerId(seeker);
    }


}
 