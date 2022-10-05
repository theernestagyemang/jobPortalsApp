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
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class JobsApplicationsController {


    @Autowired
    public MyDateobj dateobj;
    @Autowired
    private JobApplicationsService service;
    @Autowired
    private JobsService jobService;
    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private ShortlistedApplicantService shortlistedApplicantService;
    private int index;

    @GetMapping("/recruiter/applications")
    public String totalApplications(Model model, Principal principal,
                                    @RequestParam(required = false) String trx,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "5") Integer max) {

        String url = "";

        Page<JobApplications> ls = null;
        if (trx == null) {
            ls = service.findAll(page, max);
            url = "/recruiter/applications";
        } else {
            Optional<JobSeeker> oppSeeker = seekerService.findByTransactionId(trx);
            if (!oppSeeker.isPresent()) {
                ls = service.findAll(page, max);
                url = "/recruiter/applications";
            } else {
                JobSeeker seeker = oppSeeker.get();
                ls = service.findByJobseeker(seeker, page, max);
                url = "/recruiter/applications?trx=" + trx;
            }
        }


        PageWrapper<JobApplications> pages = new PageWrapper<>(ls, url);
        model.addAttribute("page", pages);
        List<JobApplications> list = pages.getContent();


        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("list", convertToLines(list));

        return "recruiter/applications";
    }


    @GetMapping("/recruiter/application-by-status")
    public String totalApplications(Model model, Principal principal,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(defaultValue = "today") String st,
                                    @RequestParam(defaultValue = "today") String ed,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "5") Integer max) {

        String url = "/recruiter/application-by-status";
        //System.out.println("Using sta "+status);
        Page<JobApplications> ls = null;
        if (status == null) {
            return "redirect:/recruiter/applications";
        }

        MyDateConverter cv = new MyDateConverter(st, ed);
        ls = service.findByApplicationStatusAndDates(status, cv.getStart(), cv.getEnd(), page, max);

//            if("today".equals(st) || "today".equals(ed) ){
//                ls = service.findByApplicationStatus(status,page, max);
//            }else{
//                LocalDate start = cv.getStart();
//                LocalDate end = cv.getEnd();
//               
//            }

        PageWrapper<JobApplications> pages = new PageWrapper<>(ls, url);
        model.addAttribute("page", pages);
        List<JobApplications> list = pages.getContent();


        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("list", convertToLines(list));

        return "recruiter/applications";
    }

    @GetMapping("/recruiter/candidates")
    public String candidates(Model model, Principal principal,
                             @RequestParam(required = false) String trx,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "5") Integer max) {

        if (trx == null) {
            return "redirect:/recruiter/applications";
        }
        Optional<Jobs> opp = jobService.findByTransactionId(trx);
        if (!opp.isPresent()) {
            return "redirect:/recruiter/applications";
        }

        Jobs jobs = opp.get();
        model.addAttribute("job", jobs);

        Page<JobApplications> ls = service.findByJob(jobs, page, max);


        PageWrapper<JobApplications> pages = new PageWrapper<>(ls, "/recruiter/candidates?trx=" + trx);
        model.addAttribute("page", pages);
        List<JobApplications> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("list", convertToLines(list));


        model.addAttribute("shortlisted", findByAccredStatusCount(trx, JsfUtil.SHORTLISTED));
        model.addAttribute("awaitingReview", findByAccredStatusCount(trx, JsfUtil.PENDING));
        model.addAttribute("hired", findByAccredStatusCount(trx, JsfUtil.HIRED));
        model.addAttribute("rejected", findByAccredStatusCount(trx, JsfUtil.REJECTED));

        return "recruiter/candidates";
    }

    @GetMapping("/recruiter/applications-total")
    @ResponseBody
    public Integer totalApplications(@RequestParam(defaultValue = "today") String st,
                                     @RequestParam(defaultValue = "today") String ed) {

        List<Object[]> ls = null;
        if ("today".equals(st) || "today".equals(ed)) {
            return service.findDailyTotalJobApplications();
        }
        LocalDate start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
        LocalDate end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");


        return service.findDailyTotalJobApplications(start, end);
    }


    @GetMapping("/recruiter/new-applications")
    @ResponseBody
    public JobApplicationResponseHeader newApplications(@RequestParam(defaultValue = "5") String limit) {
        List<JobApplications> list = service.findByDailyApplied();
        Integer totalRecords = list.size();
        if ("All".equalsIgnoreCase(limit)) {
            List<JobApplicationResponse> lines = convertToLines(list);
            return new JobApplicationResponseHeader(totalRecords, lines);
        }
        if (list.size() > 5) {
            list = list.subList(0, 5);
        }

        List<JobApplicationResponse> lines = convertToLines(list);
        return new JobApplicationResponseHeader(totalRecords, lines);
    }

    @GetMapping("/recruiter/job-applications")
    @ResponseBody
    public JobApplicationResponseHeader jobApplications(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer max) {
        Page<JobApplications> pages = service.findAll(page, max);
        List<JobApplications> list = pages.getContent();

        List<JobApplicationResponse> lines = convertToLines(list);
        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();

        return new JobApplicationResponseHeader(numberOfElements, totalPages, page, max, lines);


    }

    @GetMapping("/recruiter/candidates/{trx}/{type}")
    @ResponseBody
    public JobApplicationResponseHeader jobApplications2(@PathVariable String trx, @PathVariable String type, @RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "5") Integer max) {

        Optional<Jobs> opp = jobService.findByTransactionId(trx);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid transactionId: " + trx);
        }
        Jobs job = opp.get();

        Page<JobApplications> pages = service.findByJobAndApplicationStatus(job, type, page, max);
        List<JobApplications> list = pages.getContent();

        List<JobApplicationResponse> lines = convertToLines(list);
        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();

        return new JobApplicationResponseHeader(numberOfElements, totalPages, page, max, lines);


    }

    @GetMapping("/recruiter/find-by-application-status/{trx}/{type}")
    @ResponseBody
    public Integer findByAccredStatusCount(@PathVariable String trx, @PathVariable String type) {
        Optional<Jobs> opp = jobService.findByTransactionId(trx);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid transactionId: " + trx);
        }
        Jobs job = opp.get();
        return service.findByJobAndApplicationStatusCount(job, type);
    }

    @GetMapping("/employer/hire/{jobId}/{seekerId}")
    public String hire(@PathVariable Integer jobId, @PathVariable Integer seekerId, Principal principal) {
        try {
            Jobs job = jobService.findById(jobId).orElse(null);
            JobSeeker seeker = seekerService.findById(seekerId).orElse(null);

            if(job != null && seeker != null){
                Users user = JsfUtil.findOnline(principal);
                Employer employer = employerService.findByEmail(user.getUsername()).get();
                ShortlistedApplicants shortlistedApplicants = shortlistedApplicantService.findBySeekerAndJobAndEmployer(
                        seeker, job,employer
                );

                if(!shortlistedApplicants.getApproved()){
                    shortlistedApplicants.setApproved(true);
                    shortlistedApplicants.setStatus("Hired");
                   String status = shortlistedApplicantService.save(shortlistedApplicants) ? "SUCCESS" : "FAILED";
//
                    return "redirect:/employer/browse-by-shortlisted";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/employer/browse-by-shortlisted";
    }

    @GetMapping("/employer/reject/{jobId}/{seekerId}")

    public String reject(@PathVariable Integer jobId, @PathVariable Integer seekerId, Principal principal) {
        try {
            Jobs job = jobService.findById(jobId).orElse(null);
            JobSeeker seeker = seekerService.findById(seekerId).orElse(null);

            if(job != null && seeker != null){
                Users user = JsfUtil.findOnline(principal);
                Employer employer = employerService.findByEmail(user.getUsername()).get();
                ShortlistedApplicants shortlistedApplicants = shortlistedApplicantService.findBySeekerAndJobAndEmployer(
                        seeker, job,employer
                );

                if(!shortlistedApplicants.getApproved()){
                    shortlistedApplicants.setStatus("Rejected");
                    String status = shortlistedApplicantService.save(shortlistedApplicants) ? "SUCCESS" : "FAILED";

                    return "redirect:/employer/browse-by-shortlisted";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/employer/browse-by-shortlisted";
    }


    @GetMapping("/recruiter/on-hold-total")
    @ResponseBody
    public Integer totalOnHoldApplications(@RequestParam(defaultValue = "today") String st,
                                           @RequestParam(defaultValue = "today") String ed) {

        if ("today".equals(st) || "today".equals(ed)) {
            return service.findDailyTotalJobApplications(JsfUtil.ON_HOLD);
        }
        LocalDate start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
        LocalDate end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");

        return service.findDailyTotalJobApplications(JsfUtil.ON_HOLD, start, end);
    }

    @GetMapping("/recruiter/rejected-total")
    @ResponseBody
    public Integer totalRejectedApplications(@RequestParam(defaultValue = "today") String st,
                                             @RequestParam(defaultValue = "today") String ed) {

        if ("today".equals(st) || "today".equals(ed)) {
            return service.findDailyTotalJobApplications(JsfUtil.REJECTED);
        }
        LocalDate start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
        LocalDate end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");

        return service.findDailyTotalJobApplications(JsfUtil.REJECTED, start, end);
    }


    @GetMapping("/recruiter/shortlisted-total")
    @ResponseBody
    public Integer totalShortlistdApplications(@RequestParam(defaultValue = "today") String st,
                                               @RequestParam(defaultValue = "today") String ed) {

        List<Object[]> ls = null;
        if ("today".equals(st) || "today".equals(ed)) {
            return service.findDailyTotalJobApplications(JsfUtil.SHORTLISTED);
        }
        LocalDate start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
        LocalDate end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");

        return service.findDailyTotalJobApplications(JsfUtil.SHORTLISTED, start, end);
    }


//     public List<BigDecimal> getMonthlySales(String applicationtye){
//   
//        List<Object[]> list = service.findMonthlySalesByYear(applicationtye);
//    
//   
//        
//        List<BigDecimal> sale = new ArrayList<>();
//        
//        List<Shortlisted> sales = new ArrayList<>();
//        
//        list.stream().forEach((item)->{
//            sales.add(new Shortlisted((Integer) item[0] , (Integer)item[1])); //Application,Month
//        });
//        
//        
//        
//        return sale;
//        
//        //return Arrays.asList(50, 10, 4, 20, 7, 45, 5, 35, 20, 48,20, 48);
//    }

    @GetMapping("/recruiter/active-jobs")
    @ResponseBody
    public List<Shortlisted> activeJobs(Integer year) {

        List<Object[]> list = service.findMonthlyApplication(year);
        List<Shortlisted> sales = new ArrayList<>();

        list.stream().forEach((item) -> {
            sales.add(new Shortlisted((item[0].toString()),
                    Integer.parseInt((item[1].toString())))
            );
        });

        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "Setptember", "October", "November", "December");


        return sales;
    }

    @GetMapping("/recruiter/applications-api")
    @ResponseBody
    public ApplicationsResponse applicationsApi() {

        Integer[] shortlisted = {35, 20, 25, 4, 57, 6, 7, 8, 80, 100, 11, 130};
        Integer[] applications = {15, 20, 30, 4, 57, 6, 7, 8, 90, 100, 11, 130};
        Integer[] rejected = {5, 5, 30, 4, 50, 6, 7, 8, 40, 8, 11, 156};

        ApplicationsResponse app = new ApplicationsResponse();


        app.setJan(shortlisted[0]);
        app.setFeb(shortlisted[1]);
        app.setMar(shortlisted[2]);
        app.setApr(shortlisted[3]);
        app.setMay(shortlisted[4]);
        app.setJune(shortlisted[5]);
        app.setJul(shortlisted[6]);


        app.setS_jan(applications[0]);
        app.setS_feb(applications[1]);
        app.setS_mar(applications[2]);
        app.setS_apr(applications[3]);
        app.setS_may(applications[4]);
        app.setS_june(applications[5]);
        app.setS_jul(applications[6]);


        app.setR_jan(rejected[0]);
        app.setR_feb(rejected[1]);
        app.setR_mar(rejected[2]);
        app.setR_apr(rejected[3]);
        app.setR_may(rejected[4]);
        app.setR_june(rejected[5]);
        app.setR_jul(rejected[6]);


        return app;


    }


    @GetMapping("/recruiter/applications-api2")
    @ResponseBody
    public ApplicationsResponse applicationsApi2() {

        Integer[] applications = fetchWeeklyData().toArray(new Integer[5]);
        Integer[] shortlisted = fetchWeeklyData("Shortlisted").toArray(new Integer[5]);
        Integer[] rejected = fetchWeeklyData("Rejected").toArray(new Integer[5]);
        Integer[] hired = fetchWeeklyData("Hired").toArray(new Integer[5]);

        ApplicationsResponse app = new ApplicationsResponse();

        //Applications
        app.setJan(applications[0]);
        app.setFeb(applications[1]);
        app.setMar(applications[2]);
        app.setApr(applications[3]);
        app.setMay(applications[4]);
        app.setJune(applications[5]);
        app.setJul(applications[6]);

        //Shortlisted
        app.setS_jan(shortlisted[0]);
        app.setS_feb(shortlisted[1]);
        app.setS_mar(shortlisted[2]);
        app.setS_apr(shortlisted[3]);
        app.setS_may(shortlisted[4]);
        app.setS_june(shortlisted[5]);
        app.setS_jul(shortlisted[6]);

        //Rejected
        app.setR_jan(rejected[0]);
        app.setR_feb(rejected[1]);
        app.setR_mar(rejected[2]);
        app.setR_apr(rejected[3]);
        app.setR_may(rejected[4]);
        app.setR_june(rejected[5]);
        app.setR_jul(rejected[6]);

        //Hired
        app.setH_jan(hired[0]);
        app.setH_feb(hired[1]);
        app.setH_mar(hired[2]);
        app.setH_apr(hired[3]);
        app.setH_may(hired[4]);
        app.setH_june(hired[5]);
        app.setH_jul(hired[6]);

        return app;
    }

    private List<JobApplicationResponse> convertToLines(List<JobApplications> list) {
        return list.stream().map(n ->
                // id,  jobSeeker,  appliedDate,  company,  companyFileName, String jobType, String position, String jobStatus
                new JobApplicationResponse(

                        n.getTransactionId(),
                        findJobSeeker(n),
                        JsfUtil.convertToString(n.getAppliedDate(), "dd/MM/yyyy"),
                        findJobType(n, "Name"),
                        findFileName(n),
                        findJobType(n, "JobType"),
                        findJobType(n, "Profession"),
                        n.getApplicationStatus(),
                        findJobType(n, "Transaction")
                )
        ).collect(Collectors.toList());
    }

    private String findFileName(JobApplications n) {
        Jobs job = n.getJob();
        if (job == null) {
            return "";
        }
        Employer employer = job.getPostedBy();
        if (employer == null) {
            return "";
        }
        String fn = employer.getFileName();
        if (fn == null) {
            return "default-comp-img.jpg";
        }
        return fn;
    }

    public JobSeekerDetails findJobSeeker(JobApplications n) {
        JobSeeker seeker = n.getJobseeker();
        if (seeker == null) {
            return new JobSeekerDetails();
        }
        return new JobSeekerDetails(seeker);
    }

    private String findJobType(JobApplications n, String option) {
        Jobs job = n.getJob();
        if (job == null) {
            return "";
        }
        switch (option) {
            case "Name":
                Employer emp = job.getPostedBy();
                if (emp != null) {
                    return String.valueOf(emp);
                }
                return "Not Provided";
            case "JobType":
                return job.getJobType();
            case "Profession":
                return job.getProfession();
            case "Transaction":
                return job.getTransactionId();
        }
        return job.getJobType();

    }

    @GetMapping("/recruiter/find-applications-by-job-category")
    @ResponseBody
    public List<JobTitleApplications> jobTitleApp(
            @RequestParam(defaultValue = "today") String st,
            @RequestParam(defaultValue = "today") String ed,
            @RequestParam(defaultValue = "5") String limit) {

        LocalDate start = null;
        LocalDate end = null;
        List<Object[]> ls = null;

        MyDateConverter cv = new MyDateConverter(st, ed);

        ls = service.findApplicationsByJobCategory(cv.getStart(), cv.getEnd());
        if ("All".equalsIgnoreCase(limit)) {
            return createList(ls);
        }
        if (ls.size() > 5) {
            ls = ls.subList(0, 5);
        }
        return createList(ls);
    }

    private List<JobTitleApplications> createList() {

        List<JobTitleApplications> list = new ArrayList<>();
        list.add(new JobTitleApplications("Project Manager", 150));
        list.add(new JobTitleApplications("Sales Manager", 180));
        list.add(new JobTitleApplications("Project Manager", 600));
        list.add(new JobTitleApplications("Humain8 Resource", 150));
        list.add(new JobTitleApplications("Machine Instrument", 70));
        list.add(new JobTitleApplications("Operation Manager", 180));

        return list;
    }

    private String checkText(String tx) {
        if (tx == null) {
            return "";
        }
        return tx;
    }

    private List<JobTitleApplications> createList(List<Object[]> list) {
        //  id,  product, productNo, qty,  unitPrice,  unitCost, totalSales, totalCost,  cashier
        if (list == null) {
            return createList();
        }
        try {
            return list.stream().map(c ->
                            new JobTitleApplications(
                                    ((Long) c[0]).intValue(),
                                    checkText((String) c[1])
                            )
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/recruiter/total-applications")
    @ResponseBody
    public TotalApplications totalApplicationsApi(@RequestParam(defaultValue = "today") String st,
                                                  @RequestParam(defaultValue = "today") String ed) {

        Integer totalApplication = 0;
        Integer shortlisted = 0;
        Integer onHold = 0;
        Integer rejected = 0;
        Integer hired = 0;
        //LocalDate start =null;
        // LocalDate end =null;

        //If no date provide, let look for
//        if("today".equals(st) || "today".equals(ed)){
//            
//            LocalDate initial = LocalDate.now();
//            start = initial.withDayOfMonth(1);
//            end = initial.withDayOfMonth(initial.lengthOfMonth());
//
//        }else{
//             start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
//             end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");
//        }

        MyDateConverter cv = new MyDateConverter(st, ed);

        List<JobApplications> list = service.findDailyJobApplications(cv.getStart(), cv.getEnd());
        // System.out.println("list..."+list.size());
        list = list.stream().filter(c -> c != null)
                .filter(c -> c.getApplicationStatus() != null)
                .collect(Collectors.toList());

        List<JobApplications> slist = list.stream()
                .filter(c -> c.getApplicationStatus().equalsIgnoreCase(JsfUtil.SHORTLISTED))
                .collect(Collectors.toList());


        List<JobApplications> onholdlist = list.stream()
                .filter(c -> c.getApplicationStatus().equalsIgnoreCase(JsfUtil.PENDING))
                .collect(Collectors.toList());

        List<JobApplications> hiredlist = list.stream()
                .filter(c -> c.getApplicationStatus().equalsIgnoreCase(JsfUtil.HIRED))
                .collect(Collectors.toList());

        List<JobApplications> rejectedlist = list.stream()
                .filter(c -> c.getApplicationStatus().equalsIgnoreCase(JsfUtil.REJECTED))
                .collect(Collectors.toList());

        totalApplication = list.size();
        shortlisted = slist.size();
        onHold = onholdlist.size();
        rejected = rejectedlist.size();
        hired = hiredlist.size();


        //totalApplication =service.findDailyTotalJobApplications(start,end);
//        shortlisted = service.findDailyTotalJobApplications(JsfUtil.SHORTLISTED,start,end);
//        onHold = service.findDailyTotalJobApplications(JsfUtil.ON_HOLD,start,end);
//        rejected = service.findDailyTotalJobApplications(JsfUtil.REJECTED,start,end);
//        hired = service.findDailyTotalJobApplications(JsfUtil.HIRED,start,end);

        return new TotalApplications(totalApplication, shortlisted, onHold, rejected, hired);

    }

    @GetMapping("/recruiter/test")
    public String test(Model model) {
        return "recruiter/test";
    }

    private Integer[] getYearlyShortlisted() {
        return null;
    }

    public void filterLoad() {
        List<Shortlisted> list = activeJobs(2019);

        List<Shortlisted> list2 = new ArrayList<>();
        for (index = 0; index <= 12; index++) {
            list.stream().forEach((item) -> {

                if (item.getMaonth() == index) {
                    list2.add(item);
                } else {

                }

            });
        }
    }


    private List<Integer> fetchWeeklyData() {
        LocalDate today = dateobj.getFirstDay();


        LocalDate monday = today;
        LocalDate tuesday = today.plusDays(1);
        LocalDate wednesday = today.plusDays(2);
        LocalDate thursday = today.plusDays(3);
        LocalDate friday = today.plusDays(4);
        LocalDate saturday = today.plusDays(5);
        LocalDate sunday = today.plusDays(6);


        Integer mondayData = service.findDailyTotalJobApplications(monday, monday);
        if (mondayData == null) {
            mondayData = 0;
        }

        Integer tuesdayData = service.findDailyTotalJobApplications(tuesday, tuesday);
        if (tuesdayData == null) {
            tuesdayData = 0;
        }

        Integer wedData = service.findDailyTotalJobApplications(wednesday, wednesday);
        if (wedData == null) {
            wedData = 0;
        }

        Integer thursdayData = service.findDailyTotalJobApplications(thursday, thursday);
        if (thursdayData == null) {
            thursdayData = 0;
        }

        Integer fridayData = service.findDailyTotalJobApplications(friday, friday);
        if (fridayData == null) {
            fridayData = 0;
        }

        Integer saturdayData = service.findDailyTotalJobApplications(saturday, saturday);
        if (saturdayData == null) {
            saturdayData = 0;
        }

        Integer sundayData = service.findDailyTotalJobApplications(sunday, sunday);
        if (sundayData == null) {
            sundayData = 0;
        }

        return Arrays.asList(
                mondayData,
                tuesdayData,
                wedData,
                thursdayData,
                fridayData,
                saturdayData,
                sundayData
        );

    }

    private List<Integer> fetchWeeklyData(String applicationStatus) {
        LocalDate today = dateobj.getFirstDay();


        LocalDate monday = today;
        LocalDate tuesday = today.plusDays(1);
        LocalDate wednesday = today.plusDays(2);
        LocalDate thursday = today.plusDays(3);
        LocalDate friday = today.plusDays(4);
        LocalDate saturday = today.plusDays(5);
        LocalDate sunday = today.plusDays(6);


        Integer mondayData = service.findDailyTotalJobApplications(applicationStatus, monday, monday);
        if (mondayData == null) {
            mondayData = 0;
        }

        Integer tuesdayData = service.findDailyTotalJobApplications(applicationStatus, tuesday, tuesday);
        if (tuesdayData == null) {
            tuesdayData = 0;
        }

        Integer wedData = service.findDailyTotalJobApplications(applicationStatus, wednesday, wednesday);
        if (wedData == null) {
            wedData = 0;
        }

        Integer thursdayData = service.findDailyTotalJobApplications(applicationStatus, thursday, thursday);
        if (thursdayData == null) {
            thursdayData = 0;
        }

        Integer fridayData = service.findDailyTotalJobApplications(applicationStatus, friday, friday);
        if (fridayData == null) {
            fridayData = 0;
        }

        Integer saturdayData = service.findDailyTotalJobApplications(applicationStatus, saturday, saturday);
        if (saturdayData == null) {
            saturdayData = 0;
        }

        Integer sundayData = service.findDailyTotalJobApplications(applicationStatus, sunday, sunday);
        if (sundayData == null) {
            sundayData = 0;
        }

        return Arrays.asList(
                mondayData,
                tuesdayData,
                wedData,
                thursdayData,
                fridayData,
                saturdayData,
                sundayData
        );

    }
}
