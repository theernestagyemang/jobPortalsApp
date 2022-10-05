/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SL002
 */

@RestController
public class JobSeekerApiController {

    @Autowired
    private JobSeekerService service;

    @RequestMapping("/api/jobseeker")
    public List<JobSeeker> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/jobseeker")
    public void addJobSeeker(@RequestBody JobSeeker jobseeker) {
        service.save(jobseeker);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/jobseekerlist")
    public void addJobSeeker(@RequestBody List<JobSeeker> jobseeker) {
        service.saveAll(jobseeker);
    }

    @RequestMapping("/api/jobseeker/{id}")
    public JobSeeker findById(@PathVariable Integer id) {
        return service.findById(id).orElse(new JobSeeker());
    }


}
