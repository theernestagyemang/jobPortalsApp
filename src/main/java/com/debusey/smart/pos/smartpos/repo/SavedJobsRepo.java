/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.entity.SavedJobs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface SavedJobsRepo extends CrudRepository<SavedJobs, Integer> {

    List<SavedJobs> findByJobSeekerId(JobSeeker seeker);

    List<SavedJobs> findByCategory(String category);

    @Query("SELECT  c  FROM SavedJobs c WHERE c.jobsId = :jobsId and c.jobSeekerId = :jobSeekerId ")
    Optional<SavedJobs> findByJobSeekerIdJobsId(@Param("jobsId") Jobs jobsId, @Param("jobSeekerId") JobSeeker jobSeekerId);

}
