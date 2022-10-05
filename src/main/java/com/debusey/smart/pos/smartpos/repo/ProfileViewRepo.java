/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ProfileViews;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface ProfileViewRepo extends PagingAndSortingRepository<ProfileViews, Integer> {

    @Query("SELECT c FROM ProfileViews c WHERE  c.jobseeker = :jobseeker and c.employer =  :employer")
    Optional<ProfileViews> findByJobseekerAndEmployer(JobSeeker jobseeker, Employer employer);

    List<ProfileViews> findByJobseeker(JobSeeker seeker);
}