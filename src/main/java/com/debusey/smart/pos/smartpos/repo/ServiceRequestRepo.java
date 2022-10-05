/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

 
 
import com.debusey.smart.pos.smartpos.entity.ServiceRequest;
import java.util.List;
import java.util.Optional;

import com.debusey.smart.pos.smartpos.entity.ServiceType;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Admin
 */
public interface ServiceRequestRepo extends JpaRepository<ServiceRequest, Integer>{
     List<ServiceRequest> findByRequestedBy(Users users);

     List<ServiceRequest> findAllByServiceType(ServiceType serviceType);

     Optional<ServiceRequest> findByRequestedByAndWorkStatus(Users users, String workStatus);

     Optional<ServiceRequest> findByRequestedByAndStatusAndServiceType(Users users, String status, ServiceType serviceType);
     Page<ServiceRequest> findByWorkStatus(String workStatus, Pageable pageable);
    
//    @Query("SELECT c FROM ResumeRequest c WHERE  c.workStatus =  :workStatus ")
//    public List<ResumeRequest> findByWorkStatus(@Param("workStatus")String workStatus);
}