/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.IncompleteProfile;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ProfileStrength;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.ProfileCompletenessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author AlhassanHussein
 */

@Controller
public class ProfileCompletenessController {
    @Autowired
    private ProfileCompletenessService service;

    @Autowired
    private JobSeekerService seekerService;

    @GetMapping("/seeker/findByCompletePctAndJobSeeker")
    @ResponseBody
    public Integer findByCompletePctAndJobSeeker(Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        System.out.println("user ...." + user);
        if (user == null) {
            throw new BeanNotFoundException("Invalid Jobseeker");
        }
        String email = user.getUsername();
        if (email == null) {
            throw new BeanNotFoundException("Invalid Email");
        }
        Optional<JobSeeker> opp = seekerService.findByEmail(email);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();
            System.out.println("seeker..." + seeker);
            Integer result = service.findByCompletePctAndJobSeeker(seeker.getId());
            if (result == null) {
                return 0;
            }
            return result;
        }

        return 0;


    }


    @GetMapping("/seeker/findIncompleteProfile")
    @ResponseBody
    public List<IncompleteProfile> findIncompleteProfile(Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            throw new BeanNotFoundException("Invalid Jobseeker");
        }
        String email = user.getUsername();
        if (email == null) {
            throw new BeanNotFoundException("Invalid Email");
        }
        Optional<JobSeeker> opp = seekerService.findByEmail(email);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Email");
        }
        JobSeeker seeker = opp.get();
        Optional<ProfileStrength> oppPf = service.findByJobSeekerId(seeker);
        if (!oppPf.isPresent()) {
            throw new BeanNotFoundException("Profile Strength");
        }

        ProfileStrength pf = oppPf.get();
        List<IncompleteProfile> profile = new ArrayList<>();

//        Integer accomplishment = pf.getAccomplishment();
//        if(accomplishment == null || accomplishment !=1){
//            profile.add(new IncompleteProfile(new Date().getTime(),"Accomplishment","/accomplishment"));
//        }


        //1
        Integer desired_career_profile = pf.getDesiredCareerProfile();
        if (desired_career_profile == null || desired_career_profile != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Desired Career Profile", "/desired-career-profile"));
        }

        //2
        Integer education = pf.getEducation();
        if (education == null || education != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Education", "/education"));
        }

        //3
        Integer employment = pf.getEmployment();
        if (employment == null || employment != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Employment", "/employment"));
        }

        //4
        Integer itSkills = pf.getItSkills();
        if (itSkills == null || itSkills != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "IT Skills", "/it-skills"));

        }

        //5
        Integer keySkills = pf.getKeySkills();
        if (keySkills == null || keySkills != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Key Skills", "/key-skills"));
        }

        //6
        Integer personalDetails = pf.getPersonalDetails();
        if (personalDetails == null || personalDetails != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Personal Details", "/personal-detailsx"));
        }

        //7
        Integer profileSummary = pf.getProfileSummary();
        if (profileSummary == null || profileSummary != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Profile Summary", "/profile-summary"));
        }

        //8
        Integer resumeHeadline = pf.getResumeHeadline();
        if (resumeHeadline == null || resumeHeadline != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Resume Headline", "/resume-headline"));
        }

        //9 
        Integer attachedResume = pf.getAttachedResume();
        if (attachedResume == null || attachedResume != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Attached Resume", "/attach-resume"));
        }

        //10
        Integer coverLetter = pf.getCoverLetter();
        if (coverLetter == null || coverLetter != 1) {
            profile.add(new IncompleteProfile(new Date().getTime(), "Cover Letter", "/cover-letter"));
        }

        return profile;
    }

    @GetMapping("/seeker/incomplete-profile")
    public String imcompleteProfile(Model model, Principal principal) {

        List<IncompleteProfile> profile = findIncompleteProfile(principal);
        model.addAttribute("profile", profile);

        return "seeker/incomplete-profile";
    }
}
