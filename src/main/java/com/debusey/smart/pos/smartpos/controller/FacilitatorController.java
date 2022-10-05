/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.FacilitatorResponse;
import com.debusey.smart.pos.smartpos.entity.Facilitators;
import com.debusey.smart.pos.smartpos.service.FacilitatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class FacilitatorController {
    @Autowired
    private FacilitatorsService service;

    @RequestMapping(value = {"/recruiter/facilitors/api", "/facilitor/api"})
    @ResponseBody
    public List<FacilitatorResponse> facilitators() {
        List<Facilitators> list = service.findAll();
        return converted(list);
    }

    @RequestMapping(value = {"/recruiter/facilitors/api/{id}"})
    @ResponseBody
    public FacilitatorResponse facilitators(@PathVariable Integer id) {
        Optional<Facilitators> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid id provided: " + id);
        }
        return converted(opp.get());
    }

    @RequestMapping(value = {"/facilitator-details/{id}"})
    public String facilitator(@PathVariable Integer id, Model model) {

        try {
            Optional<Facilitators> opp = service.findById(id);
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid id provided: " + id);
            } else {
                model.addAttribute("facilitator", opp.get());
            }

            return "facilitator-details";
        } catch (Exception e) {

            return null;

        }
    }


    @RequestMapping(value = {"/recruiter/delete-facilitator/{id}"})
    @ResponseBody
    public ResponseEntity<String> deleteFacilitators(@PathVariable Integer id) {
        try {
            String status = service.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(value = {"/recruiter/facilitors"})
    @ResponseBody
    public ResponseEntity<String> addFacilitators(@RequestParam(required = false) MultipartFile policy, Integer id,
                                                  String fn, String ln, String email, String tel, String title, String skill,
                                                  String experience, String profile) {
        try {
            Facilitators ethics = service.findById(id).orElse(new Facilitators());
            String fileName = null;
            if (policy != null) {
                fileName = JsfUtil.createFileName(policy).getFileName();
                if (fileName != null) {
                    ethics.setFileName(fileName);
                }
            }

            ethics.setContactNumber(tel);
            ethics.setEmail(email);
            ethics.setFirstName(fn);
            ethics.setLastName(ln);
            ethics.setTitle(title);
            ethics.setSkill(skill);
            ethics.setProfile(profile);
            ethics.setExperience(experience);

            String status = service.save(ethics) ? "SUCCESS" : "FAILED";
            if ("SUCCESS".equals(status)) {
                return new ResponseEntity(status, HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest().body(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private List<FacilitatorResponse> converted(List<Facilitators> list) {
        return list.stream().map(n ->
                // id,  firstName,  lastName, contactNumber,  email,  fileName
                new FacilitatorResponse(
                        n.getId(),
                        n.getFirstName(),
                        n.getLastName(),
                        n.getExperience(),
                        n.getContactNumber(),
                        n.getEmail(),
                        fetchPicture(n.getFileName()),
                        n.getTitle(),
                        n.getSkill(),
                        n.getProfile()
                )
        ).collect(Collectors.toList());
    }

    private FacilitatorResponse converted(Facilitators n) {
        return new FacilitatorResponse(
                n.getId(),
                n.getFirstName(),
                n.getLastName(),
                n.getExperience(),
                n.getContactNumber(),
                n.getEmail(),
                fetchPicture(n.getFileName()),
                n.getTitle(),
                n.getSkill(),
                n.getProfile()
        );

    }

    private String fetchPicture(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return "team.jpg";
        }
        return fileName;
    }
}
