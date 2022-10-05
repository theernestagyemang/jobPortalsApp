/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


import com.debusey.smart.pos.smartpos.entity.ScheduledMeeting;
import com.debusey.smart.pos.smartpos.repo.ScheduledMeetingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class ScheduledMeetinService {

    @Autowired
    public ScheduledMeetingRepo repository;


    public List<ScheduledMeeting> findAll() {
        List<ScheduledMeeting> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public boolean save(ScheduledMeeting t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean saveAll(List<ScheduledMeeting> t) {
        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(ScheduledMeeting t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Optional<ScheduledMeeting> findById(Integer id) {
        return repository.findById(id);
    }


    public Optional<ScheduledMeeting> findByTransactionId(String name) {
        return repository.findByTransactionId(name);
    }


    public List<ScheduledMeeting> findByWorkStatusLimit(String satus) {
        return repository.findByWorkStatusLimit(satus);
    }
}
 