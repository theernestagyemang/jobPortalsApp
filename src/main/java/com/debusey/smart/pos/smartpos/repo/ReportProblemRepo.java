/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.ReportProblem;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface ReportProblemRepo extends PagingAndSortingRepository<ReportProblem, Integer> {
    Optional<ReportProblem> findByTransactionId(String tx);

    List<ReportProblem> findByResponded(boolean tx);

}
