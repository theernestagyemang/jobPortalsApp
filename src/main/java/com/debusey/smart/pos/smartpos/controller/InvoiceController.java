/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.InvoiceBd;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author Admin
 */

@Controller
public class InvoiceController {

    @Autowired
    public EmployerService emService;
    @Autowired
    public JobSeekerService jService;
    @Autowired
    public SubscriptionService subService;
    @Autowired
    public SubscriptionUsageService usageService;
    @Autowired
    private InvoiceService service;

    @GetMapping("/invoice")
    public String invoice(Model model, Principal principal) {
        return "invoice";
    }

    @PostMapping("/create-invoice")
    @ResponseBody
    public InvoiceBd createInvoice(String sub, Integer empId) {
        try {
            Optional<Subscription> oppSub = subService.findByName(sub);
            if (!oppSub.isPresent()) {
                return new InvoiceBd();
            }

            Subscription subscription = oppSub.get();
            BigDecimal amount = subscription.getAmout();
            BigDecimal cot = subscription.getCot();
            if (cot == null) {
                cot = BigDecimal.ZERO;
            }

            Optional<Employer> oppEmp = emService.findById(empId);
            if (oppEmp.isPresent()) {
                Employer employer = oppEmp.get();
                Invoice invoice = new Invoice();

                Optional<Invoice> oppInv = service.findByEmployerAndSub(employer, sub);
                if (oppInv.isPresent()) {
                    invoice = oppInv.get();
                }
                invoice.setAmount(amount);

                invoice.setConfirmed(false);
                invoice.setCot(cot);
                invoice.setInvoiceType(sub);
                invoice.setEntryDate(new Date());
                invoice.setTransactionDate(new Date());
                invoice.setPaymentStatus("Pending");
                invoice.setEmployerId(employer);
                invoice.setDescription("Payment for " + sub + " Subscription");
                invoice.setOrderId(JsfUtil.generateSerial());


                if (service.save(invoice)) {
                    debitEmployer(employer, subscription);

                    return createdInvoice(invoice);
                }

            }

            return new InvoiceBd();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new InvoiceBd();
    }


    @PostMapping("/create-seeker-invoice")
    @ResponseBody
    public InvoiceBd createSeekerInvoice(String sub, Integer empId) {
        try {
            Optional<Subscription> oppSub = subService.findByName(sub);
            if (!oppSub.isPresent()) {
                return new InvoiceBd();
            }

            Subscription subscription = oppSub.get();
            BigDecimal amount = subscription.getAmout();
            BigDecimal cot = subscription.getCot();
            if (cot == null) {
                cot = BigDecimal.ZERO;
            }

            Optional<JobSeeker> oppEmp = jService.findById(empId);
            if (oppEmp.isPresent()) {
                JobSeeker seeker = oppEmp.get();
                Invoice invoice = new Invoice();

                Optional<Invoice> oppInv = service.findByJobSeekerAndSub(seeker, sub);
                if (oppInv.isPresent()) {
                    invoice = oppInv.get();
                }
                invoice.setAmount(amount);

                invoice.setConfirmed(false);
                invoice.setCot(cot);
                invoice.setInvoiceType(sub);
                invoice.setEntryDate(new Date());
                invoice.setTransactionDate(new Date());
                invoice.setPaymentStatus("Pending");
                invoice.setSeekerId(seeker);
                invoice.setDescription("Payment for " + sub + " Subscription");
                invoice.setOrderId(JsfUtil.generateSerial());


                if (service.save(invoice)) {
                    debitJobSeeker(seeker, subscription);

                    return createdSeekerInvoice(invoice);
                }

            }

            return new InvoiceBd();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new InvoiceBd();
    }


    @GetMapping("/find-invoice/{id}")
    @ResponseBody
    public Invoice findInvoice(@PathVariable Integer id) {
        return service.findById(id).orElse(new Invoice());
    }

    @GetMapping("/delete-invoice/{id}")
    @ResponseBody
    public boolean deleteInvoice(@PathVariable Integer id) {
        return service.deleteById(id);
    }

    private InvoiceBd createdSeekerInvoice(Invoice invoice) {
        String email = null;
        String name = null;
        Integer employerId = null;
        String paymentStatus = invoice.getPaymentStatus();
        Date transactionDate = invoice.getTransactionDate();
        BigDecimal amount = invoice.getAmount();
        BigDecimal cot = invoice.getCot();
        String orderId = invoice.getOrderId();
        JobSeeker employer = invoice.getSeekerId();
        String description = invoice.getDescription();
        if (employer != null) {
            employerId = employer.getId();
            email = employer.getEmail();
            name = employer.getFullName();
        }


        BigDecimal total = addTotals(cot, amount);
        String transDate = JsfUtil.convertFromDate(transactionDate);

        return new InvoiceBd(paymentStatus, transactionDate, amount, cot, orderId, employerId, email, name, total, transDate, description);
    }

    private InvoiceBd createdInvoice(Invoice invoice) {
        String email = null;
        String name = null;
        Integer employerId = null;
        String paymentStatus = invoice.getPaymentStatus();
        Date transactionDate = invoice.getTransactionDate();
        BigDecimal amount = invoice.getAmount();
        BigDecimal cot = invoice.getCot();
        String orderId = invoice.getOrderId();
        Employer employer = invoice.getEmployerId();
        String description = invoice.getDescription();
        if (employer != null) {
            employerId = employer.getId();
            email = employer.getEmail();
            name = employer.getCompanyName();
        }


        BigDecimal total = addTotals(cot, amount);
        String transDate = JsfUtil.convertFromDate(transactionDate);

        return new InvoiceBd(paymentStatus, transactionDate, amount, cot, orderId, employerId, email, name, total, transDate, description);
    }

    private BigDecimal addTotals(BigDecimal cot, BigDecimal amount) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        if (cot == null) {
            cot = BigDecimal.ZERO;
        }

        return amount.add(cot);
    }

    public void debitEmployer(Employer employer, Subscription sub) {
        try {
            if (sub == null) {
                return;
            }
            Integer cv = sub.getCvCount();
            if (cv == null) {
                cv = 0;
            }
            SubscriptionUsage usage = new SubscriptionUsage();
            usage.setUsageDate(LocalDate.now());
            usage.setEntryDate(LocalDateTime.now());
            usage.setDebit(BigDecimal.valueOf(cv));
            usage.setCredit(BigDecimal.ZERO);
            usage.setEmployerId(employer.getId());
            usage.setSubscriptionId(sub.getId());
            usage.setTransactionId(JsfUtil.generateSerial());


            usageService.save(usage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void debitJobSeeker(JobSeeker seeker, Subscription sub) {
        try {
            if (sub == null) {
                return;
            }
            Integer cv = sub.getCvCount();
            if (cv == null) {
                cv = 0;
            }
            SubscriptionUsage usage = new SubscriptionUsage();
            usage.setUsageDate(LocalDate.now());
            usage.setEntryDate(LocalDateTime.now());
            usage.setDebit(BigDecimal.valueOf(cv));
            usage.setCredit(BigDecimal.ZERO);
            usage.setJobSeekerId(seeker.getId());
            usage.setSubscriptionId(sub.getId());
            usage.setTransactionId(JsfUtil.generateSerial());


            usageService.save(usage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/pay-invoice/{invoiceId}")
    @ResponseBody
    public String payInvoice(@PathVariable Integer invoiceId, @RequestParam(defaultValue = "Employer") String type, BigDecimal amt) {
        Invoice invoice = findInvoice(invoiceId);
        invoice.setPaymentStatus("Paid");
        invoice.setTransactionDate(new Date());

        return "SUCCESS";
    }

}
