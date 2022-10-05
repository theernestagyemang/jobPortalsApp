/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.db.ImageUtil;
import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Positions;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.search.UserSearch;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import com.debusey.smart.pos.smartpos.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class PositionsController {
    @Autowired
    private PositionsService service;

    @Autowired
    private UserSearch userSearch;

    @PostMapping("/create-job-category")
    @ResponseBody
    public boolean creatPositions(String name, Integer id, String jobIcon) {

        Positions positions = service.findById(id).orElse(new Positions());
        positions.setName(name);
        positions.setEntryDate(new Date());
        positions.setCatIcon(jobIcon);

        return service.save(positions);
    }

    @GetMapping("/admin/job-category")
    public String getCategory() {
        return "admin/job-category";
    }

    @GetMapping("/admin/all-job-categories")
    @ResponseBody
    public List<Positions> getCategories() {
        return service.findAll();
    }

    @GetMapping("/job-categories")
    public String jobCategories(Model model, Principal principal, @RequestParam(defaultValue = "1", required = false) Integer page) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        if (user != null) {
            Employee employee = user.getStaffId();
            model.addAttribute("emp", employee);
        } else {
            model.addAttribute("emp", new Employee());
        }
        Page<Positions> pages = service.findAll(page - 1);
        List<Positions> list = pages.getContent();
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);

        model.addAttribute("list", list);
        model.addAttribute("totalPages", pages.getTotalPages());

        return "admin/job-category";
    }


    @RequestMapping("/find-job-category")
    @ResponseBody
    public List<Positions> findJobCat(String q) {

        List<Positions> list1 = userSearch.searchPositions(q);
        return list1;
    }

    @RequestMapping("/findOneCategory/{id}")
    @ResponseBody
    public Optional<Positions> findOneCategory(@PathVariable Integer id, Principal principal) {
        return service.findOne(id);
    }


    @RequestMapping("/deleteCategory/{id}")
    @ResponseBody
    public boolean deleteCategory(@PathVariable Integer id) {
        return service.deleteById(id);
    }


}
