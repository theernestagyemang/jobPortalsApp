/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.ShortlistApp;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.entity.ShortlistedApplicants;
import com.debusey.smart.pos.smartpos.entity.WorkExperience;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.JobsService;
import com.debusey.smart.pos.smartpos.service.ShortlistedApplicantService;
import com.debusey.smart.pos.smartpos.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class JobSeekerFinder {

    @Autowired
    private WorkExperienceService wexpService;

    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private ShortlistedApplicantService shortService;
    @Autowired
    private JobsService jobService;


    @GetMapping("/current-wk")
    @ResponseBody
    public WorkExperience getWorkExperience(Integer id) {

        Optional<JobSeeker> oppSeeker = seekerService.findById(id);
        if (oppSeeker.isPresent()) {

            JobSeeker seeker = oppSeeker.get();
            Optional<WorkExperience> oppWork = wexpService.findCurrent(seeker);

            return oppWork.orElse(new WorkExperience());
        }
        return new WorkExperience();
    }


    public List<ShortlistedApplicants> findShortListed(Jobs id) {
        List<ShortlistedApplicants> list = new ArrayList<>();
        if (id == null) {
            return list;
        }
        List l = shortService.findByJobId(id);
        if (l == null) {
            return list;
        }

        return l;
    }

    public int findShortListedCount(Jobs id) {

        if (id == null) {
            return 0;
        }
        List<ShortlistedApplicants> list = shortService.findByJobId(id);

        if (list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    public Integer findDef() {
        int number = 201;
        return number;
    }

    @GetMapping("/findShortListed/{id}")
    @ResponseBody
    public List<ShortlistApp> findDef(@PathVariable Integer id) {
        try {
            Optional<Jobs> oppJob = jobService.findById(id);
            Jobs job = oppJob.orElse(new Jobs());

            List<ShortlistedApplicants> list = shortService.findByJobId(job);

            List<ShortlistApp> app = new ArrayList<>();
            list.stream().forEach((item) -> {
                ShortlistApp sh = new ShortlistApp();
                sh.setFullName(item.getJobSeekerId().getFullName());
                sh.setEmail(item.getJobSeekerId().getFullName());
                //  sh.setCv((item.getJobSeekerId().getCv()));
                sh.setDob(item.getJobSeekerId().getDob());
                sh.setGender(item.getJobSeekerId().getGender());
                sh.setMaritalStatus(item.getJobSeekerId().getMaritalStatus());
                sh.setPrimaryContact(item.getJobSeekerId().getPrimaryContact());

                app.add(sh);
            });
            return app;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
