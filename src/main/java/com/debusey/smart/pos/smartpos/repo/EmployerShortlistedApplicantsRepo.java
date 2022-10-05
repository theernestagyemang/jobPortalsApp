/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.EmployerShortlistedApplicants;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface EmployerShortlistedApplicantsRepo extends PagingAndSortingRepository<EmployerShortlistedApplicants, Integer> {

    List<EmployerShortlistedApplicants> findByJobSeekerId(JobSeeker seeker);

    List<EmployerShortlistedApplicants> findByJobTitle(String jobtitle);

    Optional<EmployerShortlistedApplicants> findByJobSeekerIdAndJobTitle(JobSeeker seeker, String jobTitle);

    List<EmployerShortlistedApplicants> findByShortListedBy(Employer employer);

}
