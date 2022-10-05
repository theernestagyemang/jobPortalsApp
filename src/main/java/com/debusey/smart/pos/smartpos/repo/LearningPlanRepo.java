/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.LearningPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LearningPlanRepo extends JpaRepository<LearningPlan, Integer> {
    Optional<LearningPlan> findByTransactionId(String transactionId);

    Optional<LearningPlan> findByName(String name);
}
