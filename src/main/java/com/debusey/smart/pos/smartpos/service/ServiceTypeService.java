package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.ServiceType;
import com.debusey.smart.pos.smartpos.repo.ServiceTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepo serviceTypeRepo;

    public List<ServiceType> findAll(){
        try {
            return serviceTypeRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean addServiceType(ServiceType serviceType){
        try {
            serviceTypeRepo.save(serviceType);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<ServiceType> findById(Integer id){
        try {
            return serviceTypeRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<ServiceType> findByName(String name){
        try {
            return serviceTypeRepo.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            serviceTypeRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
