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
import com.debusey.smart.pos.smartpos.search.UserSearch;
import com.debusey.smart.pos.smartpos.service.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.debusey.smart.pos.smartpos.JsfUtil.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Admin
 */

@Controller
public class JobsController {

    private final String duties = "duties";
    private final String requirements = "requirements";
    private final String special = "special";
    @Autowired
    public CountriesService countryService;
    @Autowired
    private TestimonialService testimonyService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobsService service;

    @Autowired
    private JobViewService jvservice;
    @Autowired
    private JobAlertService alertservice;

    @Autowired
    private CompanyCvService cvService;

    @Autowired
    private JobRequirementService jservice;

    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private JobPreferenceService prefService;

    @Autowired
    private SavedJobsService svService;

    @Autowired
    private JobApplicationsService jobAppService;

    @Autowired
    private EmployeeService empService;

    @Autowired
    private SavedJobsService svJobService;

    //@Value("${app.startPaymentMode}")
    // private String startPayment;
    @Autowired
    private ShortlistedApplicantService shortService;

    @Autowired
    private EmployerShortlistedApplicantsService employerShortService;

    @Autowired
    private UserSearch userSearch;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriptionTokenService tokenService;

    @Autowired
    private WorkExperienceService wService;

    @Value("${app.hostName}")
    private String appUrl;

    @GetMapping("/job-request-list")
    public String jobRequestList(Model model, Principal principal, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") Integer max) {

        Page<Jobs> list = service.findAll(page, max);


        model.addAttribute("list", list);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "job-request-list";
    }


    //This is for Jobseekers
    @GetMapping("/job-details")
    public String jobDetails(Model model, @RequestParam(defaultValue = "0", required = false) Integer id) {
        try {
            if (id == 0) {
                return "redirect:all-jobs";
            }
            Optional<Jobs> oppJop = service.findById(id);
            if (!oppJop.isPresent()) {
                return "redirect:all-jobs";
            }

            model.addAttribute("timeAgo", new TimeAgo());
            Jobs jobs = oppJop.get();

            Boolean published = jobs.getPublished();
            if (published == null) {
                return "redirect:unpublished-job?id=" + id;
            }
            if (!published) {
                return "redirect:unpublished-job?id=" + id;
            }

            List<String> jreqList = getRequirement(jobs, requirements);
            List<String> dutiesList = getRequirement(jobs, duties);
            List<String> specialReq = getRequirement(jobs, special);

            //

            model.addAttribute("job", jobs);
            model.addAttribute("jreqList", jreqList);
            model.addAttribute("dutiesList", dutiesList);
            model.addAttribute("specialReq", specialReq);

            Boolean show = jobs.getShowCompanyName();

            loadModelForCompany(model, jobs, show);


            if (show == null) {
                show = false;
            }
            model.addAttribute("showCompany", show);

            return "job-details";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:all-jobs";
    }

    @GetMapping("/job/details/{transactionId}")
    public String jobDetailsByTransactionId(Model model, @PathVariable String transactionId) {
        try {
            if (transactionId == null) {
                return "redirect:all-jobs";
            }
            Optional<Jobs> oppJop = service.findByTransactionId(transactionId);
            if (!oppJop.isPresent()) {
                return "redirect:all-jobs";
            }

            model.addAttribute("timeAgo", new TimeAgo());
            Jobs jobs = oppJop.get();

            Boolean published = jobs.getPublished();
            if (published == null) {
                return "redirect:/unpublished-job/" + transactionId;
            }
            if (!published) {
                return "redirect:/unpublished-job/" + transactionId;
            }

            List<String> jreqList = getRequirement(jobs, requirements);
            List<String> dutiesList = getRequirement(jobs, duties);
            List<String> specialReq = getRequirement(jobs, special);

            //

            model.addAttribute("job", jobs);
            model.addAttribute("jreqList", jreqList);
            model.addAttribute("dutiesList", dutiesList);
            model.addAttribute("specialReq", specialReq);


            Boolean show = jobs.getShowCompanyName();
            if (show == null) {
                show = false;
            }
            loadModelForCompany(model, jobs, show);
            model.addAttribute("showCompany", show);

            return "job-details";
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "job-details";
    }

    @GetMapping("/employer/job-details/{transactionId}")
    public String jobDetailsByTransactionIdAndEmployer(Model model, @PathVariable String transactionId) {
        try {
            if (transactionId == null) {
                return "redirect:all-jobs";
            }
            Optional<Jobs> oppJop = service.findByTransactionId(transactionId);
            if (!oppJop.isPresent()) {
                return "redirect:all-jobs";
            }

            model.addAttribute("timeAgo", new TimeAgo());
            Jobs jobs = oppJop.get();


            List<String> jreqList = getRequirement(jobs, requirements);
            List<String> dutiesList = getRequirement(jobs, duties);
            List<String> specialReq = getRequirement(jobs, special);


            model.addAttribute("job", jobs);
            model.addAttribute("jreqList", jreqList);
            model.addAttribute("dutiesList", dutiesList);
            model.addAttribute("specialReq", specialReq);

            loadModelForCompany(model, jobs, true);
            model.addAttribute("showCompany", true);

            return "employer/job-details";
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "employer/job-details";
    }

    @GetMapping("/recruiter/job-details/{transactionId}")
    public String jobDetailsByTransactionIdAndRecruiter(Model model, @PathVariable String transactionId) {
        try {
            if (transactionId == null) {
                return "redirect:manage-jobs";
            }
            Optional<Jobs> oppJop = service.findByTransactionId(transactionId);
            if (!oppJop.isPresent()) {
                return "redirect:manage-jobs";
            }

            model.addAttribute("timeAgo", new TimeAgo());
            Jobs jobs = oppJop.get();


            List<String> jreqList = getRequirement(jobs, requirements);
            List<String> dutiesList = getRequirement(jobs, duties);
            List<String> specialReq = getRequirement(jobs, special);


            model.addAttribute("job", jobs);
            model.addAttribute("jreqList", jreqList);
            model.addAttribute("dutiesList", dutiesList);
            model.addAttribute("specialReq", specialReq);

            loadModelForCompany(model, jobs, true);
            model.addAttribute("showCompany", true);

            return "recruiter/job-details";
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "recruiter/job-details";
    }

    @GetMapping("/com-job-details")
    public String comJobDetails(Model model, Integer id, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "redirect:login";
            }
            boolean showcompanyName = false;
            model.addAttribute("timeAgo", new TimeAgo());

            Optional<Employer> oppEmp = employerService.findByEmail(user.getUsername());

            if (oppEmp.isPresent()) {
                Employer employer = oppEmp.get();


                Optional<Jobs> oppJop = service.findById(id);


                if (oppJop.isPresent()) {

                    Jobs jobs = oppJop.get();

                    List<String> jreqList = getRequirement(jobs, requirements);


                    model.addAttribute("job", jobs);
                    model.addAttribute("jreqList", jreqList);

                    model.addAttribute("company", employer);
                    model.addAttribute("showCompany", jobs.getShowCompanyName());


                    return "com-job-details";

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("job", new Jobs());
        model.addAttribute("jreqList", new ArrayList<>());
        model.addAttribute("company", new Employer());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("showCompany", false);
        return "com-job-details";
    }


    @GetMapping("/unpublished-job/{transactionId}")
    public String unpublishedJob(Model model, @PathVariable String transactionId) {

        Optional<Jobs> opp = service.findByTransactionId(transactionId);
        if (opp.isPresent()) {
            Jobs job = opp.get();
            model.addAttribute("job", job);
        } else {
            model.addAttribute("job", new Jobs());
        }


        return "unpublished-job";
    }


    //This is for internal Staff
    @GetMapping("/job-detail")
    public String jobDetail(Model model, @RequestParam(defaultValue = "0") Integer id) {
        Optional<Jobs> oppJop = service.findById(id);

        if (id == 0) {
            return "redirect:all-jobs";
        }
        if (!oppJop.isPresent()) {
            return "redirect:all-jobs";
        }

        model.addAttribute("timeAgo", new TimeAgo());
        Jobs jobs = oppJop.get();


        List<String> jreqList = getRequirement(jobs, requirements);
        List<String> dutiesList = getRequirement(jobs, duties);
        List<String> specialReq = getRequirement(jobs, special);

        //
        model.addAttribute("job", jobs);
        model.addAttribute("jreqList", jreqList);
        model.addAttribute("dutiesList", dutiesList);
        model.addAttribute("specialReq", specialReq);

        Employer company = jobs.getPostedBy();
        if (company == null) {
            company = new Employer();
        }

        model.addAttribute("company", company);
        model.addAttribute("showCompany", true);


        return "job-detail";
    }

    @GetMapping("/assigned-jobs")
    public String assignedJobs(Model model, Principal principal) {

        return "assigned-jobs";
    }

    @GetMapping("/assign")
    public String assignJobs(Model model, Principal principal) {

        return "assign";
    }

    @GetMapping("/findJob/{id}")
    @ResponseBody
    public Jobs findJob(@PathVariable Integer id) {

        Optional<Jobs> ass = service.findById(id);
        return ass.orElse(new Jobs());
    }

    @GetMapping("/findJob-db/{id}")
    @ResponseBody
    public JobSearch findJobSearch(@PathVariable Integer id) {

        Optional<Jobs> ass = service.findById(id);
        Jobs jobs = ass.orElse(new Jobs());
        JobSearch search = new JobSearch();
        search.setCategory(jobs.getCategory());
        search.setCountry(jobs.getCountry());
        search.setExpireDate(jobs.getExpireDate());
        search.setId(jobs.getId());
        search.setLocation(jobs.getLocation());
        search.setNatureOfContract(jobs.getNatureOfContract());
        search.setPostedBy(JsfUtil.createPostedBy(jobs.getPostedBy()));
        search.setProfession(jobs.getProfession());
        search.setPublishedDate(jobs.getPublishedDate());
        search.setRenumeration(jobs.getRenumeration());
        search.setShowCompanyName(jobs.getShowCompanyName());
        search.setToRenumeration(jobs.getToRenumeration());
        search.setDetails(jobs.getJobDescription());
        search.setSkillls(getRequirement(jobs, requirements));
        search.setHowToApply(jobs.getHowToApply());
        search.setIndustry(jobs.getIndustry());
        search.setQualification(jobs.getMinQualification());
        search.setSlot(jobs.getNoNeeded());
        search.setDutiesAndRespo(getRequirement(jobs, duties));
        search.setSpecialRequirements(getRequirement(jobs, special));
        search.setTransactionId(jobs.getTransactionId());


        return search;
    }


    @GetMapping("/free-job-alerts")
    public String freeJobAlert(Model model, Principal principal, @RequestParam(defaultValue = "0") Integer id) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        JobAlert alert = alertservice.findById(id).orElse(new JobAlert());


        if (user != null) {
            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            JobSeeker seeker = oppSeeker.orElse(new JobSeeker());

            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("seeker", seeker);
            model.addAttribute("alert", alert);

            return "free-job-alerts";
        }

        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("alert", alert);

        return "free-job-alerts";
    }


    @GetMapping("/free-job-alerts8")
    public String freeJobAlert2(Model model, Principal principal, @RequestParam(defaultValue = "0") Integer id2) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {
            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            JobSeeker seeker = oppSeeker.orElse(new JobSeeker());

            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("seeker", seeker);

            return "free-job-alerts";
        }

        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("seeker", new JobSeeker());

        return "free-job-alerts";
    }


    @RequestMapping(value = "/clientArea", method = RequestMethod.GET)
    public String clientArea(Model model, Principal principal) {

        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            Users user = JsfUtil.findOnline(principal);
            String userInfo = WebUtils.userRole(loginedUser);
            model.addAttribute("userInfo", userInfo);


            if (userInfo.contains("ROLE_JOBSEEKER")) {
                try {
                    return "redirect:/candidate-dashboard";

                } catch (Exception e) {
                    return "redirect:/candidate-dashboard";
                }

            }
            if (userInfo.contains("ROLE_COMPANY")) {
                try {
                    if (!user.isSubscribed()) {
                        return "redirect:/subscription-list";
                    }
                    return "redirect:/company-dashboard";
                } catch (Exception e) {
                    return "redirect:/company-dashboard";
                }
            }
            if (userInfo.contains("ROLE_RECRUITER")) {
                return "redirect:/assigned-posted-jobs";
            }
            if (userInfo.contains("ROLE_HEAD_RECRUITER")) {
                return "redirect:/posted-jobs";
            }

            if (userInfo.contains("ROLE_ADMIN")) {
                return "redirect:/admin";
            }


            if (userInfo.contains("ROLE_ACC_HOD")) {

                return "redirect:/invoice-list";
            }
            if (userInfo.contains("ROLE_ACCOUNTS")) {
                return "redirect:/accounts-dashboard";
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }


    @GetMapping("/seeker/apply/{transactionId}")
    public String apply(Model model, Principal principal, @PathVariable String transactionId) {
        Jobs job = new Jobs();
        JobSeeker seeker = new JobSeeker();


        Optional<Jobs> opp = service.findByTransactionId(transactionId);
        if (opp.isPresent()) {
            job = opp.get();
        }


        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            user = new Users();
        }


        Optional<JobSeeker> oppSeeker = seekerService.findByEmail(user.getUsername());
        if (oppSeeker.isPresent()) {
            seeker = oppSeeker.get();
        }

        model.addAttribute("seeker", seeker);
        model.addAttribute("job", job);

        return "seeker/apply-for-job";

    }

    public Optional<JobSeeker> findSeekerOnline(Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user != null) {
            String email = user.getUsername();
            if (email != null) {
                return seekerService.findByEmail(email);
            }
        }
        return null;
    }

    public boolean startPayment() {
        Company company = companyService.findByName("L'aine").orElse(new Company("L'aine", false));
        Boolean paymentMode = company.getActivatePayment();
        if (paymentMode == null) {
            paymentMode = false;
        }

        return paymentMode;
    }

    @GetMapping("/find-priv")
    @ResponseBody
    public boolean findPriv(@RequestParam(defaultValue = "") String category, Principal principal) {
        try {
            if (startPayment()) {
                return false;
            }
            if ("".equals(category)) {
                return false;
            }

            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return false;
            }

            String email = user.getUsername();
            if (email == null) {
                return false;
            }

            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                List<SeekerJobPrefrence> listOfJobPref = prefService.findByJobSeeker(seeker);
                listOfJobPref = listOfJobPref.stream()
                        .filter(prod ->
                                (prod.getPositions() != null)
                                        && (prod.getPositions().getName() != null)
                                        && (prod.getPositions().getName()).equalsIgnoreCase(category)
                        ).collect(Collectors.toList());

                return listOfJobPref.isEmpty();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @GetMapping("/apply-for-job")
    public String applyForJob(Model model, Principal principal, Integer id) {
        if (id == null) {
            return "job-details";
        }
        Jobs job = new Jobs();
        Optional<Jobs> opp = service.findById(id);
        if (opp.isPresent()) {
            job = opp.get();
        }
        model.addAttribute("job", job);

        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return "apply-for-job";
        }
        Optional<JobSeeker> oppSeeker = seekerService.findByEmail(user.getUsername());
        if (!oppSeeker.isPresent()) {
            return "apply-for-job";
        }
        String filtertext = job.getCategory();
        if (filtertext == null) {
            return "apply-for-job";
        }
        if (startPayment()) {

            JobSeeker seeker = oppSeeker.get();
            List<SeekerJobPrefrence> listOfJobPref = prefService.findByJobSeeker(seeker);
            listOfJobPref = listOfJobPref.stream()
                    .filter(prod ->
                            (prod.getPositions() != null)
                                    && (prod.getPositions().getName() != null)
                                    && (prod.getPositions().getName()).equalsIgnoreCase(filtertext)
                    ).collect(Collectors.toList());

            if (listOfJobPref.isEmpty()) {
                model.addAttribute("priv", "INSUFFICIENT-PRIV");
            }
        }

        return "apply-for-job";
    }

    @PostMapping("/apply-job")
    @ResponseBody
    public boolean applyJob(Integer id, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {

            String email = user.getUsername();
            JobSeeker seeker = null;
            Jobs job = null;

            Set<JobSeeker> sk = new HashSet<>(0);

            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            if (oppSeeker.isPresent()) {
                seeker = oppSeeker.get();
                sk.add(seeker);
            }

            Optional<Jobs> opp = service.findById(id);

            if (opp.isPresent()) {
                job = opp.get();

                if (!sk.isEmpty()) {

                    job.setJobseekers(sk);
                    job.setJobStatus("Filled");

                    if (service.save(job)) {
                        //save job as applied in the SavedJobs
                        return savejobAsApplied(job, seeker);
                    }
                }

            }
            return false;
        }
        return false;
    }


    @PostMapping("/job/apply/{transactionId}")
    @ResponseBody
    @Transactional
    public String applyForJob(@PathVariable String transactionId, Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return "INVALID-USER";
        }
        String email = user.getUsername();

        Set<JobSeeker> sk = new HashSet<>(0);

        Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
        if (!oppSeeker.isPresent()) {
            return "INVALID-JOBSEEKER";
        }
        JobSeeker seeker = oppSeeker.get();
        sk.add(seeker);

        Optional<Jobs> opp = service.findByTransactionId(transactionId);
        if (!opp.isPresent()) {
            return "INVALID-JOB";
        }


        Jobs job = opp.get();
        if (isAlreadyApplied(job, seeker)) {
            return "ALREADY-APPLIED";
        }

        job.setJobseekers(sk);
        job.setJobStatus("Filled");

        JobApplications ja = new JobApplications();
        ja.setApplicationStatus(JsfUtil.ON_HOLD);
        ja.setAppliedDate(LocalDate.now());
        ja.setJob(job);
        ja.setJobseeker(seeker);
        ja.setTransactionDate(LocalDateTime.now());
        ja.setEntryDate(LocalDate.now());
        ja.setTransactionId(transactionId);

        jobAppService.save(ja);

        if (service.save(job)) {
            //save job as applied in the SavedJobs
            return savejobAsApplied(job, seeker) ? "SUCCESS" : "FAILED";
        }


        return "ERROR";
    }


    @PostMapping("/apply-saved-job")
    @ResponseBody
    public boolean applySavedJob(Integer id, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            String email = user.getUsername();
            JobSeeker seeker = null;


            Set<JobSeeker> sk = new HashSet<>(0);

            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            if (oppSeeker.isPresent()) {
                seeker = oppSeeker.get();
                sk.add(seeker);
            }


            Optional<SavedJobs> svOpp = svJobService.findById(id);

            if (svOpp.isPresent()) {
                SavedJobs svjob = svOpp.get();
                Jobs job = svjob.getJobsId();

                if (!sk.isEmpty()) {

                    job.setJobseekers(sk);
                    job.setJobStatus("Filled");

                    svjob.setCategory(SAVED_JOB_APPLIED);
                    svJobService.save(svjob);

                    return service.save(job);
                }

            }
            return false;
        }
        return false;
    }

