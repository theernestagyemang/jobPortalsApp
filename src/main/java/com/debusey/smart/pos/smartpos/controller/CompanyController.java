/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.*;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.search.UserSearch;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Admin
 */

@Controller
public class CompanyController {

    private static final String UPLOADED_FILE_SESSION = "file-upload";
    @Autowired
    private EmployerService service;
    @Autowired
    private SubscriptionUsageService usageService;
    @Autowired
    private EmployerShortlistedApplicantsService employerShortService;
    @Autowired
    private JobsService jobService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private SubscriptionService subService;
    @Autowired
    private CompanyCvService cvService;
    @Autowired
    private JobSeekerService seekerService;
    @Autowired
    private JobRequirementService jservice;
    @Autowired
    private PositionsService positionService;
    @Autowired
    private EmployeeService empservice;
    @Autowired
    private UserSearch search;
    @Autowired
    private CompanyService cservice;

    @Autowired
    private ShortlistedApplicantService shortlistedApplicantService;
    @Autowired
    private PackageTransactionService packageTransactionService;
    @Value("${api-callback-url}")
    private String apiCallBackUrl;
    @Value("${api-key}")
    private String apiKey;

    @GetMapping("/company-profile")
    public String companyProfile(Principal principal, Model model, @RequestParam(defaultValue = "0", required = false) Integer id) {
        try {

            Users user = JsfUtil.findOnline(principal);
            Employer employer = null;

            if (id > 0) {
                employer = service.findById(id).orElse(new Employer());
            } else {


                String usertype = user.getUserType();
                if (usertype != null) {
                    if (usertype.equalsIgnoreCase("Staff")) {
                        return "redirect:employers";
                    }
                }

                String email = user.getUsername();
                employer = service.findByEmail(email).orElse(new Employer());

            }


            model.addAttribute("employer", employer);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("user", user);

            model.addAttribute("postedJobs", findPostedJobs(employer));
            // model.addAttribute("viewed", findViewed(employer));


            return "company-profile";

            // return  "redirect:duplicate-cmp?rt="+email;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "company-profile";
    }


    @GetMapping("/find-seeker-specialization")
    @ResponseBody
    public List<JobSeekerSpecialization> findBySpecialization() {
        Optional<List<Object[]>> option = seekerService.findBySeekerSpecialization();

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

    @GetMapping("/applied-applicants")
    public String appliedApplicants(Principal principal, Model model, @RequestParam(name = "id", required = false) Integer id) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        String view = "applied-applicants";

        if (user != null) {

            String userType = user.getUserType();
            if (userType.equals("Staff")) {
                createStaffModel(model, user);
                view = "applied-applicants-x";
            } else {
                createEmployerModel(model, user);
            }


            Optional<Jobs> oppJob = jobService.findById(id);
            Jobs job = oppJob.orElse(new Jobs());

//            ShortlistedApplicants shortlistedApplicants = shortlistedApplicantService.findBySeekerAndJobAndEmployer(
//
//            );

            Set<JobSeeker> seeker = job.getJobseekers();

            model.addAttribute("seeker", seeker);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("job", job);
            model.addAttribute("specialization", findBySpecialization());


            return view;
        }

        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("employer", new Employer());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("job", new Jobs());

        return "applied-applicants";
    }

    @GetMapping("/applied-applicants/{transactionId}")
    public String appliedApplicants2(Principal principal, Model model, @PathVariable String transactionId) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        String view = "applied-applicants";

        if (user != null) {

            String userType = user.getUserType();
            if (userType.equals("Staff")) {
                createStaffModel(model, user);
                view = "applied-applicants-x";
            } else {
                createEmployerModel(model, user);
            }


            Optional<Jobs> oppJob = jobService.findByTransactionId(transactionId);
            Jobs job = oppJob.orElse(new Jobs());

            Set<JobSeeker> seeker = job.getJobseekers();

            model.addAttribute("seeker", seeker);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("job", job);
            model.addAttribute("specialization", findBySpecialization());


            return view;
        }

        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("employer", new Employer());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("job", new Jobs());

        return "applied-applicants";
    }

    @GetMapping("/comp-shortlisted-candidate")
    public String compShortlisteCad(Model model, Integer jobId, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);

        String email = user.getUsername();

        Optional<Employer> oppEmp = service.findByEmail(email);

        Employer employer = oppEmp.orElse(new Employer());
        model.addAttribute("employer", employer);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<Jobs> oppJob = jobService.findById(jobId);
        Jobs job = oppJob.orElse(new Jobs());

        Set<JobSeeker> seeker = job.getJobseekers();
        model.addAttribute("job", job);

        model.addAttribute("seeker", seeker);

