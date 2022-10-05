/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

/**
 * @author AlhassanHussein
 */

import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Positions;
import com.debusey.smart.pos.smartpos.entity.SeekerJobPrefrence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JobPreferenceRepo extends CrudRepository<SeekerJobPrefrence, Integer> {

    List<SeekerJobPrefrence> findByJobseeker(JobSeeker c);

    List<SeekerJobPrefrence> findByPositions(Positions c);

    @Query("SELECT u.jobseeker FROM SeekerJobPrefrence u WHERE  u.positions = :positions")
    List<JobSeeker> findByJobSeekerPrefrence(@Param("positions") Positions positions);

}