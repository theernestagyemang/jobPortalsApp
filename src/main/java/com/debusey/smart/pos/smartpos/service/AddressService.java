/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Company;
import com.debusey.smart.pos.smartpos.entity.CompanyAddress;
import com.debusey.smart.pos.smartpos.repo.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class AddressService {

    @Autowired
    public AddressRepo repository;


    public List<CompanyAddress> findAll() {
        List<CompanyAddress> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(CompanyAddress t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<CompanyAddress> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(CompanyAddress t) {
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

    public Optional<CompanyAddress> findById(Integer id) {
        return repository.findById(id);
    }

    public List<CompanyAddress> findByCompany(Company c) {
        List<CompanyAddress> addressList = repository.findByCompany(c);
        return addressList;

    }

}
 