        return "com-shortlisted-candidate";
    }


    @GetMapping("/candidate-cv")
    public String candidateCv(Model model, Principal principal, Integer id, Integer jobId) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);

        String email = user.getUsername();
        Optional<Employer> oppEmp = service.findByEmail(email);

        Employer employer = oppEmp.orElse(new Employer());
        model.addAttribute("employer", employer);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<JobSeeker> oppSeeker = seekerService.findById(id);
        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());


        Optional<Jobs> oppJob = jobService.findById(jobId);
        Jobs job = oppJob.orElse(new Jobs());

        model.addAttribute("seeker", seeker);
        model.addAttribute("job", job);

        return "candidate-cv";
    }


    @PostMapping("/company-profile")
    @ResponseBody
    public boolean createCompanyProfile(String companyName, String email, String webSite, String foundedDate
            , String category, String description, String contactPhoneNumber
            , String contactEmail, String country, String city, String tin, String address, String linkedin
            , String facebook, String twitter, String google, Integer id) {

        Optional<Employer> oppEmp = service.findById(id);
        if (oppEmp.isPresent()) {

            Employer emp = oppEmp.get();
            emp.setCompanyName(companyName);
            emp.setEmail(email);
            emp.setWebSite(webSite);
            emp.setFoundedDate(JsfUtil.convertToDate(foundedDate));
            emp.setCategory(category);
            emp.setDescription(description);
            emp.setContactPhoneNumber(contactPhoneNumber);
            emp.setContactEmail(contactEmail);
            emp.setCountry(country);
            emp.setCity(city);
            emp.setTin(tin);
            emp.setAddress(address);
            emp.setLinkedin(linkedin);
            emp.setFacebook(facebook);
            emp.setTwitter(twitter);
            emp.setGoogle(google);

            return service.save(emp);
        }
        return false;
    }
    //

    @GetMapping("/my-countries")
    @ResponseBody
    public List<Country> mycountries() {
        List<String> list = JsfUtil.getCountries();
        List<Country> cl = new ArrayList<>();

        list.stream().forEach((item) -> {
            int count = 0;
            Country c = new Country();
            c.setId(count);
            c.setText(item);
            cl.add(c);
            count++;
        });

        return cl;
    }

    @GetMapping("/edit-posted-jobs")
    public String editPostJob(Model model, Principal principal, @RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);

        String email = user.getUsername();

        Optional<Employer> oppEmp = service.findByEmail(email);

        Employer employer = oppEmp.orElse(new Employer());
        model.addAttribute("employer", employer);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        List<String> countryList = JsfUtil.getCountries();

        model.addAttribute("countryList", countryList);

        Optional<Jobs> oppJob = jobService.findById(id);
        Jobs job = oppJob.orElse(new Jobs());
        model.addAttribute("job", job);

        model.addAttribute("categoryList", getCategories());

        return "company-post-jobs";
    }


    @RequestMapping(value = "/rec-post-jobs", method = RequestMethod.POST)
    public ModelAndView postRecJob(ModelAndView modelAndView, String jobTitle, String jobTags, String jobType, String experience,
                                   String category, BigDecimal renumeration, BigDecimal toRenumeration,
                                   String region, String jobCountry, String expDate, String city, String howToApply,
                                   String jobTown, String minQualification, boolean showComp, boolean publish,
                                   String jobDescription, Integer slot, Integer jobId, String prefSkills,
                                   String companyName, String comDescription, String compPhone, String compEmail, String compCity, String compAddress, Principal principal, HttpSession session) {

        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            modelAndView.addObject("user", user);
            modelAndView.addObject("userInfo", userInfo);


            Employee recruiter = user.getStaffId();
            modelAndView.addObject("emp", recruiter);

            Jobs jobs = jobService.findById(jobId).orElse(new Jobs());
            jobs.setJobTitle(jobTitle);

            Employer employer = jobs.getPostedBy();
            if (employer == null) {
                employer = findOrCreateEmployer(companyName);

            }


            employer.setEmail(compEmail);
            employer.setDescription(comDescription);
            employer.setPhoneNumber(compPhone);
            employer.setCity(compCity);
            employer.setAddress(compAddress);
            jobs.setJobType(jobType);
            jobs.setExperience(experience);
            jobs.setMinYearsExperience(JsfUtil.createYearsOfExp(experience));
            jobs.setCategory(category);
            jobs.setRenumeration(renumeration);
            jobs.setToRenumeration(toRenumeration);
            jobs.setJobCountry(jobCountry);
            jobs.setCountry(jobCountry);
            jobs.setPrefSkillsAttribute(prefSkills);

            jobs.setDeleted(false);
            jobs.setJobStateRegion(region);
            jobs.setJobStatus("New");
            //jobs.setJobTags(jobTags);

            jobs.setPaymentStatus("Unpaid");
            jobs.setPostedDate(new Date());
            jobs.setProcessed(false);
            jobs.setPublished(false);
            jobs.setRecruiterId(recruiter.getEmployeeid());
            jobs.setRegion(region);
            jobs.setRenumeration(renumeration);
            jobs.setTelephone(compPhone);
            jobs.setAssigned(false);
            jobs.setNoNeeded(jobId);

            jobs.setRecruiterId(recruiter.getEmployeeid());


            if (jobCountry != null) {

                if (jobCountry.equalsIgnoreCase("Ghana")) {
                    jobs.setRegion(region);
                }
            }


            jobs.setProcessed(false);
            jobs.setProfession(jobTitle);
            jobs.setPostedBy(employer);

            jobs.setDeleted(false);
            jobs.setExpireDate(JsfUtil.convertToDate(expDate));
            jobs.setHowToApply(howToApply);
            jobs.setJobDescription(jobDescription);
            jobs.setLocation(jobTown);
            jobs.setNameOfCompany(employer.getCompanyName());
            jobs.setNatureOfContract(jobType);
            jobs.setNoNeeded(slot);
            jobs.setPublished(publish);
            if (publish) {
                jobs.setPublishedBy(String.valueOf(recruiter));
                jobs.setPublishedDate(new Date());
            }
            jobs.setMinQualification(minQualification);
            jobs.setShowCompanyName(showComp);
            jobs.setAssigned(true);
            jobs.setAssignedTo(recruiter);
            jobs.setRequirements(jobTags);
            jobs.setJobCity(jobTown);
            String tx = jobs.getTransactionId();
            if (tx == null) {
                jobs.setTransactionId(JsfUtil.generateSerial());
            }

            DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);
            if (dbfile != null) {
                JsfUtil.deleteFromDisk(jobs.getAttachedFileName());
                jobs.setAttachedFileName(dbfile.getFileName());
                jobs.setAttachedFileType(dbfile.getFileType());
                // notices.setPic(dbfile.getUploadedFile());
                JsfUtil.saveToDisk(dbfile);
            }

            if (service.save(employer)) {

                if (jobService.save(jobs)) {
                    modelAndView.addObject("msg_success", "Job Posted Successully");
                    modelAndView.setViewName("rec-post-jobs");

                } else {
                    modelAndView.addObject("errorMessage", "Could not save....");
                }
            }

            modelAndView.addObject("employer", employer);
            modelAndView.addObject("job", new Jobs());
            modelAndView.addObject("req", new ArrayList<>());
            modelAndView.addObject("user", user);
            modelAndView.addObject("userInfo", userInfo);


            modelAndView.addObject("imgUtil", new ImageUtil());
            modelAndView.addObject("timeAgo", new TimeAgo());

            List<String> countryList = JsfUtil.getCountries();

            modelAndView.addObject("countryList", countryList);
            modelAndView.addObject("categoryList", getCategories());

            modelAndView.setViewName("rec-post-jobs");

            return modelAndView;
        } catch (Exception e) {

        } finally {
            clearSession(session);
        }
        return modelAndView;
    }


    @GetMapping("/company-transactions")
    public String compTransaction(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {

            String email = user.getUsername();

            Optional<Employer> oppEmp = service.findByEmail(email);
            if (oppEmp.isPresent()) {
                Employer employer = oppEmp.get();


                List<Invoice> invoiceList = invoiceService.findByEmployer(employer);

                model.addAttribute("invoiceList", invoiceList);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("timeAgo", new TimeAgo());
                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("employer", employer);

                return "company-transactions";
            }
        }

        model.addAttribute("invoiceList", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("employer", new Employer());

        return "company-transactions";
    }


//    @GetMapping("/comp-subscription")
//    public String companySubscriptions(Model model,Principal principal){
//        
//        model.addAttribute("list", new ArrayList<>());
//        model.addAttribute("basic", new Subscription("Basic", "Default Basic Subscription", BigDecimal.ZERO) );
//        model.addAttribute("extended", new Subscription("Premium", "Default premium Subscription", BigDecimal.ZERO) );
//        model.addAttribute("prof", new Subscription("Professional", "Default professional Subscription", BigDecimal.ZERO) );
//
//        model.addAttribute("invoiceList", new ArrayList<>() );
//        model.addAttribute("imgUtil", new ImageUtil());
//        model.addAttribute("employer", new Employer());
//        model.addAttribute("timeAgo", new TimeAgo());
//        model.addAttribute("user", new Users());
//        
//        model.addAttribute("url", apiCallBackUrl);
// 
//        model.addAttribute("apiKey", "Yzk2NTU5ZDhiNzg1YjhkMWJmOTJjYWJjMTgzNDFkYzc=");
//        
//        try{
//            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
//
//            String userInfo = WebUtils.toString(loginedUser);
//            Users user = loginedUser.getUser();
//            model.addAttribute("userInfo", userInfo);
//
//            if(user ==null){
//                return "comp-subscription";
//            }
//            model.addAttribute("user", user);
//
//            String email = user.getUsername();
//
//
//            List<Subscription> subList = subService.findAll();
//
//            Subscription basic = subList.stream()
//                    .filter(u -> u != null)
//                    .filter(u -> u.getName() != null)
//                    .filter(u -> u.getName().equalsIgnoreCase("Basic"))
//                    .findAny()
//                    .orElse(new Subscription());
//
//            Subscription extended = subList.stream()
//                    .filter(u -> u != null)
//                    .filter(u -> u.getName() != null)
//                    .filter(u -> u.getName().equalsIgnoreCase("Premium"))
//                    .findAny()
//                    .orElse(new Subscription());
//
//            Subscription prof = subList.stream()
//                    .filter(u -> u != null)
//                    .filter(u -> u.getName() != null)
//                    .filter(u -> u.getName().equalsIgnoreCase("Professional"))
//                    .findAny()
//                    .orElse(new Subscription());
//
//
//            model.addAttribute("list", subList);
//            model.addAttribute("basic", basic);
//            model.addAttribute("extended", extended);
//            model.addAttribute("prof", prof);
//
//            List<String>  basicItems  = JsfUtil.createListFromString(basic.getMessage());
//            List<String> basicMsg = new ArrayList<>();
//            basicItems.forEach(basicMsg::add);
//            basicMsg.add("CV Download: "+basic.getCvCount());
//
//            List<String> proItems  = JsfUtil.createListFromString(prof.getMessage());
//            List<String> proMsg = new ArrayList<>();
//            proItems.forEach(proMsg::add);
//            proMsg.add("CV Download: "+prof.getCvCount());
//
//
//            List<String> extItems  = JsfUtil.createListFromString(extended.getMessage());
//            List<String> extendedMsg = new ArrayList<>();
//            extItems.forEach(extendedMsg::add);
//            extendedMsg.add("CV Download: "+extended.getCvCount());
////        
////            List<String> basicMsg = JsfUtil.createListFromString(basic.getMessage());
////            List<String> proMsg = JsfUtil.createListFromString(prof.getMessage());
////            List<String> extendedMsg = JsfUtil.createListFromString(extended.getMessage());
//            
//            
//
//            model.addAttribute("basicMsg", basicMsg);
//            model.addAttribute("proMsg", proMsg);
//            model.addAttribute("extendedMsg", extendedMsg);
//
//
//            Optional<Employer> oppEmp = service.findByEmail(email);
//                if(oppEmp.isPresent()){
//                    Employer employer = oppEmp.get();
//
//                        List<Invoice> invoiceList = invoiceService.findByEmployer(employer);
//
//                        model.addAttribute("invoiceList", invoiceList);
//                        model.addAttribute("imgUtil", new ImageUtil());
//                        model.addAttribute("employer", employer);
//
//                }
//
//
//                return "comp-subscription";
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        model.addAttribute("userInfo", "");
//        return "comp-subscription";
//        
//         
//        
//    }
//    

    @GetMapping("/comp-transactions")
    public String transactions(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user != null) {

            Optional<Employer> oppEmp = service.findByEmail(user.getUsername());
            if (oppEmp.isPresent()) {

                Employer employer = oppEmp.get();
                List<Invoice> invoiceList = invoiceService.findByEmployer(employer);
                model.addAttribute("invoiceList", invoiceList);
                model.addAttribute("user", user);
                model.addAttribute("employer", employer);

                return "comp-transactions";
            }
        }
        return "index";

    }

    @RequestMapping(value = {"/comp-subscription"})
    public String companySubscriptions(Model model, Principal principal) {

        try {

            Users user = JsfUtil.findOnline(principal);
            Employer employer = new Employer();
            Subscription sub = new Subscription();
            List<SubscriptionUsage> ulist = new ArrayList<>();

            if (user != null) {
                Optional<Employer> oppEmp = service.findByEmail(user.getUsername());

                List<SubscriptionBd> list = new ArrayList<>();
                if (oppEmp.isPresent()) {
                    employer = oppEmp.get();

                    sub = employer.getSubscriptionId();
                    if (sub != null) {

                        ulist = usageService.findByEmployerId(employer.getId());

                        BigDecimal credit = ulist.stream()
                                .filter(c -> c != null)
                                .filter(c -> c.getCredit() != null)
                                .map(SubscriptionUsage::getCredit)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        BigDecimal debit = ulist.stream()
                                .filter(c -> c != null)
                                .filter(c -> c.getDebit() != null)
                                .map(SubscriptionUsage::getDebit)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);


                        SubscriptionBd db = new SubscriptionBd();
                        db.setExpiry("No");

                        Integer total = debit.intValue();
                        Integer used = credit.intValue();
                        Integer remaining = total - used;

                        db.setId(1);
                        db.setPackageName(sub.getName());
                        db.setRemaining(remaining);
                        db.setStatus("");
                        db.setTotalCv(total);
                        db.setUsed(used);
                        list.add(db);
                    }
                }
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("employer", employer);
                model.addAttribute("list", list);
                model.addAttribute("user", user);
                model.addAttribute("sub", sub);
                model.addAttribute("ulist", ulist);

                return "comp-subscription";
            }

            return "index";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";


    }


    @GetMapping("/company-resume")
    public String companyResume(Model model, Principal principal,
                                @RequestParam(defaultValue = "1", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer max) {

        Users user = JsfUtil.findOnline(principal);
        String email = user.getUsername();

        Optional<Employer> oppEmp = service.findByEmail(email);
        if (!oppEmp.isPresent()) {

            model.addAttribute("employer", new Employer());

            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("user", user);
            model.addAttribute("userInfo", "");
            model.addAttribute("cvlist", new ArrayList<>());

            return "company-resume";

        }
        Employer employer = oppEmp.get();

        Page<CompanyCv> cvlist = cvService.findByEmployerId(employer, page, max);

        PageWrapper<CompanyCv> pages = new PageWrapper<>(cvlist, "/company-resume");
        model.addAttribute("page", pages);
        List<CompanyCv> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("joblist", list);

        model.addAttribute("employer", employer);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", "");
        model.addAttribute("cvlist", cvlist);
        model.addAttribute("currentPage", page);


        return "company-resume";

    }

    @RequestMapping(value = {"/employers"}, method = RequestMethod.GET)
    public String employers(Model model, Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "0") String q, @RequestParam(defaultValue = "5") Integer max) {

        Users user = JsfUtil.findOnline(principal);
        Page<Employer> pages = null;
        if (q.equals("0")) {
            pages = findEmployer(page - 1);
        } else {
            pages = findEmployer(page - 1, q, max);
        }
        List<Employer> list = pages.getContent();

        model.addAttribute("list", list);

        model.addAttribute("user", user);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchTerm", q);
        model.addAttribute("totalPages", pages.getTotalPages());

        return "employers";


    }

    @RequestMapping(value = {"/recruiter/employers"}, method = RequestMethod.GET)
    public String employers2(Model model, Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "0") String q, @RequestParam(defaultValue = "5") Integer max) {

        Users user = JsfUtil.findOnline(principal);
        Page<Employer> pages = null;
        if (q.equals("0")) {
            pages = findEmployer(page - 1);
        } else {
            pages = loadEmployer(page - 1, max);
        }
        List<Employer> list = pages.getContent();

        model.addAttribute("list", createEmployerDetais(list));

        model.addAttribute("user", user);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchTerm", q);
        model.addAttribute("totalPages", pages.getTotalPages());

        // return "employers";
        return "recruiter/employers";

    }

    @GetMapping("/post-job")
    public String postJob(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.userRole(loginedUser);

        switch (userInfo) {
            case "ROLE_COMPANY":
            case "ROLE_JOBSEEKER":
                return "redirect:company-post-jobs";

            default:
                return "redirect:rec-post-jobs";
        }
    }

    @GetMapping("/company-manage-job")
    public String companyMangeJob(Model model, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            //Findout if staff

            if (user != null) {
                String usertype = user.getUserType();
                if (usertype == null) {
                    usertype = "Unknown";
                }
                switch (usertype) {
                    case "Staff":
                        return "redirect:employers";

                    case "Company":
                        String email = user.getUsername();

                        try {
                            Optional<Employer> oppEmp = service.findByEmail(email);

                            if (oppEmp.isPresent()) {

                                Employer employer = oppEmp.get();
                                model.addAttribute("employer", employer);

                                Page<Jobs> joblist = jobService.findByEmployer(employer, page, max);
                                model.addAttribute("joblist", joblist);
                                model.addAttribute("currentPage", page);

                                model.addAttribute("user", user);
                                model.addAttribute("timeAgo", new TimeAgo());
                                model.addAttribute("imgUtil", new ImageUtil());
                                model.addAttribute("postedjobs", joblist.getNumberOfElements());
                                model.addAttribute("seekerFinder", new JobSeekerFinder());

                                return "company-manage-job";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }
//               


            }

            return "login";

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "company-manage-job";
    }


    @RequestMapping(value = "/company-post-jobs", method = RequestMethod.POST)
    public ModelAndView companyPostJobs(ModelAndView modelAndView, String jobTitle, String jobTags, String jobType, String experience,
                                        String category, BigDecimal renumeration, BigDecimal toRenumeration,
                                        String region, String jobCountry, String expDate, String howToApply,
                                        String jobCity, String minQualification, boolean showComp, boolean jobPublished, String prefSkills,
                                        String jobDescription, Integer slot, Integer jobId, Principal principal, HttpSession session) {

        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            Employer employer = service.findByEmail(user.getUsername()).orElse(new Employer());

            modelAndView.addObject("user", user);
            modelAndView.addObject("userInfo", userInfo);
            modelAndView.addObject("emp", employer);

            Jobs jobs = jobService.findById(jobId).orElse(new Jobs());
            jobs.setJobTitle(jobTitle);

            Employer postedBy = jobs.getPostedBy();
            if (postedBy == null) {
                jobs.setPostedBy(employer);
            }


            jobs.setJobType(jobType);
            jobs.setExperience(experience);
            jobs.setMinYearsExperience(JsfUtil.createYearsOfExp(experience));
            jobs.setCategory(category);
            jobs.setRenumeration(renumeration);
            jobs.setToRenumeration(toRenumeration);
            jobs.setJobCountry(jobCountry);
            jobs.setCountry(jobCountry);
            jobs.setPrefSkillsAttribute(prefSkills);
            jobs.setTransactionId(JsfUtil.generateSerial());


            if (jobCountry != null) {

                if (jobCountry.equalsIgnoreCase("Ghana")) {
                    jobs.setRegion(region);
                }
            }


            jobs.setProcessed(false);
            jobs.setProfession(jobTitle);
            jobs.setPostedBy(employer);

            jobs.setDeleted(false);
            jobs.setExpireDate(JsfUtil.convertToDate(expDate));
            jobs.setHowToApply(howToApply);
            jobs.setJobDescription(jobDescription);
            jobs.setLocation(jobCity);
            jobs.setNameOfCompany(employer.getCompanyName());
            jobs.setNatureOfContract(jobType);
            jobs.setNoNeeded(slot);
            jobs.setPublished(jobPublished);
            jobs.setMinQualification(minQualification);
            jobs.setShowCompanyName(showComp);
            jobs.setAssigned(true);
            jobs.setRequirements(jobTags);
            jobs.setJobCity(jobCity);
            jobs.setPostedDate(new Date());
            jobs.setAlertSent(false);
            String tx = jobs.getTransactionId();
            if (tx == null) {
                jobs.setTransactionId(JsfUtil.generateSerial());
            }


            DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);
            if (dbfile != null) {
                JsfUtil.deleteFromDisk(jobs.getAttachedFileName());
                jobs.setAttachedFileName(dbfile.getFileName());
                jobs.setAttachedFileType(dbfile.getFileType());
                // notices.setPic(dbfile.getUploadedFile());
                JsfUtil.saveToDisk(dbfile);
            }

            if (service.save(employer)) {

                if (jobService.save(jobs)) {
                    modelAndView.addObject("msg_success", "Job Posted Successully");

                } else {
                    modelAndView.addObject("errorMessage", "Could not save....");
                }
            }

            modelAndView.addObject("employer", employer);
            modelAndView.addObject("job", new Jobs());
            modelAndView.addObject("req", new ArrayList<>());
            modelAndView.addObject("user", user);
            modelAndView.addObject("userInfo", userInfo);


            modelAndView.addObject("imgUtil", new ImageUtil());
            modelAndView.addObject("timeAgo", new TimeAgo());

            List<String> countryList = JsfUtil.getCountries();

            modelAndView.addObject("countryList", countryList);
            modelAndView.addObject("categoryList", getCategories());

            modelAndView.setViewName("company-post-jobs");

            return modelAndView;
        } catch (Exception e) {

        } finally {
            clearSession(session);
        }
        return modelAndView;
    }

    @PostMapping("/company-post-jobs2")
    @ResponseBody
    public boolean postJob(String jobTitle, String jobTags, String jobType, String experience,
                           String category, BigDecimal renumeration, BigDecimal toRenumeration,
                           String region, String jobCountry, String expDate, String city, String howToApply,
                           String jobTown, String minQualification, boolean showComp,
                           String jobDescription, Integer slot, Integer id, Integer jobId) {
        //+"&jobTown="+jobTown+"&minQualification="+minQualification+"&showComp="+showComp;


        Optional<Employer> empOpp = service.findById(id);
        if (empOpp.isPresent()) {

            Employer employer = empOpp.get();
            Jobs jobs = null;
            if (jobId != null) {
                Optional<Jobs> jobOpp = jobService.findById(jobId);
                jobs = jobOpp.orElse(new Jobs());
            } else {
                jobs = new Jobs();
            }


            jobs.setJobTitle(jobTitle);
            jobs.setRequirements(jobTags);
            jobs.setJobType(jobType);
            jobs.setExperience(experience);
            jobs.setMinYearsExperience(JsfUtil.createYearsOfExp(experience));
            jobs.setCategory(category);
            jobs.setRenumeration(renumeration);
            jobs.setToRenumeration(toRenumeration);
            jobs.setJobCountry(jobCountry);
            jobs.setCountry(jobCountry);
            jobs.setJobCity(jobTown);

            if (jobCountry != null) {
                if (jobCountry.equalsIgnoreCase("Ghana")) {
                    jobs.setRegion(region);
                }
            }


            jobs.setProcessed(false);
            jobs.setProfession(jobTitle);
            jobs.setPostedBy(employer);

            jobs.setDeleted(false);
            jobs.setExpireDate(JsfUtil.convertToDate(expDate));
            jobs.setHowToApply(howToApply);
            jobs.setJobDescription(jobDescription);
            jobs.setLocation(jobTown);
            jobs.setNameOfCompany(employer.getCompanyName());
            jobs.setNatureOfContract(jobType);
            jobs.setNoNeeded(slot);
            jobs.setPublished(false);
            jobs.setMinQualification(minQualification);
            jobs.setShowCompanyName(showComp);

            // List<JobRequirement> reqs = creteRequirement(jobTags,jobs);
            //jobs.setRequirementList(reqs);

            return jobService.save(jobs);
        }
        return false;
    }


    //+"&companyName="+companyName+"&companyEmail="+companyEmail+"&comDescription="+comDescription+"&compPhone="+compPhone
    // +"&compEmail="+compEmail+"&compCity="+compCity+"&compAddress="+compAddress;
    @PostMapping("/create-rec-job")
    @ResponseBody
    public boolean postRecJob(String jobTitle, String jobTags, String jobType, String experience,
                              String category, BigDecimal renumeration, BigDecimal toRenumeration,
                              String region, String jobCountry, String expDate, String city, String howToApply,
                              String jobTown, String minQualification, boolean showComp,
                              String jobDescription, Integer slot, Integer id, Integer jobId, String prefSkills,
                              String companyName, String comDescription, String compPhone, String compEmail, String compCity, String compAddress) {


        Employee recruiter = null;
        Optional<Employee> oppRecruiter = empservice.findById(id);
        if (oppRecruiter.isPresent()) {
            recruiter = oppRecruiter.get();
        }


        Jobs jobs = null;
        if (jobId != null) {
            Optional<Jobs> jobOpp = jobService.findById(jobId);
            jobs = jobOpp.orElse(new Jobs());
        } else {
            jobs = new Jobs();
        }
//             
//            Optional<Jobs> jobOpp = jobService.findById(jobId);
//            
//            Jobs jobs = jobOpp.orElse(new Jobs());
        jobs.setJobTitle(jobTitle);

        Employer employer = jobs.getPostedBy();
        if (employer == null) {
            employer = new Employer();
        }
        employer.setCompanyName(companyName);
        employer.setEmail(compEmail);
        employer.setDescription(comDescription);
        employer.setPhoneNumber(compPhone);
        employer.setCity(compCity);
        employer.setAddress(compAddress);


        jobs.setJobType(jobType);
        jobs.setExperience(experience);
        jobs.setMinYearsExperience(JsfUtil.createYearsOfExp(experience));
        jobs.setCategory(category);
        jobs.setRenumeration(renumeration);
        jobs.setToRenumeration(toRenumeration);
        jobs.setJobCountry(jobCountry);
        jobs.setCountry(jobCountry);
        jobs.setRecruiterId(id);
        jobs.setPrefSkillsAttribute(prefSkills);

        if (jobCountry != null) {

            if (jobCountry.equalsIgnoreCase("Ghana")) {
                jobs.setRegion(region);
            }
        }


        jobs.setProcessed(false);
        jobs.setProfession(jobTitle);
        jobs.setPostedBy(employer);

        jobs.setDeleted(false);
        jobs.setExpireDate(JsfUtil.convertToDate(expDate));
        jobs.setHowToApply(howToApply);
        jobs.setJobDescription(jobDescription);
        jobs.setLocation(jobTown);
        jobs.setNameOfCompany(employer.getCompanyName());
        jobs.setNatureOfContract(jobType);
        jobs.setNoNeeded(slot);
        jobs.setPublished(false);
        jobs.setMinQualification(minQualification);
        jobs.setShowCompanyName(showComp);
        jobs.setAssigned(true);
        jobs.setAssignedTo(recruiter);
        jobs.setRequirements(jobTags);
        jobs.setJobCity(jobTown);
        String tx = jobs.getTransactionId();
        if (tx == null) {
            jobs.setTransactionId(JsfUtil.generateSerial());
        }

        //List<JobRequirement> reqs = creteRequirement(jobTags,jobs);
        // jobs.setRequirementList(reqs);

        service.save(employer);

        return jobService.save(jobs);

    }

    public boolean addUsage(Principal principal, Integer seekerId) {
        try {

            Users user = JsfUtil.findOnline(principal);

            String type = user.getUserType();
            if (type == null) {
                return false;
            }
            if (!type.equalsIgnoreCase("Company")) {
                return false;
            }
            Optional<Employer> oppEmp = service.findByEmail(user.getUsername());
            if (oppEmp.isPresent()) {

                Employer employer = oppEmp.get();
                SubscriptionUsage usage = new SubscriptionUsage();
                Optional<SubscriptionUsage> opp = usageService.findByEmployerAndJobSeeker(seekerId, employer.getId());
                if (opp.isPresent()) {
                    usage = opp.get();
                }


                usage.setEmployerId(employer.getId());
                usage.setJobSeekerId(seekerId);
                usage.setEntryDate(LocalDateTime.now());
                usage.setTransactionId(JsfUtil.generateSerial());
                usage.setUsageDate(LocalDate.now());
                usage.setCredit(BigDecimal.ONE);
                usage.setDebit(BigDecimal.ZERO);

                return usageService.save(usage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @GetMapping("/downloadCv/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId, Principal principal) {
        // Load file from database
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return new ResponseEntity("INVALID-USER", HttpStatus.NOT_FOUND);
        }
        if (!allowedTodownload(user)) {
            String restext2 = "<html><body> <center><h2>You are not allowed to download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
            return new ResponseEntity(restext2, HttpStatus.FORBIDDEN);
        }
        String restext = "<html><body> <center><h2>No file available for download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
        if (fileId != null) {

            Optional<JobSeeker> opp = seekerService.findById(fileId);
            if (opp.isPresent()) {
                try {
                    JobSeeker dbFile = opp.get();
                    String mime = dbFile.getCvFileType();
                    String fileName = dbFile.getCvFileName();

                    if (fileName == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    Path path = JsfUtil.findPath(fileName);

                    File file = path.toFile();
                    if (file == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    ResponseEntity<Resource> result = null;

                    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                    if (mime == null) {
                        result = ResponseEntity.ok()
                                // .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                                .contentLength(file.length())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                                .body(resource);

                        if (result.getStatusCode() == HttpStatus.OK) {
                            addUsage(principal, dbFile.getId());
                            return result;
                        }
                    }

                    result = ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                            .body(resource);

                    if (result.getStatusCode() == HttpStatus.OK) {
                        addUsage(principal, dbFile.getId());
                        return result;
                    }
                } catch (java.nio.file.NoSuchFileException e) {
                    e.printStackTrace();
                    return new ResponseEntity(restext, HttpStatus.NOT_FOUND);

                } catch (IOException ex) {
                    Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/seeker/downloadCV/{transactionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String transactionId, Principal principal) {
        // Load file from database
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return new ResponseEntity("INVALID-USER", HttpStatus.NOT_FOUND);
        }
        if (!allowedTodownload(user, transactionId)) {
            String restext2 = "<html><body> <center><h2>You are not allowed to download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
            return new ResponseEntity(restext2, HttpStatus.FORBIDDEN);
        }
        String restext = "<html><body> <center><h2>No file available for download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
        if (transactionId != null) {

            Optional<JobSeeker> opp = seekerService.findByTransactionId(transactionId);
            if (opp.isPresent()) {
                try {
                    JobSeeker dbFile = opp.get();
                    String mime = dbFile.getCvFileType();
                    String fileName = dbFile.getCvFileName();

                    if (fileName == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    Path path = JsfUtil.findPath(fileName);

                    File file = path.toFile();
                    if (file == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    ResponseEntity<Resource> result = null;

                    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                    if (mime == null) {
                        result = ResponseEntity.ok()
                                // .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                                .contentLength(file.length())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                                .body(resource);

                        if (result.getStatusCode() == HttpStatus.OK) {
                            addUsage(principal, dbFile.getId());
                            return result;
                        }
                    }

                    result = ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                            .body(resource);

                    if (result.getStatusCode() == HttpStatus.OK) {
                        addUsage(principal, dbFile.getId());
                        return result;
                    }
                } catch (java.nio.file.NoSuchFileException e) {
                    e.printStackTrace();
                    return new ResponseEntity(restext, HttpStatus.NOT_FOUND);

                } catch (IOException ex) {
                    Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }


    @GetMapping("/seeker/downloadCVByEmail/{email}")
    public ResponseEntity<Resource> downloadFileByEmail(@PathVariable String email, Principal principal) {
        // Load file from database
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return new ResponseEntity("INVALID-USER", HttpStatus.NOT_FOUND);
        }
        if (!allowedTodownloadByEmail(user, email)) {
            String restext2 = "<html><body> <center><h2>You are not allowed to download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
            return new ResponseEntity(restext2, HttpStatus.FORBIDDEN);
        }
        String restext = "<html><body> <center><h2>No file available for download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
        if (email != null) {

            Optional<JobSeeker> opp = seekerService.findByEmail(email);
            if (opp.isPresent()) {
                try {
                    JobSeeker dbFile = opp.get();
                    String mime = dbFile.getCvFileType();
                    String fileName = dbFile.getCvFileName();

                    if (fileName == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    Path path = JsfUtil.findPath(fileName);

                    File file = path.toFile();
                    if (file == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    ResponseEntity<Resource> result = null;

                    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                    if (mime == null) {
                        result = ResponseEntity.ok()
                                // .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                                .contentLength(file.length())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                                .body(resource);

                        if (result.getStatusCode() == HttpStatus.OK) {
                            addUsage(principal, dbFile.getId());
                            return result;
                        }
                    }

                    result = ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getCvFileName() + "\"")
                            .body(resource);

                    if (result.getStatusCode() == HttpStatus.OK) {
                        addUsage(principal, dbFile.getId());
                        return result;
                    }
                } catch (java.nio.file.NoSuchFileException e) {
                    e.printStackTrace();
                    return new ResponseEntity(restext, HttpStatus.NOT_FOUND);

                } catch (IOException ex) {
                    Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }


    @GetMapping("/download-attachment/{fileId}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Integer fileId, Principal principal) {
        // Load file from database
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            return new ResponseEntity("INVALID-USER", HttpStatus.NOT_FOUND);
        }

        String restext = "<html><body> <center><h2>No file available for download, <br/> <button class='btn btn-info' onclick='history.back()' style='color:green'> Go Back</button></h2><center></body></html>";
        if (fileId != null) {

            Optional<Jobs> opp = jobService.findById(fileId);
            if (opp.isPresent()) {
                try {
                    Jobs dbFile = opp.get();
                    String mime = dbFile.getAttachedFileType();
                    String fileName = dbFile.getAttachedFileName();

                    if (fileName == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    Path path = JsfUtil.findPath(fileName);

                    File file = path.toFile();
                    if (file == null) {
                        return new ResponseEntity(restext, HttpStatus.NOT_FOUND);
                    }
                    ResponseEntity<Resource> result = null;

                    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                    if (mime == null) {
                        result = ResponseEntity.ok()
                                // .contentType(MediaType.parseMediaType(dbFile.getCvFileType()))
                                .contentLength(file.length())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                                .body(resource);

                        if (result.getStatusCode() == HttpStatus.OK) {
                            addUsage(principal, dbFile.getId());
                            return result;
                        }
                    }

                    result = ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType(mime))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                            .body(resource);

                    if (result.getStatusCode() == HttpStatus.OK) {
                        addUsage(principal, dbFile.getId());
                        return result;
                    }
                } catch (java.nio.file.NoSuchFileException e) {
                    e.printStackTrace();
                    return new ResponseEntity(restext, HttpStatus.NOT_FOUND);

                } catch (IOException ex) {
                    Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    //    @GetMapping("/com-job-details2222")
//    public String comJobDetails(Model model,Principal principal,Integer id){
//        
//        Optional<Jobs> oppJop = jobService.findById(id);
//        Jobs jobs = oppJop.orElse(new Jobs());
//        
//        List<String> jreqList= getRequirement(jobs);
//       // List<JobRequirement> jreqList= jservice.findByJobId(jobs);
//        model.addAttribute("job", jobs);
//        model.addAttribute("jreqList", jreqList);
//        
//   
//        
//        return "com-job-details";
//    }
    private List<JobRequirement> creteRequirement(String jobRequirement, Jobs job) {

        // step one : converting comma separate String to array of String
        List<JobRequirement> reqs = new ArrayList<>();
        String[] elements = jobRequirement.split(",");

        // step two : convert String array to list of String
        List<String> strReq = Arrays.asList(elements);

        strReq.forEach((str) -> {
            reqs.add(new JobRequirement(str, job));
        });


        return reqs;

    }


    private List<String> getRequirement(Jobs job) {
        if (job == null) {
            return new ArrayList<>();
        }
        String req = job.getRequirements();
        if (req == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(req.split(","));


    }

    private String getRequirement2(Jobs job) {

        List<JobRequirement> reqs = jservice.findByJobId(job);

        String citiesCommaSeparated = reqs.stream()
                .map(JobRequirement::getRequirement)
                .collect(Collectors.joining(","));


        return citiesCommaSeparated;

    }


    @GetMapping("/duplicate-cmp")
    public String duplicateComp(Model model, String rt) {

        List<Employer> slist = service.findDuplicates(rt);

        model.addAttribute("list", slist);
        model.addAttribute("msg", "You have two account, please choose active one");
        model.addAttribute("rt", rt);

        return "duplicate-cmp";
    }

    @GetMapping("/delete-cprofile/{id}")
    @ResponseBody
    public boolean deleteProfile(Principal principal, @PathVariable Integer id) {
        return service.deleteById(id);
    }

    private void deleteRequirements(List<JobRequirement> req) {
        if (req.isEmpty()) {
            return;
        }
        if (jservice.deleteAll(req)) {
        }
    }

    private List<Positions> getCategories() {
        return positionService.findAll();
    }

    @GetMapping("/rec-post-jobs")
    public String recPostJob(Model model, Principal principal, @RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.userRole(loginedUser);
        Users user = loginedUser.getUser();

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);


        Employer employer = new Employer();
        Employee employee = user.getStaffId();
        model.addAttribute("emp", employee);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());

        List<String> countryList = JsfUtil.getCountries();

        model.addAttribute("countryList", countryList);
        model.addAttribute("categoryList", getCategories());
        if (id != 0) {
            Optional<Jobs> oppJob = jobService.findById(id);
            if (oppJob.isPresent()) {

                Jobs job = oppJob.get();
                employer = job.getPostedBy();
                if (employer == null) {
                    employer = new Employer();
                }
                // model.addAttribute("req", getRequirement(job) );
                model.addAttribute("job", job);
                model.addAttribute("employer", employer);

                return "rec-post-jobs";
            }
        }


        model.addAttribute("employer", employer);
        model.addAttribute("job", new Jobs());
        model.addAttribute("req", new ArrayList<>());


        return "rec-post-jobs";
    }

    @GetMapping("/recruiter/post-job")
    public String recPostJob2(Model model, Principal principal) {

        return "recruiter/post-job";
    }


    @GetMapping("/browse-applicants")
    public String browseApplicants(Model model, Principal principal) {
        return "browse-applicants";
    }


    public boolean findEmp(String name) {
        Optional<Employer> opp = service.findByName(name);
        return opp.isPresent();
    }

    @GetMapping("/find-employer/{name}")
    @ResponseBody
    public EmpDb findCompany(@PathVariable String name) {

        Employer emp = service.findByName(name).orElse(new Employer());
        EmpDb db = new EmpDb();
        db.setAddress(emp.getAddress());
        db.setCity(emp.getCity());
        db.setCompanyName(emp.getCompanyName());
        db.setCountry(emp.getCountry());
        db.setTelephone(emp.getPhoneNumber());
        db.setWebSite(emp.getWebSite());

        return db;
    }

    @PostMapping("/upload-employer-pic")
    @ResponseBody
    public void uploadSeekerPic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id) {

        String appendName = String.valueOf((new Date()).getTime());

        Optional<Employer> opp = service.findById(id);
        if (opp.isPresent()) {
            if (files != null) {
                try {
                    Employer employer = opp.get();
                    String fileName = appendName + files.getOriginalFilename();
                    String fileType = files.getContentType();
                    byte[] originalByte = files.getBytes();

                    //BufferedImage bi = JsfUtil.simpleResizeImage(originalByte, 140, 140);
                    //byte[] uploadedFile = JsfUtil.convertToByte(bi);


                    DbFile dbfile = new DbFile(fileName, fileType, originalByte);
                    if (dbfile != null) {
                        JsfUtil.deleteFromDisk(employer.getFileName());

                        employer.setFileName(fileName);
                        employer.setFileType(dbfile.getFileType());
                        JsfUtil.saveToDisk(dbfile);
                    }

                    service.save(employer);

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {

                }
            }
        }

    }


    @GetMapping("/company-post-jobs")
    public String postJob(Model model, Principal principal, @RequestParam(value = "id", required = false, defaultValue = "0") Integer id) {
        setDefaultSettings(model);
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            String email = user.getUsername();
            Optional<Employer> oppEmp = service.findByEmail(email);
            Employer employer = oppEmp.orElse(new Employer());

            Users currentUser = JsfUtil.findOnline(principal);

            if (!packageTransactionService.findByUserAndStatus(currentUser)) {
                Optional<Company> oppComOptional = cservice.findByName("L'aine");
                if (oppComOptional.isPresent()) {
                    Company com = oppComOptional.get();
                    if (com.getActivatePayment()) {
                        if (employer.getSubscriptionId() == null) {
                            return "redirect:subscription-list";
                        }
                    }
                }
                List<String> countryList = JsfUtil.getCountries();


                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("employer", employer);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("timeAgo", new TimeAgo());
                model.addAttribute("categoryList", getCategories());
                model.addAttribute("countryList", countryList);

                Optional<Jobs> oppJob = jobService.findById(id);
                if (oppJob.isPresent()) {

                    Jobs job = oppJob.get();
                    Employer emp = job.getPostedBy();
                    if (emp == null) {
                        job = new Jobs();
                    } else {
                        if (!emp.equals(employer)) {
                            job = new Jobs();
                        }
                    }

                    model.addAttribute("job", job);

                    return "company-post-jobs";
                }

                model.addAttribute("job", new Jobs());
                model.addAttribute("req", new ArrayList<>());
            } else {
                return "redirect:subscription-list";
            }

        } catch (Exception e) {

        }
        return "company-post-jobs";
    }


    @GetMapping("/employer/edit-job/{transactionId}")
    public String editJob(Model model, Principal principal,
                          @PathVariable String transactionId) {
        setDefaultSettings(model);
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            String email = user.getUsername();
            Optional<Employer> oppEmp = service.findByEmail(email);
            Employer employer = oppEmp.orElse(new Employer());


            Optional<Company> oppComOptional = cservice.findByName("L'aine");
            if (oppComOptional.isPresent()) {
                Company com = oppComOptional.get();
                if (com.getActivatePayment()) {
                    if (employer.getSubscriptionId() == null) {
                        return "redirect:subscription-list";
                    }
                }
            }
            List<String> countryList = JsfUtil.getCountries();

            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("employer", employer);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("timeAgo", new TimeAgo());
            model.addAttribute("categoryList", getCategories());
            model.addAttribute("countryList", countryList);

            Optional<Jobs> oppJob = jobService.findByTransactionId(transactionId);
            if (oppJob.isPresent()) {

                Jobs job = oppJob.get();
                Employer emp = job.getPostedBy();
                if (emp == null) {
                    job = new Jobs();
                } else {
                    if (!emp.equals(employer)) {
                        job = new Jobs();
                    }
                }

                model.addAttribute("job", job);

                return "employer/company-post-jobs";
            }

            model.addAttribute("job", new Jobs());
            model.addAttribute("req", new ArrayList<>());

        } catch (Exception e) {

        }

        return "employer/company-post-jobs";
    }


    @PostMapping("/document-upload")
    @ResponseBody
    public void uploadSingleFile(@RequestParam("file") MultipartFile[] files, HttpSession session) {
        try {

            DbFile dbFile = null;
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                if (fileName.contains("..")) {
                    System.out.println("Invalid File " + fileName);
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                dbFile = new DbFile(fileName, file.getContentType(), file.getBytes());
            }

            session.setAttribute(UPLOADED_FILE_SESSION, dbFile);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void clearSession(HttpSession session) {
        session.removeAttribute(UPLOADED_FILE_SESSION);
    }

    private Page<Employer> findEmployer(Integer page) {
        return service.findAll(page);
    }

    private Page<Employer> findEmployer(Integer page, String q, Integer max) {


        List<Employer> list = search.searchPaginatedEmployers(q, page, max);
        Pageable pageable = PageRequest.of(page, max, Sort.by(Sort.Direction.ASC, "companyName"));

        return new PageImpl<Employer>(list, pageable, list.size());
    }

    private Page<Employer> loadEmployer(Integer page, Integer max) {
        System.out.println("loading...................emp");
        return service.findAll(page, max);


    }


    private Integer findPostedJobs(Employer employer) {
        return jobService.findByPostedByCount(employer);
    }

    @GetMapping("/update-employer-profile")
    public String updateEmployerProfile(Principal principal, Model model, Integer id) {
        Users user = JsfUtil.findOnline(principal);
        Optional<Employer> opp = service.findById(id);

        if (opp.isPresent()) {
            Employer employer = opp.get();
            model.addAttribute("employer", employer);
            model.addAttribute("user", user);

            return "update-employer-profile";
        }

        model.addAttribute("employer", new Employer());
        model.addAttribute("user", user);

        return "update-employer-profile";
    }

    private boolean allowedTodownload(Users user) {
        String userty = user.getUserType();
        String username = user.getUsername();

        switch (userty) {
            case "Staff":
                return true;

            case "Company":
                return checked(username);

            default:
                return false;
        }
    }

    private boolean allowedTodownload(Users user, String transactionId) {
        String userty = user.getUserType();
        String username = user.getUsername();

        switch (userty) {
            case "Staff":
                return true;

            case "Individual":
                return checked(username, transactionId);

            default:
                return false;
        }
    }

    private boolean allowedTodownloadByEmail(Users user, String transactionId) {
        try {
            String username = user.getUsername();
            return username.equals(transactionId);
        } catch (Exception e) {
            return false;
        }

    }

    @GetMapping("/company-dashboard")
    public String companyDashboard(Principal principal, Model model) {
        try {
            Users user = JsfUtil.findOnline(principal);


            if (user == null) {
                model.addAttribute("ulist", new ArrayList<>());
                model.addAttribute("subscription", new Subscription());
                model.addAttribute("count", 0);
                model.addAttribute("user", new Users());
                return "company-dashboard";
            }
            model.addAttribute("user", user);
            Optional<Employer> opp = service.findByEmail(user.getUsername());
            if (opp.isPresent()) {
                Employer employer = opp.get();

                //Posted jobs
                Integer postedjobs = jobService.findByPostedByCount(employer);
                model.addAttribute("postedJobs", postedjobs);


                Subscription subscription = employer.getSubscriptionId();
                List<SubscriptionUsage> ulist = usageService.findByEmployerId(employer.getId());
                if (ulist != null) {
                    model.addAttribute("ulist", ulist);
                } else {
                    model.addAttribute("ulist", new ArrayList<>());
                }
                if (subscription != null) {
                    model.addAttribute("subscription", subscription);
                } else {
                    model.addAttribute("subscription", new Subscription());
                }

                model.addAttribute("jobSeekers", createJobSeekers(ulist));

                return "company-dashboard";
            }

            model.addAttribute("ulist", new ArrayList<>());
            model.addAttribute("subscription", new Subscription());
            model.addAttribute("postedjobs", 0);


            return "company-dashboard";

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("subscription", new Subscription());
        model.addAttribute("count", 0);
        model.addAttribute("ulist", new ArrayList<>());

        return "company-dashboard";
    }

    @GetMapping("/delete-cv/{id}")
    @ResponseBody
    public boolean deleteCv(@PathVariable Integer id) {
        return cvService.deleteById(id);
    }

    @PostMapping("/save-cv/{id}")
    @ResponseBody
    public boolean saveCv(Principal principal, @PathVariable Integer id) {
        try {
            Users user = JsfUtil.findOnline(principal);
            Optional<Employer> opp = service.findByEmail(user.getUsername());
            if (opp.isPresent()) {
                Employer employer = opp.get();
//                System.out.println("employer..."+employer);
                Optional<JobSeeker> oppSeeker = seekerService.findById(id);
                if (oppSeeker.isPresent()) {
                    JobSeeker seeker = oppSeeker.get();

//                    System.out.println("seeker..."+seeker);

                    CompanyCv cv = new CompanyCv();
                    cv.setApproved(true);
                    cv.setEmployerId(employer);
                    cv.setEntryDate(new Date());
                    cv.setJobSeekerId(seeker);
                    cv.setProcessed(true);
                    cv.setTransactionNo(JsfUtil.generateSerial());

                    return cvService.save(cv);
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createEmployerModel(Model model, Users user) {
        Optional<Employer> opp = service.findByEmail(user.getUsername());
        if (opp.isPresent()) {
            model.addAttribute("employer", opp.get());
        } else {
            model.addAttribute("employer", new Employer());
        }

    }


    private void createStaffModel(Model model, Users user) {
        model.addAttribute("employee", user.getStaffId());
    }

    private boolean checked(String username, String trx) {
        try {
            Optional<JobSeeker> opp = seekerService.findByEmail(username);
            if (opp.isPresent()) {
                return opp.get().getTransactionId().equalsIgnoreCase(trx);
            }
        } catch (Exception e) {

        }
        return false;
    }

    private boolean checked(String username) {

        Optional<Employer> opp = service.findByEmail(username);
        if (!opp.isPresent()) {
            return false;
        }

        Employer employer = opp.get();
        Subscription subscription = employer.getSubscriptionId();

        if (subscription == null) {
            return false;
        }

        List<SubscriptionUsage> list = usageService.findByEmployerId(employer.getId());

        BigDecimal credit = list.stream()
                .filter(c -> c != null)
                .filter(c -> c.getCredit() != null)
                .map(SubscriptionUsage::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("total credit..." + credit);

        BigDecimal debit = list.stream()
                .filter(c -> c != null)
                .filter(c -> c.getDebit() != null)
                .map(SubscriptionUsage::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("total debit..." + debit);

        return debit.compareTo(credit) == 1;


    }

    private Employer findOrCreateEmployer(String companyName) {
        return service.findByName(companyName).orElse(new Employer());
    }


    @GetMapping("/search-employers")
    @ResponseBody
    public List<EmpDb> searchEmployer(String q, Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        if (!user.getUserType().equals("Staff")) {
            return new ArrayList<>();
        }
        List<Employer> list = search.searchPaginatedEmployers(q, 0, 15);

        List<EmpDb> edlist = new ArrayList<>();
        list.stream().forEach((emp) -> {

            EmpDb db = new EmpDb();
            db.setAddress(emp.getAddress());
            db.setCity(emp.getCity());
            db.setCompanyName(emp.getCompanyName());
            db.setCountry(emp.getCountry());
            db.setTelephone(emp.getPhoneNumber());
            db.setWebSite(emp.getEmail());
            db.setId(emp.getId());

            edlist.add(db);

        });

        return edlist;

    }

    public String findJobSeekerDetails(SubscriptionUsage u, String option) {
        if (u == null) {
            return "";
        }
        Integer jobSeekerId = u.getJobSeekerId();
        Optional<JobSeeker> opp = seekerService.findById(jobSeekerId);
        if (!opp.isPresent()) {
            return null;
        }
        JobSeeker jobseeker = opp.get();
        switch (option) {
            case "name":
                return jobseeker.getFullName();
            case "location":
                return jobseeker.getCountryOfOrigin();
            case "prof":
                return jobseeker.getProffesionalTitile();
            case "fileName":
                return jobseeker.getPicFileName();

            default:
                return jobseeker.getFullName();
        }
    }

    private List<JobSeekerObject> createJobSeekers(List<SubscriptionUsage> list) {

        return list.stream().map(c ->
                        //id, name, proffession, location
                        new JobSeekerObject(
                                new Date().getTime(),
                                findJobSeekerDetails(c, "name"),
                                findJobSeekerDetails(c, "prof"),
                                findJobSeekerDetails(c, "location"),
                                findJobSeekerDetails(c, "fileName"),
                                c.getUsageDate()
                        )
                )
                .collect(Collectors.toList());

    }

    private void setDefaultSettings(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", "");
        model.addAttribute("employer", new Employer());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("categoryList", getCategories());
        model.addAttribute("countryList", new ArrayList<>());
    }

    private List<CompanyDetails> createEmployerDetais(List<Employer> list) {


        // logo,  name, String message,  industry,  jobs,  id
        return list.stream().map(c ->
                        new CompanyDetails(
                                c.getFileName(),
                                c.getCompanyName(),
                                c.getDescription(),
                                c.getCategory(),
                                findPostedJobs(c),
                                createLocation(c),
                                c.getId()
                        )
                )
                .collect(Collectors.toList());

    }

    public String createLocation(Employer c) {
        String city = c.getCity();
        String region = c.getCountry();

        return city + " , " + region;
    }

    private void defaultSetup(Employer employer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @RequestMapping(value = "/recruiter/post-job", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> postJob(@RequestBody PostJob job, Principal principal, HttpSession session) {

        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            Jobs jobs = null;

            Employee recruiter = user.getStaffId();
            Integer jobId = job.getJobId();
            if (jobId != null || jobId != 0) {
                jobs = jobService.findById(job.getJobId()).orElse(new Jobs());
            } else {
                jobs = new Jobs();
            }


            jobs.setJobTitle(job.getJobTitle());

            Employer employer = jobs.getPostedBy();
            if (employer == null) {
                employer = findOrCreateEmployer(job.getCompanyName());

            }

            employer.setEmail(job.getCompEmail());
            employer.setDescription(job.getComDescription());
            employer.setPhoneNumber(job.getCompPhone());
            employer.setCity(job.getCompCity());
            employer.setAddress(job.getCompAddress());
            jobs.setJobType(job.getJobType());
            jobs.setExperience(job.getExperience());
            jobs.setMinYearsExperience(JsfUtil.createYearsOfExp(job.getExperience()));
            jobs.setCategory(job.getCategory());
            jobs.setRenumeration(job.getRenumeration());
            jobs.setToRenumeration(job.getToRenumeration());
            jobs.setJobCountry(job.getJobCountry());
            jobs.setCountry(job.getJobCountry());
            jobs.setPrefSkillsAttribute(job.getPrefSkills());

            jobs.setDeleted(false);
            jobs.setJobStateRegion(job.getRegion());
            jobs.setJobStatus("New");
            //jobs.setJobTags(jobTags);

            jobs.setPaymentStatus("Unpaid");
            jobs.setPostedDate(new Date());
            jobs.setProcessed(false);
            jobs.setPublished(false);
            jobs.setRecruiterId(recruiter.getEmployeeid());
            jobs.setRegion(job.getRegion());
            jobs.setTelephone(job.getCompPhone());
            jobs.setAssigned(false);

            jobs.setRecruiterId(recruiter.getEmployeeid());

            String jobCountry = job.getJobCountry();
            if (jobCountry != null) {

                if (jobCountry.equalsIgnoreCase("Ghana")) {
                    jobs.setRegion(job.getRegion());
                }
            }


            jobs.setProcessed(false);
            jobs.setProfession(job.getJobTitle());
            jobs.setPostedBy(employer);

            jobs.setDeleted(false);
            jobs.setExpireDate(JsfUtil.convertToDate(job.getExpDate()));
            jobs.setHowToApply(job.getHowToApply());
            jobs.setJobDescription(job.getJobDescription());
            jobs.setLocation(job.getJobTown());
            jobs.setNameOfCompany(employer.getCompanyName());
            jobs.setNatureOfContract(job.getJobType());
            jobs.setNoNeeded(job.getSlot());

            Boolean publish = job.getPublish();
            if (publish == null) {
                publish = false;
            }
            jobs.setPublished(publish);
            if (publish) {
                jobs.setPublishedBy(String.valueOf(recruiter));
                jobs.setPublishedDate(new Date());
            }

            Boolean showComp = job.getShowComp();
            if (showComp == null) {
                showComp = false;
            }
            jobs.setMinQualification(job.getMinQualification());
            jobs.setShowCompanyName(showComp);
            jobs.setAssigned(true);
            jobs.setAssignedTo(recruiter);
            jobs.setRequirements(job.getJobTags());
            jobs.setJobCity(job.getJobTown());
            String tx = jobs.getTransactionId();
            if (tx == null) {
                jobs.setTransactionId(JsfUtil.generateSerial());
            }

            DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);
            if (dbfile != null) {
                JsfUtil.deleteFromDisk(jobs.getAttachedFileName());
                jobs.setAttachedFileName(dbfile.getFileName());
                jobs.setAttachedFileType(dbfile.getFileType());
                // notices.setPic(dbfile.getUploadedFile());
                JsfUtil.saveToDisk(dbfile);
            }

            service.save(employer);
            String status = jobService.save(jobs) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        } finally {
            clearSession(session);
        }
    }


}
