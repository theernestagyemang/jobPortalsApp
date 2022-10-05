/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Invoice;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface InvoiceRepo extends CrudRepository<Invoice, Integer> {

    List<Invoice> findByEmployerId(Employer employer);

    @Query("SELECT c FROM Invoice c WHERE  c.employerId =:employerId and c.invoiceType =:invoiceType ")
    Optional<Invoice> findByEmployerAndSub(@Param("employerId") Employer employerId, @Param("invoiceType") String invoiceType);

    @Query("SELECT c FROM Invoice c WHERE  c.seekerId =:seekerId and c.invoiceType =:invoiceType ")
    Optional<Invoice> findByJobSeekerAndSub(@Param("seekerId") JobSeeker seekerId, @Param("invoiceType") String invoiceType);
}
