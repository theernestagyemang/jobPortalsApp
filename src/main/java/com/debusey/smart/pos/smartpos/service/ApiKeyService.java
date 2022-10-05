/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.ApiKeys;
import com.debusey.smart.pos.smartpos.repo.ApiKeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ApiKeyService {

    @Autowired
    public ApiKeyRepo repository;


    public List<ApiKeys> findAll() {
        List<ApiKeys> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(ApiKeys t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<ApiKeys> t) {
        repository.saveAll(t);
    }

    public void delete(ApiKeys t) {
        repository.delete(t);
    }

    public Optional<ApiKeys> findById(Integer id) {
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


    public Optional<ApiKeys> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<ApiKeys> findByUniqueKey(String key) {
        return repository.findByUniqueKey(key);
    }

}

