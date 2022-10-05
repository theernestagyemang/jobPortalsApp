/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Qualifications;
import com.debusey.smart.pos.smartpos.repo.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class QualificationService {

    @Autowired
    public QualificationRepository productRepository;


    public List<Qualifications> getAllQualifications() {
        List<Qualifications> items = new ArrayList<>();
        productRepository.findAll().forEach(items::add);
        return items;
    }

    public void updateQualifications(Qualifications t) {
        productRepository.save(t);
    }

    public void updateQualifications(List<Qualifications> t) {
        productRepository.saveAll(t);
    }

    public void deleteQualifications(Qualifications t) {
        productRepository.delete(t);
    }

    public Optional<Qualifications> getQualifications(Integer id) {
        return productRepository.findById(id);
    }


}

