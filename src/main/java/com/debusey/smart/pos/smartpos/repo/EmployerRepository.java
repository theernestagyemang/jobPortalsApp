/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Employer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface EmployerRepository extends PagingAndSortingRepository<Employer, Integer> {

    Optional<Employer> findByEmail(String email);

    @Query("SELECT u FROM Employer u WHERE  u.email = :email")
    List<Employer> findDuplicates(@Param("email") String email);

    Optional<Employer> findByCompanyName(String name);

    List<Employer> findByPopular(Boolean popular);

    @Query("SELECT count(u.id) FROM Employer u")
    Integer getCompaniesCount();


}