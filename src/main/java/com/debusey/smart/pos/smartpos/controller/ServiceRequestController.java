/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.*;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.CompanyService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import com.debusey.smart.pos.smartpos.service.ServiceRequestService;
import com.debusey.smart.pos.smartpos.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Admin
 */

@Controller
public class ServiceRequestController {

    @Autowired
    private CompanyService companyservice;

    @Autowired
    private JavaMailSender emailService;

    @Autowired
    private ServiceRequestService service;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Value("${spring.mail.username}")
    private String appTitle;

    @Value("${app.hostName}")
    private String appUrl;

    @GetMapping("/resume-request")
    public String jobAlers(Model model) {

        List<ServiceRequest> list = service.findAll();
        model.addAttribute("list", list);

        return "resume-request";

    }

    @GetMapping("/recruiter/service/requested")
    @ResponseBody
    public List<ServiceRequest>  findAllServiceRequested() {
        return service.findAll();
    }

    @PostMapping("/seeker/service-transaction")
    @ResponseBody
    public ResponseEntity<String> createAlert(@RequestBody AssessmentTransactionResponse currentTransaction, Principal principal) {

        try {
            ServiceRequest serviceRequest = new ServiceRequest();
            Users user = JsfUtil.findOnline(principal);
            ServiceType serviceType = serviceTypeService.findById(
                  Integer.parseInt(currentTransaction.getAssessmentLine())
            ).get();

            if (user != null){

                serviceRequest.setRequestedBy(user);
                serviceRequest.setAmount(BigDecimal.valueOf(currentTransaction.getAmount()));
                serviceRequest.setServiceType(serviceType);
                serviceRequest.setTransactionId(currentTransaction.getTransactionId());
                serviceRequest.setEntryDate(new Date());
                serviceRequest.setWorkStatus("Pending");
                serviceRequest.setStatus("Not Issued");
                String result = service.save(serviceRequest) ? "SUCCESS" : "FAILED";
                if (result.equals("SUCCESS")) {
                    sendNotification(serviceRequest);
                }
                return new ResponseEntity(result, HttpStatus.OK);
            }
            return new ResponseEntity("No user found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/create-cv-request")
//    @ResponseBody
//    public ResponseEntity<String> createAlert(ServiceRequestHelper serviceRequestHelper, Principal principal) {
//
//        try {
//            ServiceRequest serviceRequest = new ServiceRequest();
//            Users user = JsfUtil.findOnline(principal);
//            ServiceType serviceType = serviceTypeService.findById(serviceRequestHelper.getServiceTypeId()).get();
//
//            if (user != null){
//                serviceRequest.setRequestedBy(user);
//                serviceRequest.setAmount(serviceRequestHelper.getAmount());
//                serviceRequest.setServiceType(serviceType);
//                serviceRequest.setTransactionId(serviceRequestHelper.getTransactionId());
//                serviceRequest.setEntryDate(new Date());
//                serviceRequest.setWorkStatus("Pending");
//                String result = service.save(serviceRequest) ? "SUCCESS" : "FAILED";
//                if (result.equals("SUCCESS")) {
//                    sendNotification(serviceRequest);
//                }
//                return new ResponseEntity(result, HttpStatus.OK);
//            }
//            return new ResponseEntity("No user found", HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


    @GetMapping("/resume-request-ls")
    public String resumeReqList(Model model, Principal principal,
                                @RequestParam(required = false, defaultValue = "New") String status,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "5") Integer max) {

        if (page == 0) {
            page = 1;
        }
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();

            if (user != null) {
                Employee employee = user.getStaffId();
                model.addAttribute("emp", employee);
            } else {
                model.addAttribute("emp", new Employee());
            }

            model.addAttribute("user", user);
            model.addAttribute("imgUtil", new ImageUtil());


            Page<ServiceRequest> pages = service.findByWorkStatus(status, page, max);
            List<ServiceRequest> list = pages.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("list", list);
            model.addAttribute("url", "resume-request-ls");


            return "resume-request-ls";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "resume-request-ls";
    }


    @GetMapping("/recruiter/resume-request")
    public String resumeRequest(Model model) {
        return "recruiter/resume-request";
    }

    @GetMapping("/recruiter/interview-request")
    public String interviewRequest(Model model) {
        return "recruiter/interview-grooming";
    }

    @GetMapping("/recruiter/resume-request-api")
    @ResponseBody
    public ServiceRequestHeader resumeRequestApi(Principal principal,
                                                 @RequestParam(required = false, defaultValue = "New") String status,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "5") Integer max) {

        Page<ServiceRequest> pages = service.findAll(page, max);
        List<ServiceRequest> list2 = pages.getContent();

        List<ServiceRequestLines> list = convertedToList(list2);

        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();

        return new ServiceRequestHeader(list, numberOfElements, totalPages, page, max);
    }

