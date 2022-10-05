/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author AlhassanHussein
 */

@Controller
public class MenuController {


    @GetMapping("/admin/menu-setup")
    public String menuSetup(Model model, Principal principal) {
        return "menu-setup";
    }

}
