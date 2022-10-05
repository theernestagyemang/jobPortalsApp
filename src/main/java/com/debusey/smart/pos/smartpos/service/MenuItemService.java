/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.MenuItem;
import com.debusey.smart.pos.smartpos.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class MenuItemService {

    @Autowired
    public MenuItemRepo repository;


    public List<MenuItem> getAllMenuItem() {
        List<MenuItem> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(MenuItem t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<MenuItem> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(MenuItem t) {
        repository.delete(t);
    }

    public void deleteById(Integer t) {
        repository.deleteById(t);
    }

    public Optional<MenuItem> findById(Integer id) {
        return repository.findById(id);
    }


}

