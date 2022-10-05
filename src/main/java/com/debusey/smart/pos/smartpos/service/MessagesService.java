/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.Messages;
import com.debusey.smart.pos.smartpos.repo.MessagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class MessagesService {

    @Autowired
    public MessagesRepo repository;


    public List<Messages> getAllMessages() {
        List<Messages> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(Messages t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<Messages> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(Messages t) {
        repository.delete(t);
    }

    public void deleteById(Integer t) {
        repository.deleteById(t);
    }

    public Optional<Messages> findById(Integer id) {
        return repository.findById(id);
    }


    public List<Messages> findByRead(boolean status) {
        return repository.findByMsgRead(status);
    }


}

