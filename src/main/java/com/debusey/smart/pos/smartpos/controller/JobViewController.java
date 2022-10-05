/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.ProfileVievBd;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class JobViewController {

    @Autowired
    public JobViewService service;

    @Autowired
    public ProfileViewService proService;

    @Autowired
    public JobsService jobService;

    @Autowired
    public JobSeekerService seekerService;

    @Autowired
    public EmployerService empService;


    @GetMapping("/findView/{seekerId}/{jobId}")
    @ResponseBody
    public Integer findView(@PathVariable Integer seekerId, @PathVariable Integer jobId) {
        return 0;
    }

    @PostMapping("/log-job-view/{trx}")
    @ResponseBody
    public void logJobView(@PathVariable String trx) {
        Optional<Jobs> opp = jobService.findByTransactionId(trx);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Job Id: " + trx);
        }
        Jobs job = opp.get();
        JobViews jv = new JobViews();
        jv.setJobs(job);
        jv.setViewDate(LocalDateTime.now());

        service.save(jv);
    }


    @GetMapping("/create-jview/{id}")
    @ResponseBody
    public String createJView(Principal principal, @PathVariable Integer id) {
        try {

            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            Optional<Employer> opp = empService.findByEmail(user.getUsername());
            if (opp.isPresent()) {

                Optional<JobSeeker> oppSeeker = seekerService.findById(id);

                if (oppSeeker.isPresent()) {

                    JobSeeker seeker = oppSeeker.get();
                    Employer emp = opp.get();

                    ProfileViews views = proService.findByJobseekerAndJobs(seeker, emp).orElse(new ProfileViews());
                    views.setJobseeker(seeker);
                    views.setEmployer(emp);
                    views.setViewDate(LocalDateTime.now());

                    return proService.save(views) ? "SUCCESS" : "FAILED";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private JobSeeker findSeeker(Integer seekerId) {

        Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
        if (oppSeeker.isPresent()) {
            return oppSeeker.get();
        }
        return new JobSeeker();
    }

    private Jobs findJob(Integer id) {

        Optional<Jobs> opp = jobService.findById(id);
        if (opp.isPresent()) {
            return opp.get();
        }
        return new Jobs();
    }


    @GetMapping("/find-views/{id}")
    @ResponseBody
    public List<ProfileVievBd> findVies(@PathVariable Integer id) {

        List<ProfileVievBd> dblist = new ArrayList<>();

        JobSeeker seeker = new JobSeeker(id);
        List<ProfileViews> list = proService.findByJobseeker(seeker);

        if (list == null || list.isEmpty()) {
            return dblist;
        }
        list.stream().forEach((item) -> {


            Employer employer = item.getEmployer();
            if (employer != null) {

                Integer bdId = item.getId();
                String fileName = employer.getFileName();
                String companyName = employer.getFileName();
                LocalDateTime viewDate = item.getViewDate();

                ProfileVievBd db = new ProfileVievBd(bdId, fileName, companyName, viewDate);
                dblist.add(db);
            }

        });

        return dblist;
    }

    @GetMapping("/recruiter/job-view-count")
    @ResponseBody
    public Integer jobViewCount(@PathVariable Integer id) {
        Optional<Jobs> opp = jobService.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Job Id: " + id);
        }
        Jobs job = opp.get();
        int count = service.findByJobCount(job);
        return count;
    }


}
