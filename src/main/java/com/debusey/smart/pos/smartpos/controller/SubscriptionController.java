/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.api.SubscriptionRetObject;
import com.debusey.smart.pos.smartpos.db.ImageUtil;
import com.debusey.smart.pos.smartpos.db.SubscriptionDb;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */

@Controller
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    @Autowired
    private AssessmentLinesService assessmentService;

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private EmployerService empSrvice;

    @Autowired
    private JobSeekerService jservice;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployerSubscriptionService subscriptionService;

    @Value("${app.gateway.url}")
    private String gatewayUrl;

    @Value("${app.gateway.token}")
    private String token;


    @RequestMapping(value = {"/subscription"})
    public String subscriptions(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        String view = "subscription";

        String userType = user.getUserType();

        if (userType.equals("Staff")) {
            createStaffModel(model, user);

        } else {
            createEmployerModel(model, user);
            view = "subscription-list";
        }


        //List<Subscription> list = service.findAll();

        List<Subscription> subList = service.findAll();

        Subscription basic = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Basic"))
                .findAny()
                .orElse(new Subscription());

        Subscription extended = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Premium"))
                .findAny()
                .orElse(new Subscription());

        Subscription prof = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Professional"))
                .findAny()
                .orElse(new Subscription());


        model.addAttribute("list", subList);
        model.addAttribute("basic", basic);
        model.addAttribute("extended", extended);
        model.addAttribute("prof", prof);
        model.addAttribute("imgUtil", new ImageUtil());

        List<String> basicItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> basicMsg = new ArrayList<>();
        basicItems.forEach(basicMsg::add);
        basicMsg.add("" + basic.getCvCount());

        List<String> proItems = JsfUtil.createListFromString(prof.getMessage());
        List<String> proMsg = new ArrayList<>();
        proItems.forEach(proMsg::add);
        proMsg.add("" + prof.getCvCount());


        List<String> extItems = JsfUtil.createListFromString(extended.getMessage());
        List<String> extendedMsg = new ArrayList<>();
        extItems.forEach(extendedMsg::add);
        extendedMsg.add("" + extended.getCvCount());

        model.addAttribute("basicMsg", basicMsg);
        model.addAttribute("proMsg", proMsg);
        model.addAttribute("extendedMsg", extendedMsg);


        return view;
    }


