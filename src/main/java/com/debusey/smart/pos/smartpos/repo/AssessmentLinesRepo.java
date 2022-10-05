/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface AssessmentLinesRepo extends CrudRepository<AssessmentLines, Integer> {
    Optional<AssessmentLines> findByTransactionId(String transactionId);

    List<AssessmentLines> findByActive(boolean status);

}