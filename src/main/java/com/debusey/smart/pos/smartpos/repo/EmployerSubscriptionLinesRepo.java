/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.EmployerSubscription;
import com.debusey.smart.pos.smartpos.entity.EmployerSubscriptionLines;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface EmployerSubscriptionLinesRepo extends PagingAndSortingRepository<EmployerSubscriptionLines, Integer> {

    List<EmployerSubscriptionLines> findBySubscription(EmployerSubscription category);

    Optional<EmployerSubscriptionLines> findByTransactionId(String transactionId);
}