/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Awards;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AwardsRepo extends CrudRepository<Awards, Integer> {

    List<Awards> findByJobseeker(JobSeeker seeker);
}