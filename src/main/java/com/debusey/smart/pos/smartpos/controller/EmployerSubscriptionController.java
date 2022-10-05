/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.EmployerSubscriptionsDb;
import com.debusey.smart.pos.smartpos.db.EmployerSubscriptionsLinesDb;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.EmployerSubscription;
import com.debusey.smart.pos.smartpos.entity.EmployerSubscriptionLines;
import com.debusey.smart.pos.smartpos.service.EmployerSubscriptionLinesService;
import com.debusey.smart.pos.smartpos.service.EmployerSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class EmployerSubscriptionController {

    @Autowired
    private EmployerSubscriptionService service;

    @Autowired
    private EmployerSubscriptionLinesService lineService;


    @GetMapping("/employer-subscriptions")
    @ResponseBody
    public List<EmployerSubscriptionsDb> subscriptions() {
        List<EmployerSubscription> sublist = service.findAll();
        return converted(sublist);
    }


    @GetMapping("/recruiter/employer-subscriptions")
    public String subscriptions(Model model, Principal principal) {

        return "recruiter/employer-subscriptions";
    }

    @PostMapping("/add-emp-subscriptions")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> subscriptions(@RequestBody EmployerSubscriptionsDb sub) {
        try {

            Employee employee = employee = new Employee();

            List<EmployerSubscriptionLines> lines = new ArrayList<>();
            EmployerSubscription subscription = service.findByCategory(sub.getCategory()).orElse(new EmployerSubscription());
            subscription.setAmount(sub.getAmount());
            subscription.setCategory(sub.getCategory());
            subscription.setCreatedByEmployeeId(employee.getEmployeeid());
            subscription.setTransactionId(JsfUtil.generateSerial());
            subscription.setMessage(sub.getMessage());
            subscription.setCaption(sub.getCaption());

            //subscription.setUnitOfMeasure(unitOfMeasure);
            createLines(lines, sub, employee);
            lines.stream().forEach((item) -> {
                item.setSubscription(subscription);
                item.setTransactionId(item.getTransactionId());
            });
            lineService.saveAll(lines);


            String status = service.save(subscription) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/employer/subscriptions-db")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> subscriptions(@RequestBody EmployerSubscriptionsDb sub, Principal principal) {
        try {

            Employee employee = JsfUtil.currentStaff(principal);
            if (employee == null) {
                employee = new Employee();
            }
            List<EmployerSubscriptionLines> lines = new ArrayList<>();
            EmployerSubscription subscription = service.findByCategory(sub.getCategory()).orElse(new EmployerSubscription());
            subscription.setAmount(sub.getAmount());
            subscription.setCategory(sub.getCategory());
            subscription.setCreatedByEmployeeId(Integer.MAX_VALUE);
            subscription.setTransactionId(JsfUtil.generateSerial());
            subscription.setMessage(sub.getMessage());
            subscription.setCaption(sub.getCaption());
            //subscription.setUnitOfMeasure(unitOfMeasure);
            createLines(lines, sub, employee);
            lines.stream().forEach((item) -> {
                item.setSubscription(subscription);
            });
            lineService.saveAll(lines);


            String status = service.save(subscription) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private List<EmployerSubscriptionsDb> converted(List<EmployerSubscription> list) {
        // id,  category,  transactionId,  amount, List<EmployerSubscriptionsDb> lines
        return list.stream().map((n ->
                new EmployerSubscriptionsDb(
                        n.getId(),
                        n.getCategory(),
                        n.getMessage(),
                        n.getCaption(),
                        n.getTransactionId(),
                        n.getAmount(),
                        findLines(n),
                        n.getHeaderColor(),
                        n.getBodyColor()
                )
        )).collect(Collectors.toList());
    }

    private List<EmployerSubscriptionsLinesDb> findLines(EmployerSubscription sub) {
        List<EmployerSubscriptionLines> list = lineService.findBySubscription(sub);

        //Integer id, String message, String transactionId, String entryDate, EmployerSubscriptionsDb subscription
        return list.stream().map(n ->
                new EmployerSubscriptionsLinesDb(
                        n.getId(),
                        n.getMessage(),
                        n.getTransactionId(),
                        JsfUtil.convertToString(n.getEntryDate(), "dd/MM/yyyy HH:mm:ss"),
                        convertToDb(n.getSubscription())
                )
        ).collect(Collectors.toList());
    }

    private Integer convertToDb(EmployerSubscription subscription) {
        if (subscription == null) {
            return 0;
        }
        return subscription.getId();
    }

    private void createLines(List<EmployerSubscriptionLines> lines, EmployerSubscriptionsDb sub, Employee employee) {

        List<EmployerSubscriptionsLinesDb> ls = sub.getLines();

        ls.stream().forEach((item) -> {
            EmployerSubscriptionLines ln = new EmployerSubscriptionLines();
            ln.setCreatedByEmployeeId(employee.getEmployeeid());
            ln.setEntryDate(LocalDateTime.now());
            ln.setMessage(item.getMessage());

            lines.add(ln);
        });
    }
}
