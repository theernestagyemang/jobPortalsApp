/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Facilitators;
import com.debusey.smart.pos.smartpos.repo.FacilitatorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class FacilitatorsService {

    @Autowired
    public FacilitatorsRepo repository;


    public List<Facilitators> findAll() {
        List<Facilitators> facilitators = (List<Facilitators>) repository.findAll();

        return facilitators;
    }

    public boolean save(Facilitators t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<Facilitators> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(Facilitators t) {
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

    public Optional<Facilitators> findById(Integer id) {
        return repository.findById(id);
    }


    public List<Facilitators> findLimit(int i) {
        return repository.findLimit(i);
    }


}
 