/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Applicants;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author SL002
 */
public interface ApplicantsRepository extends CrudRepository<Applicants, Integer> {
    List<Applicants> findByApisent(boolean s);

    Optional<Applicants> findByEmail(String email);
}