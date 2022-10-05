/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Countries;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author SL002
 */
public interface CountriesRepository extends CrudRepository<Countries, Integer> {
    List<Countries> findByName(String name);
}