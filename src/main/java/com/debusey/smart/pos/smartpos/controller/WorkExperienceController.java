/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * @author AlhassanHussein
 */

@Controller
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService service;


    public Optional<List<Object[]>> findWorkExperience(Integer jobSeekerId) {
        return service.findWorkExperience(jobSeekerId);
    }
}
