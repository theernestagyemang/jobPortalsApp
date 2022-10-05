/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.CareerGuidance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CareerGuidanceRepo extends CrudRepository<CareerGuidance, Integer> {

    @Query(nativeQuery = true, value = "select * FROM career_guidance ORDER BY event_date asc LIMIT 3")
    List<CareerGuidance> findThreeEvents();
}