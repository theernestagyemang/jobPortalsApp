/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.SubscriptionToken;
import com.debusey.smart.pos.smartpos.repo.SubscriptionTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class SubscriptionTokenService {

    @Autowired
    public SubscriptionTokenRepo repository;


    public List<SubscriptionToken> findAll() {
        List<SubscriptionToken> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(SubscriptionToken t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean saveAll(List<SubscriptionToken> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(SubscriptionToken t) {
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

    public boolean deleteAll(List<SubscriptionToken> list) {
        try {
            repository.deleteAll(list);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<SubscriptionToken> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<SubscriptionToken> findByEmail(String id) {
        return repository.findByEmail(id);
    }


    public Optional<SubscriptionToken> findByToken(String token) {
        return repository.findByToken(token);
    }


}
 