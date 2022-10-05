/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import com.debusey.smart.pos.smartpos.repo.TrainingScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */

@Service
public class TrainingScheduleService {

    @Autowired
    public TrainingScheduleRepo repository;


    public List<TrainingSchedule> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<TrainingSchedule> findAllByStatus(String status) {
        return repository.findByTrainingStatus(status);
    }

    public boolean save(TrainingSchedule t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAll(List<TrainingSchedule> t) {
        repository.saveAll(t);
    }

    public void delete(TrainingSchedule t) {
        repository.delete(t);
    }

    public Optional<TrainingSchedule> findById(Integer id) {
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


    public Optional<TrainingSchedule> findOne(Integer id) {
        return repository.findById(id);
    }

    public Optional<TrainingSchedule> findByTransactionId(String trx) {
        return repository.findByTransactionId(trx);
    }

    public List<TrainingSchedule> findByYear(int year) {
        return repository.findByYear(year);
    }

    public List<TrainingSchedule> findByEventMonths(List<String> month) {
        return repository.findByEventMonthIn(month);
    }


}

