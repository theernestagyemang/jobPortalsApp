/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.SubscriptionUsage;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.SubscriptionUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class SubscriptionUsageController {


    @Autowired
    private SubscriptionUsageService service;

    @GetMapping("/add-usage")
    @ResponseBody
    public boolean addUsage(Principal principal, Integer empId, Integer seekerId) {
        try {

            Users user = JsfUtil.findOnline(principal);

            String type = user.getUserType();
            if (type == null) {
                return false;
            }
            if (!type.equalsIgnoreCase("Company")) {
                return false;
            }
            SubscriptionUsage usage = new SubscriptionUsage();
            Optional<SubscriptionUsage> opp = service.findByEmployerAndJobSeeker(seekerId, empId);
            if (opp.isPresent()) {
                usage = opp.get();
            }


            usage.setEmployerId(empId);
            usage.setJobSeekerId(seekerId);
            usage.setEntryDate(LocalDateTime.now());
            usage.setTransactionId(JsfUtil.generateSerial());
            usage.setUsageDate(LocalDate.now());

            return service.save(usage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
