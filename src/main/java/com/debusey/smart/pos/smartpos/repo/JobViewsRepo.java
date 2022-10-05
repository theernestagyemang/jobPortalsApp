/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.JobViews;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface JobViewsRepo extends PagingAndSortingRepository<JobViews, Integer> {

    @Query("SELECT c FROM JobViews c WHERE  c.jobseeker = :jobseeker and c.jobs =  :jobs")
    Optional<JobViews> findByJobseekerAndJobs(JobSeeker jobseeker, Jobs jobs);

    List<JobViews> findByJobseeker(JobSeeker jobseeker);

    List<JobViews> findByJobs(Jobs jobs);

    @Query("SELECT count(c.id) FROM JobViews c WHERE c.jobs =  :jobs")
    Integer findByJobCount(@Param("jobs") Jobs jobs);


}