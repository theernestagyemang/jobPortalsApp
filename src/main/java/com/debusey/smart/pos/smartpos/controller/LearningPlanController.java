/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.LearningPlanResponse;
import com.debusey.smart.pos.smartpos.entity.LearningPlan;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author AlhassanHussein
 */

@Controller
public class LearningPlanController {

    @Autowired
    public LearningPlanService service;


    @GetMapping("/recruiter/learning-plan")
    public String training(Model model, Principal principal) {

        List<LearningPlan> tx = service.findAll();
        model.addAttribute("tx", tx);

        return "recruiter/learning-plan";
    }

    @GetMapping("/recruiter/learning-plan/api")
    @ResponseBody
    public List<LearningPlan> learningPlanApi(Model model, Principal principal) {
        return service.findAll();
    }

    @GetMapping("/recruiter/find-learning-plan/{id}")
    @ResponseBody
    public LearningPlan findLearningPlan(@PathVariable Integer id) {
        Optional<LearningPlan> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        return opp.get();
    }

    @GetMapping("/recruiter/delete-learning-plan/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            String status = service.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recruiter/learning-plan")
    @ResponseBody
    public ResponseEntity<String> addSchedule(@RequestBody LearningPlanResponse plan, Principal principal) {
        try {

            Users user = JsfUtil.findOnline(principal);
            LearningPlan trx = service.findByName(plan.getName()).orElse(new LearningPlan());
            trx.setEnteredBy(user.getUsername());


            trx.setDescription(plan.getDesc());
            trx.setDuration(plan.getDuration());
            trx.setEnteredBy(user.getUsername());
            trx.setName(plan.getName());
            trx.setPrice(plan.getPrice());
            trx.setTransactionId(JsfUtil.generateSerial());
            trx.setColor(plan.getColor());


            String status = service.save(trx) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
