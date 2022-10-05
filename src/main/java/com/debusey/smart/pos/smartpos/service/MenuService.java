/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Menu;
import com.debusey.smart.pos.smartpos.repo.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class MenuService {

    @Autowired
    public MenuRepo repository;


    public List<Menu> getAllMenu() {
        List<Menu> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(Menu t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<Menu> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(Menu t) {
        repository.delete(t);
    }

    public void deleteById(Integer t) {
        repository.deleteById(t);
    }

    public Optional<Menu> findById(Integer id) {
        return repository.findById(id);
    }


}

