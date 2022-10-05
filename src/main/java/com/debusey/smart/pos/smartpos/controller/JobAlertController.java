/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.Alerts;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.JobAlertService;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.JobsService;
import com.debusey.smart.pos.smartpos.service.SubscriptionTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class JobAlertController {


    @Autowired
    private JobAlertService service;

    @Autowired
    private SubscriptionTokenService tokenService;

    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private JobsService jobService;

    @GetMapping("/job-alert")
    public String jobAlers(Model model, Principal principal, Integer page) {

        Page<JobAlert> list = service.findAll(page);
        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);

        return "job-alert";

    }


    @GetMapping("/find-job-alert/{id}")
    @ResponseBody
    public Alerts findJobAlert(@PathVariable Integer id) {
        JobAlert job = service.findById(id).orElse(new JobAlert());

        Alerts alerts = new Alerts();
        alerts.setApproved(job.getApproved());
        alerts.setEmail(job.getEmail());
        alerts.setEntryDate(job.getEntryDate());
        alerts.setExpSalary(job.getExpSalary());
        alerts.setId(job.getId());
        alerts.setJobCategory(job.getJobCategory());
        alerts.setJobSeekerId(job.getJobSeekerId().getId());
        alerts.setJobKeywors(job.getJobKeywors());
        alerts.setJobLocation(job.getJobLocation());
        alerts.setJobWorkExp(job.getJobWorkExp());
        alerts.setName(job.getName());

        return alerts;
    }

    @GetMapping("/delete-job-alert/{id}")
    @ResponseBody
    public boolean deleteAlert(@PathVariable Integer id) {
        return service.deleteById(id);
    }


    @GetMapping("/job-alert-by-seeker")
    public String jobAlers(Model model, Integer seekerId, Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
        if (oppSeeker.isPresent()) {

            JobSeeker seeker = oppSeeker.get();
            List<JobAlert> list = service.findByJobSeeker(seeker);
            model.addAttribute("list", list);
        }

        model.addAttribute("list", new ArrayList<>());
        model.addAttribute("user", user);


        return "job-alert";

    }

    @GetMapping("/job-alert-list")
    public String jobAlertList(Model model, Principal principal, @RequestParam(defaultValue = "1") Integer page) {

        Users user = JsfUtil.findOnline(principal);
        Page<JobAlert> list = service.findAll(page - 1);
        model.addAttribute("list", list.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("totalItems", list.getTotalElements());


        model.addAttribute("user", user);
        return "job-alert-list";

    }

    @PostMapping("/create-alert")
    @ResponseBody
    public boolean createAlert(String jobKeywors, String jobLocation,
                               String jobWorkExp, String name, BigDecimal expSalary,
                               String jobCategory, Integer id, Integer alertId) {
        try {
            JobAlert alert = service.findById(alertId).orElse(new JobAlert());
            Optional<JobSeeker> oppSeeker = seekerService.findById(id);
            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();


                alert.setEntryDate(new Date());
                alert.setExpSalary(expSalary);
                alert.setJobCategory(jobCategory);
                alert.setJobKeywors(jobKeywors);
                alert.setJobLocation(jobLocation);
                alert.setJobSeekerId(seeker);
                alert.setJobWorkExp(jobWorkExp);
                alert.setName(name);
                alert.setApproved(false);


                if (seeker != null) {
                    String email = seeker.getEmail();
                    alert.setEmail(email);

                    Optional<SubscriptionToken> oppToken = tokenService.findByEmail(email);
                    if (!oppToken.isPresent()) {
                        SubscriptionToken token = new SubscriptionToken();
                        token.setEmail(email);
                        token.setToken(JsfUtil.generateSerial());
                        tokenService.save(token);
                    }
                }

                return service.save(alert);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @PostMapping("/job-alert-subscription")
    @ResponseBody
    public String emailSubsription(String dzEmail) {
        try {

            JobAlert alert = new JobAlert();
            alert.setEmail(dzEmail);
            alert.setEntryDate(new Date());
            alert.setApproved(false);

            Optional<SubscriptionToken> oppToken = tokenService.findByEmail(dzEmail);
            if (!oppToken.isPresent()) {
                SubscriptionToken token = new SubscriptionToken();
                token.setEmail(dzEmail);
                token.setToken(JsfUtil.generateSerial());
                tokenService.save(token);
            }


            if (service.save(alert)) {
                return "SUCCESS";
            }

            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
    }

    @PostMapping("/job-alert-subscription-x")
    @ResponseBody
    public String emailSubsription2(Integer jobId, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            String dzEmail = user.getUsername();
            Jobs job = null;
            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(dzEmail);
            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                Optional<Jobs> opp = jobService.findById(jobId);

                JobAlert alert = new JobAlert();
                alert.setEmail(dzEmail);
                alert.setEntryDate(new Date());
                alert.setApproved(false);

                Optional<SubscriptionToken> oppToken = tokenService.findByEmail(dzEmail);
                if (!oppToken.isPresent()) {
                    SubscriptionToken token = new SubscriptionToken();
                    token.setEmail(dzEmail);
                    token.setToken(JsfUtil.generateSerial());
                    tokenService.save(token);
                }

                if (opp.isPresent()) {
                    job = opp.get();
                    alert.setJobCategory(job.getCategory());
                    alert.setJobKeywors(job.getLocation());
                    alert.setJobSeekerId(seeker);
                }
                return service.save(alert) ? "SUCCESS" : "FAILED";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "ERROR";
    }


    @GetMapping("/unsubscribe/{token}")
    public String unscribe(@PathVariable String token) {

        Optional<SubscriptionToken> oppToken = tokenService.findByToken(token);
        if (oppToken.isPresent()) {
            String email = oppToken.get().getEmail();

            List<JobAlert> listOfAlert = service.findByEmail(email);
            return service.deleteAll(listOfAlert) ? "SUCCESS" : "FAILED";

        }

        return "invalid";
    }

    private boolean deleteBySeeker(JobSeeker seeker) {
        List<JobAlert> list = service.findByJobSeeker(seeker);
        return service.deleteAll(list);
    }

    @PostMapping("/add-job-alert/{transactionId}")
    @ResponseBody
    public String addJobAlert(Principal principal, @PathVariable String transactionId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            String email = user.getUsername();
            if (email == null) {
                System.out.println("invlid-email");
                return "INVALID-USER";
            }
            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                System.out.println("invlid-INVALID-USER");
                return "INVALID-USER";
            }
            JobSeeker seeker = oppSeeker.get();
            Optional<Jobs> oppJob = jobService.findByTransactionId(transactionId);
            if (!oppJob.isPresent()) {
                System.out.println("NVALID-JOB-ID");
                return "INVALID-JOB-ID";
            }

            Jobs job = oppJob.get();
            String jobCategory = job.getCategory();
            JobAlert alert = new JobAlert();
            alert.setApproved(true);
            alert.setEmail(email);
            alert.setJobCategory(jobCategory);
            alert.setJobLocation(job.getLocation());
            alert.setJobSeekerId(seeker);
            alert.setExpSalary(job.getToRenumeration());
            alert.setEntryDate(new Date());
            alert.setJobWorkExp(job.getExperience());

            return service.save(alert) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
