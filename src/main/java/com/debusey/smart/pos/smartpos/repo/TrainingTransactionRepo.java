package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import com.debusey.smart.pos.smartpos.entity.TrainingTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingTransactionRepo extends JpaRepository<TrainingTransaction, Integer> {
    List<TrainingTransaction> findByUsers(Users users);
    TrainingTransaction findByUsersAndTrainingScheduleAndStatus(Users users, TrainingSchedule trainingSchedule, boolean status);
}
