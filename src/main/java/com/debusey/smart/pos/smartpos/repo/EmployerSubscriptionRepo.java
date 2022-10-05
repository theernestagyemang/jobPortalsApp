/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.EmployerSubscription;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author Admin
 */
public interface EmployerSubscriptionRepo extends PagingAndSortingRepository<EmployerSubscription, Integer> {

    Optional<EmployerSubscription> findByCategory(String category);

    Optional<EmployerSubscription> findByTransactionId(String category);


}