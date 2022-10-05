/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author SL002
 */

@Controller
public class RegisterController {

    @Autowired
    private JobSeekerService service;
    @Autowired
    private EmployerService eservice;

    @Autowired
    private CountriesService cservice;

    @Autowired
    private PositionsService pservice;

    @Autowired
    private IndustryService indService;

    @Autowired
    private HttpSession httpSession;

    private List<EducationalExperience> educationList;
    private List<WorkExperience> worklist;

    @Autowired
    private WorkExperienceService wservice;

    @Autowired
    private EducationService eduService;
    private String txtId;


    @PostMapping("/client-register")
    public String clientRegister(Model model, @ModelAttribute JobSeeker jobSeeker) {


        if (service.save(jobSeeker)) {
            model.addAttribute("msg_success", "Registration Successful");
        } else {
            model.addAttribute("msg_error", "Cound not register");
        }

        List<String> countries = cservice.getCountries();
        List<Positions> pos = pservice.findAll();

        model.addAttribute("jobseeker", new JobSeeker());
        model.addAttribute("pos", pos);
        model.addAttribute("countries", countries);


        return "client-register";

    }


    @GetMapping("/client-register")
    public String register(Model model) {

        List<Positions> pos = pservice.findAll();
        List<String> countries = cservice.getCountries();


        model.addAttribute("jobseeker", new JobSeeker());
        model.addAttribute("pos", pos);
        model.addAttribute("countries", countries);

        return "client-register";
    }

    @GetMapping("/client-register2")
    public String register2(Model model) {

        List<Positions> pos = pservice.findAll();
        List<String> countries = cservice.getCountries();


        model.addAttribute("jobseeker", new JobSeeker());
        model.addAttribute("pos", pos);
        model.addAttribute("countries", countries);

        return "client-register2";
    }


    @GetMapping("/com-register")
    public String compRegister(Model model) {

        List<Positions> pos = pservice.findAll();
        List<String> countries = cservice.getCountries();


        model.addAttribute("employer", new Employer());
        model.addAttribute("pos", pos);
        model.addAttribute("countries", countries);

        return "com-register";
    }


    @PostMapping("/com-register")
    public String clientRegister2(Model model, @ModelAttribute Employer employer) {


        if (eservice.save(employer)) {
            model.addAttribute("msg_success", "Registration Successful");
        } else {
            model.addAttribute("msg_error", "Cound not register");
        }

        List<String> countries = cservice.getCountries();
        List<Positions> pos = pservice.findAll();

        model.addAttribute("jobseeker", new JobSeeker());
        model.addAttribute("pos", pos);
        model.addAttribute("countries", countries);


        return "com-register";

    }

    //   ====================PERSONAL PROFILE==============================
    @GetMapping("/build-profile")
    public String buildProfile(Model model) {

        model.addAttribute("profile", new JobSeeker());

        return "wizard-build-profile";
    }

    @PostMapping("/build-profile")
    public ModelAndView postBuildProfile(Model model, @ModelAttribute JobSeeker profile) {
        String txt = UUID.randomUUID().toString();
        setDefault(txt, profile);

        if (service.save(profile)) {
            return new ModelAndView("redirect:/work-profile?id=" + txt);

        }
        return new ModelAndView("build-profile");
    }


    private void setDefault(String txt, JobSeeker profile) {
        profile.setApproved(false);
        profile.setEntryDate(new Date());

    }


    //   ====================EDUCATION==============================

    @GetMapping("/education-profile")
    public ModelAndView educationProfile(Model model, String id) {

        model.addAttribute("transactionId", id);
        model.addAttribute("exp", new EducationalExperience());
        model.addAttribute("years", generateYears());

        if (educationList == null) {
            educationList = new ArrayList<>();
        }
        model.addAttribute("educationList", educationList);

        return new ModelAndView("education-profile");

    }

