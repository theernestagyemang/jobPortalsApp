/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

 

 
 
import com.debusey.smart.pos.smartpos.entity.ServiceRequest;
import com.debusey.smart.pos.smartpos.entity.ServiceType;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.ServiceRequestRepo;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author SL002
 */

@Service
public class ServiceRequestService {
    
    @Autowired
    public ServiceRequestRepo repository;
    
  
    public List<ServiceRequest> findAll(){
        List<ServiceRequest> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }
    
    public Page<ServiceRequest> findAll(Integer page, Integer max){
        return repository.findAll(PageRequest.of(page-1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
  
    public boolean save(ServiceRequest t){
        try{
            repository.save(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    public boolean saveAll(List<ServiceRequest> t){
        
        try{
            repository.saveAll(t);
            return true;
        }catch(Exception e){
            return false;
        }
      
    }
    
    public boolean delete(ServiceRequest t){
         try{
            repository.delete(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    public boolean deleteById(Integer t){
         try{
            repository.deleteById(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }

    public Optional<ServiceRequest> findById(Integer id) {
       return repository.findById(id);
    }

    public Page<ServiceRequest> findByProcessed(Integer page, Integer max){
        String st = "Finished";
        return repository.findByWorkStatus(st,PageRequest.of(page-1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
    
    public Page<ServiceRequest> findByWorkStatus(String st, Integer page, Integer max){
        return repository.findByWorkStatus(st,PageRequest.of(page-1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Optional<ServiceRequest> findByUserAndWorkStatus(Users user, String workStatus){
        try {
            return repository.findByRequestedByAndWorkStatus(user,workStatus);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<ServiceRequest> findByUserAndStatus(Users user, String status, ServiceType serviceType){
        try {
            return repository.findByRequestedByAndStatusAndServiceType(user,status,serviceType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<ServiceRequest> findAllByServiceType(ServiceType serviceType){
        try {
            return repository.findAllByServiceType(serviceType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
