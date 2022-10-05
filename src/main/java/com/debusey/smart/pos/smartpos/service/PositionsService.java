/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Positions;
import com.debusey.smart.pos.smartpos.repo.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class PositionsService {

    @Autowired
    public PositionsRepository repository;


    public List<Positions> findAll() {
        List<Positions> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(Positions t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<Positions> t) {
        repository.saveAll(t);
    }

    public void delete(Positions t) {
        repository.delete(t);
    }

    public Optional<Positions> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<Positions> findByName(String profession) {
        return repository.findByName(profession);
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


    public Optional<Positions> findOne(Integer id) {
        return repository.findById(id);
    }

    public Page<Positions> findAll(Integer page) {
        return repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "entryDate")));
    }

}

