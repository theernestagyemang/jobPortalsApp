/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api;


import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.api.model.Requisitions;
import com.debusey.smart.pos.smartpos.db.ApiSearchJob;
import com.debusey.smart.pos.smartpos.db.PostedBy;
import com.debusey.smart.pos.smartpos.entity.ApiKeys;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.service.ApiKeyService;
import com.debusey.smart.pos.smartpos.service.EmployerService;
import com.debusey.smart.pos.smartpos.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author SL002
 */

@RestController
@RequestMapping("/api/v1")
public class JobsApiController {

    private final String duties = "duties";
    private final String requirements = "requirements";
    private final String special = "special";
    private final String skills = "skills";
    @Autowired
    public ApiKeyService keyService;
    @Autowired
    private JobsService service;
    @Autowired
    private EmployerService cservice;

    @RequestMapping("/job")
    public Page<ApiSearchJob> findAllJobs(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max, @RequestParam(defaultValue = "AB") String apiKey) {

        Optional<ApiKeys> oppKey = keyService.findByUniqueKey(apiKey);
        if (!oppKey.isPresent()) {
            throw new BeanNotFoundException("Invalid Api-key: " + apiKey);
        }
        try {
            Page<Jobs> jobs = service.findAll(page, max);
            List<Jobs> list = jobs.getContent();

            return createJobSearch(list, PageRequest.of(page, max));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanNotFoundException("Some Error Occurred: ");
        }
    }

