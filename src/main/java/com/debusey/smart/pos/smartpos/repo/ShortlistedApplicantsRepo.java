/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.entity.ShortlistedApplicants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface ShortlistedApplicantsRepo extends PagingAndSortingRepository<ShortlistedApplicants, Integer> {

    List<ShortlistedApplicants> findByJobSeekerId(JobSeeker seeker);

    List<ShortlistedApplicants> findByJobId(Jobs jobs);

    ShortlistedApplicants findByJobSeekerIdAndJobIdAndEmployer(JobSeeker jobSeeker, Jobs jobs, Employer employer);

    List<ShortlistedApplicants> findByEmployer(Employer employer);

    @Query("SELECT c  FROM ShortlistedApplicants c WHERE  c.jobSeekerId = :jobSeekerId and c.jobId = :jobId ")
    Optional<ShortlistedApplicants> findByJobSeekerIdAndJobId(@Param("jobSeekerId") JobSeeker jobSeekerId, @Param("jobId") Jobs jobId);

}
