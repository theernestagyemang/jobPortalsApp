/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Facilitators;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Admin
 */
public interface FacilitatorsRepo extends CrudRepository<Facilitators, Integer> {

    @Query(nativeQuery = true, value = "SELECT * from Facilitators  order by id desc LIMIT :limit")
    List<Facilitators> findLimit(@Param("limit") Integer limit);
}