    @GetMapping("/deleteRequest/{id}")
    @ResponseBody
    public boolean deleteRequest(@PathVariable Integer id) {
        return service.deleteById(id);
    }

    @GetMapping("/findRequest/{id}")
    @ResponseBody
    public ServiceRequest findRequest(@PathVariable Integer id) {
        return service.findById(id).orElse(new ServiceRequest());
    }

    @PostMapping("/change-request")
    @ResponseBody
    public boolean changeStatus(Integer id, String status, Principal principal) {
        try {

            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            if (user == null) {
                return false;
            }
            Optional<ServiceRequest> opp = service.findById(id);
            if (opp.isPresent()) {

                ServiceRequest request = opp.get();
                request.setWorkStatus(status);
                request.setProcessedBy(user.getId());

                return service.save(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @GetMapping("/find-by-status")
    public String findByStatus(String status, Model model, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {

        Page<ServiceRequest> pages = service.findByWorkStatus(status, page, max);
        model.addAttribute("list", pages.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());

        return "resume-request";
    }

    private void sendNotification(ServiceRequest cv) {
        try {
            String msg = "Hello "
                    + "\n Please be informed that an applicant has made a request for " + cv.getServiceType().getName() + " on " + cv.getEntryDate() + "."
                    + "\n Details of the applicant is as below."
                    + "\n"
                    + "\n Applicant Name: " + cv.getRequestedBy().getFullName()
                    + "\n Applicant Telephone: " +  cv.getRequestedBy().getTelephone()
                    + "\n Applicant Email: " + cv.getRequestedBy().getEmail()
                    + "\n "
                    + "\n To process this request, click the link: " + appUrl + "/resume-request-ls";


            Optional<Company> company = companyservice.findByName("L'aine");
            Company com = company.orElse(new Company("L'aine", false));
            List<String> to = JsfUtil.convertToList(com.getNotificationEmail());

            String[] address = new String[to.size()];
            to.toArray(address);

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(address);
            passwordResetEmail.setFrom(appTitle);
            passwordResetEmail.setSubject("Service Request on xjobs");
            passwordResetEmail.setText(msg);

            emailService.send(passwordResetEmail);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    private List<ServiceRequestLines> convertedToList(List<ServiceRequest> list) {
        return list.stream().map(n ->
                //Integer id, String entryDate, String name, String email, String telephone, String commentMsg, String processedBy, String processedDate, String workStatus, String serviceType
                new ServiceRequestLines(
                        n.getId(),
                        JsfUtil.convertFromDate(n.getEntryDate()),
                        n.getRequestedBy().getFullName(),
                        n.getRequestedBy().getEmail(),
                        n.getRequestedBy().getTelephone(),
                        n.getComment(),
                        n.getProcessedBy().toString(),
                        JsfUtil.convertFromDate(n.getUpload_date()),
                        n.getWorkStatus(),
                        n.getServiceType().getName()
                )
        ).collect(Collectors.toList());

    }

    @GetMapping("/recruiter/resume-request-status-change/{id}/{status}")
    @ResponseBody
    public ResponseEntity<String> changeStatus(@PathVariable Integer id, @PathVariable String status) {
        try {
            Optional<ServiceRequest> opp = service.findById(id);
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid Credential");
            }

            ServiceRequest req = opp.get();
            req.setWorkStatus(status);
            String st = service.save(req) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(st, HttpStatus.OK);

        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
