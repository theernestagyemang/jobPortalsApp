/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.Mail;
import com.debusey.smart.pos.smartpos.db.TimeAgo;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.service.EmailService;
import com.debusey.smart.pos.smartpos.service.JobAlertService;
import com.debusey.smart.pos.smartpos.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */

@Controller
public class MailSchedulerController {

    @Autowired
    private JobsService service;

    @Autowired
    private JobAlertService alertService;


    @Autowired
    private EmailService emailService;

    @Value("${app.hostName}")
    private String appUrl;

    //@Scheduled(initialDelay = 1000, fixedRate = 86400000)
    public void run() {
        List<Jobs> unsentList = service.findByAlertSent(false);

        if (!unsentList.isEmpty()) {
            if (unsentList.size() > 0) {
                sendEmail(unsentList);
            }
        }
    }


    public boolean sendEmail(List<Jobs> list) {
        try {
            Mail mail = new Mail();
            mail.setFrom("info@xjobs.com.gh");

            List<String> emails = alertService.findByDistictEmails();
            String[] address = JsfUtil.createList(emails);

//            mail.setTo(address);
            mail.setSubject("Daily Job Alert");

            Map model = new HashMap();
            model.put("name", "Hussein");
            model.put("location", "Accra");
            model.put("signature", "Sign");
            model.put("appUrl", appUrl);


            model.put("list", list);
            model.put("timeAgo", new TimeAgo());
            mail.setModel(model);


            emailService.sendEmail(mail);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


}