    @PostMapping("/education-profile")
    public ModelAndView postEducationProfile(Model model, @ModelAttribute EducationalExperience exp) {
        txtId = null;
        if (educationList == null) {
            educationList = new ArrayList<>();
        }
        educationList.add(exp);

        httpSession.setAttribute("educationList", educationList);
        model.addAttribute("educationList", educationList);
        model.addAttribute("exp", exp);
        model.addAttribute("years", generateYears());


        return new ModelAndView("education-profile");
        // return "redirect:/educaton-profile";
    }

    @GetMapping("/save-education-profile")
    public String saveEducationProfile(Model model) {
        List<EducationalExperience> wk = (List<EducationalExperience>) httpSession.getAttribute("educationList");
        if (wk != null) {

            List<EducationalExperience> list = new ArrayList<>();
            wk.stream().forEach((item) -> {
                JobSeeker j = findJobSeeker(item.getTransactionId());

                if (j != null) {
                    item.setEntryDate(new Date());
                    item.setJobSeekerId(j);
                    list.add(item);
                }
            });

            if (eduService.saveAll(list)) {
                model.addAttribute("msg_success", "Profile successfully created..");
                return "success";

            } else {
                model.addAttribute("msg_error", "Could not create profile");
            }

        }

        return "work-profile";
    }


    //   ====================WORK==============================
    @GetMapping("/work-profile")
    public String workprofile(Model model, String id) {


        model.addAttribute("transactionId", id);
        model.addAttribute("exp", new WorkExperience());
        model.addAttribute("years", generateYears());
        model.addAttribute("jobtitleList", generateYears());

        List<Industry> industryList = indService.getAllIndustry();
        model.addAttribute("industryList", industryList);

        if (worklist == null) {
            worklist = new ArrayList<>();
        }
        model.addAttribute("educationList", worklist);


        return "work-profile";
    }

    @PostMapping("/work-profile")
    public String createWorkProfile(Model model, @ModelAttribute WorkExperience exp) {

        if (worklist == null) {
            worklist = new ArrayList<>();
        }
        worklist.add(exp);

        httpSession.setAttribute("worklist", worklist);
        model.addAttribute("educationList", worklist);
        model.addAttribute("exp", exp);
        model.addAttribute("years", generateYears());
        model.addAttribute("jobtitleList", generateYears());
        return "work-profile";
    }


    @GetMapping("/save-work-profile")
    public String saveWorkProfile(Model model) {
        List<WorkExperience> wk = (List<WorkExperience>) httpSession.getAttribute("worklist");
        if (wk != null) {

            List<WorkExperience> list = new ArrayList<>();
            wk.stream().forEach((item) -> {
                JobSeeker j = findJobSeeker(item.getTransactionId());

                if (j != null) {
                    item.setEntryDate(new Date());
                    item.setJobSeekerId(j);
                    list.add(item);
                }
            });

            if (wservice.saveAll(list)) {
                model.addAttribute("msg_success", "Profile successfully created..");
                return "success";

            } else {
                model.addAttribute("msg_error", "Could not create profile");
            }

        }

        return "work-profile";
    }

    private JobSeeker findJobSeeker(String transactionId) {
        Optional<JobSeeker> opp = service.findByTransactionId(transactionId);
        //   System.out.println("id..."+transactionId);
        if (opp.isPresent()) {
            //  System.out.println("present.....");
            return opp.get();
        }
        return null;
    }


    @GetMapping("/removeEdu")
    @ResponseBody
    public EducationalExperience removeOne(Integer assessId) {
        EducationalExperience ass = new EducationalExperience(assessId);
        educationList.remove(ass);
        return ass;

    }

    private List<String> generateYears() {
        List<String> years = new ArrayList<>();
        years.add("2020");
        years.add("2019");
        years.add("2018");
        years.add("2017");
        years.add("2016");
        years.add("2015");
        years.add("2014");
        years.add("2013");

        return years;
    }
}
