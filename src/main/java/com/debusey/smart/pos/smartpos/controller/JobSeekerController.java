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
import com.debusey.smart.pos.smartpos.repo.EmployerRepository;
import com.debusey.smart.pos.smartpos.search.UserSearch;
import com.debusey.smart.pos.smartpos.service.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
public class JobSeekerController {


    @Autowired
    public CountriesService countryService;
    @Autowired
    private JobSeekerService service;
    @Autowired
    private JobsService jobService;

    @Autowired
    private WorkExperienceService workService;

    @Autowired
    private EducationService eduService;
    @Autowired
    private ITSkillsService skillService;

    @Autowired
    private ProjectWorkService pService;

    @Autowired
    private OnlineProfileService onlineService;

    @Autowired
    private WorkSampleService wsampleService;

    @Autowired
    private ResearchService researchService;

    @Autowired
    private PresentationService preSerive;

    @Autowired
    private PatentService patentService;

    @Autowired
    private CertificationService certService;

    @Autowired
    private AwardsService awardService;

    @Autowired
    private SavedJobsService savedService;

    @Autowired
    private JobAlertService alertSservice;

    @Autowired
    private EmployerRepository empSservice;

    @Autowired
    private ProfileViewService profileService;

    @Autowired
    private ProfileStrengthService strengthService;

    @Autowired
    private PositionsService positionService;

    @Autowired
    private JobPreferenceService jobprefService;
    @Autowired
    private UserSearch search;
    private int counter;


