/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author AlhassanHussein
 */

@Controller
public class FacilitatorsController {

    @GetMapping("/recruiter/facilitators")
    public String testLogin(Model model) {
        return "recruiter/facilitators";
    }
}