    @RequestMapping("/findJob/{jobId}")
    public ApiSearchJob findJob(@PathVariable Integer jobId, @RequestParam(defaultValue = "AB") String apiKey) {
        Optional<ApiKeys> oppKey = keyService.findByUniqueKey(apiKey);
        if (!oppKey.isPresent()) {
            throw new BeanNotFoundException("Invalid Api-key: " + apiKey);
        }
        Optional<Jobs> oppJob = service.findById(jobId);
        if (!oppJob.isPresent()) {
            throw new BeanNotFoundException("Could not find Job: " + jobId);
        }
        Jobs jobs = oppJob.get();
        return createOneJobSearch(jobs);


    }

//    @RequestMapping(method = RequestMethod.POST,value="/jobs")
//    public void addJobs(@RequestBody Jobs job){
//        service.save(job);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/joblist")
    public void addJobs(@RequestBody List<Jobs> job) {
        service.saveAll(job);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/load-jobs")
    public void loadJobs(@RequestBody List<Requisitions> reqlist) {

        List<Jobs> list = new ArrayList<>();
        reqlist.stream().forEach((item) -> {
            Jobs jobs = new Jobs();
            jobs.setAssigned(false);
            //jobs.setCategory(item.getC);
            jobs.setConditionOfService(item.getConditions());
            jobs.setCountryId(item.getCountryId());
            jobs.setDeleted(Boolean.FALSE);
            jobs.setEmail(item.getEmail());
            jobs.setExperience(String.valueOf(item.getMinExp()));
            jobs.setFromAge(item.getAgeFrom());
            jobs.setJobCity(findCity(item.getStateId()));
            jobs.setJobCountry(findCountry(item.getCountryId()));
            jobs.setJobTitle(item.getTitle());
            jobs.setProfession(item.getProfession());
        });
        service.saveAll(list);
    }

    @RequestMapping("/job/{id}")
    public Jobs findById(@PathVariable Integer id) {
        return service.findById(id).orElse(new Jobs());
    }

    @RequestMapping("/jobs")
    public List<Jobs> findAll() {
        return service.findAll();
    }

    private String findCity(int stateId) {
        return String.valueOf(stateId);
    }

    private String findCountry(int countryId) {
        return String.valueOf(countryId);
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

                case "skills":
                    String skills = job.getPrefSkillsAttribute();
                    if (skills == null) {
                        return new ArrayList<>();
                    }
                    return Arrays.asList(skills.split(","));

                case "requirements":
                    String req = job.getRequirements();
                    if (req == null) {
                        return new ArrayList<>();
                    }
                    return Arrays.asList(req.split(","));


                case "special":
                    String sp = job.getSpecialRequirements();
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

    private Page<ApiSearchJob> createJobSearch(List<Jobs> list, Pageable pageable) throws Exception {
        List<ApiSearchJob> serchlist = new ArrayList<>();

        list.stream().forEach((item) -> {

            ApiSearchJob js = new ApiSearchJob();
            Employer emp = item.getPostedBy();
            PostedBy postedBy = createPostedby(emp);

            js.setPostedBy(postedBy);
            if (emp != null) {
                js.setFileName(emp.getFileName());
            }

            //js.setId(item.getId());
            js.setCategory(item.getCategory());
            js.setCountry(item.getCountry());
            js.setExpireDate(JsfUtil.convertFromDate(item.getExpireDate()));
            js.setNatureOfContract(item.getNatureOfContract());
            js.setLocation(item.getLocation());
            js.setPublishedDate(JsfUtil.convertFromDate(item.getPublishedDate()));
            js.setMinSalary(item.getRenumeration());
            js.setMaxSalary(item.getToRenumeration());
            js.setShowCompanyName(item.getShowCompanyName());
            js.setProfession(item.getProfession());
            js.setDetails(item.getJobDescription());
            js.setDutiesAndRespo(getRequirement(item, duties));
            js.setSkillls(getRequirement(item, skills));
            js.setSlot(item.getNoNeeded());
            js.setGender(item.getGender());
            js.setSpecialRequirements(getRequirement(item, requirements));
            js.setQualification(item.getMinQualification());
            js.setExpireDate(JsfUtil.convertFromDate(item.getExpireDate()));
            js.setHowToApply(item.getHowToApply());
            js.setIndustry(item.getIndustry());
            js.setMinYearsExperience(item.getMinYearsExperience());
            js.setTelephone(item.getTelephone());

            serchlist.add(js);

        });

        return new PageImpl<>(serchlist, pageable, serchlist.size());
        //return serchlist;
    }

    private ApiSearchJob createOneJobSearch(Jobs item) {

        ApiSearchJob js = new ApiSearchJob();
        Employer emp = item.getPostedBy();
        PostedBy postedBy = createPostedby(emp);

        js.setPostedBy(postedBy);
        if (emp != null) {
            js.setFileName(emp.getFileName());
        }

        js.setId(item.getId());
        js.setCategory(item.getCategory());
        js.setCountry(item.getCountry());
        js.setExpireDate(JsfUtil.convertFromDate(item.getExpireDate()));
        js.setNatureOfContract(item.getNatureOfContract());
        js.setLocation(item.getLocation());
        js.setPublishedDate(JsfUtil.convertFromDate(item.getPublishedDate()));
        js.setMinSalary(item.getRenumeration());
        js.setMaxSalary(item.getToRenumeration());
        js.setShowCompanyName(item.getShowCompanyName());
        js.setProfession(item.getProfession());
        js.setDetails(item.getJobDescription());
        js.setDutiesAndRespo(getRequirement(item, duties));
        js.setSkillls(getRequirement(item, requirements));
        js.setSlot(item.getNoNeeded());
        js.setGender(item.getGender());
        js.setSpecialRequirements(getRequirement(item, special));
        js.setQualification(item.getMinQualification());
        js.setExpireDate(JsfUtil.convertFromDate(item.getExpireDate()));
        js.setHowToApply(item.getHowToApply());
        js.setIndustry(item.getIndustry());
        js.setMinYearsExperience(item.getMinYearsExperience());
        js.setTelephone(item.getTelephone());


        return js;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/job")
    public boolean createApiJob(@RequestBody ApiSearchJob js, @RequestParam(defaultValue = "AB") String apiKey) {

        Optional<ApiKeys> oppKey = keyService.findByUniqueKey(apiKey);
        if (!oppKey.isPresent()) {
            throw new BeanNotFoundException("Invalid Api-key: " + apiKey);
        }
        Jobs job = createJobFromJobSearch(js);
        if (job != null) {
            return service.save(job);
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/job/post")
    public boolean createJob(@RequestBody ApiSearchJob js, @RequestParam String email) {

        Optional<Employer> employer = cservice.findByEmail(email);
        Jobs job = new Jobs();
        if (employer.isPresent()) {
            Employer emp = employer.get();
//            System.out.println(employer.get());

            job.setAlertSent(false);
            job.setCategory(js.getCategory());
            job.setCountry(js.getCountry());
            job.setJobCity(js.getCity());
            job.setDeleted(false);

//            job.setDutiesAndRespo(JsfUtil.convertToString(js.getDutiesAndRespo()));
            job.setEmail(email);
//            job.setExpireDate(js.getExpireDate());
            job.setExpireDate(job.getExpireDate());
            job.setFromAge(js.getStartAge());
            job.setGender(js.getGender());
            job.setHowToApply(js.getHowToApply());
            job.setIndustry(js.getIndustry());
            job.setJobDescription(js.getDetails());
            job.setJobTitle(js.getProfession());
            job.setLocation(js.getLocation());
            job.setMinQualification(js.getMinQualification());
            job.setMinYearsExperience(js.getMinYearsExperience());

            job.setPostedBy(emp);
//            job.setPrefInterviewDate(JsfUtil.convertToDate(js.getPrefInterviewDate(),"dd-MM-yyyy"));
            job.setPostedDate(new Date());
            job.setPrefSkillsAttribute(job.getPrefSkillsAttribute());
//            job.setPrefStartDate(JsfUtil.convertToDate(js.getPrefStartDate(),"dd-MM-yyyy"));
            job.setProcessed(false);
//            job.setProfession(js.getProfession());
            job.setPublished(false);
            job.setRegion(js.getRegion());
            job.setRenumeration(js.getMinSalary());
            job.setShowCompanyName(js.getShowCompanyName());
//            job.setSpecialRequirements(JsfUtil.convertToString(js.getSpecialRequirements()));
            job.setTelephone(js.getTelephone());
            job.setToAge(js.getMaxAge());
            job.setToRenumeration(js.getMaxSalary());
            job.setName(js.getProfession());
            job.setNameOfCompany(emp.getCompanyName());
//            job.setNameOfRequester();
            job.setNatureOfContract(js.getNatureOfContract());
            job.setNoNeeded(js.getSlot());
            job.setCountry(js.getCountry());
            job.setJobCountry(js.getCountry());
            job.setProfession(js.getProfession());
            job.setJobTitle(js.getProfession());
            job.setJobDescription(js.getDetails());
            job.setExpireDate(JsfUtil.convertToDate(js.getExpireDate()));
            job.setJobType(js.getJobType());
            job.setExperience(js.getExperience());
        } else {
            throw new BeanNotFoundException("Wrong employer email : " + email);
        }

        return service.save(job);
    }


    private Jobs createJobFromJobSearch(ApiSearchJob js) {
        try {
            PostedBy postedBy = js.getPostedBy();
            Employer employer = null;
            String email = postedBy.getEmail();
            String companyName = postedBy.getCompanyName();

            Optional<Employer> oppEmpl = cservice.findByName(companyName);
            if (oppEmpl.isPresent()) {
                employer = oppEmpl.get();
            } else {

                employer = new Employer();
                employer.setCompanyName(postedBy.getCompanyName());
                employer.setEmail(postedBy.getEmail());
                employer.setContactName(postedBy.getRequesterName());

                cservice.save(employer);
            }

            String expDate = js.getExpireDate();
            String prefDate = js.getPrefInterviewDate();
            String startDate = js.getPrefStartDate();
            System.out.println("expDate " + expDate);
            System.out.println("prefDate " + prefDate);
            System.out.println("startDate " + startDate);

            Jobs job = new Jobs();
            job.setAlertSent(false);
            job.setCategory(js.getCategory());
            job.setCountry(js.getCountry());
            job.setJobCity(js.getCity());
            job.setDeleted(false);

            job.setDutiesAndRespo(JsfUtil.convertToString(js.getDutiesAndRespo()));
            job.setEmail(email);
            //job.setExpireDate(js.getExpireDate());
            job.setExpireDate(JsfUtil.convertToDate(expDate, "dd-MM-yyyy"));
            job.setFromAge(js.getStartAge());
            job.setGender(js.getGender());
            job.setHowToApply(js.getHowToApply());
            job.setIndustry(js.getIndustry());
            job.setJobDescription(js.getDetails());
            job.setJobTitle(js.getProfession());
            job.setLocation(js.getLocation());
            job.setMinQualification(js.getMinQualification());
            job.setMinYearsExperience(js.getMinYearsExperience());

            job.setPostedBy(employer);
            job.setPrefInterviewDate(JsfUtil.convertToDate(prefDate, "dd-MM-yyyy"));
            job.setPostedDate(new Date());
            job.setPrefSkillsAttribute(JsfUtil.convertToString(js.getSkillls()));
            job.setPrefStartDate(JsfUtil.convertToDate(startDate, "dd-MM-yyyy"));
            job.setProcessed(false);
            job.setProfession(js.getProfession());
            job.setPublished(false);
            job.setRegion(js.getRegion());
            job.setRenumeration(js.getMinSalary());
            job.setShowCompanyName(js.getShowCompanyName());
            job.setSpecialRequirements(JsfUtil.convertToString(js.getSpecialRequirements()));
            job.setTelephone(js.getTelephone());
            job.setToAge(js.getMaxAge());
            job.setToRenumeration(js.getMaxSalary());
            job.setName(js.getProfession());
            job.setNameOfCompany(postedBy.getCompanyName());
            job.setNameOfRequester(postedBy.getRequesterName());
            job.setNatureOfContract(js.getNatureOfContract());
            job.setNoNeeded(js.getSlot());


            return job;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PostedBy createPostedby(Employer emp) {
        PostedBy postedBy = new PostedBy();
        if (emp == null) {
            postedBy.setCompanyName("Reputable Company");
            return postedBy;
        }
        postedBy.setCompanyName(emp.getCompanyName());
        postedBy.setEmail(emp.getEmail());
        postedBy.setRequesterName(emp.getContactName());
        postedBy.setRequesterContact(emp.getContactPhoneNumber());

        return postedBy;
    }


}
