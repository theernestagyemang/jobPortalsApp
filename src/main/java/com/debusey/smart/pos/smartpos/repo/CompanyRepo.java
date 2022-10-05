/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Admin
 */
public interface CompanyRepo extends CrudRepository<Company, Integer> {

    Optional<Company> findByName(String name);

}
