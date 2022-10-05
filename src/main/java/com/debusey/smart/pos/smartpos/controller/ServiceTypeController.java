package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.entity.ServiceType;
import com.debusey.smart.pos.smartpos.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping("/admin/service-type")
    public String getView(){
        return "admin/service-type";
    }

    @PostMapping("/admin/create-service")
    @ResponseBody
    public ResponseEntity<String> addServiceType(@RequestBody ServiceType serviceType){
        String status = serviceTypeService.addServiceType(serviceType) ? "SUCCESS" : "FAILED";
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @GetMapping("/admin/services")
    @ResponseBody
    public List<ServiceType> getAll(){
        return serviceTypeService.findAll();
    }

    @GetMapping("/service-types")
    @ResponseBody
    public List<ServiceType> getAllServices(){
        return serviceTypeService.findAll();
    }

    @GetMapping("/admin/services/{id}")
    @ResponseBody
    public ServiceType findOne(@PathVariable Integer id){
        Optional<ServiceType> serviceType = serviceTypeService.findById(id);
        if(serviceType.isPresent()){
            return serviceType.get();
        }else {
            return null;
        }
    }

    @GetMapping("/admin/services/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteOne(@PathVariable Integer id){
        try {
            String status = serviceTypeService.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
