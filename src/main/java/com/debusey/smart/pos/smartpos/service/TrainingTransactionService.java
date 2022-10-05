package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import com.debusey.smart.pos.smartpos.entity.TrainingTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.TrainingTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTransactionService {
    @Autowired
    private TrainingTransactionRepo trainingTransactionRepo;

    public boolean addTrainingTransaction(TrainingTransaction trainingTransaction) {
        try {
            trainingTransactionRepo.save(trainingTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TrainingTransaction findByUserAndTrainingAndStatus(Users user, TrainingSchedule training){
        try {
            return trainingTransactionRepo.findByUsersAndTrainingScheduleAndStatus(user,training,true);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<TrainingTransaction> findByUser(Users user) {
        return trainingTransactionRepo.findByUsers(user);
    }

    public List<TrainingTransaction> findAll() {
        return trainingTransactionRepo.findAll();
    }

    public TrainingTransaction findById(Integer id) {
        return trainingTransactionRepo.findById(id).orElse(null);
    }
}
