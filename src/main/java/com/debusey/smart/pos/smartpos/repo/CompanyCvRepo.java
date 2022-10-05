/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.CompanyCv;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Admin
 */
public interface CompanyCvRepo extends PagingAndSortingRepository<CompanyCv, Integer> {

    List<CompanyCv> findByJobSeekerId(JobSeeker seeker);

    Page<CompanyCv> findByEmployerId(Employer employer, Pageable pageabl);
}