//    @RequestMapping(value = {"/subscription-list"})
//    public String subscriptions2222(Model model){
//        
//        
//        List<Subscription> subList = service.findAll();
//        JsfUtil.loadSubscription(model, subList, 0);
//        
//        return "subscription-list";
//    }

    @RequestMapping(value = {"/employer-subscription", "/subscription-list"})
    public String employerSuscription(Model model) {
        return "redirect:/employer-package/subscription";
    }

    @RequestMapping(value = {"/employer/subscription", "/employer/subscription-list"})
    public String employerSuscription2(Model model) {
        return "employer/employer-subscriptions";
    }

    @RequestMapping(value = {"/seeker/subscription", "/seeker/subscription-list"})
    public String subscriptionsList(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        Optional<JobSeeker> opp = jservice.findByEmail(user.getUsername());
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();
            model.addAttribute("seeker", seeker);
        } else {
            model.addAttribute("seeker", new JobSeeker());
        }

        List<Subscription> subList = service.findAll();
        JsfUtil.loadSubscription(model, subList, 1);

        model.addAttribute("imgUtil", new ImageUtil());


        return "seeker/subscription-list";
    }

    @GetMapping("/findJobPrefSubscription")
    @ResponseBody
    public SubscriptionDb findSubscription(Principal principal) {
        try {
            Users onlineUser = JsfUtil.findOnline(principal);
            if (onlineUser == null) {
                return new SubscriptionDb(BigDecimal.ZERO, BigDecimal.ZERO, null, new Date(), null, 0, null);
            }
            String username = onlineUser.getUsername();
            Optional<JobSeeker> oppSeeker = jservice.findByEmail(username);
            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();
                Subscription subscription = seeker.getSubscriptionId();

                return createDb(subscription);
            }
            return new SubscriptionDb(BigDecimal.ZERO, BigDecimal.ZERO, null, new Date(), null, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SubscriptionDb();
        }
    }

    @GetMapping("/subscriptions")
    public String subscriptions2(Model model) {


        List<Subscription> subList = service.findAll();

        Subscription basic = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Basic"))
                .findAny()
                .orElse(new Subscription());

        Subscription extended = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Premium"))
                .findAny()
                .orElse(new Subscription());

        Subscription prof = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Professional"))
                .findAny()
                .orElse(new Subscription());


        model.addAttribute("list", subList);
        model.addAttribute("basic", basic);
        model.addAttribute("extended", extended);
        model.addAttribute("prof", prof);
        model.addAttribute("imgUtil", new ImageUtil());

        List<String> basicItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> basicMsg = new ArrayList<>();
        basicItems.forEach(basicMsg::add);
        basicMsg.add("CV Download: " + basic.getCvCount());

        List<String> proItems = JsfUtil.createListFromString(prof.getMessage());
        List<String> proMsg = new ArrayList<>();
        proItems.forEach(proMsg::add);
        proMsg.add("CV Download: " + prof.getCvCount());


        List<String> extItems = JsfUtil.createListFromString(extended.getMessage());
        List<String> extendedMsg = new ArrayList<>();
        extItems.forEach(extendedMsg::add);
        extendedMsg.add("CV Download: " + extended.getCvCount());

        model.addAttribute("basicMsg", basicMsg);
        model.addAttribute("proMsg", proMsg);
        model.addAttribute("extendedMsg", extendedMsg);


        return "subscriptions";
    }

    @GetMapping("/app-success")
    public String appSuccess(Model model, String code) {
        return "success";
    }

    @GetMapping("/subscribe/{id}")
    @ResponseBody
    public ResponseEntity<EmployerSubscription> addEmployerSubscription(@PathVariable Integer id, Principal principal) {

        try {

            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();

            if (user == null) {
                throw new BeanNotFoundException("Not logged In: ");
            }

            String email = user.getUsername();
            Optional<Employer> empOpp = empSrvice.findByEmail(email);
            if (!empOpp.isPresent()) {
                throw new BeanNotFoundException("Invalid employer credentials: " + email);
            }

            Optional<EmployerSubscription> oppSub = subscriptionService.findById(id);
            if (!oppSub.isPresent()) {
                throw new BeanNotFoundException("Invalid subscription ID: " + id);
            }

            EmployerSubscription subscription = oppSub.get();
            Employer employer = empOpp.get();
            employer.setEmployerSubscription(subscription);

            boolean status = empSrvice.save(employer);
            if (status) {
                return new ResponseEntity(subscription, HttpStatus.OK);
            } else {
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            }


        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/seeker-basic-subscribe/{id}")
    @ResponseBody
    public String basicSubscription(@PathVariable Integer id, Principal principal) {
        try {
            if (id == null) {
                return "INVALID-SUBSCRIPTION";
            }

            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            Users user = loginedUser.getUser();
            if (user == null) {
                return "INVALID-USER";
            }
            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                return "INVALID-USER";
            }

            Optional<Subscription> oppSub = service.findById(id);
            if (!oppSub.isPresent()) {
                return "INVALID-SUBSCRIPTION";
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription seekerSub = seeker.getSubscriptionId();

            if (seekerSub == null) {
                seeker.setSubscriptionId(subscription);
                return jservice.save(seeker) ? "SUCCESS" : "FAILED";
            }
            if (subscription.equals(seekerSub)) {
                return "ALREADY_SUBSCRIBED";
            }

            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "FAILED";
    }

    @PostMapping("/jobseeker-basic-subscribe/{transactionId}")
    @ResponseBody
    public String basicSubscription2(@PathVariable String transactionId, Principal principal) {
        try {
            if (transactionId == null) {
                return "INVALID-SUBSCRIPTION";
            }

            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            Users user = loginedUser.getUser();
            if (user == null) {
                return "INVALID-USER";
            }
            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                return "INVALID-USER";
            }

            Optional<Subscription> oppSub = service.findByTransactionId(transactionId);
            if (!oppSub.isPresent()) {
                return "INVALID-SUBSCRIPTION";
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription seekerSub = seeker.getSubscriptionId();

            if (seekerSub == null) {
                seeker.setSubscriptionId(subscription);
                return jservice.save(seeker) ? "SUCCESS" : "FAILED";
            }
            if (subscription.equals(seekerSub)) {
                return "ALREADY_SUBSCRIBED";
            }

            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "FAILED";
    }

    @GetMapping("/subscription-page")
    public String getSubscriptionForm() {
        return "paystack-form";
    }

    @PostMapping("/seeker-subscribe/{id}")
    @ResponseBody
    public SubscriptionRetObject addSeekerSub(@PathVariable Integer id, Principal principal) {
        Invoice invoice = null;
        Date today = new Date();
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            if (user == null) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<Subscription> oppSub = service.findById(id);
            if (!oppSub.isPresent()) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription seekerSub = seeker.getSubscriptionId();
            if (seekerSub == null || !subscription.equals(seekerSub)) {
                seeker.setSubscriptionId(subscription);
                invoice = createInvoice(seeker, subscription);
                jservice.save(seeker);

            }

            return createSubscriptionRetObject(seeker, subscription); // Invoice the applicant

        } catch (Exception e) {
            e.printStackTrace();
            return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

    }


    @PostMapping("/jobseeker-subscribe/{transactionId}")
    @ResponseBody
    public SubscriptionRetObject addSeekerSub2(@PathVariable String transactionId, Principal principal) {
        Invoice invoice = null;
        Date today = new Date();
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            if (user == null) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<Subscription> oppSub = service.findByTransactionId(transactionId);
            if (!oppSub.isPresent()) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription seekerSub = seeker.getSubscriptionId();
            //  System.out.println("seekerSub "+seekerSub);
            if (seekerSub == null || !subscription.equals(seekerSub)) {
                seeker.setSubscriptionId(subscription);
                invoice = createInvoice(seeker, subscription);
                jservice.save(seeker);
                //
                /// System.out.println("Saved New Sub ");
            }
            return createSubscriptionRetObject(seeker, subscription); // Invoice the applicant

        } catch (Exception e) {
            e.printStackTrace();
            return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

    }


    @GetMapping("/subscribe-by-email/{id}")
    @ResponseBody
    public SubscriptionRetObject subscribeByEmail(@PathVariable Integer id, String email, @RequestParam(defaultValue = "0") String option) {

        switch (option) {
            case "1":
                return employerSubscription(id, email);
            case "2":
                return learningPlanSubscription(id, email);

            case "3":
                return assessmentSubscription(id, email);
            default:
                return jobSeekerSubscription(id, email);
        }
    }

    @PostMapping("/subscription")
    @ResponseBody
    public boolean addTestimony(String name, BigDecimal amout, String enteredBy) {

        try {

            Subscription tx = new Subscription();
            tx.setAmout(amout);
            tx.setEntryDate(new Date());
            tx.setName(name);
            tx.setEnteredBy(enteredBy);
            tx.setTransactionId(JsfUtil.generateSerial());


            return service.save(tx);


        } catch (Exception e) {
            return false;
        }
    }

//subscription name amout message enteredBy

    @PostMapping("/add-subscription")
    @ResponseBody
    public boolean addSubscription(String name, BigDecimal amount, String message, String cv, Principal principal) {
        Integer noOfCv = 0;
        try {
            if (cv != null) {
                if (!cv.isEmpty()) {
                    try {
                        noOfCv = Integer.parseInt(cv);
                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                    }

                }
            }

            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            String username = null;
            if (user != null) {
                username = user.getUsername();
            }

            Optional<Subscription> opp = service.findByName(name);
            Subscription tx = opp.orElse(new Subscription());
            tx.setAmout(amount);
            tx.setEntryDate(new Date());
            tx.setName(name);
            tx.setEnteredBy(username);
            tx.setMessage(message);
            tx.setCvCount(noOfCv);
            tx.setTransactionId(JsfUtil.generateSerial());


            return service.save(tx);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createEmployerModel(Model model, Users user) {
        Optional<Employer> opp = empSrvice.findByEmail(user.getUsername());
        if (opp.isPresent()) {

            Employer employer = opp.get();
            model.addAttribute("employer", employer);
        } else {
            model.addAttribute("employer", new Employer());
        }

    }

    private void createStaffModel(Model model, Users user) {
        model.addAttribute("emp", user.getStaffId());
    }

    private SubscriptionDb createDb(Subscription sub) {
        if (sub == null) {
            return new SubscriptionDb(BigDecimal.ZERO, BigDecimal.ZERO, null, new Date(), null, 0, null);
        }
        SubscriptionDb db = new SubscriptionDb();
        db.setAmout(sub.getAmout());
        db.setCot(sub.getCot());
        db.setCvCount(sub.getCvCount());
        db.setEnteredBy(sub.getEnteredBy());
        db.setEntryDate(sub.getEntryDate());
        db.setId(sub.getId());
        db.setMessage(sub.getMessage());
        db.setName(sub.getName());

        return db;
    }

    private Invoice createInvoice(JobSeeker seeker, Subscription subscription) {
        String orderId = JsfUtil.generateSerial();
        BigDecimal amt = subscription.getAmout();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();
        Invoice invoice = new Invoice();
        invoice.setAmount(amt);
        invoice.setCot(cot);
        invoice.setDescription(description);
        invoice.setEntryDate(invoiceDate);
        invoice.setInvoiceType(subscription.getName());
        invoice.setPaymentStatus("Pending");
        invoice.setSeekerId(seeker);
        invoice.setTransactionDate(new Date());
        invoice.setOrderId(orderId);

        invoiceService.save(invoice);

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
        obj.setTotal(total);

        return invoice;
    }

    private Invoice createInvoice(JobSeeker seeker, AssessmentLines subscription) {
        String orderId = JsfUtil.generateSerial();
        BigDecimal amt = subscription.getPrice();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
//        BigDecimal cot = subscription.getCot();
//        if(cot ==null){
//            cot =BigDecimal.ZERO;
//        }

//        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();
        Invoice invoice = new Invoice();
        invoice.setAmount(amt);
//        invoice.setCot(cot);
        invoice.setDescription(description);
        invoice.setEntryDate(invoiceDate);
        invoice.setInvoiceType(subscription.getName());
        invoice.setPaymentStatus("Pending");
        invoice.setSeekerId(seeker);
        invoice.setTransactionDate(new Date());
        invoice.setOrderId(orderId);

        invoiceService.save(invoice);

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
//        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
//        obj.setTotal(total);

        return invoice;
    }

    private Invoice createInvoice(JobSeeker seeker, LearningPlan subscription) {
        String orderId = JsfUtil.generateSerial();
        BigDecimal amt = subscription.getPrice();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();
        Invoice invoice = new Invoice();
        invoice.setAmount(amt);
        invoice.setCot(cot);
        invoice.setDescription(description);
        invoice.setEntryDate(invoiceDate);
        invoice.setInvoiceType(subscription.getName());
        invoice.setPaymentStatus("Pending");
        invoice.setSeekerId(seeker);
        invoice.setTransactionDate(new Date());
        invoice.setOrderId(orderId);

        invoiceService.save(invoice);

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
        obj.setTotal(total);

        return invoice;
    }

    private Invoice createInvoice(Employer employer, Subscription subscription) {
        String orderId = JsfUtil.generateSerial();
        BigDecimal amt = subscription.getAmout();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();
        Invoice invoice = new Invoice();
        invoice.setAmount(amt);
        invoice.setCot(cot);
        invoice.setDescription(description);
        invoice.setEntryDate(invoiceDate);
        invoice.setInvoiceType(subscription.getName());
        invoice.setPaymentStatus("Pending");
        invoice.setEmployerId(employer);
        invoice.setTransactionDate(new Date());
        invoice.setOrderId(orderId);

        invoiceService.save(invoice);

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(employer.getEmail());
        obj.setSeekerName(employer.getCompanyName());
        obj.setTotal(total);

        return invoice;
    }

    private SubscriptionRetObject createSubscriptionRetObject(JobSeeker seeker, Subscription subscription) {
        String orderId = JsfUtil.getInvoice();
        BigDecimal amt = subscription.getAmout();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getCaption();

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
        obj.setTotal(total);
        obj.setIsConfirmed(true);

        return obj;
    }

    private SubscriptionRetObject createSubscriptionRetObject(JobSeeker seeker, AssessmentLines subscription) {
        String orderId = JsfUtil.getInvoice();
        BigDecimal amt = subscription.getPrice();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }

        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
//        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
//        obj.setTotal(total);
        obj.setIsConfirmed(true);

        return obj;
    }

    private SubscriptionRetObject createSubscriptionRetObject(JobSeeker seeker, LearningPlan subscription) {
        String orderId = JsfUtil.getInvoice();
        BigDecimal amt = subscription.getPrice();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getName();

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getFullName());
        obj.setTotal(total);
        obj.setIsConfirmed(true);

        return obj;
    }

    private SubscriptionRetObject createSubscriptionRetObject(Employer seeker, Subscription subscription) {
        String orderId = JsfUtil.getInvoice();
        BigDecimal amt = subscription.getAmout();
        if (amt == null) {
            amt = BigDecimal.ZERO;
        }
        BigDecimal cot = subscription.getCot();
        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        BigDecimal total = amt.add(cot);
        Date invoiceDate = new Date();
        String description = "Subscription for " + subscription.getCaption();

        SubscriptionRetObject obj = new SubscriptionRetObject();
        obj.setAmt(amt);
        obj.setCot(cot);
        obj.setDescription(description);
        obj.setInvoiceDate(invoiceDate);
        obj.setInvoiceNumber(orderId);
        obj.setInvoiceStatus("Pending");
        obj.setSeekerEmail(seeker.getEmail());
        obj.setSeekerName(seeker.getCompanyName());
        obj.setTotal(total);
        obj.setIsConfirmed(true);
        obj.setStrInvoiceDate(JsfUtil.convertFromDate(invoiceDate));

        return obj;
    }

    private SubscriptionRetObject jobSeekerSubscription(Integer id, String email) {
        Invoice invoice = null;
        Date today = new Date();
        try {

            if (email == null) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
                //return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<Subscription> oppSub = service.findById(id);
            if (!oppSub.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription seekerSub = seeker.getSubscriptionId();

            if (seekerSub == null || !subscription.equals(seekerSub)) {
                seeker.setSubscriptionId(subscription);
                invoice = createInvoice(seeker, subscription);

                if (jservice.save(seeker)) {
                    Users user = findUserByEmail(email);
                    if (user != null) {
                        user.setSubscribed(true);
                        userService.updateUsers(user);
                    }
                }
            }

            return createSubscriptionRetObject(seeker, subscription); // Invoice the applicant

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Email: " + email + " not Found");
        }

    }

    //assessmentSubscription
    private SubscriptionRetObject assessmentSubscription(Integer id, String email) {
        Invoice invoice = null;
        Date today = new Date();
        try {

            if (email == null) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
                //return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<AssessmentLines> oppSub = assessmentService.findById(id);
            if (!oppSub.isPresent()) {
                throw new BeanNotFoundException("Invalid id for assessment " + id);
            }

            JobSeeker seeker = oppSeeker.get();
            AssessmentLines subscription = oppSub.get();

            AssessmentLines seekerSub = seeker.getAssessmentLines();

            if (seekerSub == null || !subscription.equals(seekerSub)) {
                seeker.setAssessmentLines(subscription);
                invoice = createInvoice(seeker, subscription);

                if (jservice.save(seeker)) {
                    Users user = findUserByEmail(email);
                    if (user != null) {
                        user.setSubscribed(true);
                        userService.updateUsers(user);
                    }
                }
            }

            return createSubscriptionRetObject(seeker, subscription); // Invoice the applicant

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Email: " + email + " not Found");
        }

    }

    private SubscriptionRetObject learningPlanSubscription(Integer id, String email) {
        Invoice invoice = null;
        Date today = new Date();
        try {

            if (email == null) {
                return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<JobSeeker> oppSeeker = jservice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
                //return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<LearningPlan> oppSub = learningPlanService.findById(id);
            if (!oppSub.isPresent()) {
                throw new BeanNotFoundException("Invalid Id for learning plan");
            }

            JobSeeker seeker = oppSeeker.get();
            LearningPlan subscription = oppSub.get();

            LearningPlan seekerSub = seeker.getLearningPlan();

            if (seekerSub == null || !subscription.equals(seekerSub)) {
                seeker.setLearningPlan(subscription);
                invoice = createInvoice(seeker, subscription);

                if (jservice.save(seeker)) {
                    Users user = findUserByEmail(email);
                    if (user != null) {
                        user.setSubscribed(true);
                        userService.updateUsers(user);
                    }
                }
            }

            return createSubscriptionRetObject(seeker, subscription); // Invoice the applicant

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Email: " + email + " not Found");
        }

    }


    private Users findUserByEmail(String email) {
        try {
            return userService.findByEmail(email).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private SubscriptionRetObject employerSubscription(Integer id, String email) {
        Invoice invoice = null;

        try {
            if (email == null) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
            }

            Optional<Employer> oppSeeker = empSrvice.findByEmail(email);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
                //return new SubscriptionRetObject("", "", today, "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            }

            Optional<Subscription> oppSub = service.findById(id);
            if (!oppSub.isPresent()) {
                throw new BeanNotFoundException("Email: " + email + " not Found");
            }

            Employer employer = oppSeeker.get();
            Subscription subscription = oppSub.get();

            Subscription employerSub = employer.getSubscriptionId();

            if (employerSub == null || !subscription.equals(employerSub)) {
                employer.setSubscriptionId(subscription);
                invoice = createInvoice(employer, subscription);

                if (empSrvice.save(employer)) {
                    Users user = findUserByEmail(email);
                    if (user != null) {
                        user.setSubscribed(true);
                        userService.updateUsers(user);
                    }
                }
            }

            return createSubscriptionRetObject(employer, subscription); // Invoice the applicant

        } catch (BeanNotFoundException e) {

            throw new BeanNotFoundException("Email: " + email + " not Found");
        }

    }


    @GetMapping("/admin/employer-subscriptions")
    public String employerSubscription(Model model, Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("user", user);


        List<Subscription> subList = service.findAll();
        JsfUtil.loadSubscription(model, subList, 0);

        return "admin/employer-subscriptions";
    }

    @GetMapping("/admin/candidate-subscriptions")
    public String candidateSubscription(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("user", user);
        List<Subscription> subList = service.findAll();

        JsfUtil.loadSubscription(model, subList, 1);

        return "admin/candidate-subscriptions";
    }

    @PostMapping("/modify-subscription/{id}")
    @ResponseBody
    public String modifySubscription(@PathVariable Integer id, String message, double price, Integer cvcount, String name, Integer jbcount, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            if (name == null) {
                return "INVALID-NAME";
            }
            Subscription subscription = service.findById(id).orElse(new Subscription());
//          

            subscription.setMessage(message);
            subscription.setAmout(BigDecimal.valueOf(price));
            subscription.setCvCount(cvcount);
            subscription.setJobprefCount(jbcount);
            subscription.setCot(BigDecimal.ZERO);
            subscription.setEntryDate(new Date());
            subscription.setTransactionId(JsfUtil.generateSerial());
            subscription.setUnlimited(false);
            subscription.setName(name);

            return service.save(subscription) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ERROR";
    }


    @GetMapping("/find-subscription-by-id/{id}")
    @ResponseBody
    public SubscriptionDb findSubcriptionById(@PathVariable Integer id, Principal principal) {
        Optional<Subscription> oppsu = service.findById(id);
        if (!oppsu.isPresent()) {
            throw new BeanNotFoundException("Could not find ");
        }
        Subscription su = oppsu.get();
        return new SubscriptionDb(
                su.getAmout(),
                su.getCot(),
                su.getEnteredBy(),
                su.getEntryDate(),
                su.getName(),
                su.getCvCount(),
                su.getMessage(),
                su.getJobprefCount(),
                su.getId()
        );
    }

    @GetMapping("/create-payment")
    public String createPaymen(Model model, BigDecimal amount, String subscription, HttpServletRequest request) {

        try {


            String servername = request.getServerName();
            String schema = request.getScheme();
            Integer port = request.getServerPort();


            String redirectUrl = schema + "://" + servername + ":" + port + "/subscription-list";


            String backUrl = redirectUrl;
            String ref = JsfUtil.getInvoice();
            String uniqueRef = "0";
            String serviceDescription = "Being payment of " + subscription;

            LocalDate today = LocalDate.now();

            String serviceDate = today.toString();//"yyyy-MM-dd HH:mm:ss"
            //String token = "9F416C11-127B-4DE2-AC7F-D5710E4C5E0A";


            String transToken = JsfUtil.sendPayment(gatewayUrl, token, amount, ref, redirectUrl, backUrl, uniqueRef, serviceDescription, serviceDate, "GHS");
            //return "redirect:https://secure.3gdirectpay.com/payv2.php?ID=9F416C11-127B-4DE2-AC7F-D5710E4C5E0A";

            return "redirect:https://secure.3gdirectpay.com/payv2.php?ID=" + transToken;

            // return "redirect:"+paymentUrl+"?ID=" +transToken;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/subscription-list";

    }
}