    @PostMapping("/save-jobs")
    @ResponseBody
    public boolean saveJobs(Integer id, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            String email = user.getUsername();
            JobSeeker seeker = null;
            Jobs job = null;


            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);
            if (oppSeeker.isPresent()) {
                seeker = oppSeeker.get();

                Optional<Jobs> opp = service.findById(id);
                if (opp.isPresent()) {

                    job = opp.get();
                    //Find out if job already saved
                    SavedJobs sv = svService.findByJobSeekerAndJob(job, seeker).orElse(new SavedJobs());

                    // SavedJobs sv = new SavedJobs();
                    sv.setEntryDate(new Date());
                    sv.setJobSeekerId(seeker);
                    sv.setJobsId(job);
                    sv.setCategory(SAVED_JOB_SAVED);

                    return svService.save(sv);
                }
            }

            return false;
        }
        return false;
    }


    @PostMapping("/assign-to-recruiter")
    @ResponseBody
    public boolean assign(Integer jobId, Integer recruiterId) {
        Optional<Employee> oppEmp = empService.findById(recruiterId);
        Employee assignedTo = oppEmp.orElse(new Employee());

        Optional<Jobs> oppJob = service.findById(jobId);
        Jobs job = null;

        if (oppJob.isPresent()) {
            job = oppJob.get();
            job.setAssignedTo(assignedTo);
            job.setAssigned(true);
            job.setAssignedDate(new Date());

            return service.save(job);
        }
        return false;
    }

    @GetMapping("/delete-saved-job/{id}")
    @ResponseBody
    public boolean deleteSavedJob(@PathVariable Integer id, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = seekerService.findByEmail(email);

            if (oppSeeker.isPresent()) {
                return svService.deleteById(id);
            }
            //        return trye
            return false;
        }
        return false;
    }

    @GetMapping("/posted-jobs")
    public String postedJobs(Model model, Principal principal, @RequestParam(required = false) String st, @RequestParam(required = false) String ed,
                             @RequestParam(defaultValue = "1", required = false) Integer page,
                             @RequestParam(defaultValue = "10", required = false) Integer max) {

        Date start = new Date();
        Date end = start;
        if (page < 1) {
            page = 1;
        }
        if (st != null || ed != null) {
            start = JsfUtil.convertToDate(st, "dd-MM-yyyy");
            end = JsfUtil.convertToDate(ed, "dd-MM-yyyy");

            st = JsfUtil.convertFromDate(start, "dd-MM-yyyy");
            ed = JsfUtil.convertFromDate(end, "dd-MM-yyyy");
        }

        Page<Jobs> ls = service.findByDate(start, end, page, max);

        model.addAttribute("list", ls);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("currentPage", page);
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        if (user != null) {
            model.addAttribute("emp", user.getStaffId());
        } else {
            model.addAttribute("emp", new Employee());
        }


        List<Employee> recruiters = empService.findRecruiter("ROLE_HEAD_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());


        model.addAttribute("user", user);


        PageWrapper<Jobs> pages = new PageWrapper<>(ls, "/posted-jobs?st=" + st + "&ed=" + ed);
        model.addAttribute("page", pages);
        List<Jobs> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("joblist", list);

        return "posted-jobs";
    }

    @GetMapping("/filled-jobs")
    public String filledJobs(Model model, Principal principal, @RequestParam(required = false) String st, @RequestParam(required = false) String ed) {
        List<Jobs> ls = new ArrayList<>();

        if (st != null || ed != null) {

            Date start = JsfUtil.convertToDate(st, "dd-MM-yyyy");
            Date end = JsfUtil.convertToDate(ed, "dd-MM-yyyy");

            ls = service.findByDateAndFilled(start, end);

        }

        model.addAttribute("list", ls);

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        if (user != null) {
            model.addAttribute("emp", user.getStaffId());
        } else {
            model.addAttribute("emp", new Employee());
        }


        List<Employee> recruiters = empService.findRecruiter("ROLE_HEAD_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "filled-jobs";
    }

    @GetMapping("/recruiter-assigned-job")
    public String recruiterAssignedJobs(Model model, Principal principal) {

        List<RecruiterAssignment> list = findByAssignmentCount();
        model.addAttribute("list", list);


        List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "recruiter-assigned-job";
    }

    @PostMapping("/update-emp-pic")
    @ResponseBody
    public boolean updateEmpPic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id) {

        Optional<Employee> opp = empService.findById(id);

        String appendName = String.valueOf((new Date()).getTime());

        if (opp.isPresent()) {
            if (files != null) {
                try {
                    Employee employee = opp.get();
                    String fileName = appendName + files.getOriginalFilename();
                    String fileType = files.getContentType();
                    byte[] originalByte = files.getBytes();

                    BufferedImage bi = JsfUtil.simpleResizeImage(originalByte, 140, 140);
                    byte[] uploadedFile = JsfUtil.convertToByte(bi);


                    DbFile dbfile = new DbFile(fileName, fileType, uploadedFile);
                    if (dbfile != null) {
                        JsfUtil.deleteFromDisk(employee.getFileName());

                        employee.setFileName(fileName);
                        employee.setFileType(dbfile.getFileType());
                        JsfUtil.saveToDisk(dbfile);
                    }


                    return empService.save(employee);

                } catch (IOException ex) {
                    Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return false;
    }

    @PostMapping("/upload-emp-pic")
    @ResponseBody
    public void uploadEmployeePic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id) {

        String appendName = String.valueOf((new Date()).getTime());

        Optional<Employee> opp = empService.findById(id);
        if (opp.isPresent()) {
            if (files != null) {
                try {
                    Employee employee = opp.get();
                    JsfUtil.deleteFromDisk(employee.getFileName());

                    String fileName = appendName + files.getOriginalFilename();
                    String fileType = files.getContentType();
                    byte[] originalByte = files.getBytes();

                    BufferedImage bi = JsfUtil.simpleResizeImage(originalByte, 140, 140);
                    byte[] uploadedFile = JsfUtil.convertToByte(bi);

                    DbFile dbfile = new DbFile(fileName, fileType, uploadedFile);


                    employee.setFileName(fileName);
                    employee.setFileType(dbfile.getFileType());
                    JsfUtil.saveToDisk(dbfile);


                    empService.save(employee);

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


    @PostMapping("/upload-pic2")
    @ResponseBody
    public boolean uploadPic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id, Model model) {

        boolean status = false;
        Optional<Employee> opp = empService.findById(id);
        if (opp.isPresent()) {
            if (files != null) {
                try {
                    Employee seeker = opp.get();


                    seeker.setEmployeePicture(files.getBytes());
                    status = empService.save(seeker);

                } catch (IOException ex) {
                    Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Employee seeker = empService.findById(id).orElse(new Employee());
        model.addAttribute("emp", seeker);

        return status;
    }

    @GetMapping("/assigned-posted-jobs")
    public String assignedPostedJob(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            Employee employee = user.getStaffId();

            List<Jobs> list = new ArrayList<>();

            if (employee != null) {
                list = service.findByAssignedTo(employee);

            }

            model.addAttribute("list", list);
            List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
            model.addAttribute("recruiters", recruiters);
            model.addAttribute("emp", employee);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());

            return "assigned-posted-jobs";
        }

        model.addAttribute("recruiters", new ArrayList<>());
        model.addAttribute("emp", new Employee());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "assigned-posted-jobs";

    }


    @GetMapping("/seeker-profile")
    public String jobSeekerProfile(Model model, Principal principal, Integer id) {
        Optional<JobSeeker> oppSeeker = seekerService.findById(id);
        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());

        model.addAttribute("seeker", seeker);
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {


            Employee employee = user.getStaffId();

            List<Jobs> list = new ArrayList<>();

            if (employee != null) {
                list = service.findByAssignedTo(employee);
            }

            model.addAttribute("list", list);
            List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
            model.addAttribute("recruiters", recruiters);
            model.addAttribute("emp", employee);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());


            return "seeker-profile";
        }

        model.addAttribute("list", new ArrayList<>());
        List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("emp", new Employee());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "seeker-profile";
    }


    @GetMapping("/seeker-profile-cv")
    public String jobSeekerProfiles(Model model, Principal principal, Integer id) {
        Optional<JobSeeker> oppSeeker = seekerService.findById(id);
        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());

        List<String> skills = findSkills(seeker);
        List<String> lang = findLangs(seeker);

        model.addAttribute("seeker", seeker);
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            Employee employee = user.getStaffId();

            List<Jobs> list = new ArrayList<>();

            if (employee != null) {
                list = service.findByAssignedTo(employee);

            }

            model.addAttribute("list", list);
            List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
            model.addAttribute("recruiters", recruiters);
            model.addAttribute("emp", employee);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());

            model.addAttribute("skills", skills);
            model.addAttribute("languages", lang);

            return "rec-profile-cv";
        }

        model.addAttribute("list", new ArrayList<>());
        List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("emp", new Employee());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        model.addAttribute("skills", skills);
        model.addAttribute("lang", lang);

        return "rec-profile-cv";
    }

    @RequestMapping("/search")
    public String search(Integer id, String q, Model model) {
        List<JobSeeker> list = null;
        try {
            list = userSearch.search(q);


            Optional<Jobs> oppJob = service.findById(id);
            Jobs job = oppJob.orElse(new Jobs());

            model.addAttribute("job", job);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("list", list);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
        return "browse-candidates";

    }


    @GetMapping("find-multiple2")
    @ResponseBody
    public List<JobSeeker> findSeekrs(String q) {
        return userSearch.searchMultipleTerm(q);
    }

    @RequestMapping(value = {"/admin/find-multiple", "/public/find-multiple"})
    @ResponseBody
    public Page<SearchedSeeker> findSeekrs2(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer max) {

        List<JobSeeker> listOfSeekers = userSearch.search(q);

        List<SearchedSeeker> list = convertToSearchedSeeker(listOfSeekers);

        Pageable pageable = PageRequest.of(page, max, Sort.by(Sort.Direction.DESC, "proffesionalTitile"));
        Page<SearchedSeeker> pageList = new PageImpl<>(list, pageable, list.size());

        return pageList;
    }

    @GetMapping("/find-jobseekers")
    public String findJobSeekers(String q, Model model, Principal principal) {
        List<JobSeeker> list = null;

        Users user = JsfUtil.findOnline(principal);
        Employer emp = employerService.findByEmail(user.getUsername()).orElse(new Employer());

        try {
            list = userSearch.search(q);


            model.addAttribute("job", new Jobs());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("list", list);
            model.addAttribute("employer", emp);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
        return "comp-find-job-seeker";
    }


    @RequestMapping("/find-job-seeker")
    public String findSeeker(Model model, Principal principal) {
        try {
            model.addAttribute("job", new Jobs());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return "find-job-seeker";

    }
//

    @GetMapping("/browse-candidates")
    public String browseCandidate2(Model model, Principal principal, Integer id) {

        if (id == null) {
            return "redirect:/assigned-posted-jobs";
        }
        Optional<Jobs> oppJob = service.findById(id);
        Jobs job = oppJob.orElse(new Jobs());


        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        model.addAttribute("employee", user.getStaffId());

        Set<JobSeeker> seeker = job.getJobseekers();

        model.addAttribute("seeker", seeker);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("job", job);
        model.addAttribute("specialization", findBySpecialization());

        return "browse-candidates";
    }

    @GetMapping("/browse-candidates/{transactionId}")
    public String browseCandidate(Model model, Principal principal, @PathVariable String transactionId) {

        if (transactionId == null) {
            return "redirect:/assigned-posted-jobs";
        }
        Optional<Jobs> oppJob = service.findByTransactionId(transactionId);
        Jobs job = oppJob.orElse(new Jobs());


        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        model.addAttribute("employee", user.getStaffId());

        Set<JobSeeker> seeker = job.getJobseekers();

        model.addAttribute("seeker", seeker);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("job", job);
        model.addAttribute("specialization", findBySpecialization());

        return "browse-candidates";
    }

    @GetMapping("/recruiter/search-cv")
    public String searchCV(Model model, Principal principal, @RequestParam(defaultValue = "") String q) {

        if (q.equals("")) {
            return "redirect:/recruiter/select-jobs";
        }
        Optional<Jobs> oppJob = service.findByTransactionId(q);
        if (!oppJob.isPresent()) {
            return "redirect:/recruiter/select-jobs";
        }
        Jobs job = oppJob.get();
        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("employee", user.getStaffId());

        model.addAttribute("job", job);
        model.addAttribute("user", user);
        model.addAttribute("specialization", findBySpecialization());

        return "recruiter/search-cv";
    }


    @GetMapping("/recruiter/search-cv2")
    public String searchCV2(Model model, Principal principal, @RequestParam(defaultValue = "") String q) {

        if (q.equals("")) {
            return "redirect:/recruiter/select-jobs";
        }
        Optional<Jobs> oppJob = service.findByTransactionId(q);
        if (!oppJob.isPresent()) {
            return "redirect:/recruiter/select-jobs";
        }
        Jobs job = oppJob.get();
        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("employee", user.getStaffId());

        model.addAttribute("job", job);
        model.addAttribute("user", user);
        model.addAttribute("specialization", findBySpecialization());

        return "recruiter/search-cv2";
    }


    @GetMapping("/employer/subscription-notice")
    public String employerSubNotice(Model model) {
        return "employer/subscription-notice";
    }

    @GetMapping("/employer/standard-cv")
    public String employerStandard(Model model) {
        return "employer/standard-cv";
    }

    @GetMapping("/employer/best-match-cv")
    public String employerBestMatch(Model model) {
        return "employer/best-match-cv";
    }

    @GetMapping("/employer/search-cv")
    public String employerSearchCV(Model model, Principal principal) {


        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("job", new Jobs());
        model.addAttribute("user", user);
        model.addAttribute("specialization", findBySpecialization());

        try {
            Employer employer = employerService.findByEmail(user.getUsername()).orElse(new Employer());
            model.addAttribute("employer", employer);

            //Check employer subscription
            EmployerSubscription subscription = employer.getEmployerSubscription();
            if (subscription == null) {
                return "redirect:/employer/subscription-notice";
            }

            String category = subscription.getCategory();

            switch (category) {
                case "Basic":
                    return "employer/search-cv";
                case "Standard":
                    return "redirect:/employer/standard-cv";
                case "Best Match":
                    return "redirect:/employer/best-match-cv";
                default:
                    return "employer/search-cv";
            }


        } catch (Exception e) {
            model.addAttribute("employer", new Employer());
        }


        return "employer/search-cv";
    }

    //COUNT(c.id), c.proffesionalTitile
    public List<JobSeekerSpecialization> findBySpecialization() {
        Optional<List<Object[]>> option = wService.findByWorkSpecialization();

        if (option.isPresent()) {

            List<Object[]> list = option.get();
            return list.stream().map(c ->

                            new JobSeekerSpecialization(
                                    JsfUtil.generateSerial(),
                                    Integer.valueOf(c[0].toString()),
                                    (String) c[1]))

                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    @RequestMapping(method = RequestMethod.POST, value = {"/recruiter/shortlist-candidates"})
    @ResponseBody
    public List<String> shortlistCandidate(@RequestBody ShortlistedCandidate obj, Principal principal) {
        Users user = JsfUtil.findOnline(principal);

        List<String> success = new ArrayList<>();
        List<ShortlistCart> q = obj.getCart();
        Integer job = obj.getJobId();

        q.stream().forEach((item) -> {
            success.add(shortlistCandidate(item.getId(), job, user));
        });

        return success;
    }


    @RequestMapping(method = RequestMethod.POST, value = {"/employer/shortlist-candidates"})
    @ResponseBody
    @Transactional
    public List<String> shortlistCandidateForEmployer(@RequestBody ShortlistedCandidate obj, Principal principal) {
        Users user = JsfUtil.findOnline(principal);

        Optional<Employer> opp = employerService.findByEmail(user.getUsername());
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Could not find employer");
        }
        Employer employer = opp.get();

        List<String> success = new ArrayList<>();
        List<ShortlistCart> q = obj.getCart();

        q.stream().forEach((item) -> {
            //Integer seekerId,Employer employer,String jobTitle
            success.add(shortlistEmployerCandidate(item.getId(), employer, obj.getJobtitle()));
        });

        return success;
    }


    @PostMapping("/shortlist-candidate")
    @ResponseBody
    public String shortlistCandidate(Integer seekerId, Integer jobId, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();

            if (user != null) {
//            Employee employee = user.getStaffId();
                Employer employer = employerService.findByEmail(user.getUsername()).get();
                JobSeeker seeker = null;
                Jobs job = null;

                Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
                Optional<Jobs> oppJob = service.findById(jobId);


                if (oppSeeker.isPresent()) {
                    if (oppJob.isPresent()) {
                        job = oppJob.get();
                    }
                    seeker = oppSeeker.get();

                    Optional<ShortlistedApplicants> oppShort = shortService.findByJobSeekerAndJobId(seeker, job);
                    if (oppShort.isPresent()) {
                        return "SHORTLISTED";
                    }
                    ShortlistedApplicants shortlst = new ShortlistedApplicants();
                    shortlst.setEntryDate(new Date());
                    shortlst.setJobSeekerId(seeker);
                    shortlst.setJobId(job);
                    shortlst.setEmployer(employer);

                    CompanyCv cv = sendCvToEmployer(job, seeker, employer.getCompanyName());

                    cvService.save(cv);
                    job.setJobStatus("Shortlisted");
                    service.save(job);
                    return shortService.save(shortlst) ? "SUCCESS" : "FAILED";
                }
                return "FAILED";
            }
            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }


    @PostMapping("/recruiter/shortlist-candidate")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> recruiterShortlistCandidate(@RequestBody RecShortlist shortlist, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            Employer employer = employerService.findByEmail(user.getUsername()).get();

            String seekerTrx = shortlist.getSeekerTrx();
            String jobTrx = shortlist.getJobTrx();
            String jobAppTrx = shortlist.getJobAppTrx();
            String type = shortlist.getType();


            Optional<JobSeeker> oppSeeker = seekerService.findByTransactionId(seekerTrx);
            Optional<Jobs> oppJob = service.findByTransactionId(jobTrx);
            Optional<JobApplications> oppJobApp = jobAppService.findByTransactionId(jobAppTrx);

            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could not find Job Seeker: " + seekerTrx);
            }
            if (!oppJob.isPresent()) {
                throw new BeanNotFoundException("Could not find Job : " + jobTrx);
            }
            if (!oppJobApp.isPresent()) {
                throw new BeanNotFoundException("Could not find Job Application: " + jobAppTrx);
            }

            JobSeeker seeker = oppSeeker.get();
            Jobs job = oppJob.get();
            JobApplications application = oppJobApp.get();
            application.setApplicationStatus(type);
            job.setJobStatus(type);


            Optional<ShortlistedApplicants> oppShort = shortService.findByJobSeekerAndJobId(seeker, job);
            if (oppShort.isPresent()) {
                return new ResponseEntity("SHORTLISTED", HttpStatus.OK);
            }

            ShortlistedApplicants shortlst = new ShortlistedApplicants();
            shortlst.setEntryDate(new Date());
            shortlst.setJobSeekerId(seeker);
            shortlst.setJobId(job);
            shortlst.setEmployer(employer);

            CompanyCv cv = sendCvToEmployer(job, seeker, employer.getCompanyName());
            cvService.save(cv);
            service.save(job);
            jobAppService.save(application);

            String status = shortService.save(shortlst) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //@Transactional
    public String shortlistCandidate(Integer seekerId, Integer jobId, Users user) {
        try {

            if (user == null) {
                return "FAILED";
            }
            Employer employer = employerService.findByEmail(user.getUsername()).get();

            Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
            if (!oppSeeker.isPresent()) {
                return "FAILED";
            }

            Optional<Jobs> oppJob = service.findById(jobId);
            if (!oppJob.isPresent()) {
                return "FAILED";
            }

            Jobs job = oppJob.get();
            JobSeeker seeker = oppSeeker.get();


            Optional<ShortlistedApplicants> oppShort = shortService.findByJobSeekerAndJobId(seeker, job);
            if (oppShort.isPresent()) {
                return "SHORTLISTED";
            }
            ShortlistedApplicants shortlst = new ShortlistedApplicants();
            shortlst.setEntryDate(new Date());
            shortlst.setJobSeekerId(seeker);
            shortlst.setJobId(job);
            shortlst.setEmployer(employer);

            CompanyCv cv = sendCvToEmployer(job, seeker, employer.getCompanyName());

            cvService.save(cv);
            job.setJobStatus("Shortlisted");
            service.save(job);

            return shortService.save(shortlst) ? "SUCCESS" : "FAILED";


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public String shortlistEmployerCandidate(Integer seekerId, Employer employer, String jobTitle) {
        try {


            Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
            if (!oppSeeker.isPresent()) {
                return "FAILED";
            }


            JobSeeker seeker = oppSeeker.get();


            Optional<EmployerShortlistedApplicants> oppShort = employerShortService.findByJobSeekerAndJobId(seeker, jobTitle);
            if (oppShort.isPresent()) {
                return "SHORTLISTED";
            }
            EmployerShortlistedApplicants shortlst = new EmployerShortlistedApplicants();
            shortlst.setEntryDate(LocalDate.now());
            shortlst.setJobSeekerId(seeker);
            shortlst.setJobTitle(jobTitle);
            shortlst.setShortListedBy(employer);

            CompanyCv cv = sendCvToEmployer(employer, seeker, String.valueOf(employer));

            cvService.save(cv);


            return employerShortService.save(shortlst) ? "SUCCESS" : "FAILED";


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @PostMapping("/emp-shortlist-candidate")
    @ResponseBody
    public boolean emplShortlisteCad(Integer seekerId, Integer jobId, Principal principal) {

        ///MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        //Users user2 = loginedUser.getUser();


        JobSeeker seeker = null;
        Jobs job = null;

        Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
        Optional<Jobs> oppJob = service.findById(jobId);

        if (oppSeeker.isPresent()) {
            if (oppJob.isPresent()) {
                job = oppJob.get();
            }
            seeker = oppSeeker.get();
            ShortlistedApplicants shortlst = new ShortlistedApplicants();
            shortlst.setEntryDate(new Date());
            shortlst.setJobSeekerId(seeker);
            shortlst.setJobId(job);
            //shortlst.setShortListedBy(employee);

            CompanyCv cv = sendCvToEmployer(job, seeker, null);

            cvService.save(cv);
            job.setJobStatus("Shortlisted");
            service.save(job);
            return shortService.save(shortlst);
        }
        return false;
    }

    @GetMapping("/emp-shortlist-candidates")
    @ResponseBody
    public boolean emplShortlisteCad2(Integer seekerId, Integer jobId, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        Users users = JsfUtil.findOnline(principal);

        Employer employer = employerService.findByEmail(users.getUsername()).get();
//        Employee employee = empService.
        JobSeeker seeker = null;
        Jobs job = null;

        Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);
        Optional<Jobs> oppJob = service.findById(jobId);


        if (oppSeeker.isPresent()) {
            if (oppJob.isPresent()) {
                job = oppJob.get();
            }
            seeker = oppSeeker.get();
            ShortlistedApplicants shortlst = new ShortlistedApplicants();
            shortlst.setEntryDate(new Date());
            shortlst.setJobSeekerId(seeker);
            shortlst.setJobId(job);
            shortlst.setApproved(false);
            shortlst.setStatus("Pending");
            shortlst.setEmployer(employer);

            CompanyCv cv = sendCvToEmployer(job, seeker, null);

            cvService.save(cv);
            job.setJobStatus("Shortlisted");
            service.save(job);
            return shortService.save(shortlst);
        }
        return false;
    }

    @GetMapping("/shortlisted-candidate/{transactionId}")
    public String shortlisteCad(Model model, @PathVariable String transactionId, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            Employee employee = user.getStaffId();


            Optional<Jobs> oppJob = service.findByTransactionId(transactionId);
            if (oppJob.isPresent()) {
                Jobs job = oppJob.get();


                model.addAttribute("job", job);
                List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
                model.addAttribute("recruiters", recruiters);
                model.addAttribute("emp", employee);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("timeAgo", new TimeAgo());

                List<ShortlistedApplicants> shorlisted = shortService.findByJobId(job);

                if (shorlisted == null) {
                    shorlisted = new ArrayList<>();
                }

                model.addAttribute("shorlisted", shorlisted);

                return "shortlisted-candidate";
            }

        }
        model.addAttribute("shorlisted", new ArrayList<>());
        model.addAttribute("recruiters", new ArrayList<>());
        model.addAttribute("emp", new Employee());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "shortlisted-candidate";
    }

    @GetMapping("/all-job-seekers")
    public String allJobSeekrs(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        Users user = loginedUser.getUser();
        if (user != null) {
            Employee employee = user.getStaffId();
            model.addAttribute("emp", employee);
        } else {
            model.addAttribute("emp", new Employee());
        }


        List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        List<JobSeeker> seekerList = seekerService.findAll();

        if (seekerList == null) {
            seekerList = new ArrayList<>();
        }

        model.addAttribute("seekerList", seekerList);

        return "all-job-seekers";
    }

    @GetMapping("/browse-by-date")
    public String broseByDate(Model model, Principal principal) {

        return "browse-by-date";
    }

    @GetMapping("/browse-by-shortlisted")
    public String browseByShortlisted(Model model, Principal principal,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer max) {

        Users user = JsfUtil.findOnline(principal);
        Employer employer = employerService.findByEmail(user.getUsername()).get();
        if (user != null) {
            Employee employee = user.getStaffId();
            model.addAttribute("emp", employee);
        } else {
            model.addAttribute("emp", new Employee());
        }

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        Page<ShortlistedApplicants> shorlisted = shortService.findAll(page, max);

        PageWrapper<ShortlistedApplicants> pages = new PageWrapper<>(shorlisted, "/browse-by-shortlisted");
        model.addAttribute("page", pages);
        List<ShortlistedApplicants> list = pages.getContent();

        list = list.stream()
                .filter(u -> u != null)
                .filter(u -> u.getJobSeekerId() != null)
                .filter((n -> n.getJobId() != null)).collect(Collectors.toList());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("joblist", list);
        List<ShortlistedApplicants> shortlistedApplicants = shortService.findByEmployer(employer);

        return "browse-by-shortlisted";
    }


    @GetMapping("/employer/browse-by-shortlisted/api")
    @ResponseBody
    public List<ShortlistedEmployerResponse> findShortlisted(Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        Optional<Employer> opp = employerService.findByEmail(user.getUsername());
//        if (!opp.isPresent()) {
//            throw new BeanNotFoundException("Invalid Employer Credential");
//        }
        Employer employer = opp.get();
//        List<EmployerShortlistedApplicants> shorlisted = employerShortService.findAll();
        List<ShortlistedApplicants> shortlistedApplicants = shortService.findByEmployer(employer);
        List<ShortlistedEmployerResponse> shortlistedEmployerResponses = new ArrayList<>();
        shortlistedApplicants.forEach(item->{
            ShortlistedEmployerResponse temp = new ShortlistedEmployerResponse();
            temp.setId(item.getId());
            temp.setJobId(item.getJobId().getId());
            temp.setJobTitle(item.getJobId().getJobTitle());
            temp.setSeekerId(item.getJobSeekerId().getId());
            temp.setSeekerCurrentCompany(item.getJobSeekerId().getCurrentCompany());
            temp.setSeekerName(item.getJobSeekerId().getFullName());
            temp.setSeekerPicture(item.getJobSeekerId().getPicFileName());
            temp.setSeekerProfessionalTitle(item.getJobSeekerId().getProffesionalTitile());
            temp.setLocation(item.getJobSeekerId().getCurrentLocation());
            temp.setApplicationStatus(item.getStatus());
            temp.setYesOfExperience(item.getJobSeekerId().getYearsOfExperience());
            shortlistedEmployerResponses.add(temp);
        });
        return shortlistedEmployerResponses;
    }

    @GetMapping("/employer/browse-by-shortlisted/api/{jobTile}")
    @ResponseBody
    public List<EmployerShortlisted> findShortlisted(Principal principal, @PathVariable String jobTile) {
        Users user = JsfUtil.findOnline(principal);
        Optional<Employer> opp = employerService.findByEmail(user.getUsername());
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Employer Credential");
        }
        Employer employer = opp.get();
        List<EmployerShortlistedApplicants> shorlisted = employerShortService.findByJobTitle(jobTile);

        return convertedShortlist(shorlisted);
    }


    @GetMapping("/employer/browse-by-shortlisted")
    public String employerBrowseByShortlisted(Model model, Principal principal) {
        return "employer/browse-by-shortlisted";
    }

    private CompanyCv sendCvToEmployer(Jobs job, JobSeeker seeker, String submittedBy) {
        CompanyCv cv = new CompanyCv();
        cv.setApproved(true);
        cv.setEmployerId(job.getPostedBy());
        cv.setEntryDate(new Date());
        cv.setJobSeekerId(seeker);
        cv.setProcessed(Boolean.TRUE);

        if (submittedBy != null) {
            cv.setSubmitedBy(submittedBy);
        }


        return cv;
    }

    private CompanyCv sendCvToEmployer(Employer employer, JobSeeker seeker, String submittedBy) {
        CompanyCv cv = new CompanyCv();
        cv.setApproved(true);
        cv.setEmployerId(employer);
        cv.setEntryDate(new Date());
        cv.setJobSeekerId(seeker);
        cv.setProcessed(Boolean.TRUE);

        if (submittedBy != null) {
            cv.setSubmitedBy(submittedBy);
        }


        return cv;
    }

    private CompanyCv sendCvToEmployer(Employer employer, Integer seekerId) {

        Optional<JobSeeker> oppSeeker = seekerService.findById(seekerId);


        CompanyCv cv = new CompanyCv();
        cv.setApproved(true);
        cv.setEmployerId(employer);
        cv.setEntryDate(new Date());

        cv.setProcessed(Boolean.TRUE);

        cv.setSubmitedBy(employer.getCompanyName());
        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            cv.setJobSeekerId(seeker);
        }

        return cv;
    }


    @GetMapping("/jobs-by-qualifications-details")
    public String jobDetailsByQualications(Model model, String ct, Principal principal, @RequestParam(defaultValue = "1") Integer page) {

        Page<Jobs> listPages = service.findByPublishedQualification(ct, page);


        PageWrapper<Jobs> pages = new PageWrapper<>(listPages, "/jobs-by-qualifications-details?ct=" + ct);
        model.addAttribute("page", pages);
        model.addAttribute("jreqList", pages.getContent());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", 10);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        model.addAttribute("message", ct);

        return "jobs-by-qualifications-details";
    }


    @GetMapping("/job-by-category")
    public String jobDetailsByCat(Model model, String ct, Principal principal, @RequestParam(defaultValue = "1") Integer page) {

        Page<Jobs> listPages = service.findByPublishedCategory(ct, page);
        PageWrapper<Jobs> pages = new PageWrapper<>(listPages, "/job-by-category?ct=" + ct);
        model.addAttribute("page", pages);
        model.addAttribute("jreqList", pages.getContent());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", 10);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        model.addAttribute("message", ct);


        return "job-by-category-details";
    }

    @GetMapping("/job-by-skills")
    public String findByPreferedSkills(Model model, String ct, Principal principal, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer max) {


        List<Jobs> list1 = service.findByPreferedSkills(ct);
        Page<JobSearch> jobSearch = createJobSearch(list1, PageRequest.of(page, max));

        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", jobSearch);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        //List<CategoryCount> clist = findCategories();
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();


        model.addAttribute("elements", elementsMsge(jobSearch.getTotalElements()));

        model.addAttribute("catlist", findJobCategories());
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", ct);
        model.addAttribute("url", "/job-by-skills?ct=" + ct);


        return "browse-job-filter-list";

        // return "job-by-category";


    }

    @GetMapping("/job-by-city")
    public String jobDetailsByCity(Model model, @RequestParam(defaultValue = "Accra") String ct, Principal principal, @RequestParam(defaultValue = "1") Integer page) {


        Page<Jobs> pages = service.findByJobCity(ct, page);
        List<Jobs> jreqList = pages.getContent();
        model.addAttribute("jreqList", jreqList);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("currentPage", page);
        model.addAttribute("location", ct);
        model.addAttribute("totalPage", pages.getTotalPages());


        return "job-by-city";
    }

    @GetMapping("/job-by-country")
    public String jobDetailsByCountry(Model model, String ct, Principal principal, @RequestParam(defaultValue = "0") Integer page) {


        Page<Jobs> jreqList = service.findByCountry(ct, page);

        model.addAttribute("jreqList", jreqList);

        List<Testimonial> testimonyList = testimonyService.findByStatus(true);
        if (!testimonyList.isEmpty()) {

            Random random = new Random();
            Testimonial test = testimonyList.get(random.nextInt(testimonyList.size()));
            model.addAttribute("testimonyList", testimonyList);
            model.addAttribute("test", test);
        }


        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "job-by-country";
    }


    @GetMapping("/findByCategoryLike")
    @ResponseBody
    public List<ShortlistApp> findByCategoryLike(String pf) {

        List<ShortlistApp> applist = new ArrayList<>();
        List<Jobs> list = service.findByCategoryLike(pf);

        list.stream().forEach((item) -> {
            ShortlistApp sh = new ShortlistApp();
            sh.setFullName(item.getProfession());
            sh.setGender(item.getCategory());
            sh.setMaritalStatus(item.getJobCity());

            applist.add(sh);
        });

        return applist;
    }

    @RequestMapping("/findJobSearch")
    @ResponseBody
    public Page<JobSearch> findJobSearch(String q, @RequestParam(defaultValue = "0") Integer page, Model model) {
        List<Jobs> list1 = userSearch.searchByKeyword(q, page, 10);

        Page<JobSearch> jobSearch = createJobSearch(list1, PageRequest.of(page, 10));

        return jobSearch;
    }

    @RequestMapping("/findJobs88")
    public String findJobs(@RequestParam(defaultValue = "Information Technology") String q, Model model, @RequestParam(defaultValue = "0") Integer page) {

        try {
            List<Jobs> list1 = userSearch.searchByKeyword(q, page, 10);

            Page<JobSearch> jobSearch = createJobSearch(list1, PageRequest.of(page, 10));

            List<String> cats = service.findByCategories();
            model.addAttribute("cats", cats);
            model.addAttribute("list", jobSearch);

            model.addAttribute("proflist", service.findByDistictProfession());
            model.addAttribute("locationList", service.findByDistictLocation());

            model.addAttribute("currentPage", page);
            // model.addAttribute("countryList", getCountries());
            Long element = jobSearch.getTotalElements();

            //List<CategoryCount> clist = findCategories();
            List<CategoryCount> clist2 = findCountries();
            List<CategoryCount> clist3 = findCompanies();


            model.addAttribute("elements", elementsMsge(jobSearch.getTotalElements()));

            model.addAttribute("catlist", findJobCategories());
            model.addAttribute("countrylist", clist2);
            model.addAttribute("companylist", clist3);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());

            model.addAttribute("currentPage", page);
            model.addAttribute("ct", q);
            model.addAttribute("url", "/findJobs?q=" + q);


            return "browse-job-filter-list";
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "browse-job-filter-list";
    }

    @RequestMapping(value = {"/search-jobs", "/findJobs"})
    public String searchJobs2(@RequestParam(required = false) String q, Model model, @RequestParam(defaultValue = "0") Integer page) {

        try {
            if (q == null || "".equals(q)) {
                defaultModel(model, page, q);
                return "search-jobs";
            }

            List<Jobs> list1 = userSearch.find(q);
            Page<JobSearch> jobSearch = createJobSearch(list1, PageRequest.of(page, 10));
            int totalFound = list1.size();


            model.addAttribute("totalFound", totalFound);
            model.addAttribute("list", jobSearch);
            model.addAttribute("currentPage", page);
            // Long element = jobSearch.getTotalElements();
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());

            List<CategoryCount> clist = findCategories(2);
            model.addAttribute("catlist", clist);
            model.addAttribute("currentPage", page);
            model.addAttribute("ct", q);
            model.addAttribute("url", "/findJobs?q=" + q);


            return "search-jobs";
        } catch (NullPointerException e) {
            defaultModel(model, page, q);
            e.printStackTrace();
        } catch (Exception e) {
            defaultModel(model, page, q);
        }
        return "search-jobs";
    }


    // @RequestMapping(value = {"/search-jobs","/findJobs"})
//    public String searchJobs(@RequestParam(defaultValue = "DefaultSearch")String q,Model model,@RequestParam(defaultValue = "0")Integer page) {
//
//        return "search-jobs";
//    }


    @RequestMapping("/find-by-company")
    public String findJobsByCompany(@RequestParam(defaultValue = "Information Technology") String q, Model model, @RequestParam(defaultValue = "0") Integer page) {

        try {
            Page<Jobs> list1 = service.findByNameOfCompany(q, page);

            Page<JobSearch> jobSearch = createJobSearch(list1.getContent(), PageRequest.of(page, 10));

            List<String> cats = service.findByCategories();
            model.addAttribute("cats", cats);
            model.addAttribute("list", jobSearch);

            model.addAttribute("proflist", service.findByDistictProfession());
            model.addAttribute("locationList", service.findByDistictLocation());

            model.addAttribute("currentPage", page);
            // model.addAttribute("countryList", getCountries());
            Long element = jobSearch.getTotalElements();

            //List<CategoryCount> clist = findCategories();
            List<CategoryCount> clist2 = findCountries();
            List<CategoryCount> clist3 = findCompanies();


            model.addAttribute("elements", elementsMsge(jobSearch.getTotalElements()));

            model.addAttribute("catlist", findJobCategories());
            model.addAttribute("countrylist", clist2);
            model.addAttribute("companylist", clist3);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());

            model.addAttribute("currentPage", page);
            model.addAttribute("ct", q);
            model.addAttribute("url", "find-by-company");

            return "browse-job-filter-list";
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "browse-job-filter-list";
    }

    @GetMapping("/browse-jobs")
    public String browseJobs(Model model, @RequestParam(defaultValue = "All Functions") String profession,
                             @RequestParam(defaultValue = "All Locations") String location,
                             @RequestParam(defaultValue = "0") int page) {


        Page<Jobs> list2 = service.findByProfessionAndLocation(profession, location, page);
        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, 10));
        long elements = list.getTotalElements();
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        // model.addAttribute("countryList", getCountries());

        //List<CategoryCount> clist = findCategories();
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("elements", elementsMsge(elements));

        model.addAttribute("catlist", findJobCategories());
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", profession);
        model.addAttribute("url", "/browse-jobs?profession=" + profession + "&location=" + location);

        return "browse-job-filter-list";
    }

    @GetMapping("/all-jobs")
    public String allJobs(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") Integer max) {

        Page<Jobs> list2 = service.findAll(page, max);
        Page<JobSearch> listOfSearch = createJobSearch(list2, PageRequest.of(page, 10));

        PageWrapper<JobSearch> pages = new PageWrapper<>(listOfSearch, "/all-jobs");
        model.addAttribute("page", pages);

        List<JobSearch> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);

        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);

        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", findJobCategories());
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("ct", "");
        model.addAttribute("url", "/all-jobs");

        return "browse-job-filter-list";
    }

    @GetMapping("/browse-jobs-company")
    public String browseJobsByCompay(Model model, String company, @RequestParam(defaultValue = "0") int page) {

        Page<Jobs> list2 = service.findByNameOfCompany(company, page);
        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, 10));
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        // model.addAttribute("countryList", getCountries());

        List<CategoryCount> clist = findCategories(1);
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", company);
        model.addAttribute("url", "/browse-jobs?company=" + company);

        return "browse-job-filter-list";
    }

    @GetMapping("/browse-jobs-country")
    public String browseJobsByCountry(Model model, String country, @RequestParam(defaultValue = "0") int page) {

        Page<Jobs> list2 = service.findByCountry(country, page);
        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, 10));
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        List<CategoryCount> clist = findCategories(1);
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", country);
        model.addAttribute("url", "/browse-jobs-country?country=" + country);

        return "browse-job-filter-list";
    }

    @GetMapping("/browse-jobs-category")
    public String browseJobsByCategory(Model model, String category, @RequestParam(defaultValue = "0") int page) {

        Page<Jobs> pges = service.findByCategory(category, page);
        Page<JobSearch> list = createJobSearch(pges, PageRequest.of(page, 10));
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        List<CategoryCount> clist = findCategories(1);
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", category);
        model.addAttribute("url", "/browse-jobs-category?category=" + category);

        return "browse-job-filter-list";
    }


    @GetMapping("/joblisting")
    public String jobs(Model model, @RequestParam(defaultValue = "10") Integer end, @RequestParam(defaultValue = "1") int page) {

        Page<Jobs> list2 = service.findAll(page, end);
        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, end));
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        List<CategoryCount> clist = findCategories(1);
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);

        model.addAttribute("currentPage", page);
        model.addAttribute("ct", end);
        model.addAttribute("url", "/joblisting");

        return "browse-job-filter-list";
    }


    @GetMapping("/browse-jobs-salary")
    public String browseJobsBySalary(Model model,
                                     @RequestParam(defaultValue = "1") BigDecimal start,
                                     @RequestParam(defaultValue = "10000") BigDecimal end,
                                     @RequestParam(defaultValue = "1") int page) {

        Page<Jobs> list2 = service.findByRenumeration(start, end, page);
        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, 10));
        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        //model.addAttribute("countryList", getCountries());

        //List<CategoryCount> clist = findCategories();
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", findJobCategories());
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("url", "/browse-jobs-salary?start=" + start + "&end=" + end);
        model.addAttribute("currentPage", page);
        model.addAttribute("ct", start);

        return "browse-job-filter-list";
    }

    private List<CategoryCount> findCountries() {
        List<CategoryCount> list = new ArrayList<>();

        Optional<List<Object[]>> option = service.findByCountryCount();

        if (option.isPresent()) {
            List<Object[]> objects = option.get();

            objects.stream().forEach((item) -> {

                CategoryCount dash = new CategoryCount();
                dash.setId(new Date().getTime());

                Object obj = item[0];
                Long lg = Long.valueOf(obj.toString());
                dash.setCount(lg);
                dash.setCountry((String) item[1]);


                list.add(dash);
            });

        }
        return list;
    }

    private List<CategoryCount> findCompanies() {
        List<CategoryCount> list = new ArrayList<>();

        Optional<List<Object[]>> option = service.findByCompanyCount();

        if (option.isPresent()) {
            List<Object[]> objects = option.get();

            objects.stream().forEach((item) -> {

                CategoryCount dash = new CategoryCount();
                dash.setId(new Date().getTime());

                Object obj = item[0];
                Long lg = Long.valueOf(obj.toString());
                dash.setCount(lg);
                dash.setCountry((String) item[1]);


                list.add(dash);
            });

        }
        return list;
    }

    public List<String> getCountries() {

        List<String> list = service.findByCountry();

        if (list == null) {
            List<String> str = new ArrayList<>();
            return str;
        }

        return list;
    }

    private String elementsMsge(Long elements) {
        int no = elements.intValue();
        switch (no) {
            case 0:
            case 1:
                return no + " Job found";
            default:
                return no + " Jobs found";
        }
    }

    private String elementsMsge(int no) {

        switch (no) {
            case 0:
            case 1:
                return no + " Job found";
            default:
                return no + " Jobs found";
        }
    }


    @GetMapping("/create-sample-jobs")
    @ResponseBody
    public Jobs createSampleJob(String fn, String cat, String ctry, String em, String ex, String jc, String title, String lc, String pr, String cmp, String reg, String tel) {

        Employer emp = employerService.findById(3).orElse(new Employer());

        Jobs jobs = new Jobs();
        jobs.setAssigned(Boolean.FALSE);
        jobs.setCategory(cat);
        jobs.setCountry(ctry);
        jobs.setEmail(em);
        jobs.setExperience(ex);
        jobs.setExpireDate(new Date());
        jobs.setGender("Any");
        jobs.setHowToApply("Please upload cv and cover letter");
        jobs.setJobCity(jc);
        jobs.setJobCountry(ctry);
        jobs.setJobDescription("Some job description");
        jobs.setJobTitle(title);
        jobs.setLocation(lc);
        jobs.setMinQualification("Bachelors Degree");
        jobs.setMinYearsExperience(2);
        jobs.setName(pr);
        jobs.setNameOfCompany(cmp);
        jobs.setNatureOfContract("Permanent");
        jobs.setNoNeeded(5);
        jobs.setPostedBy(emp);
        jobs.setPostedDate(new Date());
        jobs.setProfession(pr);
        jobs.setPaymentDate(new Date());
        jobs.setPublishedDate(new Date());
        jobs.setPublishedBy("Admin");
        jobs.setRegion(reg);
        jobs.setRenumeration(BigDecimal.valueOf(3000));
        jobs.setShowCompanyName(true);
        jobs.setTelephone(tel);
        jobs.setToAge(25);
        jobs.setToRenumeration(BigDecimal.valueOf(8000));
        jobs.setTransactionId(JsfUtil.generateSerial());

        service.save(jobs);

        return jobs;
    }

    private Page<JobSearch> createJobSearch(List<Jobs> list, Pageable pageable) {
        List<JobSearch> serchlist = new ArrayList<>();
        list.stream().forEach((item) -> {

            JobSearch js = new JobSearch();
            Employer emp = item.getPostedBy();
            PostedBy postedBy = JsfUtil.createPostedBy(emp);

            js.setPostedBy(postedBy);
            if (emp != null) {
                js.setFileName(emp.getFileName());
            }

            js.setId(item.getId());
            js.setCategory(item.getCategory());
            js.setCountry(item.getCountry());
            js.setExpireDate((item.getExpireDate()));
            js.setNatureOfContract(item.getNatureOfContract());
            js.setLocation(item.getLocation());
            js.setPublishedDate((item.getPublishedDate()));
            js.setRenumeration(item.getRenumeration());
            js.setToRenumeration(item.getToRenumeration());
            js.setShowCompanyName(item.getShowCompanyName());
            js.setProfession(item.getProfession());
            js.setDetails(item.getJobDescription());
            js.setDutiesAndRespo(getRequirement(item, duties));
            js.setSkillls(getRequirement(item, requirements));
            js.setSlot(item.getNoNeeded());
            js.setGender(item.getGender());
            js.setSpecialRequirements(getRequirement(item, special));
            js.setQualification(item.getMinQualification());
            js.setExpireDate((item.getExpireDate()));
            js.setHowToApply(item.getHowToApply());
            js.setIndustry(item.getIndustry());
            js.setMinYearsExperience(item.getMinYearsExperience());
            js.setTelephone(item.getTelephone());
            js.setTransactionId(item.getTransactionId());


            serchlist.add(js);

        });

        return new PageImpl<>(serchlist, pageable, serchlist.size());
        //return serchlist;
    }

    private Page<JobSearch> createJobSearch(Page<Jobs> list, Pageable pageable) {
        List<JobSearch> serchlist = new ArrayList<>();

        list.stream().forEach((item) -> {

            JobSearch js = new JobSearch();
            Employer emp = item.getPostedBy();
            PostedBy postedBy = JsfUtil.createPostedBy(emp);

            js.setPostedBy(postedBy);
            if (emp != null) {
                js.setFileName(emp.getFileName());
            }

            js.setId(item.getId());
            js.setCategory(item.getCategory());
            js.setCountry(item.getCountry());
            js.setExpireDate((item.getExpireDate()));
            String natureOfContract = item.getNatureOfContract();
            if (natureOfContract == null) {
                natureOfContract = "Not Specified";
            }
            js.setNatureOfContract(natureOfContract);
            js.setLocation(item.getLocation());
            js.setPublishedDate((item.getPublishedDate()));
            BigDecimal renu = item.getRenumeration();
            if (renu == null) {
                renu = BigDecimal.ZERO;
            }
            BigDecimal renu2 = item.getToRenumeration();
            if (renu2 == null) {
                renu2 = BigDecimal.ZERO;
            }
            js.setRenumeration(renu);
            js.setToRenumeration(renu2);
            Boolean showcomp = item.getShowCompanyName();
            if (showcomp == null) {
                showcomp = false;
            }
            js.setShowCompanyName(showcomp);
            js.setProfession(item.getProfession());
            js.setTransactionId(item.getTransactionId());

            serchlist.add(js);

        });

        return new PageImpl<>(serchlist, pageable, serchlist.size());

    }

    @GetMapping("/my-jsb")
    @ResponseBody
    public Page<Jobs> myJos(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        return service.findAll(page, max);
    }


    @GetMapping("/find-jobseeker")
    @ResponseBody
    public List<SearchedSeeker> findJobSeekes(String q) {

        List<JobSeeker> list = userSearch.search(q);
        /**
         *  fullName,  gender,  email,  primaryContact,  proffesionalTitile,
         currentLocation,  currentCompany,  employmentType,  keySkills,
         dob,  maritalStatus,  description,  picFileName,  id
         */
        return list.stream().map(c ->
                        new SearchedSeeker(
                                c.getFullName(),
                                findDetails(c.getGender()),
                                findDetails(c.getEmail()),
                                findDetails(c.getPrimaryContact()),
                                findDetails(c.getProffesionalTitile()),
                                findDetails(c.getCurrentLocation()),
                                findDetails(c.getCurrentCompany()),
                                findDetails(c.getEmploymentType()),
                                c.getKeySkills(),
                                c.getDob(),
                                findDetails(c.getMaritalStatus()),
                                findDetails(c.getDescription()),
                                JsfUtil.findfile(c.getPicFileName()),
                                c.getId(),
                                c.getYearsOfExperience(),
                                c.getTransactionId(),
                                findResume(c),
                                null
                        ))
                .collect(Collectors.toList());


    }

    public SearchedSeeker convertToSearchedSeeker(JobSeeker c) {
        List<String> jobTitle = new ArrayList<>();
        return new SearchedSeeker(
                c.getFullName(),
                findDetails(c.getGender()),
                findDetails(c.getEmail()),
                findDetails(c.getPrimaryContact()),
                findDetails(c.getProffesionalTitile()),
                findDetails(c.getCurrentLocation()),
                findDetails(c.getCurrentCompany()),
                findDetails(c.getEmploymentType()),
                c.getKeySkills(),
                c.getDob(),
                findDetails(c.getMaritalStatus()),
                findDetails(c.getDescription()),
                JsfUtil.findfile(c.getPicFileName()),
                c.getId(),
                c.getYearsOfExperience(),
                c.getTransactionId(),
                findResume(c),
                jobTitle
        );
    }


    public List<SearchedSeeker> convertToSearchedSeeker(List<JobSeeker> list, List<String> jobTiles) {
        return list.stream().map(c ->
                        new SearchedSeeker(
                                c.getFullName(),
                                findDetails(c.getGender()),
                                findDetails(c.getEmail()),
                                findDetails(c.getPrimaryContact()),
                                findDetails(c.getProffesionalTitile()),
                                findDetails(c.getCurrentLocation()),
                                findDetails(c.getCurrentCompany()),
                                findDetails(c.getEmploymentType()),
                                c.getKeySkills(),
                                c.getDob(),
                                findDetails(c.getMaritalStatus()),
                                findDetails(c.getDescription()),
                                JsfUtil.findfile(c.getPicFileName()),
                                c.getId(),
                                c.getYearsOfExperience(),
                                c.getTransactionId(),
                                findResume(c),
                                jobTiles
                        ))
                .collect(Collectors.toList());
    }

    public List<SearchedSeeker> convertToSearchedSeeker(List<JobSeeker> list) {
        return list.stream().map(c ->
                        new SearchedSeeker(
                                c.getFullName(),
                                findDetails(c.getGender()),
                                findDetails(c.getEmail()),
                                findDetails(c.getPrimaryContact()),
                                findDetails(c.getProffesionalTitile()),
                                findDetails(c.getCurrentLocation()),
                                findDetails(c.getCurrentCompany()),
                                findDetails(c.getEmploymentType()),
                                c.getKeySkills(),
                                c.getDob(),
                                findDetails(c.getMaritalStatus()),
                                findDetails(c.getDescription()),
                                JsfUtil.findfile(c.getPicFileName()),
                                c.getId(),
                                c.getYearsOfExperience(),
                                c.getTransactionId(),
                                findResume(c),
                                null
                        ))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = {"/admin/findJobSeekerBySpecialization", "/public/findJobSeekerBySpecialization"})
    @ResponseBody
    public Page<SearchedSeeker> findJobSeekerBySpecialization(@RequestParam List<String> q,
                                                              @RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer max) {

        List<WorkExperience> listOfWkexp = wService.findByDesignationIn(q);
        List<JobSeeker> listOfSeekers = findJobSeekersFromWk(listOfWkexp);
        //  seekerService.findJobSeekerBySpecialization(q);

        List<SearchedSeeker> list = convertToSearchedSeeker(listOfSeekers, new ArrayList<>());

        Pageable pageable = PageRequest.of(page, max, Sort.by(Sort.Direction.DESC, "proffesionalTitile"));
        Page<SearchedSeeker> pageList = new PageImpl<>(list, pageable, list.size());

        return pageList;


    }

    public String constructFileName(Jobs job) {
        try {
            if (job == null) {
                return "icon1.png";
            }
            Boolean show = job.getShowCompanyName();
            if (show == null) {
                return "icon1.png";
            }
            if (!show) {
                return "icon1.png";
            }

            Employer fn = job.getPostedBy();
            if (fn == null) {
                return "icon1.png";
            }

            String fileName = fn.getFileName();
            if (fileName == null) {
                return "icon1.png";
            }

            return fileName;

        } catch (Exception e) {
            return "icon1.png";

        }

    }

    public String constructLocation(Jobs job) {
        try {
            if (job == null) {
                return "No Location";
            }

            String location = job.getLocation();
            String country = job.getCountry();

            List<String> ls = new ArrayList<>();
            if (location != null) {
                ls.add(location);
            }
            if (country != null) {
                ls.add(country);
            }


            return JsfUtil.convertToString(ls);
        } catch (Exception e) {
            return "No Location";
        }
    }

    public String findDuration(Jobs job) {

        TimeAgo tm = new TimeAgo();
        return tm.toDuration(job.getPublishedDate());
    }

    public String findCompanyName(Jobs job) {
        try {
            if (job == null) {
                return "Reputable Company";
            }
            Boolean show = job.getShowCompanyName();
            if (show == null) {
                return "Reputable Company";
            }
            if (!show) {
                return "Reputable Company";
            }

            Employer emp = job.getPostedBy();
            if (emp == null) {
                return "Reputable Company";
            }
            return emp.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Reputable Company";
        }
    }

    @GetMapping("/findSeekerJob")
    @ResponseBody
    public List<SeekerJob> findSeekerJob(@RequestParam List<String> q) {
        List<Jobs> list = service.findByCategoryIn(q);

        if (list == null) {
            return new ArrayList<>();
        }

        //id, profession, location,  fileName, String postedBy, String natureOfContract, String duration
        return list.stream().map(c ->
                        new SeekerJob(
                                c.getId(),
                                JsfUtil.findDetails(c.getProfession()),
                                constructLocation(c),
                                constructFileName(c),
                                findCompanyName(c),
                                JsfUtil.findDetails(c.getNatureOfContract()),
                                findDuration(c),
                                c.getTransactionId()

                        ))
                .collect(Collectors.toList());


    }


    @GetMapping("/find-seeker-job-above-five")
    @ResponseBody
    public List<SeekerJob> findSeekerJob(@RequestParam(defaultValue = "5000") BigDecimal salary) {


        List<Jobs> list = service.findByRenumerationAbove(salary);

        if (list == null) {
            return new ArrayList<>();
        }

        //id, profession, location,  fileName, String postedBy, String natureOfContract, String duration
        return list.stream().map(c ->
                        new SeekerJob(
                                c.getId(),
                                JsfUtil.findDetails(c.getProfession()),
                                constructLocation(c),
                                constructFileName(c),
                                findCompanyName(c),
                                JsfUtil.findDetails(c.getNatureOfContract()),
                                findDuration(c),
                                c.getTransactionId()

                        ))
                .collect(Collectors.toList());


    }


    @GetMapping("/find-seeker-job-by-experience-above")
    @ResponseBody
    public SeekerJobHeader findSeekerJobByExpAbove(@RequestParam(defaultValue = "10") Integer exp, @RequestParam(defaultValue = "1") Integer page) {


        Page<Jobs> pages = service.findByExperienceAbove(exp, page);
        List<Jobs> list = pages.getContent();

        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();
        Integer number = pages.getNumber();


        if (list == null) {
            return new SeekerJobHeader();
        }

        //id, profession, location,  fileName, String postedBy, String natureOfContract, String duration
        List<SeekerJob> lines = list.stream().map(c ->
                        new SeekerJob(
                                c.getId(),
                                JsfUtil.findDetails(c.getProfession()),
                                constructLocation(c),
                                constructFileName(c),
                                findCompanyName(c),
                                JsfUtil.findDetails(c.getNatureOfContract()),
                                findDuration(c),
                                c.getTransactionId()

                        ))
                .collect(Collectors.toList());

        return new SeekerJobHeader(page, 10, lines, numberOfElements, totalPages, number);


    }

    @GetMapping("/find-seeker-job-by-experience")
    @ResponseBody
    public SeekerJobHeader findSeekerJobByExp(@RequestParam(defaultValue = "1") Integer start,
                                              @RequestParam(defaultValue = "6") Integer end,
                                              @RequestParam(defaultValue = "1") Integer page) {


        Page<Jobs> pages = service.findByExperience(start, end, page);
        List<Jobs> list = pages.getContent();

        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();
        Integer number = pages.getNumber();


        if (list == null) {
            return new SeekerJobHeader();
        }

        //id, profession, location,  fileName, String postedBy, String natureOfContract, String duration
        List<SeekerJob> lines = list.stream().map(c ->
                        new SeekerJob(
                                c.getId(),
                                JsfUtil.findDetails(c.getProfession()),
                                constructLocation(c),
                                constructFileName(c),
                                findCompanyName(c),
                                JsfUtil.findDetails(c.getNatureOfContract()),
                                findDuration(c),
                                c.getTransactionId()

                        ))
                .collect(Collectors.toList());

        return new SeekerJobHeader(page, 10, lines, numberOfElements, totalPages, number);


    }


    @GetMapping("/find-seeker-job-by-salary")
    @ResponseBody
    public List<SeekerJob> findSeekerJob(@RequestParam(defaultValue = "1") BigDecimal start, @RequestParam(defaultValue = "10000") BigDecimal end) {


        List<Jobs> list = service.findByRenumeration(start, end);

        if (list == null) {
            return new ArrayList<>();
        }

        //id, profession, location,  fileName, String postedBy, String natureOfContract, String duration
        return list.stream().map(c ->
                        new SeekerJob(
                                c.getId(),
                                JsfUtil.findDetails(c.getProfession()),
                                constructLocation(c),
                                constructFileName(c),
                                findCompanyName(c),
                                JsfUtil.findDetails(c.getNatureOfContract()),
                                findDuration(c),
                                c.getTransactionId()

                        ))
                .collect(Collectors.toList());


    }

    private List<String> getRequirement(Jobs job, String type) {
        try {
            if (job == null) {
                return new ArrayList<>();
            }

            switch (type) {
                case "duties":
                    String dties = job.getDutiesAndRespo();
                    if (dties == null) {
                        return new ArrayList<>();
                    }
                    return Arrays.asList(dties.split(","));


                case "requirements":
                    String req = job.getRequirements();
                    if (req == null) {
                        return new ArrayList<>();
                    }
                    return Arrays.asList(req.split(","));


                case "special":
                    String sp = job.getPrefSkillsAttribute();
                    if (sp == null) {
                        return new ArrayList<>();
                    }
                    return Arrays.asList(sp.split(","));

                default:
                    return new ArrayList<>();
            }
        } catch (NullPointerException n) {
            return new ArrayList<>();
        }

    }



    @PostMapping("/re-assign")
    @ResponseBody
    public boolean reassign(Integer jobId, Integer recruiterId) {
        Optional<Jobs> oppJob = service.findById(jobId);
        if (oppJob.isPresent()) {
            Jobs job = oppJob.get();

            Optional<Employee> emp = empService.findById(recruiterId);
            if (emp.isPresent()) {
                Employee assignedTo = emp.get();
                job.setAssignedDate(new Date());
                job.setAssigned(true);
                job.setAssignedTo(assignedTo);

                return service.save(job);
            }
        }

        return false;
    }


    public List<RecruiterAssignment> findByAssignmentCount() {
        try {
            List<RecruiterAssignment> assigmentsList = new ArrayList<>();
            Optional<List<Object[]>> option = service.findByAssignedCount();

            if (option.isPresent()) {

                List<Object[]> objects = option.get();

                objects.stream().forEach((item) -> {

                    RecruiterAssignment dash = new RecruiterAssignment();
                    dash.setId(new Date().getTime());

                    Object obj = item[0];
                    Long lg = Long.valueOf(obj.toString());
                    dash.setCount(lg);
                    Employee emp = (Employee) item[1];
                    dash.setEmployee(emp);

                    assigmentsList.add(dash);
                });
            }

            return assigmentsList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/assignment-list")
    public String assignmentList(Model model, @RequestParam(required = false) Integer id) {

        Optional<Employee> oppemp = empService.findById(id);
        if (oppemp.isPresent()) {
            Employee b = oppemp.get();
            List<Jobs> list = service.findByAssignedTo(b);
            model.addAttribute("list", list);
        } else {
            model.addAttribute("list", new ArrayList<>());
        }

        List<Employee> recruiters = empService.findRecruiter("ROLE_RECRUITER");
        model.addAttribute("recruiters", recruiters);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        return "assignment-list";
    }

    @GetMapping("/deleteJob/{id}")
    @ResponseBody
    public boolean deleteJob(@PathVariable Integer id, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            if (user == null) {
                return false;
            }
            Employee emp = user.getStaffId();
            if (emp == null) {
                return false;
            }

            return service.deleteById(id);
        } catch (Exception ex) {
            Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    @GetMapping("/find-positions")
    @ResponseBody
    public List<String> findPos() {

        List<String> prof = new ArrayList<>();
        List<Jobs> list = service.findAll();
        list.stream().forEach((item) -> {
            prof.add(item.getProfession());
        });

        return prof;
    }

    @GetMapping("/find-job-by-city")
    @ResponseBody
    public String findJobsByCity(@RequestParam(defaultValue = "Accra") String city) {
        List<Jobs> jobs = service.findByLocation(city);
        if (jobs.isEmpty()) {
            return "0 Job";
        }
        int size = jobs.size();
        if (size > 1) {
            return size + " Jobs";
        }
        return size + " Job";
    }

    private List<String> findSkills(JobSeeker seeker) {
        return Arrays.asList("Java Developer", "Web Developer", "Front End Develoer", "Mathematician");
    }

    private List<String> findLangs(JobSeeker seeker) {
        return Arrays.asList("Englis", "Spanish", "French", "Indian");
    }


    //
    @GetMapping("/add-tocart")
    @ResponseBody
    public Integer addToCart(Integer seekerId, Integer emp, HttpSession session) {
        Set<Cart> list = new HashSet<>();
        String txId = JsfUtil.generateSerial();
        session.setAttribute("usersList", list);
        session.setAttribute("txId", txId);

        Optional<Employer> oppEmp = employerService.findById(emp);
        if (oppEmp.isPresent()) {
            Employer employer = oppEmp.get();
            Subscription sub = employer.getSubscriptionId();

            if (sub != null) {
                String type = sub.getName();
                if (type != null) {
                    if (type.equals("Premium")) {

                        Cart cart = new Cart(seekerId, emp, txId);
                        list.add(cart);
                        session.setAttribute("usersList", list);
                        CompanyCv cv = sendCvToEmployer(employer, seekerId);
                        cvService.save(cv);

                    } else {

                        Cart cart = new Cart(seekerId, emp, txId);
                        Integer count = sub.getCvCount();
                        if (list.size() <= count) {
                            list.add(cart);
                            session.setAttribute("usersList", list);
                            CompanyCv cv = sendCvToEmployer(employer, seekerId);

                            cvService.save(cv);
                        }
                    }
                }

            }
        }

        Set<Cart> carts = (Set<Cart>) session.getAttribute("usersList");
        return carts.size();
        // return list;
    }

    @GetMapping("/remove-tocart")
    @ResponseBody
    public Integer removeFromCart(Integer seekerId, HttpSession session) {

        Set<Cart> carts = (Set<Cart>) session.getAttribute("usersList");
        Cart cart = new Cart(seekerId);
        carts.remove(cart);
        CompanyCv cv = findCompanyCv(seekerId);
        if (cv != null) {
            cvService.delete(cv);
        }

        return carts.size();
    }


    private CompanyCv findCompanyCv(Integer seekerId) {
        Optional<JobSeeker> oppJob = seekerService.findById(seekerId);
        if (oppJob.isPresent()) {
            JobSeeker seeker = oppJob.get();
            List<CompanyCv> list = cvService.findByJobSeeker(seeker);
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }

        return null;

    }

    private boolean isAlreadyApplied(Jobs job, JobSeeker seeker) {
        Optional<SavedJobs> opp = svService.findByJobSeekerAndJob(job, seeker);
        return opp.isPresent();
    }

    private boolean savejobAsApplied(Jobs job, JobSeeker seeker) {
        SavedJobs sv = svService.findByJobSeekerAndJob(job, seeker).orElse(new SavedJobs());

        sv.setEntryDate(new Date());
        sv.setJobSeekerId(seeker);
        sv.setJobsId(job);
        sv.setCategory(SAVED_JOB_APPLIED);

        return svService.save(sv);
    }


    @GetMapping("/company-delete-jobs/{id}")
    @ResponseBody
    public boolean companyDeleteJob(@PathVariable Integer id, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return false;
        }
        return service.deleteById(id);

    }

    @GetMapping("/job-list")
    public String jobList(Model model) {
        return "job-list";
    }

    @GetMapping("find-by-category")
    @ResponseBody
    public Integer findByCategory(@RequestParam(defaultValue = "", required = false) String category) {
        if (category == null || category.isEmpty()) {
            return 0;
        }
        return service.findByCategory(category);
    }


    public String allJobs(Model model, Principal principal,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer max,
                          @RequestParam(defaultValue = "") String st,
                          @RequestParam(defaultValue = "") String ed) {
        try {
            Users user = JsfUtil.findOnline(principal);


            if (user != null) {

                String userType = user.getUserType();
                if (userType.equals("Company")) {
                    return "redirect:company-manage-job";
                }
                Employee emp = user.getStaffId();
                model.addAttribute("emp", emp);
            }

            model.addAttribute("user", user);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());


            Page<Jobs> rs = null;
            if (st.isEmpty() || ed.isEmpty()) {
                rs = findAllByOrderByIdDesc(page, max);
            } else {
                Date start = JsfUtil.convertToDate(st, "yyyy-MM-dd");//2021-07-04
                Date end = JsfUtil.convertToDate(ed, "yyyy-MM-dd");

                rs = findAllByOrderByIdDesc(page, max, start, end);
            }

            PageWrapper<Jobs> pages = new PageWrapper<>(rs, "/manage-jobs");
            model.addAttribute("page", pages);
            List<Jobs> list = pages.getContent();

            model.addAttribute("st", st);
            model.addAttribute("ed", ed);

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("joblist", list);
            // model.addAttribute("postedjobs", pages.getNumberOfElements());

            return "manage-jobs";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "manage-jobs";
    }

    @GetMapping("/employer/manage-jobs")
    public String employerManageJobs(Model model, @RequestParam(defaultValue = "1") Integer page, Principal principal, @RequestParam(defaultValue = "10") Integer max) {
        try {
            Employer employer = null;
            Users user = JsfUtil.findOnline(principal);
            String email = user.getUsername();
            Optional<Employer> oppEmp = employerService.findByEmail(email);
            if (!oppEmp.isPresent()) {
                employer = new Employer();
            } else {
                employer = oppEmp.get();
            }


            model.addAttribute("user", user);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());


            PageWrapper<Jobs> pages = new PageWrapper<>(findByPostedBy(employer, page, max), "/employer/manage-jobs");
            model.addAttribute("page", pages);
            List<Jobs> list = pages.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("joblist", list);

            return "employer/manage-jobs";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "employer/manage-jobs";
    }

    @GetMapping("/recruiter/select-jobs")
    public String selectJobs(Model model, @RequestParam(defaultValue = "1") Integer page, Principal principal, @RequestParam(defaultValue = "10") Integer max) {
        try {
            Users user = JsfUtil.findOnline(principal);


            if (user != null) {

                String userType = user.getUserType();
                if (userType.equals("Company")) {
                    return "redirect:company-manage-job";
                }
                Employee emp = user.getStaffId();
                model.addAttribute("emp", emp);
            }

            model.addAttribute("user", user);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());
            //Page<Jobs> pages = ;
            //List<Jobs> joblist = pages.getContent();

            PageWrapper<Jobs> pages = new PageWrapper<>(findJobs(page, max), "/recruiter/select-jobs");
            model.addAttribute("page", pages);
            List<Jobs> list = pages.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("joblist", list);
            // model.addAttribute("postedjobs", pages.getNumberOfElements());

            return "recruiter/select-jobs";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "recruiter/select-jobs";
    }


    private Page<Jobs> findJobs(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        return service.findAll(page, max);
    }

    private Page<Jobs> findAllByOrderByIdDesc(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        return service.findAllByOrderByIdDesc(page, max);
    }

    private Page<Jobs> findAllByOrderByIdDesc(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max, Date start, Date end) {
        return service.findByDate(start, end, page, max);
    }

    private Page<Jobs> findByPostedBy(Employer employer,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer max
    ) {
        return service.findByEmployer(employer, page, max);
    }

    @GetMapping("/findShortlisted/{id}")
    @ResponseBody
    public Integer findShortlisted(@PathVariable Integer id) {

        Optional<Jobs> opp = service.findById(id);
        if (opp.isPresent()) {
            List<ShortlistedApplicants> shotlisted = shortService.findByJobId(opp.get());
            return shotlisted.size();
        }


        return 0;
    }


    @GetMapping("/view-posted-jobs")
    public String viewPostedJobs(Model model, Principal principal, Integer id,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer max) {
        try {
            Users user = JsfUtil.findOnline(principal);
            Employee emp = user.getStaffId();
            Optional<Employer> opp = employerService.findById(id);
            if (opp.isPresent()) {

                Employer employer = opp.get();
                Page<Jobs> pages = service.findByEmployer(employer, page, max);//(employer, );
                List<Jobs> list = pages.getContent();

                model.addAttribute("currentPage", page);
                model.addAttribute("list", list);
                model.addAttribute("emp", emp);
                model.addAttribute("user", user);
                model.addAttribute("employer", employer);
                model.addAttribute("totalPages", pages.getTotalPages());

                return "view-posted-jobs";
            }


            return "redirect:employers";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }


    @GetMapping("/publish-jobs/{id}/{pu}")
    @ResponseBody
    public String publishJob(Principal principal, @PathVariable Integer id, @PathVariable Integer pu) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            Employee employee = user.getStaffId();
            Optional<Jobs> opp = service.findById(id);
            if (opp.isPresent()) {
                Jobs job = opp.get();
                job.setPublished(pu == 1);
                job.setPublishedBy(String.valueOf(employee));
                job.setPublishedDate(new Date());

                if (service.save(job)) {
                    if (pu == 1) {
                        alertJobSeekers(job);
                    }


                    return "SUCCESS";
                } else {
                    return "FAILED";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private void alertJobSeekers(Jobs job) {
        Thread t = new Thread() {
            @Override
            public void run() {

                //find alert by this job
                List<String> alerts = alertservice.findByDistictEmailsAndCategory(job.getCategory());

                alertByEmail(alerts, job);
            }

        };
        t.start();

    }

    public void alertByEmail(List<String> list, Jobs job) {
        Thread t = new Thread() {
            @Override
            public void run() {
                list.stream().forEach((item) -> {
                    sendEmail(item, job);
                });
            }
        };
        t.start();

    }

    public boolean sendEmail(String item, Jobs job) {
        try {
            Mail mail = new Mail();
            mail.setFrom("info@xjobs.com.gh");

            mail.setTo(item);
            mail.setSubject("Daily Job Alert");

            Map model = new HashMap();
            model.put("name", "Laine");
            model.put("location", "Accra");
            model.put("signature", "Sign");
            model.put("appUrl", appUrl);


            model.put("token", findToken(item));

            List<Jobs> listOfJobs = new ArrayList<>();
            listOfJobs.add(job);
            model.put("list", listOfJobs);
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

    public boolean sendEmail(JobAlert item, Jobs job) {
        try {
            Mail mail = new Mail();
            mail.setFrom("info@xjobs.com.gh");

            mail.setTo(item.getEmail());
            mail.setSubject("Daily Job Alert");

            Map model = new HashMap();
            model.put("name", "Laine");
            model.put("location", "Accra");
            model.put("signature", "Sign");
            model.put("appUrl", appUrl);
            JobSeeker seeker = item.getJobSeekerId();
            if (seeker != null) {
                String token = seeker.getUniqueId();
                if (token == null) {
                    token = JsfUtil.generateSerial();
                    seeker.setUniqueId(token);
                    seekerService.save(seeker);
                }
                model.put("token", token);
            } else {
                model.put("token", "");
            }


            List<Jobs> listOfJobs = new ArrayList<>();
            listOfJobs.add(job);
            model.put("list", listOfJobs);
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

    @GetMapping("/send-test-email")
    @ResponseBody
    public boolean sendTestEmail() {
        try {
            Mail mail = new Mail();
            mail.setFrom("info@xjobs.com.gh");

            String[] address = {"enquiry@laineservices.com"};
//            mail.setTo(address);
            mail.setSubject("Testing");

            Map model = new HashMap();
            model.put("name", "Laine Service");
            model.put("location", "Accra");
            model.put("signature", "Sign");
            model.put("appUrl", appUrl);

            List<Jobs> list = service.findAll();
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

    @GetMapping("/newsletter-template")
    public String newsletter(Model model) {
        model.addAttribute("name", "Laine Services");
        model.addAttribute("location", "Accra");
        model.addAttribute("signature", "Sign");

        List<Jobs> list = service.findAll();
        model.addAttribute("list", list);

        model.addAttribute("appUrl", appUrl);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("token", "enquiry@laineservices.com");

        return "newsletter-template";
    }

    private List<String> findEmails(List<Jobs> list) {
        List<String> emails = new ArrayList<>();

        list.stream().forEach((item) -> {
            List<JobAlert> alerts = alertservice.findByJobCategory(item.getCategory());

            alerts.stream().forEach((al) -> {
                emails.add(al.getEmail());
            });

        });

        return emails;
    }

    private String findToken(String item) {
        Optional<SubscriptionToken> oppToken = tokenService.findByEmail(item);
        if (!oppToken.isPresent()) {

            String token = JsfUtil.generateSerial();

            SubscriptionToken sub = new SubscriptionToken();
            sub.setEmail(item);
            sub.setToken(token);
            tokenService.save(sub);

            return token;
        } else {
            return oppToken.get().getToken();
        }
    }

    @GetMapping("/jobs")
    public String searchJob(Model model, Principal principal, @RequestParam(defaultValue = "", required = false) String q,
                            @RequestParam(defaultValue = "1", required = false) Integer page,
                            @RequestParam(defaultValue = "10", required = false) Integer max) {
        try {
            List<Jobs> jobs = userSearch.searchByKeyword(q, page, max);
            Page<Jobs> pagesImp = new PageImpl<>(jobs, PageRequest.of(page, max), jobs.size());

//            model.addAttribute("currentPage", page);
//            model.addAttribute("totalPages", pages.getTotalPages());
//            model.addAttribute("list", pages.getContent());
//
            model.addAttribute("ct", q);
            Users user = JsfUtil.findOnline(principal);

            if (user != null) {
                String userType = user.getUserType();
                if (userType.equals("Company")) {
                    return "redirect:company-manage-job";
                }
                Employee emp = user.getStaffId();
                model.addAttribute("emp", emp);
            }

            model.addAttribute("user", user);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());
            PageWrapper<Jobs> pages = new PageWrapper<>(pagesImp, "/jobs?q=" + q);
            model.addAttribute("page", pages);
            List<Jobs> list = pages.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("list", list);

            return "jobs";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "jobs";
    }


    @GetMapping("/all-categories")
    @ResponseBody
    public Page<String> allCategories(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "5", required = false) Integer max) {
        return service.findByDistinctCategoryPaginated(page, max);
    }

    @GetMapping("/findFourCategories")
    @ResponseBody
    public List<String> findFourCategories() {
        return service.findFourCategories();
    }


    @GetMapping("/find-job-categories")
    @ResponseBody
    public List<JobSeekerSpecialization> findJobCategories() {
        Optional<List<Object[]>> option = service.findByCategoryCount();

        if (option.isPresent()) {

            List<Object[]> list = option.get();
            return list.stream().map(c ->

                            new JobSeekerSpecialization(
                                    JsfUtil.generateSerial(),
                                    Integer.valueOf(c[0].toString()),
                                    (String) c[1]))

                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<CategoryCount> findCategories(Integer limit) {
        Optional<List<Object[]>> option = null;
        List<CategoryCount> assigmentsList = new ArrayList<>();
        switch (limit) {
            case 1:
                option = service.findByCategoryCount();
                break;

            default:

                option = service.findByCategoryCountLimit();
        }


        if (option.isPresent()) {

            List<Object[]> objects = option.get();

            objects.stream().forEach((item) -> {

                CategoryCount dash = new CategoryCount();
                dash.setId(new Date().getTime());

                Object obj = item[0];
                Long lg = Long.valueOf(obj.toString());
                dash.setCount(lg);
                dash.setCategory((String) item[1]);


                assigmentsList.add(dash);
            });
        }

        return assigmentsList;
    }


    @RequestMapping(value = {"/browse-jobs-fy-exp", "/browse-jobs-by-exp"}, method = GET)
    public String browseJobsByExperience(Model model,
                                         @RequestParam(defaultValue = "1") int start,
                                         @RequestParam(defaultValue = "5") int end,
                                         @RequestParam(defaultValue = "1") int page) {


        Page<Jobs> list2 = service.findByExperience(start, end, page);

        Page<JobSearch> list = createJobSearch(list2, PageRequest.of(page, 10));


        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("list", list);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());

        model.addAttribute("currentPage", page);
        //model.addAttribute("countryList", getCountries());

        //List<CategoryCount> clist = findCategories();
        List<CategoryCount> clist2 = findCountries();
        List<CategoryCount> clist3 = findCompanies();

        model.addAttribute("catlist", findJobCategories());
        model.addAttribute("countrylist", clist2);
        model.addAttribute("companylist", clist3);
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("url", "/browse-jobs-salary?start=" + start + "&end=" + end);
        model.addAttribute("currentPage", page);
        model.addAttribute("ct", start);

        return "browse-job-filter-list";
    }

    @PostMapping("/admin/upload-bulk-jobs")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<Jobs> list = convertFromExcel(in);
            return service.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "FAILED";
    }

    @PostMapping("/admin/upload-bulk-jobs-update")
    @ResponseBody
    public String uploadFileUpdate(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<Jobs> list = convertFromExcelUpdate(in);
            return service.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "FAILED";
    }

    public List<Jobs> convertFromExcel(InputStream is) {
        try {
            List<Jobs> productList;
            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                productList = new ArrayList<>();

                DataFormatter formater = new DataFormatter();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    Jobs product = new Jobs();


                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        //0requisition_id	1 company	2requested_by	3contact_number	4email
                        //5 min_exp	6 age_from	7age_to	8conditions
                        //9min_salary	10max_salary	11country_id	12contract_type		13 start_date	14 date_added	16 assigned_to	17status_id	17 number_needed	19published

                        String companyName = "";
                        String requestedBy = "";
                        String contactNumber = "";

                        switch (cellIdx) {

                            case 0:
                                String val = formater.formatCellValue(currentCell);
                                product.setId(Integer.valueOf(val));
                                break;

                            case 1:
                                companyName = formater.formatCellValue(currentCell);
                                break;

                            case 2:
                                requestedBy = formater.formatCellValue(currentCell);
                                break;

                            case 3:
                                contactNumber = formater.formatCellValue(currentCell);
                                break;


                            case 4:
                                String email = formater.formatCellValue(currentCell);
                                product.setPostedBy(findPostedBy(companyName, requestedBy, contactNumber, email));

                                break;

                            case 5:
                                String min_exp = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(min_exp)) {
                                    product.setExperience(min_exp);
                                    product.setMinYearsExperience(createYearsOfExp(min_exp));
                                }


                                break;
                            case 6:
                                String age_from = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(age_from)) {
                                    product.setFromAge(Integer.valueOf(age_from));
                                }

                                break;

                            case 7:
                                String age_to = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(age_to)) {
                                    product.setToAge(Integer.valueOf(age_to));
                                }
                                break;

                            case 8:
                                String conditions = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(conditions)) {
                                    product.setConditionOfService(conditions);
                                }
                                break;

                            case 9:
                                String min_salary = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(min_salary)) {
                                    Double minSal = Double.valueOf(min_salary);
                                    product.setRenumeration(BigDecimal.valueOf(minSal));
                                }

                                break;

                            case 10:
                                String max_salary = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(max_salary)) {
                                    Double minSal = Double.valueOf(max_salary);
                                    product.setToRenumeration(BigDecimal.valueOf(minSal));
                                }

                                break;


                            case 11:
                                String country_id = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(country_id)) {
                                    product.setCountry(findCountryOfOriging(country_id));
                                }
                                break;

                            case 12:

                                String contract_type = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(contract_type)) {
                                    product.setJobType(contract_type);
                                }
                                break;

                            case 13:
                                String start_date = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(start_date)) {
                                    product.setPostedDate(JsfUtil.convertToDate(start_date));
                                }
                                break;

                            case 17:
                                String number_needed = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(number_needed)) {
                                    product.setNoNeeded(Integer.valueOf(number_needed));
                                }
                                break;

                            case 18:
                                String published = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(published)) {
                                    product.setPublished("1".equals(published));
                                }
                                break;

                            case 19:
                                String profession = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(profession)) {
                                    product.setProfession(profession);
                                    product.setCategory(profession);
                                }
                                break;

                            default:
                                break;

                        }
                        product.setAlertSent(false);
                        product.setTransactionId(JsfUtil.generateSerial());
                        product.setAssigned(false);
                        product.setDeleted(false);
                        product.setProcessed(false);

                        cellIdx++;
                    }
                    productList.add(product);
                }
            }

            return productList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<Jobs> convertFromExcelUpdate(InputStream is) {
        try {
            List<Jobs> productList;
            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                productList = new ArrayList<>();

                DataFormatter formater = new DataFormatter();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    Jobs product = null;


                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIdx) {

                            case 0:
                                String val = formater.formatCellValue(currentCell);
                                Integer id = Integer.valueOf(val);
                                product = findProductById(id);
                                break;

                            case 1:
                                String jobTitle = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(jobTitle)) {
                                    if (product != null) {
                                        product.setProfession(jobTitle);
                                        product.setJobTitle(jobTitle);
                                        product.setCategory(jobTitle);
                                    }
                                }
                                break;

                            default:
                                break;

                        }

                        cellIdx++;
                    }
                    if (product != null) {
                        productList.add(product);
                    }

                }
            }

            return productList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private Employer findPostedBy(String companyName, String requestedBy, String contactNumber, String email) {
        Optional<Employer> emOptional = employerService.findByName(companyName);
        if (emOptional.isPresent()) {
            return emOptional.get();
        }
        Employer employer = new Employer();
        employer.setCompanyName(companyName);
        employer.setEmail(email);
        employer.setContactPhoneNumber(contactNumber);
        employer.setContactName(requestedBy);
        employer.setPhoneNumber(contactNumber);
        if (employerService.save(employer)) {
            return employer;
        }
        return null;
    }

    private String findCountryOfOriging(String country) {
        try {
            if ("EMPTY_FIELD".equals(country)) {
                return null;
            }
            Integer id = Integer.parseInt(country);
            Optional<Countries> opp = countryService.getCountries(id);
            if (opp.isPresent()) {
                return opp.get().getName();
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Jobs findProductById(Integer id) {
        Optional<Jobs> opp = service.findById(id);
        if (opp.isPresent()) {
            return opp.get();
        }
        return null;
    }


    @GetMapping("/admin/setup-transactionIds/{candidateType}")
    @ResponseBody
    public String setup(Principal principal, @PathVariable Integer candidateType) {
        try {
            switch (candidateType) {
                case 2:
                    return jobSeekerSetupTranx();
            }
            List<Jobs> jobs = service.findNullTransactions();
            List<Jobs> list = new ArrayList<>();
            jobs.stream().forEach((item) -> {
                item.setTransactionId(JsfUtil.generateSerial());
                list.add(item);
            });
            return service.saveAll(list) ? "Successful, Count: " + list.size() : "failed";
        } catch (Exception e) {
            throw new BeanNotFoundException("Please login");
        }
    }

    private void loadModelForCompany(Model model, Jobs job, Boolean show) {
        Employer employer = job.getPostedBy();
        JobDetails details = new JobDetails();

        if (employer == null || !show) {

            details.setCompanyAddress("Not Provided");
            details.setCompanyName("Reputable Company");
            details.setCompanyEmail("Not Provided");
            details.setCompanyPicture("defaultCompanyPic.jpg");
            details.setCompanyWebsite("Not Provided");
            details.setCompanyPhone("Not Provided");
        } else {

            String compName = show ? employer.getCompanyName() : "Reputable Company";
            String address = employer.getAddress();
            if (address == null) {
                address = "Not Provided";
            }
            details.setCompanyAddress(address);
            details.setCompanyName(compName);
            String email = employer.getEmail();
            if (email == null) {
                email = "Not Provided";
            }
            details.setCompanyEmail(email);
            String fileName = employer.getFileName();
            if (fileName == null) {
                fileName = "defaultCompanyPic.jpg";
            }
            details.setCompanyPicture(fileName);

            String website = employer.getWebSite();
            if (website == null) {
                website = "Not Provided";
            }
            details.setCompanyWebsite(website);

            String phoneNumber = employer.getPhoneNumber();
            if (phoneNumber == null) {
                phoneNumber = "Not Provided";
            }
            details.setCompanyPhone(phoneNumber);


        }
        String contract = job.getNatureOfContract();
        if (contract == null) {
            contract = "Not Provided";
        }
        details.setNatureOfContract(contract);
        String category = job.getCategory();
        if (category == null) {
            category = "Not Provided";
        }
        details.setCategory(category);
        String location = job.getLocation();
        if (location == null) {
            location = "Not Provided";
        }
        details.setLocation(location);

        BigDecimal minSal = job.getRenumeration();
        if (minSal == null) {
            minSal = BigDecimal.ZERO;
        }

        BigDecimal maxSal = job.getToRenumeration();
        if (maxSal == null) {
            maxSal = BigDecimal.ZERO;
        }
        details.setToRenumeration(maxSal);
        details.setRenumeration(minSal);

        model.addAttribute("details", details);
    }


    private String jobSeekerSetupTranx() {

        List<JobSeeker> jobs = seekerService.findNullTransactions();
        List<JobSeeker> list = new ArrayList<>();
        jobs.stream().forEach((item) -> {
            item.setTransactionId(JsfUtil.generateSerial());
            list.add(item);
        });
        return seekerService.saveAll(list) ? "Successful, Count: " + list.size() : "failed";
    }

    private List<JobSeeker> findJobSeekersFromWk(List<WorkExperience> list) {
        List<JobSeeker> js = new ArrayList<>();

        list.stream().forEach((item) -> {
            js.add(item.getJobSeekerId());
        });

        return js;
    }

    private List<String> findJobTitles(List<WorkExperience> list) {
        List<String> js = new ArrayList<>();

        list.stream().forEach((item) -> {
            js.add(item.getDesignation());
        });

        return js;
    }

    private void defaultModel(Model model, Integer page, String q) {

        model.addAttribute("totalFound", 0);
        model.addAttribute("list", new ArrayList<>());
        model.addAttribute("currentPage", page);

        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());

        List<CategoryCount> clist = findCategories(2);
        model.addAttribute("catlist", clist);
        model.addAttribute("currentPage", page);
        model.addAttribute("ct", q);
        model.addAttribute("url", "/findJobs?q=" + q);

    }

    @GetMapping("/published-job-list")
    @ResponseBody
    public List<JobSearch> createJobSearchList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        Page<Jobs> pages = service.findAll(page, max);
        List<Jobs> list = pages.getContent();


        List<JobSearch> serchlist = new ArrayList<>();
        list.stream().forEach((item) -> {

            JobSearch js = new JobSearch();
            Employer emp = item.getPostedBy();
            PostedBy postedBy = JsfUtil.createPostedBy(emp);

            js.setPostedBy(postedBy);
            if (emp != null) {
                js.setFileName(emp.getFileName());
            }

            js.setId(item.getId());
            js.setCategory(item.getCategory());
            js.setCountry(item.getCountry());
            js.setExpireDate((item.getExpireDate()));
            js.setNatureOfContract(item.getNatureOfContract());
            js.setLocation(item.getLocation());
            js.setPublishedDate((item.getPublishedDate()));
            js.setRenumeration(item.getRenumeration());
            js.setToRenumeration(item.getToRenumeration());
            js.setShowCompanyName(item.getShowCompanyName());
            js.setProfession(item.getProfession());
            js.setDetails(item.getJobDescription());
            js.setDutiesAndRespo(getRequirement(item, duties));
            js.setSkillls(getRequirement(item, requirements));
            js.setSlot(item.getNoNeeded());
            js.setGender(item.getGender());
            js.setSpecialRequirements(getRequirement(item, special));
            js.setQualification(item.getMinQualification());
            js.setExpireDate((item.getExpireDate()));
            js.setHowToApply(item.getHowToApply());
            js.setIndustry(item.getIndustry());
            js.setMinYearsExperience(item.getMinYearsExperience());
            js.setTelephone(item.getTelephone());
            js.setTransactionId(item.getTransactionId());


            serchlist.add(js);

        });


        return serchlist;
    }

    private String findJobDetails(Jobs job) {
        String category = job.getCategory();
        if (category == null) {
            return "";
        }
        return category;
    }

    private List<EmployerShortlisted> convertedShortlist(List<EmployerShortlistedApplicants> list) {
        return list.stream().map(n ->
                //Seeker jobSeekerId, String jobTitle, String entryDate, String transactionNo
                new EmployerShortlisted(
                        convertToSearchedSeeker(n.getJobSeekerId()),
                        n.getJobTitle(),
                        JsfUtil.convertToString(n.getEntryDate(), "dd/MM/yyyy"),
                        n.getTransactionNo()
                )
        ).collect(Collectors.toList());

    }


    @GetMapping("/public/profile")
    @ResponseBody
    public EmployerProfile profile(Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        Optional<Employer> opp = employerService.findByEmail(user.getUsername());
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Credentials: " + user.getUsername());
        }

        Employer employer = opp.get();
        List<EmployerShortlistedApplicants> list = employerShortService.findByEmployer(employer);

        EmployerSubscription subscription = employer.getEmployerSubscription();

        return convertedProfile(list, employer, subscription);
    }

    private EmployerProfile convertedProfile(List<EmployerShortlistedApplicants> shorlisted, Employer employer, EmployerSubscription subscription) {
        //name, EmployerSubscription subscription, List<EmployerShortlisted> candidates
        return new EmployerProfile(
                String.valueOf(employer),
                subscription,
                convertedShortlist(shorlisted));
    }


    @GetMapping("/admin/update-years")
    @ResponseBody
    public boolean updateYears(Principal principal) {
        List<Jobs> jobs = service.findAll();
        List<Jobs> list = new ArrayList<>();
        jobs.stream().forEach((item) -> {
            String exp = item.getExperience();
            if (exp != null) {
                item.setMinYearsExperience(createYearsOfExp(exp));
                list.add(item);
            }
        });

        return service.saveAll(list);
    }


    private List<JobsResponse> convertedJobs(List<Jobs> list) {
        return list.stream().map(n ->
                // id,  postedDate,  applied,  shortlisted,  publishedDate,  expireDate,  location,  company,  companyFileName,  jobType,  position, jobStatus,  summary, transactionId)
                new JobsResponse(
                        n.getId(), //id
                        JsfUtil.convertFromDate(n.getPostedDate()), //postedDate
                        //  findApplied(n), //applied
                        findByAccredStatusCount(n.getTransactionId(), JsfUtil.PENDING),//applied
                        findByAccredStatusCount(n.getTransactionId(), JsfUtil.SHORTLISTED),//shortlisted
                        JsfUtil.convertFromDate(n.getPublishedDate()),//publishedDate
                        JsfUtil.convertFromDate(n.getExpireDate()), //expireDate
                        constructLocation(n), //location
                        findPostedBy(n), //company
                        findFileName(n), //companyFileName
                        findJobType(n, "JobType"), //jobType
                        n.getProfession(), //position
                        n.getJobType(), //jobStatus
                        findSummary(n), //summary
                        n.getTransactionId(),
                        n.getRenumeration(),
                        n.getToRenumeration(),
                        findByJobCount(n)

                )
        ).collect(Collectors.toList());
    }


    @GetMapping(value = {"/manage-jobs", "/recruiter/jobs"})
    public String recruiterJobs(Model model, Principal principal,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer max,
                                @RequestParam(defaultValue = "") String st,
                                @RequestParam(defaultValue = "") String ed) {
        try {
            Users user = JsfUtil.findOnline(principal);


            if (user != null) {

                String userType = user.getUserType();
                if (userType.equals("Company")) {
                    return "redirect:company-manage-job";
                }
                Employee emp = user.getStaffId();
                model.addAttribute("emp", emp);
            }

            model.addAttribute("user", user);
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("imgUtil", new ImageUtil());


            Page<Jobs> rs = null;
            if (st.isEmpty() || ed.isEmpty()) {
                rs = findAllByOrderByIdDesc(page, max);
            } else {
                Date start = JsfUtil.convertToDate(st, "yyyy-MM-dd");//2021-07-04
                Date end = JsfUtil.convertToDate(ed, "yyyy-MM-dd");

                rs = findAllByOrderByIdDesc(page, max, start, end);
            }

            PageWrapper<Jobs> pages = new PageWrapper<>(rs, "/recruiter/jobs?st=" + st + "$ed=" + ed);
            model.addAttribute("page", pages);
            List<Jobs> list = pages.getContent();

            model.addAttribute("st", st);
            model.addAttribute("ed", ed);

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("joblist", convertedJobs(list));
            // model.addAttribute("postedjobs", pages.getNumberOfElements());

            return "recruiter/jobs";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "recruiter/jobs";
    }


    @GetMapping("/recruiter/jobs-api")
    @ResponseBody
    public JobsResponseHeader recruiterJobs(Principal principal,
                                            @RequestParam(defaultValue = "1") Integer page,

                                            @RequestParam(defaultValue = "10") Integer max) {

        Page<Jobs> rs = findAllByOrderByIdDesc(page, max);
        List<Jobs> list = rs.getContent();

        Integer numberOfElements = rs.getNumberOfElements();
        Integer totalPages = rs.getTotalPages();
        List<JobsResponse> lines = convertedJobs(list);

        return new JobsResponseHeader(numberOfElements, totalPages, page, max, lines);

    }

    @GetMapping("/recruiter/jobs-details")
    public String recruiterJobDetals(Model model, @RequestParam(required = false) String trx, Principal principal) {
        try {
            if (trx == null) {
                return "redirect:manage-jobs";
            }
            Optional<Jobs> oppJop = service.findByTransactionId(trx);
            if (!oppJop.isPresent()) {
                return "redirect:manage-jobs";
            }

            model.addAttribute("timeAgo", new TimeAgo());
            Jobs jobs = oppJop.get();


            List<String> jreqList = getRequirement(jobs, requirements);
            List<String> dutiesList = getRequirement(jobs, duties);
            List<String> specialReq = getRequirement(jobs, special);


            model.addAttribute("job", jobs);
            model.addAttribute("jreqList", jreqList);
            model.addAttribute("dutiesList", dutiesList);
            model.addAttribute("specialReq", specialReq);

            loadModelForCompany(model, jobs, true);
            model.addAttribute("showCompany", true);

            Integer views = jvservice.findByJobCount(jobs);
            if (views == null) {
                views = 0;
            }

            model.addAttribute("views", views);
            model.addAttribute("showCompany", jobs.getJobseekers().size());

            Integer applications = jobAppService.findByJobCount(jobs);
            model.addAttribute("applications", applications);

            return "recruiter/job-details";
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "recruiter/jobs-details";

    }

    public String findSummary(Jobs job) {
        String desc = job.getJobDescription();
        if (desc == null) {
            return "";
        }
        if (desc.length() > 20) {
            return desc.substring(0, 19);
        }
        return desc;

    }

    private Integer findApplied(Jobs job) {
        return 0;
    }

    private String findFileName(Jobs job) {

        try {
            if (job == null) {
                return "icon1.png";
            }


            Employer fn = job.getPostedBy();
            if (fn == null) {
                return "icon1.png";
            }

            String fileName = fn.getFileName();
            if (fileName == null) {
                return "icon1.png";
            }

            return fileName;

        } catch (Exception e) {
            return "icon1.png";

        }
    }

    public Integer findByAccredStatusCount(@PathVariable String trx, @PathVariable String type) {
        Optional<Jobs> opp = service.findByTransactionId(trx);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid transactionId: " + trx);
        }
        Jobs job = opp.get();
        return jobAppService.findByJobAndApplicationStatusCount(job, type);
    }

    //    public Integer findShortlisted(Jobs n){
//        //List<ShortlistedApplicants> shorlisted = shortService.findByJobId(job);
//        //Set<JobSeeker> seeker = n.getJobseekers();
//        if(seeker==null || seeker.isEmpty()){
//            return 0 ;
//        }
//        return seeker.size();
//    }
    private String findJobType(Jobs job, String option) {
        if (job == null) {
            return "";
        }
        switch (option) {
            case "Name":
                return job.getName();
            case "JobType":
                return job.getJobType();
            case "Profession":
                return job.getProfession();

            default:
                return "";
        }

    }

    private String findPostedBy(Jobs job) {
        if (job == null) {
            return "";
        }
        Employer employer = job.getPostedBy();
        if (employer == null) {
            return "";
        }

        return employer.getCompanyName();
    }


    @GetMapping("/recruiter/find-job/{id}")
    @ResponseBody
    public PostJob findJobs(@PathVariable Integer id) {
        Jobs job = service.findById(id).orElse(new Jobs());
        return convertedToJobs(job);
    }

    private PostJob convertedToJobs(Jobs job) {
        return new PostJob(job);
    }

    @GetMapping("/recruiter/search-jobs")
    @ResponseBody
    public JobsResponseHeader searchJob(String q,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer max, Principal principal) {
        List<Jobs> list1 = userSearch.searchByKeyword(q, page, max);

        List<JobsResponse> serchlist = convertedJobs(list1);

        PageImpl<JobsResponse> pages = new PageImpl<>(serchlist, PageRequest.of(page, max), serchlist.size());

        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();

        return new JobsResponseHeader(numberOfElements, totalPages, page, max, serchlist);
    }

    @GetMapping("/recruiter/search-jobs-by-category")
    @ResponseBody
    public JobsResponseHeader searchJobByCategory(String ct,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer max, Principal principal) {


        Page<Jobs> pages = service.findByPublishedCategory(ct, page);
        List<Jobs> list1 = pages.getContent();

        List<JobsResponse> lines = convertedJobs(list1);

        Integer numberOfElements = pages.getNumberOfElements();
        Integer totalPages = pages.getTotalPages();

        return new JobsResponseHeader(numberOfElements, totalPages, page, max, lines);

        //return new PageImpl<>(serchlist, PageRequest.of(page, max), serchlist.size());
    }

    @GetMapping("/recruiter/job-categories")
    @ResponseBody
    public List<String> jobCategories() {

        return service.findFourCategories();
    }

    private Integer findByJobCount(Jobs n) {
        Integer views = jvservice.findByJobCount(n);
        if (views == null) {
            views = 0;
        }
        return views;
    }


    @GetMapping("/admin/create-job-app")
    @ResponseBody
    public String createApp() {
        List<Jobs> job = service.findAll();
        job.stream().forEach((item) -> {

            Set<JobSeeker> seekers = item.getJobseekers();
            List<JobApplications> jobApps = new ArrayList<>();

            seekers.stream().forEach((s) -> {
                try {
                    JobApplications app = new JobApplications();
                    app.setApplicationStatus("Pending");
                    app.setAppliedDate(LocalDate.now());
                    app.setEntryDate(LocalDate.now());
                    app.setJob(item);
                    app.setJobseeker(s);
                    app.setTransactionDate(LocalDateTime.now());
                    app.setTransactionId(JsfUtil.generateSerial());

                    jobAppService.save(app);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        });

        return "saved";
    }

}
