/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.JobAlert;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Admin
 */
public interface JobAlertRepo extends PagingAndSortingRepository<JobAlert, Integer> {

    List<JobAlert> findByJobSeekerId(JobSeeker seeker);

    List<JobAlert> findByEmail(String email);

    @Query("SELECT DISTINCT c.email FROM JobAlert c ")
    List<String> findByDistictEmails();

    List<JobAlert> findByJobCategory(String category);

    @Query("SELECT DISTINCT c.email FROM JobAlert c where c.jobCategory LIKE %:category%")
    List<String> findByDistictEmailsAndCategory(@Param("category") String category);


}
