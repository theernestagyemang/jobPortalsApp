/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Company;
import com.debusey.smart.pos.smartpos.entity.CompanyAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AddressRepo extends CrudRepository<CompanyAddress, Integer> {

    List<CompanyAddress> findByCompany(Company c);

}