    //    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        initModel(model, principal);
        return "profile";

    }

    @RequestMapping(value = {"/my-profile", "/profile"})
    public String myResume(Model model, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            if (user != null) {

                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);

                String email = user.getUsername();
                String userType = user.getUserType();
                if (userType == null) {
                    return "redirect:clientArea";
                }
                switch (userType) {
                    case "Staff":
                        return "redirect:clientArea";
                    case "Company":
                        return "redirect:clientArea";
                    default:
                        break;
                }


                Optional<JobSeeker> oppSeeker = service.findByEmail(email);
                if (oppSeeker.isPresent()) {
                    JobSeeker seeker = oppSeeker.get();
                    List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                    List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                    List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                    List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                    List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                    List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                    List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                    List<Research> researchList = researchService.findByJobSeeker(seeker);
                    List<Patent> patentList = patentService.findByJobSeeker(seeker);
                    List<Certification> certList = certService.findByJobSeeker(seeker);

                    List<Awards> awardList = awardService.findByJobSeeker(seeker);
                    List<String> skills = new ArrayList<>();
                    String strSkills = seeker.getKeySkills();
                    if (strSkills != null) {
                        skills = convertToList(strSkills);
                    }


                    model.addAttribute("seeker", seeker);
                    model.addAttribute("imgUtil", new ImageUtil());
                    model.addAttribute("wlist", wlist);
                    model.addAttribute("edulist", edulist);
                    model.addAttribute("skillslist", skillslist);
                    model.addAttribute("prjList", prjList);
                    model.addAttribute("awardList", awardList);


                    model.addAttribute("onlineList", onlineList);
                    model.addAttribute("sampleList", sampleList);
                    model.addAttribute("researchList", researchList);
                    model.addAttribute("preList", preList);
                    model.addAttribute("patentList", patentList);
                    model.addAttribute("certList", certList);
                    model.addAttribute("skills", skills);

                    List<String> countryList = JsfUtil.getCountries();
                    model.addAttribute("countryList", countryList);

                    return "my-profile";
                }


            }

            model.addAttribute("seeker", new JobSeeker());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("wlist", new ArrayList<>());
            model.addAttribute("edulist", new ArrayList<>());
            model.addAttribute("skillslist", new ArrayList<>());
            model.addAttribute("prjList", new ArrayList<>());
            model.addAttribute("awardList", new ArrayList<>());

            model.addAttribute("onlineList", new ArrayList<>());
            model.addAttribute("sampleList", new ArrayList<>());
            model.addAttribute("researchList", new ArrayList<>());
            model.addAttribute("preList", new ArrayList<>());
            model.addAttribute("patentList", new ArrayList<>());
            model.addAttribute("certList", new ArrayList<>());
            model.addAttribute("skills", new ArrayList<>());

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "my-profile";
    }

    @GetMapping("/seeker/upload-cv")
    public String uploadCV(Model model, Principal principal) {
        JobSeeker seeker = new JobSeeker();
        Users user = JsfUtil.findOnline(principal);
        String email = user.getUsername();
        Optional<JobSeeker> oppSeeker = service.findByEmail(email);
        if (oppSeeker.isPresent()) {
            seeker = oppSeeker.get();

        }
        model.addAttribute("seeker", seeker);
        model.addAttribute("user", user);
        return "seeker/upload-cv";
    }

    @RequestMapping(value = {"/seeker-resume", "/view-seeker-profile"}, method = GET)
    // @GetMapping("/seeker-resume")
    public String jobSeekerResume(Model model, Principal principal, Integer id) {

        try {
            Optional<JobSeeker> oppSeeker = service.findById(id);
            Employer employer = null;
            Users user = JsfUtil.findOnline(principal);

            String userType = user.getUserType();
            if (userType != null) {
                if (!userType.equalsIgnoreCase("Individual")) {
                    employer = findEmployer(user);
                }
            }


            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                model.addAttribute("seeker", seeker);
                model.addAttribute("imgUtil", new ImageUtil());

                List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                model.addAttribute("wlist", wlist);

                List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                model.addAttribute("edulist", edulist);

                List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                model.addAttribute("skillslist", skillslist);

                List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                model.addAttribute("prjList", prjList);

                List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                model.addAttribute("onlineList", onlineList);

                List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                model.addAttribute("sampleList", sampleList);

                List<Research> researchList = researchService.findByJobSeeker(seeker);
                model.addAttribute("researchList", researchList);

                List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                model.addAttribute("preList", preList);

                List<Patent> patentList = patentService.findByJobSeeker(seeker);
                model.addAttribute("patentList", patentList);

                List<Certification> certList = certService.findByJobSeeker(seeker);
                model.addAttribute("certList", certList);

                List<String> skills = new ArrayList<>();
                String strSkills = seeker.getKeySkills();
                if (strSkills != null) {
                    skills = convertToList(strSkills);
                }
                model.addAttribute("skills", skills);
            } else {


                model.addAttribute("seeker", new JobSeeker());
                model.addAttribute("imgUtil", new ImageUtil());

                List<WorkExperience> wlist = new ArrayList<>();
                model.addAttribute("wlist", wlist);

                List<EducationalExperience> edulist = new ArrayList<>();
                model.addAttribute("edulist", edulist);

                List<ITSkills> skillslist = new ArrayList<>();
                model.addAttribute("skillslist", skillslist);

                List<ProjectWork> prjList = new ArrayList<>();
                model.addAttribute("prjList", prjList);

                List<OnlineProfile> onlineList = new ArrayList<>();
                model.addAttribute("onlineList", onlineList);

                List<WorkSample> sampleList = new ArrayList<>();
                model.addAttribute("sampleList", sampleList);

                List<Research> researchList = new ArrayList<>();
                model.addAttribute("researchList", researchList);

                List<Presentation> preList = new ArrayList<>();
                model.addAttribute("preList", preList);

                List<Patent> patentList = new ArrayList<>();
                model.addAttribute("patentList", patentList);

                List<Certification> certList = new ArrayList<>();
                model.addAttribute("certList", certList);

                List<String> skills = new ArrayList<>();
                model.addAttribute("skills", skills);

                List<String> countryList = JsfUtil.getCountries();
                model.addAttribute("countryList", countryList);
            }

            if (employer != null) {
                model.addAttribute("employer", employer);
                return "view-seeker-profile";
            }
            return "seeker-resume";

            /// return "rec-profile-cv";
        } catch (Exception ex) {
            Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "500";

    }

    @GetMapping("/duplicate-seeker")
    public String duplicateUser(Model model, String rt) {

        List<JobSeeker> slist = service.findDuplicates(rt);

        model.addAttribute("list", slist);
        model.addAttribute("msg", "You have two account, please choose active one");
        model.addAttribute("rt", rt);

        return "duplicate-seeker";
    }


    @GetMapping("/my-resume2")
    public String myProfile2(Model model, Principal principal) {


        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        if (user != null) {

            String email = user.getUsername();
            List<JobSeeker> slist = service.findDuplicates(email);

            // System.out.println("slist..."+slist.size());
            if (slist != null) {

                if (!slist.isEmpty()) {
                    if (slist.size() > 1) {
                        return "redirect:duplicate-seeker?rt=" + email;
                    }

                    JobSeeker seeker = slist.get(0);
                    List<String> countryList = JsfUtil.getCountries();
                    List<WorkExperience> wlist = workService.findByJobSeeker(seeker);


                    List<WorkExperience> sortedUsers = wlist.stream()
                            .sorted(Comparator.comparing(WorkExperience::getStillWorkThere))
                            .collect(Collectors.toList());

                    model.addAttribute("seeker", seeker);
                    model.addAttribute("wlist", sortedUsers);
                    model.addAttribute("countryList", countryList);
                    model.addAttribute("imgUtil", new ImageUtil());


                    return "my-resume-bk";
                }

            }
        }
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("wlist", new ArrayList<>());
        model.addAttribute("countryList", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());


        return null;


    }


    @GetMapping("/my-resume-updated")
    public String myProfile(Model model, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();
            List<String> countryList = JsfUtil.getCountries();

            if (user != null) {

                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);

                String email = user.getUsername();

                Optional<JobSeeker> oppSeeker = service.findByEmail(email);
                if (oppSeeker.isPresent()) {
                    JobSeeker seeker = oppSeeker.get();
                    List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                    List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                    List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                    List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                    List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                    List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                    List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                    List<Research> researchList = researchService.findByJobSeeker(seeker);
                    List<Patent> patentList = patentService.findByJobSeeker(seeker);
                    List<Certification> certList = certService.findByJobSeeker(seeker);

                    List<Awards> awardList = awardService.findByJobSeeker(seeker);
                    List<String> skills = new ArrayList<>();
                    String strSkills = seeker.getKeySkills();
                    if (strSkills != null) {
                        skills = convertToList(strSkills);
                    }


                    model.addAttribute("seeker", seeker);
                    model.addAttribute("imgUtil", new ImageUtil());
                    model.addAttribute("wlist", wlist);
                    model.addAttribute("edulist", edulist);
                    model.addAttribute("skillslist", skillslist);
                    model.addAttribute("prjList", prjList);
                    model.addAttribute("awardList", awardList);


                    model.addAttribute("onlineList", onlineList);
                    model.addAttribute("sampleList", sampleList);
                    model.addAttribute("researchList", researchList);
                    model.addAttribute("preList", preList);
                    model.addAttribute("patentList", patentList);
                    model.addAttribute("certList", certList);
                    model.addAttribute("skills", skills);


                    model.addAttribute("countryList", countryList);

                    return "my-resume-bk";
                }


            }

            model.addAttribute("seeker", new JobSeeker());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("wlist", new ArrayList<>());
            model.addAttribute("edulist", new ArrayList<>());
            model.addAttribute("skillslist", new ArrayList<>());
            model.addAttribute("prjList", new ArrayList<>());
            model.addAttribute("awardList", new ArrayList<>());

            model.addAttribute("onlineList", new ArrayList<>());
            model.addAttribute("sampleList", new ArrayList<>());
            model.addAttribute("researchList", new ArrayList<>());
            model.addAttribute("preList", new ArrayList<>());
            model.addAttribute("patentList", new ArrayList<>());
            model.addAttribute("certList", new ArrayList<>());
            model.addAttribute("skills", new ArrayList<>());


            model.addAttribute("countryList", countryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "my-resume-bk";
    }


    @PostMapping("/update-rs-header")
    @ResponseBody
    public boolean updateRsHeader(String description, Integer id) {
        Optional<JobSeeker> oppSeeker = service.findById(id);
        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setDescription(description);
            return service.save(seeker);
        }

        return false;
    }


    @PostMapping("/update-keyskills")
    @ResponseBody
    public boolean updateKeySkills(String description, Integer id) {
        Optional<JobSeeker> oppSeeker = service.findById(id);
        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(description);
            return service.save(seeker);
        }

        return false;
    }


    @PostMapping("/seeker/update-schools")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> updateSchools(@RequestBody AddSchool school) {
        try {

            Optional<JobSeeker> opp = service.findById(school.getJobSeekerId());
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid Credentials: " + school.getJobSeekerId());
            }
            JobSeeker seeker = opp.get();

            EducationalExperience education = eduService.findById(school.getSchooId()).orElse(new EducationalExperience());
            education.setCourseDescription(school.getCourse());
            education.setEntryDate(new Date());
            education.setInstitutionName(school.getNameOfSchool());
            education.setJobSeekerId(seeker);
            education.setQualificationReceived(school.getEducationLevel());
            education.setYearGraduated(school.getGradYear());
            education.setYearStarted(school.getStartYear());

            String status = eduService.save(education) ? "SUCCESS" : "FAILED";
            ProfileStrength strength = getProfileStrength(seeker);
            strength.setEducation(1);
            strength.setJobSeekerId(seeker);

            strengthService.save(strength);

            return new ResponseEntity(status, HttpStatus.OK);

        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/find-education/{id}")
    @ResponseBody
    public Education findEducation(Principal principal, @PathVariable Integer id) {
        EducationalExperience edu = eduService.findById(id).orElse(new EducationalExperience());

        Education education = new Education();
        education.setCourseDescription(edu.getCourseDescription());
        education.setEntryDate(edu.getEntryDate());
        education.setInstitutionName(edu.getInstitutionName());

        education.setProgramStudied(edu.getProgramStudied());
        JobSeeker seeker = edu.getJobSeekerId();
        if (seeker != null) {
            education.setJobSeekerId(seeker.getId());
        }
        education.setQualificationReceived(edu.getQualificationReceived());
        education.setTransactionId(edu.getTransactionId());
        education.setYearGraduated(edu.getYearGraduated());
        education.setYearStarted(edu.getYearStarted());
        education.setId(edu.getId());

        return education;
    }

    @GetMapping("/delete-education/{id}")
    @ResponseBody
    public boolean deleteEducation(Principal principal, @PathVariable Integer id) {
        return eduService.deleteById(id);
    }

    @PostMapping("/update-certification")
    @ResponseBody
    public boolean updateCertification(String name, String certificateBody, Integer year, Integer id) {
        try {

            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {
                JobSeeker seeker = opp.get();

                Certification cert = new Certification();
                cert.setCertificateBody(certificateBody);
                cert.setEntryDate(new Date());
                cert.setJobSeekerId(seeker);
                cert.setName(name);
                cert.setYear(year);

                return certService.save(cert);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @PostMapping("/update-patent")
    @ResponseBody
    public boolean updatePatent(String description, String title, String strUrl, String patentOffice, String patentSttatus, String applicationNumber, String publishMonth, Integer publishYear, Integer id) {


        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();

            Patent patent = new Patent();
            patent.setApplicationNumber(applicationNumber);
            patent.setDescription(description);
            patent.setEntryDate(new Date());
            patent.setJobSeekerId(seeker);
            patent.setPatentOffice(patentOffice);
            patent.setPatentSttatus(patentSttatus);
            patent.setPublishMonth(publishMonth);
            patent.setPublishYear(publishYear);
            patent.setStrUrl(strUrl);
            patent.setTitle(title);

            return patentService.save(patent);
        }
        return false;
    }

    @PostMapping("/update-presentation")
    @ResponseBody
    public boolean updatePresentation(String title, String strUrl, String description, Integer id) {


        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();

            Presentation presentation = new Presentation();
            presentation.setDescription(description);
            presentation.setEntryDate(new Date());
            presentation.setJobSeekerId(seeker);
            presentation.setStrUrl(strUrl);
            presentation.setTitle(title);

            return preSerive.save(presentation);
        }
        return false;

    }


    @PostMapping("/update-desiredCareer")
    @ResponseBody
    public boolean updateDesiredCareer(String industry, String department, String role,
                                       BigDecimal minSal, BigDecimal maxSal, Integer availabilityYear, String availabilityMonth,
                                       String jobType, String employmentType, String shift, Integer id,
                                       String desireedLocation, String contractType, String desireedIndustry) {


        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();

            String availability = availabilityYear + " " + availabilityMonth;


            seeker.setAvailabilityStatus(availability);
            seeker.setIndustry(industry);
            seeker.setDepartment(department);
            seeker.setSeekerRole(role);
            seeker.setExpectedSalary(maxSal);
            seeker.setExpMinSalary(minSal);
            seeker.setJobType(jobType);
            seeker.setEmploymentType(employmentType);
            seeker.setShift(shift);
            seeker.setDesiredLocation(desireedLocation);
            seeker.setDesiredIndustry(desireedIndustry);
            seeker.setContractType(contractType);

            return service.save(seeker);
        }
        return false;

    }

    @PostMapping("/update-personalDetails")
    @ResponseBody
    public boolean updatePersonalDetails(String dob, String address, String gender,
                                         String passportNo, String code,
                                         String challenge, String language, Integer id) {


        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();

            seeker.setDob(convertToDate(dob));
            seeker.setAddress(address);
            seeker.setGender(gender);
            seeker.setPassportNo(passportNo);
            seeker.setPostcode(code);
            seeker.setPhysicallyChallenged(challenge);
            seeker.setSpokenLanguages(language);


            return service.save(seeker);
        }
        return false;

    }

    @PostMapping("/update-research")
    @ResponseBody
    public boolean updateResearch(String title, String strUrl, String frmMonth, Integer frmYear, String description, Integer id, Integer proId) {


        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();

            Research research = new Research();
            Optional<Research> oppSkill = researchService.findById(proId);
            if (oppSkill.isPresent()) {
                research = oppSkill.get();
            }
            research.setDescription(description);
            research.setEntryDate(new Date());
            research.setFrmMonth(frmMonth);
            research.setFrmYear(frmYear);
            research.setJobSeekerId(seeker);
            research.setStrUrl(strUrl);
            research.setTitle(title);


            return researchService.save(research);
        }

        return false;
    }

    @PostMapping("/update-worksample")
    @ResponseBody
    public boolean updateWorksample(String worktitle, String strUrl, String frmMonth, Integer frmYear, String toMonth, Integer toYear,
                                    String currentProject, String description, Integer id, Integer proId) {

        try {
            JobSeeker seeker = null;
            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {
                seeker = opp.get();

                WorkSample sample = new WorkSample();

                Optional<WorkSample> oppSkill = wsampleService.findById(proId);
                if (oppSkill.isPresent()) {
                    sample = oppSkill.get();
                }
                sample.setCurrentlyOnProject(currentProject);
                sample.setDescription(description);
                sample.setEntryDate(new Date());
                sample.setFrmMonth(frmMonth);
                sample.setFrmYear(frmYear);
                sample.setJobSeekerId(seeker);
                sample.setStrUrl(strUrl);
                sample.setToMonth(toMonth);
                sample.setToYear(toYear);
                sample.setWorktitle(worktitle);


                return wsampleService.save(sample);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/find-project-wk/{id}")
    @ResponseBody
    public Projects findProjectWokr(@PathVariable Integer id) {
        Optional<ProjectWork> opp = pService.findById(id);
        if (opp.isPresent()) {
            Integer jobSeekerId = null;
            ProjectWork item = opp.get();
            Integer pid = item.getId();
            String title = item.getTitle();
            String client = item.getClient();
            String projectStatus = item.getProjectStatus();
            Integer startYear = item.getStartYear();
            Integer endYear = item.getEndYear();
            String startMonth = item.getStartMonth();
            String endMonth = item.getEndMonth();
            BigDecimal projectCost = item.getProjectCost();
            if (projectCost == null) {
                projectCost = BigDecimal.ZERO;
            }
            String fundedBy = item.getFundedBy();
            Date entryDate = item.getEntryDate();
            JobSeeker seeker = item.getJobSeekerId();
            if (seeker != null) {
                jobSeekerId = seeker.getId();
            }
            String details = item.getDetails();

            return new Projects(pid, title, client, projectStatus, startYear, endYear, startMonth, endMonth, projectCost.doubleValue(), fundedBy, entryDate, jobSeekerId, details);
        }
        return new Projects();
    }

    @PostMapping("/update-pjwork")
    @ResponseBody
    public boolean updateProjectWork(String title, String client, String projectStatus,
                                     Integer startYear, Integer endYear, String startMonth, String details,
                                     String endMonth, BigDecimal projectCost, String fundedBy, Integer id, Integer proId) {
        try {

            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {

                JobSeeker seeker = opp.get();

                ProjectWork project = new ProjectWork();
                Optional<ProjectWork> oppSkill = pService.findById(proId);
                if (oppSkill.isPresent()) {
                    project = oppSkill.get();
                }
                project.setClient(client);
                project.setDetails(details);
                project.setEndMonth(endMonth);
                project.setEndYear(endYear);
                project.setEntryDate(new Date());
                project.setFundedBy(fundedBy);
                project.setJobSeekerId(seeker);
                project.setProjectCost(projectCost);
                project.setProjectStatus(projectStatus);
                project.setStartMonth(startMonth);
                project.setStartYear(startYear);
                project.setTitle(title);

                return pService.save(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update-onlineProfile")
    @ResponseBody
    public boolean updatOnlineProfile(String socialProfile, String strUrl, String description, Integer id, Integer proId) {
        try {

            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {
                JobSeeker seeker = opp.get();

                OnlineProfile profile = new OnlineProfile();
                Optional<OnlineProfile> oppSkill = onlineService.findById(proId);
                if (oppSkill.isPresent()) {
                    profile = oppSkill.get();
                }
                profile.setDescription(description);
                profile.setStrUrl(strUrl);
                profile.setEntryDate(new Date());
                profile.setJobSeekerId(seeker);
                profile.setSocialProfile(socialProfile);

                return onlineService.save(profile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update-itSkills")
    @ResponseBody
    public boolean updateITSkill(String skill, String version, Integer lastUsed, String monthExp, Integer yearExp, Integer id, Integer skillId, Integer proficiency) {
        try {

            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {

                JobSeeker seeker = opp.get();


                ITSkills skills = skillService.findById(skillId).orElse(new ITSkills());

                skills.setEntryDate(new Date());
                skills.setJobSeekerId(seeker);
                skills.setLastUsed(lastUsed);
                skills.setMonthExperience(monthExp);
                skills.setSkill(skill);
                skills.setVersion(version);
                skills.setYearExperience(yearExp);
                skills.setProficiency(proficiency);


                if (skillService.save(skills)) {

                    ProfileStrength strength = getProfileStrength(seeker);

                    Integer st = strength.getItSkills();
                    if (st == null) {
                        strength.setItSkills(1);
                        strengthService.save(strength);
                    }


                    return true;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @GetMapping("/job-seeker/{id}")
    @ResponseBody
    public JobSeeker findSeeker(@PathVariable Integer id) {
        return service.findById(id).orElse(new JobSeeker());
    }

    @GetMapping("/findSeeker/{id}")
    @ResponseBody
    public AppUser findAppSeeker(@PathVariable Integer id) {
        Optional<JobSeeker> opp = service.findById(id);

        if (opp.isPresent()) {
            JobSeeker seeker = opp.get();
            AppUser appUser = new AppUser();

            String role = seeker.getSeekerRole() + "at " + seeker.getCurrentCompany();
            String coverletter = seeker.getCoverLetter();
            String fullName = seeker.getFullName();
            String emai = seeker.getEmail();
            String tele = seeker.getPrimaryContact();
            String location = seeker.getCurrentLocation();
            String fileName = seeker.getPicFileName();

            appUser.setEmail(emai);
            appUser.setFullName(fullName);
            appUser.setRole(role);
            appUser.setTelephone(tele);
            appUser.setId(seeker.getId());
            appUser.setLocation(location);
            appUser.setFileName(fileName);
            appUser.setCoverLetter(coverletter);

            return appUser;

        }

        return new AppUser();
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    @PostMapping("/update-pic")
    public String updatePic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id, Model model) {

        Optional<JobSeeker> opp = service.findById(id);
        if (opp.isPresent()) {
            if (files != null) {
                try {
                    JobSeeker seeker = opp.get();

                    JsfUtil.deleteFromDisk(seeker.getPicFileName());
                    Long name = new Date().getTime();
                    String originalFileName = files.getOriginalFilename();
                    String extention = getExtensionByApacheCommonLib(originalFileName);

                    //String fileName =name+"_"+ files.getOriginalFilename();

                    String fileName = name + "." + extention;
                    System.err.println("filname..." + fileName);


                    String contentType = files.getContentType();
                    byte[] file = files.getBytes();
                    seeker.setPicFileType(contentType);
                    seeker.setPicFileName(fileName);
                    DbFile dbFile = new DbFile(fileName, contentType, file);
                    JsfUtil.saveToDisk(dbFile);

                    service.save(seeker);

                } catch (IOException ex) {
                    Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        JobSeeker seeker = service.findById(id).orElse(new JobSeeker());
        model.addAttribute("seeker", seeker);

        return "my-profile";
    }


    @PostMapping("/file-upload")
    @ResponseBody
    public void fileUpload(@RequestParam("file") MultipartFile files, Principal principal, Model model) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        if (user != null) {
            String email = user.getUsername();
            JobSeeker seeker = null;
            Optional<JobSeeker> opp = service.findByEmail(email);
            if (opp.isPresent()) {

                if (files != null) {
                    try {
                        seeker = opp.get();
                        JsfUtil.deleteFromDisk(seeker.getCvFileName());

                        Long name = new Date().getTime();

                        String fileName = name + "_" + files.getOriginalFilename();

                        seeker.setCvFileName(fileName);
                        String contentType = files.getContentType();
                        byte[] file = files.getBytes();
                        seeker.setCvFileType(contentType);


                        DbFile dbFile = new DbFile(fileName, contentType, file);
                        JsfUtil.saveToDisk(dbFile);

                        if (service.save(seeker)) {
                            ProfileStrength strength = getProfileStrength(seeker);
                            Integer st = strength.getAttachedResume();
                            if (st == null || st != 1) {
                                strength.setAttachedResume(1);
                                strengthService.save(strength);
                            }
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }


    @PostMapping("/seeker/update-employment")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> updateEmployment(@RequestBody AddEmployment emp) {

        try {

            Optional<JobSeeker> opp = service.findById(emp.getId());
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid Credentials");
            }
            JobSeeker seeker = opp.get();

            WorkExperience work = new WorkExperience();

            Optional<WorkExperience> oppWork = workService.findById(emp.getEduId());
            if (oppWork.isPresent()) {
                work = oppWork.get();
            }
            work.setCompanyName(emp.getOrg());
            work.setDesignation(emp.getDesignation());
            work.setEntryDate(new Date());
            work.setJobProfile(emp.getJobProfile());
            work.setJobSeekerId(seeker);
            work.setMonthStartedWork(emp.getStartMonth());
            work.setYearStartedWork(emp.getStartYear());
            work.setJobTitle(emp.getDesignation());
            work.setHighestPosition(emp.getDesignation());
            work.setMonthStopedWork(emp.getEndMonth());
            work.setStillWorkThere(emp.getStillWork());
            work.setYearStopedWork(emp.getEndYear());
            work.setStillWorkThere(emp.getCurComp());

            String status = workService.save(work) ? "SUCCESS" : "FAILED";
            ProfileStrength strength = getProfileStrength(seeker);

            strength.setEmployment(1);
            strengthService.save(strength);

            return new ResponseEntity(status, HttpStatus.OK);


        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }

    @PostMapping("/upload-seeker-pic")
    @ResponseBody
    public String uploadSeekerPic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id) {

        String appendName = String.valueOf((new Date()).getTime());

        Optional<JobSeeker> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Credentials " + id);
        }
        if (files == null) {
            throw new BeanNotFoundException("Invalid File uploaded ");
        }

        try {
            String originalFileName = files.getOriginalFilename();
            String extention = getExtensionByApacheCommonLib(originalFileName);

            String fileName = appendName + "." + extention;
            System.err.println("filname..." + fileName);

            JobSeeker employee = opp.get();


            String fileType = files.getContentType();
            byte[] originalByte = files.getBytes();

            BufferedImage bi = JsfUtil.simpleResizeImage(originalByte, 145, 145);
            byte[] uploadedFile = JsfUtil.convertToByte(bi);

            DbFile dbfile = new DbFile(fileName, fileType, uploadedFile);
            JsfUtil.deleteFromDisk(employee.getPicFileName());

            fileName = JsfUtil.removeWhiteSpace(fileName);

            employee.setPicFileName(fileName);
            employee.setPicFileType(dbfile.getFileType());

            JsfUtil.saveToDisk(dbfile);

            service.save(employee);

            return fileName;

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }


    @PostMapping("/add-jobseeker")
    @ResponseBody
    public boolean addJobseeker(Integer id, String fullName, String profTitle, String languages, String dob, BigDecimal curSalary,
                                BigDecimal extSalary, String description, String phone,
                                String yearsOfExperience, String levelOfEducation, String maritalStatus,
                                String highestPosition, String email, String country, String postCode, String gender,
                                String city, String address, BigDecimal expMinSal) {
        try {

            Optional<JobSeeker> opp = service.findById(id);
            if (opp.isPresent()) {
                JobSeeker seeker = opp.get();
                seeker.setAddress(address);
                seeker.setApproved(Boolean.TRUE);
                seeker.setAvailabilityStatus(maritalStatus);
                seeker.setCountryOfOrigin(country);
                seeker.setCurrentLocation(city);
                seeker.setCurrentSalary(curSalary);
                seeker.setDescription(description);
                seeker.setDob(convertToDate(dob));
                seeker.setEmail(email);
                seeker.setEntryDate(new Date());
                seeker.setExpectedSalary(extSalary);
                seeker.setFullName(fullName);
                seeker.setGender(gender);
                seeker.setHighestQualification(highestPosition);
                seeker.setEducationLevel(levelOfEducation);
                seeker.setMaritalStatus(maritalStatus);
                seeker.setModifiedDate(new Date());
                seeker.setPostcode(postCode);
                seeker.setPrimaryContact(phone);
                seeker.setProffesionalTitile(profTitle);
                seeker.setSpokenLanguages(languages);
                seeker.setYearsOfExperience(yearsOfExperience);
                seeker.setExpMinSalary(expMinSal);
                seeker.setProfileSummary(description);

                return service.save(seeker);
            }
            // System.out.println("could not find id...");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Positions findHisghestQualification(String highestPosition) {
        return null;
    }

    private Qualifications findLevel(String levelOfEducation) {
        return null;
    }

    public Date convertToDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//2020-04-30

        try {

            Date date = formatter.parse(dateInString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    @GetMapping("/saved-jobs")
    public String savedJobs(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {

            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = service.findByEmail(email);
            if (oppSeeker.isPresent()) {

                JobSeeker seeker = oppSeeker.get();

                //find out if jobAready Saved;

                List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                List<SavedJobs> savedJobs = savedService.findByJobSeeker(seeker);

                savedJobs = savedJobs.stream()
                        .filter(c -> c != null)
                        .filter(c -> c.getCategory() != null)
                        .filter(c -> c.getCategory().equalsIgnoreCase(SAVED_JOB_SAVED))
                        .collect(Collectors.toList());

                model.addAttribute("seeker", seeker);
                model.addAttribute("wlist", wlist);
                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("savedJobs", savedJobs);
                model.addAttribute("imgUtil", new ImageUtil());

                return "saved-jobs";
            }
        }


        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("wlist", new ArrayList<>());
        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("savedJobs", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());

        return "saved-jobs";
    }

    @GetMapping("/applied-job")
    public String appliedJobs(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {

            String email = user.getUsername();
            Optional<JobSeeker> oppSeeker = service.findByEmail(email);

            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                List<SavedJobs> savedJobs = savedService.findByJobSeeker(seeker);

                savedJobs = savedJobs.stream()
                        .filter(c -> c != null)
                        .filter(c -> c.getCategory() != null)
                        .filter(c -> c.getCategory().equalsIgnoreCase(SAVED_JOB_APPLIED))
                        .collect(Collectors.toList());


                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("seeker", seeker);
                model.addAttribute("savedJobs", savedJobs);
                model.addAttribute("imgUtil", new ImageUtil());

                return "applied-job";

            }
        }

        model.addAttribute("user", new Users());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("savedJobs", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());

        return "applied-job";

    }

    @GetMapping("/jobs-alerts")
    public String jobAlerts(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {

            model.addAttribute("userInfo", userInfo);

            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = service.findByEmail(email);

            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                List<SavedJobs> savedJobs = savedService.findByJobSeeker(seeker);
                List<JobAlert> list = alertSservice.findByJobSeeker(seeker);

                model.addAttribute("user", user);
                model.addAttribute("seeker", seeker);
                model.addAttribute("savedJobs", savedJobs);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("jobAlerts", list);

                return "jobs-alerts";
            }


        }

        model.addAttribute("user", new Users());
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("savedJobs", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("jobAlerts", new ArrayList<>());


        return "jobs-alerts";
    }

    @GetMapping("/job-company-profile")
    public String jobDetails(Model model, Principal principal, Integer id) {

        Optional<Jobs> oppJob = jobService.findById(id);
        Jobs job = oppJob.orElse(new Jobs());
        model.addAttribute("job", job);

        return "job-company-profile";
    }


    @GetMapping("/my-cv-manager")
    public String cvManager(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);

            String email = user.getUsername();

            Optional<JobSeeker> oppSeeker = service.findByEmail(email);

            if (oppSeeker.isPresent()) {
                JobSeeker seeker = oppSeeker.get();

                List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                List<SavedJobs> savedJobs = savedService.findByJobSeeker(seeker);

                model.addAttribute("seeker", seeker);
                model.addAttribute("wlist", wlist);
                model.addAttribute("savedJobs", savedJobs);
                model.addAttribute("imgUtil", new ImageUtil());

                return "my-cv-manager";
            }
        }
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("wlist", new ArrayList<>());
        model.addAttribute("savedJobs", new ArrayList<>());
        model.addAttribute("imgUtil", new ImageUtil());

        return "my-cv-manager";

    }


    @GetMapping("/create-js")
    @ResponseBody
    public ShortlistApp createSeeker(String name, String qu, String loc, String prof) {
        //icreate-js?name=Martha Abu&qu=Bachelors Degree&loc=Adenta&prof=Web Developer
        JobSeeker seeker = new JobSeeker();
        seeker.setFullName(name);
        seeker.setCurrentLocation(loc);
        seeker.setHighestQualification(qu);
        seeker.setProffesionalTitile(prof);

        if (service.save(seeker)) {
            ShortlistApp app = new ShortlistApp();
            app.setFullName(seeker.getFullName());
            app.setGender(loc);
            app.setMaritalStatus(prof);
            app.setPrimaryContact(qu);

            return app;
        }
        return new ShortlistApp();
    }

    @GetMapping("/delete-profile/{id}")
    @ResponseBody
    public boolean deleteProfile(Principal principal, @PathVariable Integer id) {
        return service.deleteById(id);
    }

    @GetMapping("/delete-work/{id}")
    @ResponseBody
    public boolean deleteWork(Principal principal, @PathVariable Integer id) {
        return workService.deleteById(id);
    }


    @GetMapping("/edit-work/{id}")
    @ResponseBody
    public WorkEx editEducation(Principal principal, @PathVariable Integer id) {
        WorkExperience work = workService.findById(id).orElse(new WorkExperience());

        String companyName = work.getCompanyName();
        String descriptions = work.getDescriptions();
        Date entryDate = work.getEntryDate();
        String designation = work.getDesignation();
        String jobProfile = work.getJobTitle();
        String jobTitle = work.getJobTitle();
        String monthStartedWork = work.getMonthStartedWork();
        String monthStopedWork = work.getMonthStopedWork();
        String stillWorkThere = work.getStillWorkThere();
        Integer yearStartedWork = work.getYearStartedWork();
        Integer yearStopedWork = work.getYearStopedWork();
        Integer yearsOfExperience = work.getYearsOfExperience();
        String industry = work.getIndustry();
        String region = work.getRegion();
        String city = work.getCity();
        String country = work.getCountry();
        BigDecimal currentSalary = work.getCurrentSalary();
        JobSeeker seeker = work.getJobSeekerId();
        Integer jobSeekerId = null;
        if (seeker != null) {
            jobSeekerId = seeker.getId();
        }
        Integer workId = work.getId();
        String highestPosition = work.getHighestPosition();

        return new WorkEx(companyName, descriptions, entryDate, designation, jobProfile, jobTitle,
                monthStartedWork, monthStopedWork, stillWorkThere, yearStartedWork, yearStopedWork,
                yearsOfExperience, industry, region, region, city, country, currentSalary, jobSeekerId, highestPosition, workId);
    }

    @GetMapping("/findItSkills/{id}")
    @ResponseBody
    public Skills findSkills(@PathVariable Integer id) {
        ITSkills itskill = skillService.findById(id).orElse(new ITSkills());
        String skill = itskill.getSkill();
        String version = itskill.getVersion();
        Integer lastUsed = itskill.getLastUsed();
        String monthExperience = itskill.getMonthExperience();
        Integer yearExperience = itskill.getYearExperience();
        Integer prof = itskill.getProficiency();
        return new Skills(id, skill, version, lastUsed, monthExperience, yearExperience, lastUsed, prof);

    }

    private Employer findEmployer(Users user) {
        try {
            Optional<Employer> oppemp = empSservice.findByEmail(user.getUsername());
            if (oppemp.isPresent()) {
                return oppemp.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/edit-profile")
    public String editProfile(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);

        Optional<JobSeeker> oppSeeker = service.findByEmail(user.getUsername());
        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());

        model.addAttribute("seeker", seeker);
        model.addAttribute("imgUtil", new ImageUtil());

        return "edit-profile";

    }

    //--------------Candidate Profile-------------------- 

    @GetMapping("/resume")
    public String resume(Model model, Principal principal) {
        initModel(model, principal);
        return "resume";

    }

    @GetMapping("/cover-letter")
    public String coverLetter(Model model, Principal principal) {
        initModel(model, principal);
        return "cover-letter";
    }


    public void initModel(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        model.addAttribute("user", user);

        Optional<JobSeeker> oppSeeker = service.findByEmail(user.getUsername());
        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());
        List<String> countryList = JsfUtil.getCountries();


        model.addAttribute("seeker", seeker);
        model.addAttribute("countryList", countryList);
        model.addAttribute("imgUtil", new ImageUtil());
    }

    //var url = "add-social?seekerId="+seekerId+"&facebook="+facebook+"&linkedIn="+linkedIn+"&google="+google+"&twitter="+twitter;
    @GetMapping("/add-social")
    @ResponseBody
    public String addSocial(Principal principal, String facebook, String linkedIn, String twitter, String google, Integer seekerId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            JobSeeker seeker = service.findById(seekerId).orElse(new JobSeeker());
            seeker.setFacebook(facebook);
            seeker.setTwitter(twitter);
            seeker.setGoogle(google);
            seeker.setLinkedIn(linkedIn);

            return service.save(seeker) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @GetMapping("/add-contact")
    @ResponseBody
    public String addContact(Principal principal, String telephone, String address, String email, String google, Integer seekerId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            JobSeeker seeker = service.findById(seekerId).orElse(new JobSeeker());

            seeker.setTelephone(telephone);
            seeker.setAddress(address);
            seeker.setEmail(email);

            return service.save(seeker) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @GetMapping("/add-profile")
    @ResponseBody
    public String addProfile(Principal principal, String telephone, String address, String email, String google, Integer seekerId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                return "INVALID-USER";
            }
            JobSeeker seeker = service.findById(seekerId).orElse(new JobSeeker());

            seeker.setTelephone(telephone);
            seeker.setAddress(address);
            seeker.setEmail(email);

            return service.save(seeker) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }


    private String postEmployerJob() {
        return null;
    }

    @GetMapping("/candidate-dashboard")
    public String candidateDash(Model model, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            JobSeeker seeker = service.findByEmail(user.getUsername()).orElse(new JobSeeker());

            List<SavedJobs> savedJobs = savedService.findByJobSeeker(seeker);
            savedJobs = savedJobs.stream()
                    .filter(c -> c != null)
                    .filter(c -> c.getCategory() != null)
                    .filter(c -> c.getCategory().equalsIgnoreCase(SAVED_JOB_APPLIED))
                    .collect(Collectors.toList());

            String qual = seeker.getHighestQualification();
            String url = "/findJobs?q=" + qual;
            List<Jobs> appr = findAppropriateJobs(qual);

            List<ProfileViews> views = findViews(seeker);

            model.addAttribute("savedJobs", savedJobs);
            model.addAttribute("appr", appr);
            model.addAttribute("views", views);
            model.addAttribute("url", url);

            model.addAttribute("seeker", seeker);
            model.addAttribute("user", user);

            return "candidate-dashboard";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }

    @PostMapping("/cover-letter")
    @ResponseBody
    public String coverLetter(Principal principal, String letter, Integer id) {
        try {
            Optional<JobSeeker> opp = service.findById(id);
            if (!opp.isPresent()) {
                return "INVALID-USER";
            }
            JobSeeker seeker = opp.get();
            seeker.setCoverLetter(letter);


            String result = service.save(seeker) ? "SUCCESS" : "FAILED";
            if (result.equals("SUCCESS")) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer st = strength.getCoverLetter();
                if (st == null || st != 1) {
                    strength.setCoverLetter(1);
                    strengthService.save(strength);
                }

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    private List<Jobs> findAppropriateJobs(String qual) {
        return jobService.findByMinQualification(qual);
    }

    private List<ProfileViews> findViews(JobSeeker seeker) {
        return profileService.findByJobseeker(seeker);
    }

    private void deleteOldCv(String cvFileName) {
    }


    //For Employers
    @GetMapping("/candidate-profile")
    public String candidateProfile(Model model, Principal principal, @RequestParam(defaultValue = "0", required = false) Integer id, @RequestParam(defaultValue = "0", required = false) Integer jobId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            String userType = user.getUserType();
            String view = "candidate-profile";
            if (userType.equals("Staff")) {
                createStaffModel(model, user);
                view = "emp-candidate-profile";
            } else {
                createEmployerModel(model, user);
            }


            Optional<JobSeeker> oppSeeker = service.findById(id);

            if (oppSeeker.isPresent()) {

                JobSeeker seeker = oppSeeker.get();
                List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                List<Research> researchList = researchService.findByJobSeeker(seeker);
                List<Patent> patentList = patentService.findByJobSeeker(seeker);
                List<Certification> certList = certService.findByJobSeeker(seeker);

                List<Awards> awardList = awardService.findByJobSeeker(seeker);
                List<String> skills = new ArrayList<>();
                String strSkills = seeker.getKeySkills();
                if (strSkills != null) {
                    skills = convertToList(strSkills);
                }


                model.addAttribute("seeker", seeker);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("wlist", wlist);
                model.addAttribute("edulist", edulist);
                model.addAttribute("skillslist", skillslist);
                model.addAttribute("prjList", prjList);
                model.addAttribute("awardList", awardList);


                model.addAttribute("onlineList", onlineList);
                model.addAttribute("sampleList", sampleList);
                model.addAttribute("researchList", researchList);
                model.addAttribute("preList", preList);
                model.addAttribute("patentList", patentList);
                model.addAttribute("certList", certList);
                model.addAttribute("skills", skills);

                Boolean blacklisted = seeker.getBlacklisted();
                if (blacklisted == null) {
                    blacklisted = false;
                }
                model.addAttribute("blacklisted", blacklisted);

                List<String> countryList = JsfUtil.getCountries();
                model.addAttribute("countryList", countryList);

                return view;

            } else {
                createDefaultModel(model);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "my-profile";
    }

    @GetMapping("/candidate-profile/{transactionId}")
    public String candidateProfileDetails(Model model, Principal principal, @PathVariable String transactionId) {
        try {
            Users user = JsfUtil.findOnline(principal);
            String userType = user.getUserType();

            String view = "candidate-profile";
            if (userType.equals("Staff")) {
                createStaffModel(model, user);
                view = "emp-candidate-profile";
            } else {
                createEmployerModel(model, user);
            }


            Optional<JobSeeker> oppSeeker = service.findByTransactionId(transactionId);

            if (oppSeeker.isPresent()) {

                JobSeeker seeker = oppSeeker.get();
                List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                List<Research> researchList = researchService.findByJobSeeker(seeker);
                List<Patent> patentList = patentService.findByJobSeeker(seeker);
                List<Certification> certList = certService.findByJobSeeker(seeker);

                List<Awards> awardList = awardService.findByJobSeeker(seeker);
                List<String> skills = new ArrayList<>();
                String strSkills = seeker.getKeySkills();
                if (strSkills != null) {
                    skills = convertToList(strSkills);
                }


                model.addAttribute("seeker", seeker);
                model.addAttribute("imgUtil", new ImageUtil());
                model.addAttribute("wlist", wlist);
                model.addAttribute("edulist", edulist);
                model.addAttribute("skillslist", skillslist);
                model.addAttribute("prjList", prjList);
                model.addAttribute("awardList", awardList);


                model.addAttribute("onlineList", onlineList);
                model.addAttribute("sampleList", sampleList);
                model.addAttribute("researchList", researchList);
                model.addAttribute("preList", preList);
                model.addAttribute("patentList", patentList);
                model.addAttribute("certList", certList);
                model.addAttribute("skills", skills);

                List<String> countryList = JsfUtil.getCountries();
                model.addAttribute("countryList", countryList);

                return view;

            } else {
                createDefaultModel(model);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "my-profile";
    }


    //@GetMapping("/candidate-profile")
//    public String candidateProfile(Model model,Principal principal,@RequestParam(defaultValue = "0",required = false)Integer id,@RequestParam(defaultValue = "0",required = false)Integer jobId){
//         
//        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
//
//        String userInfo = WebUtils.toString(loginedUser);
//        Users user = loginedUser.getUser();
//        
//        model.addAttribute("user", user);
//        model.addAttribute("userInfo", userInfo);
//        
//        String email = user.getUsername();
//        Optional<Employer> oppEmp = service.findByEmail(email);
//        
//        Employer employer = oppEmp.orElse(new Employer());
//        model.addAttribute("employer", employer);
//        model.addAttribute("imgUtil", new ImageUtil());
//        model.addAttribute("timeAgo", new TimeAgo());
//        
//        Optional<JobSeeker> oppSeeker = seekerService.findById(id);
//        JobSeeker seeker = oppSeeker.orElse(new JobSeeker());
//        
//        Optional<Jobs> oppJob =jobService.findById(jobId);
//        Jobs job = oppJob.orElse(new Jobs());
//       
//      
//        
//        model.addAttribute("seeker", seeker);
//        model.addAttribute("job", job);
//         
//        return "candidate-profile";
//    }

    private void createDefaultModel(Model model) {
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("imgUtil", new ImageUtil());

        List<WorkExperience> wlist = new ArrayList<>();
        model.addAttribute("wlist", wlist);

        List<EducationalExperience> edulist = new ArrayList<>();
        model.addAttribute("edulist", edulist);

        List<ITSkills> skillslist = new ArrayList<>();
        model.addAttribute("skillslist", skillslist);

        List<ProjectWork> prjList = new ArrayList<>();
        model.addAttribute("prjList", prjList);

        List<OnlineProfile> onlineList = new ArrayList<>();
        model.addAttribute("onlineList", onlineList);

        List<WorkSample> sampleList = new ArrayList<>();
        model.addAttribute("sampleList", sampleList);

        List<Research> researchList = new ArrayList<>();
        model.addAttribute("researchList", researchList);

        List<Presentation> preList = new ArrayList<>();
        model.addAttribute("preList", preList);

        List<Patent> patentList = new ArrayList<>();
        model.addAttribute("patentList", patentList);

        List<Certification> certList = new ArrayList<>();
        model.addAttribute("certList", certList);

        List<String> skills = new ArrayList<>();
        model.addAttribute("skills", skills);

        List<String> countryList = JsfUtil.getCountries();
        model.addAttribute("countryList", countryList);
    }

    public ProfileStrength getProfileStrength(JobSeeker seeker) {
        return strengthService.findByJobSeeker(seeker).orElse(new ProfileStrength(seeker));
    }

    //------------------------Create Resume Steps--------------------------
    //Step 1
    @RequestMapping(value = {"/my-resume", "/profile-summary"}, method = RequestMethod.GET)
    public String profileSummary(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {

            model.addAttribute("seeker", new JobSeeker());
            return "profile-summary";
        }
        Optional<JobSeeker> oppSeeker = service.findByEmail(user.getUsername());

        if (!oppSeeker.isPresent()) {
            model.addAttribute("seeker", new JobSeeker());
            return "profile-summary";
        }
        JobSeeker seeker = oppSeeker.get();
        model.addAttribute("seeker", seeker);

        return "profile-summary";
    }

    @GetMapping("/seeker/profile-summary")
    @ResponseBody
    public Profile profile(Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user == null) {
            throw new BeanNotFoundException("Invalid user");
        }
        JobSeeker seeker = createJobSeekerByEmail(user.getEmail());

        String summary = seeker.getProfileSummary();

        return convertToProfile(seeker);
    }


    @GetMapping("/seeker/candidate-resume")
    public String candidateResume(Model model, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);
            Users user = loginedUser.getUser();

            if (user != null) {

                model.addAttribute("user", user);
                model.addAttribute("userInfo", userInfo);

                String email = user.getUsername();

                Optional<JobSeeker> oppSeeker = service.findByEmail(email);
                if (oppSeeker.isPresent()) {
                    JobSeeker seeker = oppSeeker.get();
                    List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
                    List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
                    List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
                    List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
                    List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
                    List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
                    List<Presentation> preList = preSerive.findByJobSeeker(seeker);
                    List<Research> researchList = researchService.findByJobSeeker(seeker);
                    List<Patent> patentList = patentService.findByJobSeeker(seeker);
                    List<Certification> certList = certService.findByJobSeeker(seeker);

                    List<Awards> awardList = awardService.findByJobSeeker(seeker);
                    List<String> skills = new ArrayList<>();
                    String strSkills = seeker.getKeySkills();
                    if (strSkills != null) {
                        skills = convertToList(strSkills);
                    }


                    model.addAttribute("seeker", seeker);
                    model.addAttribute("imgUtil", new ImageUtil());
                    model.addAttribute("wlist", wlist);
                    model.addAttribute("edulist", edulist);
                    model.addAttribute("skillslist", skillslist);
                    model.addAttribute("prjList", prjList);
                    model.addAttribute("awardList", awardList);


                    model.addAttribute("onlineList", onlineList);
                    model.addAttribute("sampleList", sampleList);
                    model.addAttribute("researchList", researchList);
                    model.addAttribute("preList", preList);
                    model.addAttribute("patentList", patentList);
                    model.addAttribute("certList", certList);
                    model.addAttribute("skills", skills);

                    List<String> countryList = JsfUtil.getCountries();
                    model.addAttribute("countryList", countryList);

                    return "seeker/candidate-resume";
                }


            }

            model.addAttribute("seeker", new JobSeeker());
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("wlist", new ArrayList<>());
            model.addAttribute("edulist", new ArrayList<>());
            model.addAttribute("skillslist", new ArrayList<>());
            model.addAttribute("prjList", new ArrayList<>());
            model.addAttribute("awardList", new ArrayList<>());

            model.addAttribute("onlineList", new ArrayList<>());
            model.addAttribute("sampleList", new ArrayList<>());
            model.addAttribute("researchList", new ArrayList<>());
            model.addAttribute("preList", new ArrayList<>());
            model.addAttribute("patentList", new ArrayList<>());
            model.addAttribute("certList", new ArrayList<>());
            model.addAttribute("skills", new ArrayList<>());

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/candidate-resume";
    }


    @PostMapping("/profile-summary")
    public String updateProfileSummary(String description, Integer id, Principal principal) {

        Users user = JsfUtil.findOnline(principal);


        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();

            String ps = seeker.getProfileSummary();
            if (ps != null && ps.equalsIgnoreCase(description)) {
                return "redirect:personal-detailsx";
            }
            seeker.setProfileSummary(description);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer st = strength.getProfileSummary();
                if (st == null || st != 0) {
                    strength.setProfileSummary(1);
                    strengthService.save(strength);
                }


                return "redirect:personal-detailsx";
            }
        }

        return "profile-summary";
    }

    @PostMapping("/seeker/profile-summary")
    @ResponseBody
    public ResponseEntity<String> updateProfileSummaryApi(String description, Integer id, Principal principal) {
        try {

            Optional<JobSeeker> oppSeeker = service.findById(id);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could not find job seeker: " + id);
            }

            JobSeeker seeker = oppSeeker.get();
            String ps = seeker.getProfileSummary();

            seeker.setProfileSummary(description);
            service.save(seeker);

            ProfileStrength strength = getProfileStrength(seeker);
            strength.setProfileSummary(1);
            strengthService.save(strength);

            return new ResponseEntity("SUCCESS", HttpStatus.OK);
        } catch (BeanNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Step 10
    @GetMapping("/resume-headline")
    public String resumeHeadline(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);
        }

        return "resume-headline";
    }

    //Step 2
    @GetMapping("/personal-detailsx")
    public String personalDetailsx(Principal principal, Model model) {
        try {
            Users user = JsfUtil.findOnline(principal);
            JobSeeker seeker = service.findByEmail(user.getUsername()).orElse(new JobSeeker());
            model.addAttribute("seeker", seeker);
            model.addAttribute("user", user);
            model.addAttribute("countrylist", JsfUtil.getCountries());

            return "personal-detailsx";
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("user", new Users());

        return "personal-detailsx";
    }

    @PostMapping("/personal-detailsx")
    public String updatePersonalDetails2(Model model, Principal principal, Integer id, String fullName, String profTitle, String languages,
                                         String dob, double curSalary, double extMinSalary, double extMaxSalary,
                                         String phone, String yearsOfExperience,
                                         String educationLevel, String maritalStatus, String highestPosition,
                                         String email, String country, String postCode, String city, String address,
                                         String gender, String facebook, String twitter, String linkedIn, String google) {


        Users user = JsfUtil.findOnline(principal);
        JobSeeker seeker = service.findByEmail(user.getUsername()).orElse(new JobSeeker());


        seeker.setAddress(address);
        seeker.setApproved(true);
        seeker.setCurrentLocation(city);
        seeker.setCurrentSalary(BigDecimal.valueOf(curSalary));
        seeker.setExpMinSalary(BigDecimal.valueOf(extMinSalary));
        seeker.setExpectedSalary(BigDecimal.valueOf(extMaxSalary));
        seeker.setCountryOfOrigin(country);
        seeker.setEmail(email);
        seeker.setEntryDate(new Date());
        seeker.setFullName(fullName);
        seeker.setGender(gender);
        seeker.setHighestQualification(highestPosition);
        seeker.setMaritalStatus(maritalStatus);
        seeker.setModifiedDate(new Date());
        seeker.setPostcode(postCode);
        seeker.setPrimaryContact(phone);
        seeker.setProffesionalTitile(profTitle);
        seeker.setSpokenLanguages(languages);
        seeker.setTelephone(phone);
        seeker.setYearsOfExperience(yearsOfExperience);
        seeker.setDob(convertToDate(dob));
        seeker.setAddress(address);
        seeker.setGender(gender);
        seeker.setEducationLevel(educationLevel);
        seeker.setFacebook(facebook);
        seeker.setGoogle(google);
        seeker.setTwitter(twitter);
        seeker.setLinkedIn(linkedIn);


        if (service.save(seeker)) {

            ProfileStrength strength = getProfileStrength(seeker);

            Integer att = strength.getPersonalDetails();
            if (att == null || att != 1) {
                strength.setPersonalDetails(1);
                strength.setJobSeekerId(seeker);
                strengthService.save(strength);
            }
            return "redirect:key-skills";
            // return "redirect:attach-resume";
        }

        model.addAttribute("seeker", new JobSeeker());
        return "personal-detailsx";

    }

    @PostMapping("/resume-headline")
    public String updateResumeHeadline(String description, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setDescription(description);

            String ps = seeker.getProfileSummary();
            if (ps != null && ps.equalsIgnoreCase(description)) {
                return "redirect:resume-headline";
            }
            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer att = strength.getResumeHeadline();
                if (att == null || att != 1) {
                    strength.setResumeHeadline(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:attach-resume";
            }
        }

        return "resume-headline";
    }


    //Step 3
    @GetMapping("/key-skills")
    public String keySkills(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);
        }

        return "key-skills";
    }


    @PostMapping("/key-skills")
    public String updateKeySkills(String skills, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(skills);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);


                Integer att = strength.getKeySkills();
                if (att == null || att != 1) {
                    strength.setKeySkills(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:employment";
            }
        }

        return "key-skills";
    }

    //Step 4
    @GetMapping("/employment")
    public String employment(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
            model.addAttribute("seeker", seeker);
            model.addAttribute("wlist", wlist);
        }

        return "employment";
    }


    @PostMapping("/employment")
    public String updateEmployment(Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            List<WorkExperience> wlist = workService.findByJobSeeker(seeker);

            if (wlist.isEmpty()) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer att = strength.getEmployment();
                if (att == null || att != 1) {
                    strength.setEmployment(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }
                return "redirect:education";
            } else {
                model.addAttribute("message", "Please add at lease one institution");
            }

        }

        return "employment";
    }


    //Step 5
    @GetMapping("/education")
    public String education(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {

            JobSeeker seeker = oppSeeker.get();
            List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
            model.addAttribute("edulist", edulist);
            model.addAttribute("seeker", seeker);
        }

        return "education";
    }

    @PostMapping("/education")
    public String updateEducation(String skills, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(skills);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);


                Integer att = strength.getEducation();
                if (att == null || att != 1) {
                    strength.setEducation(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:it-skills";
            }
        }

        return "education";
    }

    //Step 6
    @GetMapping("/it-skills")
    public String itSkills(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);

            List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
            model.addAttribute("skillslist", skillslist);

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);

        }

        return "it-skills";
    }

    @PostMapping("/it-skills")
    public String updateItSkills(Integer id, Model model, Principal principal,
                                 String skill, String version, Integer lastUsed, String monthExp, Integer yearExp, Integer skillId, Integer proficiency) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();


            ITSkills skills = skillService.findById(skillId).orElse(new ITSkills());
            skills.setEntryDate(new Date());
            skills.setJobSeekerId(seeker);
            skills.setLastUsed(lastUsed);
            skills.setMonthExperience(monthExp);
            skills.setSkill(skill);
            skills.setVersion(version);
            skills.setYearExperience(yearExp);
            skills.setProficiency(proficiency);

            if (skillService.save(skills)) {

                ProfileStrength strength = getProfileStrength(seeker);


                Integer att = strength.getItSkills();
                if (att == null || att != 1) {
                    strength.setItSkills(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }


                return "redirect:desired-career-profile";
            }
        }

        return "it-skills";
    }


    //Step 7
    @GetMapping("/projects")
    public String projects(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);

            List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
            model.addAttribute("prjList", prjList);

        }

        return "projects";
    }

    @PostMapping("/projects")
    public String updateProjects(String skills, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(skills);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer att = strength.getProjects();
                if (att == null || att != 1) {
                    strength.setProjects(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:accomplishments";
            }
        }

        return "projects";
    }

    //Step 8
    @GetMapping("/accomplishments")
    public String accomplishments(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);

            List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
            model.addAttribute("onlineList", onlineList);

            List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
            model.addAttribute("sampleList", sampleList);

            List<Presentation> preList = preSerive.findByJobSeeker(seeker);
            model.addAttribute("preList", preList);

            List<Research> researchList = researchService.findByJobSeeker(seeker);
            model.addAttribute("researchList", researchList);

            List<Patent> patentList = patentService.findByJobSeeker(seeker);
            model.addAttribute("patentList", patentList);

            List<Certification> certList = certService.findByJobSeeker(seeker);
            model.addAttribute("certList", certList);


        }

        return "accomplishments";
    }

    @PostMapping("/accomplishments")
    public String updateAccomplishments(String skills, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(skills);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);

                Integer att = strength.getAccomplishment();
                if (att == null || att != 1) {
                    strength.setAccomplishment(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:desired-career-profile";
            }
        }

        return "accomplishments";
    }

    //Step 9
    @GetMapping("/desired-career-profile")
    public String desiredCareerProfile(Model model, Principal principal) {

        Users user = findOnline(principal);
//        System.out.println();
        JobSeeker seeker = service.findByEmail(user.getUsername()).orElse(new JobSeeker());
        model.addAttribute("seeker", seeker);

        if (user != null) {
            model.addAttribute("seeker", seeker);
            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);

        }

        return "desired-career-profile";
    }

    @PostMapping("/desired-career-profile")
    public String updateDesiredCareerProfile(Model model, Principal principal, String industry, String department, String role,
                                             BigDecimal minSal, BigDecimal maxSal, Integer availabilityYear, String availabilityMonth,
                                             String jobType, String employmentType, String shift, Integer id,
                                             String desireedLocation, String contractType, String desireedIndustry) {
        Optional<JobSeeker> oppSeeker = service.findById(id);
        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();

            String availability = availabilityYear + " " + availabilityMonth;

            seeker.setAvailabilityStatus(availability);
            seeker.setIndustry(industry);
            seeker.setDepartment(department);
            seeker.setSeekerRole(role);
            seeker.setExpectedSalary(maxSal);
            seeker.setExpMinSalary(minSal);
            seeker.setJobType(jobType);
            seeker.setEmploymentType(employmentType);
            seeker.setShift(shift);
            seeker.setDesiredLocation(desireedLocation);
            seeker.setDesiredIndustry(desireedIndustry);
            seeker.setContractType(contractType);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);


                Integer att = strength.getDesiredCareerProfile();
                if (att == null || att != 1) {
                    strength.setDesiredCareerProfile(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }

                return "redirect:resume-headline";
            }
        }

        return "desired-career-profile";
    }


    //Step 10
    @GetMapping("/attach-resume")
    public String attachResume(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            model.addAttribute("seeker", seeker);
        }

        return "attach-resume";
    }

    @PostMapping("/attach-resume")
    public String updateDesiredAttachResume(String skills, Integer id, Model model, Principal principal) {
        Optional<JobSeeker> oppSeeker = service.findById(id);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();
            seeker.setKeySkills(skills);

            if (service.save(seeker)) {

                ProfileStrength strength = getProfileStrength(seeker);
                Integer att = strength.getAttachedResume();
                if (att == null || att != 1) {
                    strength.setAttachedResume(1);
                    strength.setJobSeekerId(seeker);
                    strengthService.save(strength);
                }


                return "redirect:awards";
            }
        }

        return "attach-resume";
    }

    public Optional<JobSeeker> getJobSeeker(Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        return service.findByEmail(user.getUsername());
    }


    //Step 11
    @GetMapping("/awards")
    public String awards(Model model, Principal principal) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();

            List<Awards> awards = awardService.findByJobSeeker(seeker);
            model.addAttribute("seeker", seeker);
            model.addAttribute("awards", awards);


        }

        return "awards";
    }

    //Step 12
    @GetMapping("/job-preference")
    public String jobPreference(Model model, Principal principal, @RequestParam(defaultValue = "job-preference") String page) {
        try {
            Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

            if (!oppSeeker.isPresent()) {
                return defaultModel(model, page);
            }

            JobSeeker seeker = oppSeeker.get();
            Subscription sub = seeker.getSubscriptionId();
            List<CategoryCount> clist = findCategories();

            String jpref = findJobPrefrenceBySeeker(seeker, model);
            if (jpref == null) {
                jpref = "No Job Preference";
            }

            model.addAttribute("seeker", seeker);
            model.addAttribute("clist", clist);
            model.addAttribute("jpref", jpref);

            if (sub != null) {
                model.addAttribute("sub", sub);
            } else {
                model.addAttribute("sub", new Subscription("No Subscription", "", "No Subscription", BigDecimal.ZERO));
            }
            return page;

        } catch (Exception e) {

        }
        return defaultModel(model, page);

    }

    @GetMapping("/seeker/job-preference")
    public String seekerSubcription(Model model, Principal principal) {
        return jobPreference(model, principal, "seeker/seeker-subscription");
    }

    @PostMapping("/job-preference")
    @ResponseBody
    public String addJobPreference(Principal principal, @RequestParam List<Integer> ids) {
        List<SeekerJobPrefrence> list = new ArrayList<>();
        try {
            Optional<JobSeeker> oppSeeker = getJobSeeker(principal);

            if (!oppSeeker.isPresent()) {
                return "INVALID-USER";
            }
            JobSeeker seeker = oppSeeker.get();

            ids.stream().forEach((id) -> {
                Optional<Positions> oppPos = positionService.findById(id);

                if (oppPos.isPresent()) {
                    Positions position = oppPos.get();
                    SeekerJobPrefrence pref = new SeekerJobPrefrence(seeker, position);
                    list.add(pref);
                }
            });

            return jobprefService.saveAll(list) ? "SUCCESS" : "FAILED";


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";

    }

    @GetMapping("/find-award/{id}")
    @ResponseBody
    public AwardsDb findAwards(@PathVariable Integer id, Principal principal) {
        Awards aw = awardService.findById(id).orElse(new Awards());

        AwardsDb award = new AwardsDb();
        award.setFromYear(aw.getFromYear());
        award.setTitle(aw.getTitle());
        award.setToYear(aw.getToYear());
        JobSeeker seeker = aw.getJobseeker();
        if (seeker != null) {
            award.setJobseeker(seeker.getId());
        }
        return award;

    }

    @GetMapping("/delete-award/{id}")
    @ResponseBody
    public boolean deleteAwards(@PathVariable Integer id, Principal principal) {
        return awardService.deleteById(id);
    }

    @PostMapping("/awards")
    @ResponseBody
    public String updateAwards(Model model, Principal principal, Integer id, Integer fromYear, String title, Integer toYear) {

        Optional<JobSeeker> oppSeeker = getJobSeeker(principal);


        if (oppSeeker.isPresent()) {
            JobSeeker seeker = oppSeeker.get();

            Awards award = awardService.findById(id).orElse(new Awards());
            award.setFromYear(fromYear);
            award.setJobseeker(seeker);
            award.setTitle(title);
            award.setToYear(toYear);

            if (awardService.save(award)) {
                return "redirect:my-profile";
            }

        }

        return "award";
    }


    @GetMapping("/personal-details")
    public String personalDetails(Principal principal, Model model, @ModelAttribute JobSeeker evaluation) {
        try {
            Users user = JsfUtil.findOnline(principal);
            JobSeeker seeker = service.findByEmail(user.getUsername()).orElse(new JobSeeker());
            model.addAttribute("seeker", seeker);
            model.addAttribute("user", user);

            return "personal-details";
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("user", new Users());

        return "personal-details";
    }

    @GetMapping("/find-profile-strength/{id}")
    @ResponseBody
    public Integer profileStrength(@PathVariable Integer id) {
        Optional<JobSeeker> opp = service.findById(id);

        if (opp.isPresent()) {
            ProfileStrength strength = getProfileStrength(opp.get());

            Integer acc = strength.getAccomplishment();
            Integer resume = strength.getAttachedResume();
            Integer letter = strength.getCoverLetter();
            Integer desired = strength.getDesiredCareerProfile();
            Integer education = strength.getEducation();
            Integer employment = strength.getEmployment();
            Integer itskills = strength.getItSkills();
            Integer key = strength.getKeySkills();
            Integer pd = strength.getPersonalDetails();
            Integer summary = strength.getProfileSummary();
            Integer project = strength.getProjects();
            Integer headline = strength.getResumeHeadline();

            if (acc == null) {
                acc = 0;
            }
            if (resume == null) {
                resume = 0;
            }
            if (letter == null) {
                letter = 0;
            }
            if (desired == null) {
                desired = 0;
            }
            if (education == null) {
                education = 0;
            }

            if (employment == null) {
                employment = 0;
            }
            if (itskills == null) {
                itskills = 0;
            }
            if (key == null) {
                key = 0;
            }
            if (pd == null) {
                pd = 0;
            }
            if (summary == null) {
                summary = 0;
            }
            if (project == null) {
                project = 0;
            }
            if (headline == null) {
                headline = 0;
            }
            int total = (acc + resume + letter + desired + education + employment + itskills + key + pd + summary + project + headline);
            return (total / 12) * 100;
        }
        throw new BeanNotFoundException("Could not find Jobseeker with this credential");

    }

    @GetMapping("/find-seeker")
    @ResponseBody
    public Seeker findSeeker(Principal principal) {
        try {

            Users user = JsfUtil.findOnline(principal);
            JobSeeker sk = service.findByEmail(user.getUsername()).orElse(new JobSeeker());
            Seeker seeker = new Seeker();
            seeker.setAddress(sk.getAddress());
            seeker.setAge(sk.getAge());
            seeker.setAvailabilityStatus(sk.getAvailabilityStatus());
            seeker.setCountryOfOrigin(sk.getCountryOfOrigin());
            seeker.setCurrentSalary(sk.getCurrentSalary());
            seeker.setDescription(sk.getDescription());
            seeker.setDob(sk.getDob());
            seeker.setEducationLevel(sk.getEducationLevel());
            seeker.setExpectedSalary(sk.getExpMinSalary());
            seeker.setExpectedSalary(sk.getExpectedSalary());
            seeker.setFacebook(sk.getFacebook());
            seeker.setFullName(sk.getFullName());
            seeker.setGender(sk.getGender());
            seeker.setGoogle(sk.getGoogle());
            seeker.setHighestQualification(sk.getHighestQualification());
            seeker.setHomeTown(sk.getHomeTown());
            seeker.setId(sk.getId());
            seeker.setIndustry(sk.getIndustry());
            seeker.setJobType(sk.getJobType());
            seeker.setKeySkills(sk.getKeySkills());
            seeker.setLinkedIn(sk.getLinkedIn());
            seeker.setMaritalStatus(sk.getMaritalStatus());
            seeker.setPostcode(sk.getPostcode());
            seeker.setPrimaryContact(sk.getPrimaryContact());
            seeker.setProffesionalTitile(sk.getProffesionalTitile());
            seeker.setProfileSummary(sk.getProfileSummary());
            seeker.setShift(sk.getShift());
            seeker.setSpokenLanguages(sk.getSpokenLanguages());
            seeker.setTelephone(sk.getTelephone());
            seeker.setTwitter(sk.getTwitter());
            seeker.setYearsOfExperience(sk.getYearsOfExperience());

            return seeker;
        } catch (NullPointerException n) {
            return new Seeker();
        }
    }

    private Page<JobSeeker> findJobSeeker(Integer page, String q, Integer max) {

        List<JobSeeker> list = search.searchPaginated(q, page, max);
        Pageable pageable = PageRequest.of(page, max, Sort.by(Sort.Direction.ASC, "fullName"));

        return new PageImpl<JobSeeker>(list, pageable, list.size());
    }


//    @RequestMapping("/search-seeker")
//    public String searchSeeker(String q, Model model,@RequestParam(defaultValue = "0",required = false)Integer page) {
//      List<JobSeeker> list = null;
//      try {
//          list = search.search(q);
//        
//        model.addAttribute("job", new Jobs());
//        model.addAttribute("imgUtil", new ImageUtil());
//        model.addAttribute("list", list);
//        model.addAttribute("curentPage", page);
//        model.addAttribute("searchTerm", q);
//        
//        } catch (Exception ex) {
//            
//            ex.printStackTrace();
//          
//        }
//        return "find-job-seeker";
//     
//    }

    //@GetMapping("/candidates")
    @RequestMapping(value = {"/search-seeker", "/candidates"}, method = RequestMethod.GET)
    public String candidates(Model model, Principal principal, @RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "0", required = false) String q) {
        Users user = JsfUtil.findOnline(principal);
        String userType = user.getUserType();

        String view = "candidates";


        if (userType.equals("Staff")) {
            createStaffModel(model, user);
            view = "find-job-seeker";
        } else {
            createEmployerModel(model, user);
        }

        Page<JobSeeker> pages = null;
        if (q.equals("0")) {
            pages = service.findAll(page - 1);
        } else {
            pages = findCandidates(q, page - 1);
        }

        List<JobSeeker> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("searchTerm", q);


        return view;
    }

    private void createEmployerModel(Model model, Users user) {
        Optional<Employer> opp = empSservice.findByEmail(user.getUsername());
        if (opp.isPresent()) {
            model.addAttribute("employer", opp.get());
        } else {
            model.addAttribute("employer", new Employer());
        }

    }

    private void createStaffModel(Model model, Users user) {
        model.addAttribute("employee", user.getStaffId());
    }

    private Page<JobSeeker> findCandidates(String q, Integer page) {
        List<JobSeeker> list = search.searchPaginated(q, page, 5);
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "fullName"));
        return new PageImpl<JobSeeker>(list, pageable, list.size());
    }


    @PostMapping("/blacklist")
    @ResponseBody
    public String blacklist(Principal principal, Integer id, String tx) {
        Users user = JsfUtil.findOnline(principal);
        try {
            if (user.getUserType().equals("Staff")) {
                Optional<JobSeeker> opp = service.findById(id);

                if (opp.isPresent()) {

                    JobSeeker seeker = opp.get();
                    seeker.setBlacklistComment(tx);

                    seeker.setStatusCheck("Blacklist");

                    seeker.setBlacklisted(true);
                    seeker.setBlacklistedBy(user.getFullName());
                    seeker.setBlacklistedDate(LocalDate.now());

                    return service.save(seeker) ? "SUCCESS" : "FAILED";
                }
            }

            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public List<CategoryCount> findCategories() {
        try {

            List<CategoryCount> assigmentsList = new ArrayList<>();
//            Optional<List<Object[]>> option = jobService.findByCategoryCount();

            List<Positions> positions = positionService.findAll();
            positions.stream().forEach((item) -> {

                CategoryCount dash = new CategoryCount();
                dash.setId(new Date().getTime());
                dash.setCount(Long.valueOf(item.getId()));
                dash.setCategory(item.getName());
                dash.setKey(item.getId());

                assigmentsList.add(dash);
            });

//         if(option.isPresent()) {
//             
//             List<Object[]> objects = option.get();
//
//             objects.stream().forEach((item)->{
//                 counter++;
//                 CategoryCount dash = new CategoryCount();
//                 dash.setId(new Date().getTime());
//
//                 Object obj = item[0];
//                 Long lg = Long.valueOf(obj.toString());
//                 dash.setCount(lg);
//                 dash.setCategory((String)item[1]);
//                 dash.setKey(counter);
//                 
//       
//
//                 assigmentsList.add(dash);
//             });
//         }

            return assigmentsList;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ArrayList<>();
    }


    private String defaultModel(Model model, String page) {
        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("clist", new ArrayList<>());
        model.addAttribute("sub", new Subscription("No Subscription", "", BigDecimal.ZERO));
        if (page == null) {
            return "job-preference";
        }
        return page;
    }

    private String findJobPrefrenceBySeeker(JobSeeker seeker, Model model) {
        if (seeker == null) {
            return "";
        }

        List<SeekerJobPrefrence> list = jobprefService.findByJobSeeker(seeker);
        model.addAttribute("preflist", list);
        return list.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
    }


    //Bulk Upload
    @GetMapping("/admin/seeker-bulk-update")
    public String prodBulkUpdate(Model model, Principal principal,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "5") Integer max) {

        //Page<Products> pages =service.findAll(page,max);

        PageWrapper<JobSeeker> pages = new PageWrapper<>(service.findAll(page, max), "/admin/seeker-bulk-update");

        model.addAttribute("page", pages);


        List<JobSeeker> list = pages.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("list", list);


        return "admin/seeker-bulk-updat";
    }


    public List<JobSeeker> convertFromExcel(InputStream is) {
        try {
            List<JobSeeker> productList;
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
                    JobSeeker product = new JobSeeker();
                    String firstName = "";

                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        //job_seeker_id	fname	onames	dob	email	5gender_id	6 nationality_id	7 marital_status_id
                        //8 contact_number	9 profile_picture	10 uploaded_resume	11 has_been_activated	12 registration_date	13 percent_completed	14 edu_level_id	 15 placement_count	salary


                        switch (cellIdx) {

                            case 0:
                                String val = formater.formatCellValue(currentCell);
                                product.setId(Integer.valueOf(val));

                                break;


                            case 1:
                                firstName = formater.formatCellValue(currentCell);
                                break;

                            case 2:
                                String otheName = formater.formatCellValue(currentCell);
                                if (!otheName.equalsIgnoreCase("EMPTY_FIELD") && !firstName.equalsIgnoreCase("EMPTY_FIELD")) {
                                    product.setFullName(firstName + " " + otheName);
                                }

                                break;

                            case 3:
                                String dob = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(dob)) {
                                    product.setDob(JsfUtil.convertToDate(dob));//1/31/2013
                                }
                                break;


                            case 4:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    product.setEmail(formater.formatCellValue(currentCell));
                                }

                                break;

                            case 5:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {

                                    String gender = formater.formatCellValue(currentCell);
                                    switch (gender) {
                                        case "1":
                                            product.setGender("Male");
                                            break;

                                        case "2":
                                            product.setGender("Female");
                                            break;

                                        case "3":
                                            product.setGender("Any");
                                            break;
                                        default:
                                            break;

                                    }
                                }

                                break;
                            case 6:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    String country = formater.formatCellValue(currentCell);
                                    product.setCountryOfOrigin(findCountryOfOriging(country));
                                }

                                break;

                            case 7:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    String maritalStatus = formater.formatCellValue(currentCell);
                                    switch (maritalStatus) {
                                        case "1":
                                            product.setMaritalStatus("Single");
                                            break;

                                        case "2":
                                            product.setMaritalStatus("Married");
                                            break;

                                        case "3":
                                            product.setMaritalStatus("Divorced");
                                            break;

                                        case "4":
                                            product.setMaritalStatus("Widowed");
                                            break;
                                        default:
                                            break;

                                    }
                                }
                                break;

                            //      9 profile_picture	10 uploaded_resume	11 has_been_activated	12 registration_date	13 percent_completed	14 edu_level_id	 15 placement_count	salary
                            case 8:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    String cellPhone = formater.formatCellValue(currentCell);
                                    String phone = "+" + cellPhone;
                                    product.setTelephone(phone);
                                    product.setPrimaryContact(phone);
                                }

                                break;


                            case 9:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    String profile_picture = formater.formatCellValue(currentCell);
                                    product.setPicFileName(profile_picture);
                                }

                                break;

                            case 10:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    product.setUploadedResume(currentCell.getStringCellValue());
                                }

                                // product.setCvFileName(SAVED_JOB_SAVED);
                                break;


                            case 11:
                                if (!"EMPTY_FIELD".equals(formater.formatCellValue(currentCell))) {
                                    String activated = formater.formatCellValue(currentCell);
                                    product.setApproved("1".equals(activated));
                                }

                                break;

                            case 12:

                                String regDate = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(regDate)) {
                                    product.setEntryDate(JsfUtil.convertToDate(regDate));//1/31/2013
                                }
                                break;

                            case 13:
                                String percentCompleted = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(percentCompleted)) {
                                    product.setProfileStrenght(percentCompleted);
                                }


                                break;

                            case 14:
                                String edu_level_id = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(edu_level_id)) {
                                    product.setEducationLevel(findEducationLevel(edu_level_id));
                                }
                                break;

                            case 15:
                                String salary = formater.formatCellValue(currentCell);
                                if (!"EMPTY_FIELD".equals(salary)) {

                                    Double sal = Double.valueOf(salary);

                                    product.setExpMinSalary(BigDecimal.valueOf(sal));
                                    product.setExpectedSalary(BigDecimal.valueOf(sal));
                                }

                                //  product.setExpectedSalary(BigDecimal.valueOf((double)currentCell.getNumericCellValue()));
                                break;

                            default:
                                break;

                        }
                        product.setBlacklisted(false);
                        product.setExpectedSalary(BigDecimal.ZERO);
                        product.setProffesionalTitile(SAVED_JOB_APPLIED);
                        product.setYearsOfExperience("0");

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


    private String findCountryOfOriging(String country) {
        try {
            if ("NULL".equals(country)) {
                return null;
            }
            Integer id = Integer.parseInt(country);
            Optional<Countries> opp = countryService.getCountries(id);
            if (opp.isPresent()) {
                return opp.get().getName();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    private String findEducationLevel(String level) {
        /**
         * '7', 'Junior High School (JHS)', '1', '0.00', '2015-09-29 08:33:13'
         '8', 'Senior High School (SHS)', '1', '0.00', '2015-09-29 08:33:32'
         '9', 'Diploma', '1', '0.00', '2015-09-29 08:35:22'
         '10', 'Higher National Diploma (HND)', '1', '0.00', '2015-09-29 08:35:41'
         '11', 'Bachelor\'s Degree', '1', '0.00', '2015-09-29 08:36:01'
         '12', 'Master\'s Degree', '1', '0.00', '2015-09-29 08:39:14'
         '13', 'Doctorate (Ph.D. or Ed.D.)', '1', '0.00', '2015-09-29 08:40:38'

         */
        switch (level) {
            case "7":
                return "Junior High School (JHS)";

            case "8":
                return "Senior High School (SHS)";

            case "9":
                return "Diploma";

            case "10":
                return "Higher National Diploma (HND)";

            case "11":
                return "Bachelor's Degree";

            case "12":
                return "Master's Degree";

            case "13":
                return "Doctorate (Ph.D. or Ed.D.)";

            default:
                return null;
        }
    }

    @PostMapping("/admin/upload-bulk-seekers")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {


        InputStream in = null;
        try {
            in = file.getInputStream();
            List<JobSeeker> list = convertFromExcel(in);

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


    @RequestMapping(value = {"/admin/findJobSeeker/{transactionId}", "/public/findJobSeeker/{transactionId}"})
    @ResponseBody
    public SearchedSeekerDetails findSearchedSeeker(@PathVariable Integer transactionId) {
        try {
            Optional<JobSeeker> oppSeeker = service.findById(transactionId);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could no find Jobseeker");
            }
            JobSeeker seeker = oppSeeker.get();
            return convertToSearchedSeeker(seeker);

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Could no find Jobseeker");
        }

    }

    @GetMapping("/public/findJobSeeker/{transactionId}")
    @ResponseBody
    public SearchedSeekerDetails findSearchedSeeker2(@PathVariable Integer transactionId) {
        try {
            Optional<JobSeeker> oppSeeker = service.findById(transactionId);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could no find Jobseeker");
            }
            JobSeeker seeker = oppSeeker.get();
            return convertToSearchedSeeker(seeker);

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Could no find Jobseeker");
        }

    }

    //find-job-seeker
    @GetMapping("/recruiter/job-seeker-details")
    public String jobSeekerDetails(Model model, @RequestParam(required = false) String q) {
        return "recruiter/job-seeker-details";
    }

    @GetMapping("/public/find-job-seeker/{transactionId}")
    @ResponseBody
    public SearchedSeekerDetails findSearchedSeeker2(@PathVariable String transactionId) {
        try {
            Optional<JobSeeker> oppSeeker = service.findByTransactionId(transactionId);
            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could no find Jobseeker");
            }
            JobSeeker seeker = oppSeeker.get();
            return convertToSearchedSeeker(seeker);

        } catch (BeanNotFoundException e) {
            throw new BeanNotFoundException("Could no find Jobseeker");
        }

    }

    private SearchedSeekerDetails convertToSearchedSeeker(JobSeeker c) {
        return new SearchedSeekerDetails(

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
                loadEduExp(eduService.findByJobSeeker(c)),
                loadWorkExp(workService.findByJobSeeker(c)),
                loadSkills(skillService.findByJobSeeker(c)),
                loadAward(awardService.findByJobSeeker(c)),
                findDetails(c.getExpectedSalary()),
                findDetails(c.getEducationLevel()),
                findDetails(c.getIndustry()),
                findDetails(c.getYearsOfExperience()),
                findDetails(c.getHighestQualification()),
                findDetails(c.getProfileSummary()),
                findDetails(c.getHomeTown()),
                findDetails(c.getCountryOfOrigin()),
                findDetails(c.getGoogle()),
                findDetails(c.getFacebook()),
                findDetails(c.getTwitter()),
                findDetails(c.getLinkedIn()),
                blacklisted(c.getBlacklisted()),
                c.getBlacklistComment()
        );
    }

    public Integer findJobSeekers(JobSeeker s) {
        if (s != null) {
            return s.getId();
        }
        return null;
    }

    public List<WorkEx> loadWorkExp(List<WorkExperience> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (!list.isEmpty()) {
           /*
             companyName,  descriptions,  entryDate,  designation, 
             jobProfile,  jobTitle,  monthStartedWork,  monthStopedWork, 
             stillWorkThere,  yearStartedWork,  yearStopedWork,  yearsOfExperience, 
             industry,  transactionId,  region,  city,  country, 
             currentSalary,  jobSeekerId,  highestPosition, id
            */
            return list.stream().map(c ->
                            new WorkEx(
                                    findDetails(c.getCompanyName()),
                                    findDetails(c.getDescriptions()),
                                    c.getEntryDate(),
                                    findDetails(c.getDesignation()),
                                    findDetails(c.getJobProfile()),
                                    findDetails(c.getJobTitle()),
                                    findDetails(c.getMonthStartedWork()),
                                    findDetails(c.getMonthStopedWork()),
                                    findDetails(c.getStillWorkThere()),
                                    findDetails(c.getYearStartedWork()),
                                    findDetails(c.getYearStopedWork()),
                                    findDetails(c.getYearsOfExperience()),
                                    findDetails(c.getIndustry()),
                                    c.getTransactionId(),
                                    findDetails(c.getRegion()),
                                    findDetails(c.getCity()),
                                    findDetails(c.getCountry()),
                                    findDetails(c.getCurrentSalary()),
                                    findJobSeekers(c.getJobSeekerId()),
                                    findDetails(c.getHighestPosition()),
                                    c.getId()
                            ))
                    .collect(Collectors.toList());


        }
        return new ArrayList<>();
    }

    public List<Education> loadEduExp(List<EducationalExperience> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (!list.isEmpty()) {
           /*
             Integer id, String institutionName, String qualificationReceived, String programStudied, String courseDescription,
            Integer yearGraduated, Integer yearStarted, Date entryDate, Integer jobSeekerId, String transactionId
            */
            return list.stream().map(c ->
                            new Education(
                                    c.getId(),
                                    findDetails(c.getInstitutionName()),
                                    findDetails(c.getQualificationReceived()),
                                    findDetails(c.getProgramStudied()),
                                    findDetails(c.getCourseDescription()),
                                    findDetails(c.getYearGraduated()),
                                    findDetails(c.getYearStarted()),
                                    c.getEntryDate(),
                                    findJobSeekers(c.getJobSeekerId()),
                                    c.getTransactionId()
                            ))
                    .collect(Collectors.toList());


        }
        return new ArrayList<>();
    }

    public Integer findProficiency(Integer prof) {
        if (prof == null) {
            return 10;
        }
        return prof;
    }

    public List<Skills> loadSkills(List<ITSkills> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (!list.isEmpty()) {
           /*
             Integer id, String skill, String version, Integer lastUsed, String monthExperience, Integer yearExperience, Integer jobSeekerId
            */
            return list.stream().map(c ->
                            new Skills(
                                    c.getId(),
                                    c.getSkill(),
                                    c.getVersion(),
                                    c.getLastUsed(),
                                    c.getMonthExperience(),
                                    c.getYearExperience(),
                                    findJobSeekers(c.getJobSeekerId()),
                                    findProficiency(c.getProficiency())
                            ))
                    .collect(Collectors.toList());


        }
        return new ArrayList<>();
    }

    public List<AwardsDb> loadAward(List<Awards> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (!list.isEmpty()) {
           /*
             Integer id, String title, Integer fromYear, Integer toYear, Integer jobseeker
            */
            return list.stream().map(c ->
                            new AwardsDb(
                                    c.getId(),
                                    c.getTitle(),
                                    c.getFromYear(),
                                    c.getToYear(),
                                    findJobSeekers(c.getJobseeker())
                            ))
                    .collect(Collectors.toList());


        }
        return new ArrayList<>();
    }

    @GetMapping("/recruiter/job-seekers")
    public String jobSeekers(Model model, Principal principal,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(required = false) String q,
                             @RequestParam(defaultValue = "10") Integer max) {
        try {
            Page<JobSeeker> pg = null;
            if (q != null) {
                List<JobSeeker> ls = search.search(q);
                pg = findJobSeeker(page, q, max);
            } else {
                pg = findJobSeeker(page, max);
            }
            Users user = JsfUtil.findOnline(principal);
            model.addAttribute("user", user);


            PageWrapper<JobSeeker> pages = new PageWrapper<>(pg, "/recruiter/job-seekers");
            model.addAttribute("page", pages);
            List<JobSeeker> list = pages.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pages.getTotalPages());
            model.addAttribute("max", max);
            model.addAttribute("joblist", list);

            return "recruiter/job-seekers";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "recruiter/job-seekers";
    }

    private Page<JobSeeker> findJobSeeker(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        return service.findAll(page, max);
    }


    @GetMapping("1/{transactionId}")
    @ResponseBody
    public Integer profileCompleteness(@PathVariable String transactionId) {
        try {

            Optional<JobSeeker> oppSeeker = service.findByTransactionId(transactionId);
            return 70;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    @RequestMapping(value = {"/update-my-resume"}, method = RequestMethod.GET)
    public String updateResume(Model model, Principal principal) {

        Users user = JsfUtil.findOnline(principal);
        if (user == null) {

            model.addAttribute("seeker", new JobSeeker());
            return "profile-summary";
        }
        Optional<JobSeeker> oppSeeker = service.findByEmail(user.getUsername());

        if (!oppSeeker.isPresent()) {
            model.addAttribute("seeker", new JobSeeker());
            return "profile-summary";
        }
        JobSeeker seeker = oppSeeker.get();
        model.addAttribute("seeker", seeker);
        return "update-my-resume";
    }


    @PostMapping("/seeker/update-my-profile-summary")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> updateProfileSummary2(String description, Integer id, Principal principal) {
        //Users user = JsfUtil.findOnline(principal);
        try {
            Optional<JobSeeker> oppSeeker = service.findById(id);

            if (!oppSeeker.isPresent()) {
                throw new BeanNotFoundException("Could not find applicant : " + id);
            }

            JobSeeker seeker = oppSeeker.get();
            seeker.setProfileSummary(description);
            String status = service.save(seeker) ? "Success" : "Failed";

            ProfileStrength strength = getProfileStrength(seeker);
            Integer st = strength.getProfileSummary();
            if (st == null || st != 0) {
                strength.setProfileSummary(1);
                strengthService.save(strength);
            }


            return new ResponseEntity(status, HttpStatus.OK);

        } catch (BeanNotFoundException e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private JobSeeker createJobSeekerByEmail(String email) {
        Optional<JobSeeker> oppSeeker = service.findByEmail(email);
        if (!oppSeeker.isPresent()) {
            throw new BeanNotFoundException("Could not find job seeker with the email provided: " + email);
        }
        return oppSeeker.get();
    }


    private Profile convertToProfile(JobSeeker seeker) {

        Profile profile = new Profile();


        profile.setAwards(getAwards(seeker));
        profile.setEducation(getEducation(seeker));
        profile.setId(seeker.getId());
        profile.setKeySkills(seeker.getKeySkills());
        profile.setPersonal(getPersonalDetails(seeker));
        profile.setProfileSummary(seeker.getProfileSummary());
        profile.setProjects(getProjects(seeker));
        profile.setSkills(getSkills(seeker));
        profile.setTransactionId(seeker.getTransactionId());
        profile.setWorkEx(getWorkExp(seeker));

        return profile;
    }

    private List<AwardsDb> getAwards(JobSeeker seeker) {
        List<Awards> list = awardService.findByJobSeeker(seeker);
        //Integer id, String title, Integer fromYear, Integer toYear, Integer jobseeker
        return list.stream().map(n ->
                        new AwardsDb(
                                n.getId(),
                                n.getTitle(),
                                n.getFromYear(),
                                n.getToYear(),
                                n.getJobseeker().getId()
                        ))
                .collect(Collectors.toList());
    }

    @GetMapping("/seeker/job-seeker-dashboard")
    public String jobSeekerDashboard(Principal principal, Model model) {
        try {
            Users user = JsfUtil.findOnline(principal);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/seeker-dashboard";
    }

    private List<Education> getEducation(JobSeeker seeker) {
        List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
        return edulist.stream().map(n ->
                        new Education(
                                n.getId(),
                                n.getInstitutionName(),
                                n.getQualificationReceived(),
                                n.getProgramStudied(),
                                n.getCourseDescription(),
                                n.getYearGraduated(),
                                n.getYearStarted(),
                                n.getEntryDate(),
                                n.getJobSeekerId().getId(),
                                n.getTransactionId()
                        ))
                .collect(Collectors.toList());
    }

    private PersonalDetails getPersonalDetails(JobSeeker seeker) {
        return new PersonalDetails(seeker);

    }


    private List<Projects> getProjects(JobSeeker seeker) {
        List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
        // id, String title, String client, String projectStatus, Integer startYear, 
        // endYear, String startMonth, String endMonth, double projectCost,
        // fundedBy, Date entryDate, Integer jobSeekerId, String details
        return prjList.stream().map(n ->
                        new Projects(
                                n.getId(),
                                n.getTitle(),
                                n.getClient(),
                                n.getProjectStatus(),
                                n.getStartYear(),
                                n.getEndYear(),
                                n.getStartMonth(),
                                n.getEndMonth(),
                                JsfUtil.convertToDouble(n.getProjectCost()),
                                n.getFundedBy(),
                                n.getEntryDate(),
                                n.getJobSeekerId().getId(),
                                n.getDetails()
                        ))
                .collect(Collectors.toList());
    }

    private List<Skills> getSkills(JobSeeker seeker) {
        List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
        return skillslist.stream().map(n -> new Skills()).collect(Collectors.toList());
    }

    private List<WorkEx> getWorkExp(JobSeeker seeker) {
        List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
        return wlist.stream().map(n -> new WorkEx()).collect(Collectors.toList());
    }


    @GetMapping("/seeker/profile-update")
    public String profileUpdate(Principal principal) {
        return "seeker/profile";
    }

    @PostMapping("/seeker/update-profile-summary")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> updateProfile(String summary, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            if (user == null) {
                throw new BeanNotFoundException("Invalid user credential");
            }
            String email = user.getEmail();
            if (email == null) {
                throw new BeanNotFoundException("Invalid user credential");
            }
            Optional<JobSeeker> opp = service.findByEmail(email);
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid user credential: " + email);
            }
            JobSeeker seeker = opp.get();

            String ps = seeker.getProfileSummary();
            if (summary.equalsIgnoreCase(ps)) {
                return new ResponseEntity("Success", HttpStatus.OK);
            }

            seeker.setProfileSummary(summary);
            ProfileStrength strength = getProfileStrength(seeker);

            Integer st = strength.getProfileSummary();
            if (st == null || st != 0) {
                strength.setProfileSummary(1);
                strengthService.save(strength);
            }

            String status = service.save(seeker) ? "Success" : "Failed";
            return new ResponseEntity(status, HttpStatus.OK);


        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/update-trx")
    @ResponseBody
    public String updateTx() {
        List<JobSeeker> list2 = new ArrayList<>();

        List<JobSeeker> list = service.findAll();
        list.stream().forEach((item) -> {
            item.setTransactionId(JsfUtil.generateSerial());
            list2.add(item);
        });

        return service.saveAll(list2) ? "success" : "failed";
    }


    @GetMapping("/recruiter/candidate-profile")
    public String recruiterCandidateProfile(Model model, Principal principal, @RequestParam String trx) {
        try {
            Users user = JsfUtil.findOnline(principal);

            if (trx == null) {
                return "redirect:/recruiter/applications";
            }

            Optional<JobSeeker> oppSeeker = service.findByTransactionId(trx);

            if (!oppSeeker.isPresent()) {
                createDefaultModel(model);
                return "redirect:/recruiter/applications";
            }

            JobSeeker seeker = oppSeeker.get();
            List<WorkExperience> wlist = workService.findByJobSeeker(seeker);
            List<EducationalExperience> edulist = eduService.findByJobSeeker(seeker);
            List<ITSkills> skillslist = skillService.findByJobSeeker(seeker);
            List<ProjectWork> prjList = pService.findByJobSeeker(seeker);
            List<OnlineProfile> onlineList = onlineService.findByJobSeeker(seeker);
            List<WorkSample> sampleList = wsampleService.findByJobSeeker(seeker);
            List<Presentation> preList = preSerive.findByJobSeeker(seeker);
            List<Research> researchList = researchService.findByJobSeeker(seeker);
            List<Patent> patentList = patentService.findByJobSeeker(seeker);
            List<Certification> certList = certService.findByJobSeeker(seeker);

            List<Awards> awardList = awardService.findByJobSeeker(seeker);
            List<String> skills = new ArrayList<>();
            String strSkills = seeker.getKeySkills();
            if (strSkills != null) {
                skills = convertToList(strSkills);
            }


            model.addAttribute("seeker", seeker);
            model.addAttribute("imgUtil", new ImageUtil());
            model.addAttribute("wlist", wlist);
            model.addAttribute("edulist", edulist);
            model.addAttribute("skillslist", skillslist);
            model.addAttribute("prjList", prjList);
            model.addAttribute("awardList", awardList);


            model.addAttribute("onlineList", onlineList);
            model.addAttribute("sampleList", sampleList);
            model.addAttribute("researchList", researchList);
            model.addAttribute("preList", preList);
            model.addAttribute("patentList", patentList);
            model.addAttribute("certList", certList);
            model.addAttribute("skills", skills);

            Boolean blacklisted = seeker.getBlacklisted();
            if (blacklisted == null) {
                blacklisted = false;
            }
            model.addAttribute("blacklisted", blacklisted);

            List<String> countryList = JsfUtil.getCountries();
            model.addAttribute("countryList", countryList);

            return "recruiter/candidate-profile";


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "recruiter/candiate-profile";
    }

    private boolean blacklisted(Boolean blacklisted) {
        if (blacklisted == null) {
            return false;
        }
        return blacklisted;
    }


}

     
