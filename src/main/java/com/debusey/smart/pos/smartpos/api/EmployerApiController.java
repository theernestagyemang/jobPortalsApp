/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api;

import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Admin
 */
@CrossOrigin
@RestController
public class EmployerApiController {


    @Autowired
    private EmployerService service;

    @RequestMapping("/api/employer")
    public List<Employer> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/employer")
    public void addEmployer(@RequestBody Employer employer) {
        service.save(employer);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/employerlist")
    public void addEmployer(@RequestBody List<Employer> employer) {
        service.saveAll(employer);
    }

    @RequestMapping("/api/employer/{id}")
    public Employer findById(@PathVariable Integer id) {
        return service.findById(id).orElse(new Employer());
    }
}
