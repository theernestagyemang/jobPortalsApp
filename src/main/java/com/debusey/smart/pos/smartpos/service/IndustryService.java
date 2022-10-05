/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Industry;
import com.debusey.smart.pos.smartpos.repo.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class IndustryService {

    @Autowired
    public IndustryRepository productRepository;


    public List<Industry> getAllIndustry() {
        List<Industry> items = new ArrayList<>();
        productRepository.findAll().forEach(items::add);
        return items;
    }

    public void updateIndustry(Industry t) {
        productRepository.save(t);
    }

    public void updateIndustry(List<Industry> t) {
        productRepository.saveAll(t);
    }

    public void deleteIndustry(Industry t) {
        productRepository.delete(t);
    }

    public Optional<Industry> getIndustry(Integer id) {
        return productRepository.findById(id);
    }


}
