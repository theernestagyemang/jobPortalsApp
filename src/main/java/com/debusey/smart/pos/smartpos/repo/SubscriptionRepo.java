/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Admin
 */
public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    Optional<Subscription> findByName(String name);

    Optional<Subscription> findByTransactionId(String name);

}
