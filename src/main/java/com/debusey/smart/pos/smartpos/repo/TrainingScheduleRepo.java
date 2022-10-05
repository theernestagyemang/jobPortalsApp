/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TrainingScheduleRepo extends JpaRepository<TrainingSchedule, Integer> {
    Optional<TrainingSchedule> findByTransactionId(String transactionId);

    List<TrainingSchedule> findByYear(int year);

    List<TrainingSchedule> findByTrainingStatus(String status);

    @Query("SELECT c FROM TrainingSchedule c where c.eventMonth IN (:q)")
    List<TrainingSchedule> findByEventMonthIn(@Param("q") List<String> q);

}
