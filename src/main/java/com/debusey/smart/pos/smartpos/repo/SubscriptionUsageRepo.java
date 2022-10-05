/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.SubscriptionUsage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface SubscriptionUsageRepo extends CrudRepository<SubscriptionUsage, Integer> {

    List<SubscriptionUsage> findByJobSeekerId(Integer seeker);

    List<SubscriptionUsage> findByEmployerId(Integer seeker);

    @Query("SELECT c FROM SubscriptionUsage c WHERE c.employerId = :employer and c.jobSeekerId = :seeker")
    Optional<SubscriptionUsage> findByEmployerAndJobSeeker(@Param("seeker") Integer seeker, @Param("employer") Integer employer);

}