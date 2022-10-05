/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.ImageUtil;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Testimonial;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.entity.WebUtils;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import com.debusey.smart.pos.smartpos.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class TestimonialController {
    @Autowired
    private TestimonialService service;

    @Autowired
    private JobSeekerService seekerSrvice;

    @GetMapping("/testimonial")
    public String testimonials(Model model, Principal principal) {
        List<Testimonial> list = service.findAll();

        model.addAttribute("list", list);

        return "testimonial";
    }

    @GetMapping("/seeker-testimonial")
    public String userTestimonials(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        if (user != null) {

            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);

            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = seekerSrvice.findByEmail(email);


            JobSeeker seeker = oppSeeker.orElse(new JobSeeker());
            model.addAttribute("seeker", seeker);
            model.addAttribute("imgUtil", new ImageUtil());

            return "testimonial";
        }

        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("user", new Users());


        return "testimonial";
    }

    @GetMapping("/active-testimonial")
    public String activeTestimonials(Model model, Principal principal) {
        List<Testimonial> list = service.findByStatus(true);

        model.addAttribute("list", list);

        return "testimonial";
    }

    @PostMapping("/testimonial")
    @ResponseBody
    public boolean addTestimony(String msg, String name, String prof, Integer id) {

        try {
            Optional<JobSeeker> opp = seekerSrvice.findById(id);
            if (opp.isPresent()) {
                JobSeeker seeker = opp.get();
                Testimonial tx = new Testimonial();
                tx.setApproved(false);
                tx.setEntryDate(new Date());
                tx.setMessage(msg);
                tx.setName(name);
                tx.setProfession(prof);
                tx.setStatus(false);
                tx.setJobSeekerId(seeker);

                return service.save(tx);
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
