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
import com.debusey.smart.pos.smartpos.repo.UserRepository;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.debusey.smart.pos.smartpos.JsfUtil.findDetails;

/**
 * @author Admin
 */

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JobSeekerService jservice;
    @Autowired
    EmployerService empService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private JobsService service;
    @Autowired
    private CareerGuidanceService gservice;
    @Autowired
    private EmployeeService emplService;

    @Autowired
    private JavaMailSender emailService;

    @Autowired
    private SubscriptionService subService;

    @Autowired
    private TestimonialService testimonyService;

    @Autowired
    private AdvertService advertService;


    @Autowired
    private CompanyService cservice;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssessmentLinesService assessmentLinesService;

    @Autowired
    private TrainingScheduleService trainingScheduleService;

    @Autowired
    private AssessmentTransactionService assessmentTransactionService;

    @Autowired
    private TrainingTransactionService trainingTransactionService;

    @Autowired
    private CourseTransactionService courseTransactionService;

    @Autowired
    private PackageTransactionService packageTransactionService;

    @Autowired
    private PackageSubscribeService packageSubscribeService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private AssessmentRoundService assessmentRoundService;

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Value("${spring.mail.username}")
    private String appTitle;

    @Value("${app.hostName}")
    private String appUrl;

    @GetMapping("/setup")
    public String setup(Model model) {
        Optional<Users> oppUser = userRepository.findByUsername("admin");
        if (oppUser.isPresent()) {
            return "redirect:admin";
        }
        return "setup";
    }

    @PostMapping("/setup")
    public String setupAdmin(String username, String password, String strRole, String fn, String ln, String telephone, String email) {
        boolean status = createAdmin(username, password, strRole, fn, ln, telephone, email);
        if (status) {
            return "redirect:admin";
        }
        return "redirect:setup";
    }

    @GetMapping("/")
    public String welcomePage(Model model, @RequestParam(defaultValue = "0") Integer page) {

        List<CategoryCount> clist = findByCategoryCountLimit();
        model.addAttribute("catlist", clist);
        //List<Jobs> joblist = service.findFiveJobs();
        //model.addAttribute("joblist", joblist);


        //Adverts
        //loadAdverst(model);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<Company> company = cservice.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("com", com);

        loadTestimonial(model);

        return "index";

    }

    @GetMapping("/course/contents/{id}")
    public String getCourseContent(@PathVariable Integer id, Model model) {
        Course course = courseService.findById(id).orElse(null);
        model.addAttribute("course", course);
        return "recruiter/course-content";
    }

    @GetMapping("/homepage-2")
    public String welcomePage2(Model model, @RequestParam(defaultValue = "0") Integer page) {

        List<CategoryCount> clist = findByCategoryCountLimit();
        model.addAttribute("catlist", clist);
        List<Jobs> joblist = service.findFiveJobs();
        model.addAttribute("joblist", joblist);


        //Adverts
        loadAdverst(model);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<Company> company = cservice.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("com", com);

        model.addAttribute("basicMsg", new Subscription("Basic", "Default Basic Subscription", BigDecimal.ZERO));
        model.addAttribute("proMsg", new Subscription("Professional", "Default professional Subscription", BigDecimal.ZERO));
        model.addAttribute("extendedMsg", new Subscription("Premium", "Default premium Subscription", BigDecimal.ZERO));

        //Career
        List<CareerGuidance> glist = gservice.findThreeEvents();
        model.addAttribute("careerlist", glist);

        loadTestimonial(model);


        return "homepage-2";


    }

    @GetMapping("/homepage-3")
    public String welcomePage3(Model model, @RequestParam(defaultValue = "0") Integer page) {

        List<CategoryCount> clist = findByCategoryCountLimit();
        model.addAttribute("catlist", clist);
        List<Jobs> joblist = service.findFiveJobs();
        model.addAttribute("joblist", joblist);


        //Adverts
        loadAdverst(model);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<Company> company = cservice.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("com", com);

        model.addAttribute("basicMsg", new Subscription("Basic", "Default Basic Subscription", BigDecimal.ZERO));
        model.addAttribute("proMsg", new Subscription("Professional", "Default professional Subscription", BigDecimal.ZERO));
        model.addAttribute("extendedMsg", new Subscription("Premium", "Default premium Subscription", BigDecimal.ZERO));

        //Career
        List<CareerGuidance> glist = gservice.findThreeEvents();
        model.addAttribute("careerlist", glist);

        loadTestimonial(model);


        return "homepage-3";

    }

    //all-jobs
    @GetMapping("/categories")
    public String categories(Model model) {

        List<CategoryCount> clist = findCategories();
        model.addAttribute("catlist", clist);

        return "categories";
    }


    public String allJobs(Model model, @RequestParam(defaultValue = "1") Integer page) {

        model.addAttribute("timeAgo", new TimeAgo());
        model.addAttribute("imgUtil", new ImageUtil());
        Page<Jobs> joblist = findJobs(page - 1);
        //model.addAttribute("joblist", joblist);

        //  model.addAttribute("postedjobs", joblist.getNumberOfElements());

        model.addAttribute("joblist", joblist.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", joblist.getTotalPages());
        model.addAttribute("totalItems", joblist.getTotalElements());

        return "all-jobs";
    }

    @GetMapping("/countries")
    public String countries(Model model) {

        List<CategoryCount> clist = findCountries();
        model.addAttribute("catlist", clist);


        return "countries";
    }

    @GetMapping("/category-company-jobs")
    public String categoryCompayJobs(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        List<Employer> list = empService.findAll();
        model.addAttribute("list", list);


        return "category-company-jobs";
    }

    @GetMapping("/category-jobs")
    public String categoryJobs(Model model) {

        List<CategoryCount> list = findCategories();

        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("list", list);

        return "category-jobs";
    }

    @GetMapping("/jobs-by-qualifications")
    public String categoryQualication(Model model) {

        List<CategoryCount> list = findQualification();

        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("list", list);

        return "jobs-by-qualifications";
    }


    @GetMapping("/about-us")
    public String aboutUs(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        Company company = cservice.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getAboutUs());
        return "about-us";
    }

    //
    @GetMapping("/privacy-policy")
    public String privacyPolicy(Model model) {

        Company company = cservice.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getPrivacyPolicy());

        model.addAttribute("imgUtil", new ImageUtil());

        return "privacy-policy";
    }

    @GetMapping("/careers-with-us")
    public String careersWithUs(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        Company company = cservice.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", "");

        return "careers-with-us";
    }

    @GetMapping("/tnc")
    public String tnc(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        Company company = cservice.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getTnc());
        return "tnc";
    }

    @GetMapping("/site-map")
    public String siteMap(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("siteMap"));
        return "site-map";
    }

    @GetMapping("/faq")
    public String faq(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("faq"));
        return "faq";
    }

    @GetMapping("/report-problem")
    public String reportProble(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("reportProble"));

        return "report-problem";
    }

    @GetMapping("/resume-writing")
    public String resumeWriting(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("resumeWritting"));

        return "resume-writing";
    }

    @GetMapping("/resume-database-access")
    public String resumeDbAccess(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("resumeDbAccess"));

        return "resume-database-access";
    }


    @GetMapping("/category-location-jobs")
    public String categoryJobLocation(Model model) {

        List<CategoryLocation> list = createCategoryLocations();


        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("list", list);


        return "category-location-jobs";
    }

    @GetMapping("/company-settings")
    public String categoryJobDesignation(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());

        return "company-settings";
    }

    @RequestMapping(value = {"/category-skill-jobs"}, method = RequestMethod.GET)
    public String categoryJobSkill(Model model) {
        List<String> list = service.findByDistinctPrefSkillsAttribute();

        model.addAttribute("imgUtil", new ImageUtil());

        model.addAttribute("list", list);


        return "category-skill-jobs";
    }

    @GetMapping("/find-job")
    public String browseJob(Model model, String profession, String location, String category, @RequestParam(defaultValue = "0") int page) {

        List<Jobs> list = service.findJobByCategoryAndLocation(profession, location, category);
        model.addAttribute("postedjobs", 100);
        model.addAttribute("task", 280);
        model.addAttribute("freelancer", 890);

        List<CategoryCount> clist = findByCategoryCountLimit();
        List<CategoryCount> clist2 = findCountriesLimit();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);

        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("joblist", findJobs(page));

        List<Testimonial> testimonyList = testimonyService.findByStatus(true);

        if (!testimonyList.isEmpty()) {

            Random random = new Random();
            Testimonial test = testimonyList.get(random.nextInt(testimonyList.size()));
            model.addAttribute("testimonyList", testimonyList);
            model.addAttribute("test", test);
        }


        //  model.addAttribute("testimonyList", testimonyList);
        model.addAttribute("imgUtil", new ImageUtil());
        // model.addAttribute("test", test);

        return "index";


    }

    @GetMapping("/save-jobs")
    public String saveJobs(Model model, Principal principal, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("postedjobs", 100);
        model.addAttribute("task", 280);
        model.addAttribute("freelancer", 890);

        List<CategoryCount> clist = findCategories();
        List<CategoryCount> clist2 = findCountriesLimit();

        model.addAttribute("catlist", clist);
        model.addAttribute("countrylist", clist2);

        model.addAttribute("joblist", findJobs(page));
        model.addAttribute("postedjobs", 100);
        model.addAttribute("task", 280);
        model.addAttribute("freelancer", 890);


        List<String> cats = service.findByCategories();
        model.addAttribute("cats", cats);
        model.addAttribute("joblist", findJobs(page));

        List<Testimonial> testimonyList = testimonyService.findByStatus(true);

        if (!testimonyList.isEmpty()) {

            Random random = new Random();
            Testimonial test = testimonyList.get(random.nextInt(testimonyList.size()));
            model.addAttribute("testimonyList", testimonyList);
            model.addAttribute("test", test);
        }
        // Random random = new Random();
        //Testimonial test = testimonyList.get(random.nextInt(testimonyList.size()));

        //   model.addAttribute("testimonyList", testimonyList);
        model.addAttribute("imgUtil", new ImageUtil());
//        model.addAttribute("test", test);
        model.addAttribute("currentPage", page);

        model.addAttribute("proflist", service.findByDistictProfession());
        model.addAttribute("locationList", service.findByDistictLocation());
        model.addAttribute("timeAgo", new TimeAgo());

        return "save-jobs";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {

        return "welcome";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "You have logged out successfully");
        return "login";
    }

    @GetMapping("/createAdmin")
    @ResponseBody
    public boolean createAdmin(String username, String password, String strRole, String fn, String ln, String telephone, String email) {
        try {
            Users users = new Users();

            users.setFullName("Admin");
            users.setEmail(email);
            users.setActive(true);

            Employee emp = new Employee();
            emp.setFirstName(fn);
            emp.setSurname(ln);
            emp.setMobileno(telephone);
            emp.setEmail(email);
            users.setStaffId(emp);

            users.setPassword(encoder.encode(password));

            users.setUsername(username);
            users.setUserType("Admin");
            users.setActive(true);

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roles.add(role);

            users.setRoles(roles);

            return save(users);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @GetMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(String username, String fn, String ln) {

        Optional<Users> oppUser = userService.findByUsername(username);
        if (oppUser.isPresent()) {
            Users users = oppUser.get();

            Employee emp = new Employee();

            emp.setFirstName(fn);
            emp.setSurname(ln);
            users.setStaffId(emp);

            return save(users);
        }
        return false;
    }


    public boolean saveAndSendEmail(Users user, HttpServletRequest request) {
        try {
            user.setActivationToken(UUID.randomUUID().toString());
            Users status = userRepository.save(user);
            if (status != null) {
                sendEmail(request, user);
                String tel = user.getTelephone();
                if (tel != null) {
                    sendSMS(user, request);
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(Users user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/notifications")
    public String testing(Model model) {
        return "test";
    }

    @GetMapping("/test-login")
    public String testLogin(Model model) {
        return "login-1";
    }


    public List<CategoryCount> findByCategoryCountLimit() {
        try {
            List<CategoryCount> assigmentsList = new ArrayList<>();
            Optional<List<Object[]>> option = service.findByCategoryCountLimit();

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
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<CategoryCount> findCategories() {
        try {
            List<CategoryCount> assigmentsList = new ArrayList<>();
            Optional<List<Object[]>> option = service.findByCategoryCount();

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
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ArrayList<>();
    }

    public List<CategoryCount> findQualification() {
        try {
            List<CategoryCount> assigmentsList = new ArrayList<>();
            Optional<List<Object[]>> option = service.findByQualificationCount();

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
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ArrayList<>();
    }

    private List<CategoryCount> findCountries() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<CategoryCount> findCountriesLimit() {
        try {
            List<CategoryCount> list = new ArrayList<>();

            Optional<List<Object[]>> option = service.findByCountryCount();

            if (option.isPresent()) {
                List<Object[]> objects = option.get();

                objects.stream().forEach((item) -> {

                    CategoryCount dash = new CategoryCount();
                    dash.setId(new Date().getTime());

                    Object obj = item[0];
                    Long count = Long.valueOf(obj.toString());

                    dash.setCount(count);
                    dash.setCountry((String) item[1]);


                    list.add(dash);
                });

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private Page<Jobs> findJobs(Integer page) {
        return service.findAll(page, 10);
    }


    @GetMapping("test-register")
    public String testRegister(Model model) {
        return "test-register";
    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(name = "account-type", required = false) String accountType) {
        if (accountType == null) {
            return "account-type";
        }
        switch (accountType) {
            case "Individual":
                return "register";
            case "Company":
                return "company-register";
            default:
                return "account-type";
        }

    }

    @GetMapping("/account-type")
    public String accountType(Model model) {

        return "account-type";
    }

    @PostMapping("/register-company")
    @ResponseBody
    public String registerCompany(String comp, String email, String password, String telephone, HttpServletRequest request) {
        System.out.println("company register");
        try {
            List<Users> usrlist = userRepository.findByUserEmail(email);
            if (!usrlist.isEmpty()) {
                return "EMAIL-EXIST";
            }

            List<Employer> slist = empService.findDuplicates(email);
            if (!slist.isEmpty()) {
                return "EMAIL-EXIST";
            }
            Users users = new Users();

            users.setFullName(comp);
            users.setActive(true);
            users.setPassword(encoder.encode(password));
            users.setUsername(email);
            users.setUserType("Company");
            users.setTelephone(telephone);

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("ROLE_COMPANY");
            roles.add(role);

            Employer employer = new Employer();
            employer.setEmail(email);
            employer.setPhoneNumber(telephone);


            empService.save(employer);

            users.setRoles(roles);
            if (saveAndSendEmail(users, request)) {
                return "SUCCESS";
            } else {
                return "FAILED";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    @PostMapping("/register-ind")
    @ResponseBody
    public String registerInd(String fullName, String email, String password, String title, String telephone, HttpServletRequest request) {

        try {
            List<Users> usrlist = userRepository.findByUserEmail(email);
            if (!usrlist.isEmpty()) {
                return "EMAIL-EXIST";
            }

            List<JobSeeker> slist = jservice.findDuplicates(email);
            if (!slist.isEmpty()) {
                return "EMAIL-EXIST";
            }
            Users users = new Users();

            users.setFullName(fullName);
            users.setActive(true);
            users.setPassword(encoder.encode(password));
            users.setUsername(email);
            users.setUserType("Individual");
            users.setSubscribed(false);
            users.setTelephone(telephone);

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("ROLE_JOBSEEKER");
            roles.add(role);

            JobSeeker seeker = new JobSeeker();
            seeker.setEmail(email);
            seeker.setProffesionalTitile(title);
            seeker.setTransactionId(JsfUtil.generateSerial());
            seeker.setTelephone(telephone);


            jservice.save(seeker);
            users.setRoles(roles);
            if (saveAndSendEmail(users, request)) {
                return "SUCCESS";
            } else {
                return "FAILED";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    private void sendEmail(HttpServletRequest request, Users user) {

//        String appUrl = request.getScheme() + "://" + request.getServerName();
//        String remoteHost= request.getRemoteHost();
//        String servername= request.getServerName();
//        Integer remotePort= request.getRemotePort();
//        String remoteAddress= request.getRemoteAddr();
//        String remoteUser= request.getRemoteUser();

        try {

            System.out.println("appUrl " + appUrl);

            String msg = "To activate your account, click the link below:\n" + appUrl + "/activate?token=" + user.getActivationToken();
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(user.getUsername());
            passwordResetEmail.setSubject("XJobs Job Registration");
            passwordResetEmail.setFrom(appTitle);
            passwordResetEmail.setText(msg);


            emailService.send(passwordResetEmail);


        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    private void sendPasswordResetEmail(Users user) {

        try {


            String msg = "Your request to reset your password has been successful. Your new password is start123 \n , Please login with this password and change it";
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(user.getUsername());
            passwordResetEmail.setSubject("XJobs Password Reset");
            passwordResetEmail.setFrom(appTitle);
            passwordResetEmail.setText(msg);


            emailService.send(passwordResetEmail);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/activate")
    public String activateAccount(Model model, @RequestParam("token") String token) {
        Optional<Users> user = userService.findByActivationToken(token);

        if (user.isPresent()) { // Token found in DB

            Users u = user.get();
            u.setActive(true);
            userService.updateUsers(u);

            model.addAttribute("msg", "Your account activation is successful, you can login now");
            model.addAttribute("account", "Activation Successful");

        } else { // Token not found in DB

            model.addAttribute("errorMessage", "Oops!  This is an invalid account activation link.");
            model.addAttribute("account", "Invalid Account");
        }

        return "invalid-account";

    }

    @RequestMapping(value = "/activate2", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        Optional<Users> user = userService.findByActivationToken(token);

        if (user.isPresent()) { // Token found in DB
            modelAndView.addObject("resetToken", token);
            Users u = user.get();
            u.setActive(true);
            userService.updateUsers(u);

            modelAndView.setViewName("welcome");

        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid account activation link.");
        }

        modelAndView.setViewName("invalid-account");

        return modelAndView;
    }

//    @GetMapping("/users")
//    public String addUsers(Model model,Principal principal){
//        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
//        Users user = loginedUser.getUser();
//        
//        Employee employee = user.getStaffId();
//        model.addAttribute("imgUtil", new ImageUtil());
//        model.addAttribute("emp", employee);
//        
//        
//        List<Users> list = userService.getAllUsers();
//        model.addAttribute("list", list);
//        
//        return "users";
//    }

    //    @GetMapping("/users")
    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String admin(Model model, Principal principal, @RequestParam(defaultValue = "1", required = false) Integer page) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        if (user != null) {
            Employee employee = user.getStaffId();
            model.addAttribute("emp", employee);
        } else {
            model.addAttribute("emp", new Employee());
        }


        model.addAttribute("imgUtil", new ImageUtil());
        Page<Users> pages = findUsers(page - 1);
        List<Users> list = pages.getContent();

        model.addAttribute("list", list);
        model.addAttribute("user", user);
        model.addAttribute("total", pages.getNumberOfElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());

        return "users";

    }

    private Page<Users> findUsers(Integer page) {
        return userService.findAll(page);
    }


    @PostMapping("/admin/create-user")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody UserApi api) {

        try {
            Users user = userService.findByUsername(api.getUsername()).orElse(new Users());

            user.setActive(true);
            user.setEmail(api.getEmail());
            user.setFullName(api.getFn() + " " + api.getLn());

            user.setPassword(encoder.encode(api.getPassword()));
            user.setUserType("Staff");
            user.setEntryDate(new Date());

            user.setTelephone(api.getTelphone());
            user.setUsername(api.getUsername());

            List<Role> roles = new ArrayList<>();
            Role rl = new Role();
            rl.setName(api.getRole());
            roles.add(rl);
            Employee employee = user.getStaffId();
            if (employee == null) {
                employee = new Employee();
            }


            employee.setFirstName(api.getFn());
            employee.setSurname(api.getLn());
            employee.setJobrole(api.getRole());
            employee.setEmail(api.getEmail());

            user.setRoles(roles);
            user.setStaffId(employee);

            String status = userService.updateUsers(user) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/readVal")
    @ResponseBody
    public String readValue() {
        return appUrl;
    }

    @GetMapping("/sendTestEmail")
    @ResponseBody
    public boolean testEmail(String email) {

        try {

            String msg = "To activate your account, click the link below";
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(email);
            passwordResetEmail.setSubject("Test Email");
            ///  passwordResetEmail.setFrom("halhassan@aoholdings.net");
            passwordResetEmail.setFrom(appTitle);

            passwordResetEmail.setText(msg);


            emailService.send(passwordResetEmail);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/findOneUser")
    @ResponseBody
    public Users findOne(Long assessId) {
        Optional<Users> ass = userService.findById(assessId);
        return ass.orElse(new Users());
    }

    //
    @GetMapping("/findAppUser/{id}")
    @ResponseBody
    public AppUser findAppUser(@PathVariable Long id) {
        try {
            AppUser app = new AppUser();

            Optional<Users> ass = userService.findById(id);

            if (ass.isPresent()) {
                Users user = ass.get();

                Employee emp = user.getStaffId();

                app.setId(user.getId().intValue());
                app.setFullName(user.getFullName());
                app.setTelephone(user.getTelephone());
                app.setEmail(user.getEmail());

                List<Role> roles = user.getRoles();

                if (!roles.isEmpty()) {
                    app.setRole(user.getRoles().get(0).getName());
                }

                app.setUserType(user.getUserType());
                app.setUsername(user.getUsername());

                if (emp != null) {
                    app.setFn(emp.getFirstName());
                    app.setLn(emp.getSurname());
                    app.setStaffId(emp.getEmployeeid());
                }
            }

            return app;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AppUser();
    }

    @GetMapping("/admin/deleteUser/{id}")
    @ResponseBody
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @GetMapping("/admin/resetStaffPass/{id}")
    @ResponseBody
    public boolean resetStaffPass(@PathVariable Long id) {
        Optional<Users> ass = userService.findById(id);
        if (ass.isPresent()) {
            Users user = ass.get();
            user.setPassword(encoder.encode("start123"));

            boolean status = userService.updateUsers(user);
            if (status) {
                sendPasswordResetEmail(user);
            }
            return status;
        }
        return false;
    }


    //method for the hubtel payment for assessment
    @GetMapping("/payment/assessment/{id}")
    public String getPaymentForm(@PathVariable Integer id, Model model, Principal principal) {
        AssessmentLines assessmentLine = assessmentLinesService.findById(id).orElse(null);
        Users user = JsfUtil.findOnline(principal);

        //retrieve current assessment of the online user
        Assessment currentAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                user,
                assessmentLine,
                true
        );

        //grab all assessments rounds available
        List<AssessmentRound> assessmentRounds = assessmentRoundService.findAll();

        /*
            check if the user has a valid subscription to the assessment he's subscribing for
            if yes redirect the user to his current assessment on his account
            otherwise do let him proceed
         */
        if (currentAssessment == null) {
            //grab all assessment the user has done in that particular package
            List<Assessment> completedAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                    user, assessmentLine, false, user.getUserType()
            );

            //check if we still have another round to go for
            if (completedAssessment.size() < assessmentRounds.size()) {
                //grab the last round taken and then subscribe to the next one based on priority
                AssessmentRound assessmentRoundFuture = assessmentRoundService.findByRoundPriority(
                        completedAssessment.size() + 1
                );

                if (assessmentLine.getPrice().doubleValue() == 0) {

                    Assessment assessmentTransaction = new Assessment();
                    assessmentTransaction.setAssessmentLines(assessmentLine);
                    assessmentTransaction.setUsers(user);
                    assessmentTransaction.setUserType(user.getUserType());
                    assessmentTransaction.setStatus(true);
                    assessmentTransaction.setAssessmentRound(assessmentRoundFuture);
                    assessmentTransaction.setTransactionId(JsfUtil.generateSerial());

                    assessmentTransactionService.addAssessmentTransaction(assessmentTransaction);
                    return "redirect:/seeker/current-assessment";

                }
            }

        } else {
            return "redirect:/seeker/current-assessment";
        }


        model.addAttribute("assessmentLine", assessmentLine);
        model.addAttribute("entity", "assessment");
        model.addAttribute("amount", assessmentLine.getPrice());
        model.addAttribute("user", user);
//        return "paystack-form";
        return "hubtel-payment";
    }

    @GetMapping("/payment/successful")
    public String paymentSuccess(Model model) {


        model.addAttribute("msg", "Payment was successful so kindly go to your dashboard by hitting the link below");
        return "paystack-form";
    }

    //method of payment for training
    @GetMapping("/payment/training/{id}")
    public String getPaymentFormTraining(@PathVariable Integer id, Model model, Principal principal) {
        TrainingSchedule trainingSchedule = trainingScheduleService.findById(id).orElse(null);
        Users user = JsfUtil.findOnline(principal);

        TrainingTransaction currentTraining = trainingTransactionService.findByUserAndTrainingAndStatus(
                user, trainingSchedule
        );
        if (currentTraining == null) {
            if (trainingSchedule.getTraining_cost() == 0) {
                TrainingTransaction trainingTransaction = new TrainingTransaction();
                trainingTransaction.setUsers(user);
                trainingTransaction.setTrainingSchedule(trainingSchedule);
                trainingTransaction.setTransactionId(JsfUtil.generateSerial());
                trainingTransaction.setAmount(trainingSchedule.getTraining_cost());
                trainingTransaction.setStatus(true);
                trainingTransaction.setUserType(user.getUserType());
                trainingTransaction.setEntryDate(new Date());

                trainingTransactionService.addTrainingTransaction(trainingTransaction);
                return "redirect:/seeker/my-trainings";
            }
        } else {
            return "redirect:/seeker/my-trainings";
        }
        model.addAttribute("training", trainingSchedule);
        model.addAttribute("entity", "training");
        model.addAttribute("amount", trainingSchedule.getTraining_cost());
        model.addAttribute("user", user);
//        return "paystack-form";
        return "hubtel-payment";
    }

    //method of payment for course
    @GetMapping("/payment/course/{id}")
    public String getPaymentFormCourse(@PathVariable Integer id, Model model, Principal principal) {
        Course course = courseService.findById(id).orElse(null);
        Users user = JsfUtil.findOnline(principal);

        CourseTransaction currentCourse = courseTransactionService.findByUserAndCourseAndStatus(
                user, course
        );

        if (currentCourse == null) {

            if (course.getPrice() == 0) {
                CourseTransaction courseTransaction = new CourseTransaction();

                courseTransaction.setUsers(user);
                courseTransaction.setCourse(course);
                courseTransaction.setTransactionId(JsfUtil.generateSerial());
                courseTransaction.setAmount(course.getPrice());
                courseTransaction.setStatus(true);
                courseTransaction.setUserType(user.getUserType());
                courseTransaction.setEntryDate(new Date());
                courseTransactionService.addCourseTransaction(courseTransaction);
                return "redirect:/seeker/my-courses";
            }
        } else {
            return "redirect:/seeker/my-courses";
        }
        model.addAttribute("course", course);
        model.addAttribute("entity", "course");
        model.addAttribute("amount", course.getPrice());
        model.addAttribute("user", user);
//        return "paystack-form";
        return "hubtel-payment";
    }

    //method of payment for employer package subscription
    @GetMapping("/payment/package-subscription/{id}")
    public String getPaymentFormSubscription(@PathVariable Integer id, Model model, Principal principal) {

        PackageSubscribe packageSubscribe = packageSubscribeService.findById(id);
        Users user = JsfUtil.findOnline(principal);

        if (packageSubscribe.getPrice().intValue() == 0) {

            PackageTransaction packageTransaction = new PackageTransaction();

            packageTransaction.setUsers(user);
            packageTransaction.setPackageSubscribe(packageSubscribe);
            packageTransaction.setTransactionId(JsfUtil.generateSerial());
            packageTransaction.setAmount(packageSubscribe.getPrice());
            packageTransaction.setStatus(true);
            packageTransaction.setUserType(user.getUserType());
            packageTransaction.setEntryDate(new Date());
//            courseTransactionService.addCourseTransaction(courseTransaction);
            packageTransactionService.addPackageTransaction(packageTransaction);
            return "redirect:/company-dashboard";
        }
        model.addAttribute("package", packageSubscribe);
        model.addAttribute("entity", "package");
        model.addAttribute("amount", packageSubscribe.getPrice());
        model.addAttribute("user", user);
//        return "paystack-form";
        return "hubtel-payment";
    }

    @GetMapping("/payment/service/request/{id}")
    public String getPaymentFormService(@PathVariable Integer id, Model model, Principal principal) {

        ServiceType serviceType = serviceTypeService.findById(id).get();
        Users user = JsfUtil.findOnline(principal);
        Optional<ServiceRequest> pendingServiceRequested = serviceRequestService.findByUserAndStatus(
                user,
                "Not Issued",
                serviceType
        );

        if (pendingServiceRequested.isPresent()) {
            return "redirect:/candidate-dashboard";
        }

        model.addAttribute("service", serviceType);
        model.addAttribute("entity", "service");
        model.addAttribute("amount", serviceType.getPrice());
        model.addAttribute("user", user);
        return "hubtel-payment";
    }

    @GetMapping("/rec-profile")
    public String recProfile(Model model, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        Employee employee = user.getStaffId();
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("emp", employee);

        List<String> countryList = JsfUtil.getCountries();
        model.addAttribute("countryList", countryList);

        return "rec-profile";
    }

    @GetMapping("/rec-profile-bt")
    public String recProfile(Model model, Integer id, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        if (id == null) {
            return "redirect:/rec-profile";
        }

        Optional<Employee> employee = emplService.findById(id);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("emp", employee.orElse(new Employee()));

        List<String> countryList = JsfUtil.getCountries();
        model.addAttribute("countryList", countryList);

        return "rec-profile-bt";
    }


    @GetMapping("/rec-profile-bt2")
    public String recProfile2(Model model, Integer id, Principal principal) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        if (id == null) {
            return "redirect:/rec-profile";
        }

        Optional<Employee> employee = emplService.findById(id);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("emp", employee.orElse(new Employee()));

        List<String> countryList = JsfUtil.getCountries();
        model.addAttribute("countryList", countryList);

        return "rec-profile-bt";
    }

    /**
     * &id="+id+
     *
     * @return
     */
    @PostMapping("/update-rec-profile")
    @ResponseBody
    public boolean updateRecProfile(String firstName, String surname, String otherName,
                                    String address, String dob, String description,
                                    String gender, String mobileno, String email,
                                    String currentLocation, Integer id, Principal principal) {
        try {

            Optional<Employee> oppEmp = emplService.findById(id);
            if (oppEmp.isPresent()) {
                Employee emp = oppEmp.get();

                emp.setFirstName(firstName);
                emp.setSurname(surname);
                emp.setOtherName(otherName);
                emp.setAddress(address);
                emp.setDob(JsfUtil.convertToDate(dob));
                emp.setDescription(description);
                emp.setGender(gender);
                emp.setMobileno(mobileno);
                emp.setEmail(email);
                emp.setCurrentLocation(currentLocation);

                return emplService.save(emp);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/change-password-2")
    @ResponseBody
    public boolean changePass(String pass, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();

            if (user == null) {
                return false;
            }
            user.setPassword(encoder.encode(pass));

            return userService.updateUsers(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/setup-contact")
    public String setupContact() {

        return "setup-contact";
    }

    @GetMapping("/lozard")
    public String lozard(Model model) {
        return "lozard";
    }

    @GetMapping("/rec-change-password")
    public String changePassword(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        Users user = loginedUser.getUser();
        model.addAttribute("imgUtil", new ImageUtil());
        String email = user.getUsername();


        model.addAttribute("seeker", new JobSeeker());
        model.addAttribute("wlist", new ArrayList<>());
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("savedJobs", new ArrayList<>());

        model.addAttribute("imgUtil", new ImageUtil());


        return "rec-change-password";
    }

    @PostMapping("/change-password-admin")
    // @ResponseBody
    public String changeAdminPass(String pass, Principal principal) {
        try {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();

            if (user == null) {
                return "redirect:/recruiter/profile-password";
            }
            user.setPassword(encoder.encode(pass));

            userService.updateUsers(user);
            return "redirect:/recruiter/profile-password";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/recruiter/profile-password";
    }


    @GetMapping("/find-countries")
    @ResponseBody
    public List<String> getCountriesAndCode() {

        List<String> list = new ArrayList<>();

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {
            Locale obj = new Locale("", countryCode);
            list.add(obj.getDisplayCountry());
        }

        return list;
    }

    private void loadTestimonial(Model model) {
        List<Testimonial> list = testimonyService.findByStatus(true);
        model.addAttribute("testimonies", createTestimony(list));

    }

    @GetMapping("/load-testimonials")
    @ResponseBody
    public List<Testimony> loadTestimonials() {
        List<Testimonial> list = testimonyService.findByStatus(true);
        return createTestimony(list);
    }

    private List<Testimony> createTestimony(List<Testimonial> list) {
        // id,  message, candidtate, fileName, candidateProfession
        return list.stream().map(c ->
                        new Testimony(
                                new Date().getTime(),
                                c.getMessage(),
                                c.getName(),
                                c.getFileName(),
                                c.getProfession()
                        )
                )
                .collect(Collectors.toList());
    }

    private void loadAdverst(Model model) {

        List<Adverts> subList = advertService.findAll();

        if (!subList.isEmpty()) {
            Adverts horizontal = subList.stream()
                    .filter(u -> u != null)
                    .filter(u -> u.getPageLocation() != null)
                    .filter(u -> u.getPageLocation().equalsIgnoreCase("Horizontal"))
                    .findAny()
                    .orElse(new Adverts());

            Adverts vertical1 = subList.stream()
                    .filter(u -> u != null)
                    .filter(u -> u.getPageLocation() != null)
                    .filter(u -> u.getPageLocation().equalsIgnoreCase("Vertical 1"))
                    .findAny()
                    .orElse(new Adverts());

            Adverts vertical2 = subList.stream()
                    .filter(u -> u != null)
                    .filter(u -> u.getPageLocation() != null)
                    .filter(u -> u.getPageLocation().equalsIgnoreCase("Vertical 2"))
                    .findAny()
                    .orElse(new Adverts());

            Adverts vertical3 = subList.stream()
                    .filter(u -> u != null)
                    .filter(u -> u.getPageLocation() != null)
                    .filter(u -> u.getPageLocation().equalsIgnoreCase("Vertical 3"))
                    .findAny()
                    .orElse(new Adverts());

            model.addAttribute("horizontal", horizontal);
            model.addAttribute("vertical1", vertical1);
            model.addAttribute("vertical2", vertical2);
            model.addAttribute("vertical3", vertical3);


            return;
        }

        model.addAttribute("horizontal", new Adverts());
        model.addAttribute("vertical1", new Adverts());
        model.addAttribute("vertical2", new Adverts());
        model.addAttribute("vertical3", new Adverts());


    }

    @GetMapping("/cv")
    public String cv(Model model) {
        return "cv";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/test-pay")
    public String test2(Model model) {
        return "test-pay";
    }

    @GetMapping("/job-posting")
    public String jobPosting(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("jobPosting"));
        return "job-posting";
    }

    @GetMapping("/resume-package")
    public String resumePackage(Model model) {

        List<Subscription> subList = subService.findAll();

        if (!subList.isEmpty()) {

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

            return "resume-package";
        }
        return "resume-package";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound(Model model, Principal principal) {

        if (principal != null) {
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> We could find the page you are looking for!";
            model.addAttribute("message", message);

        }

        return "404";
    }

    private List<CategoryLocation> createCategoryLocations() {
        List<CategoryLocation> catlist = new ArrayList<>();
        List<String> locations = service.findByDistictLocation();

        //Get job titles for every location
        locations.stream().forEach((item) -> {
            CategoryLocation cat = new CategoryLocation();
            List<Jobs> jobs = service.findByLocation(item);
            cat.setCategory(item);
            cat.setListOfJobs(jobs);
            catlist.add(cat);

        });
        return catlist;
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    @ResponseBody
    public boolean islogin() {
        return !JsfUtil.isLogin();
    }

    @GetMapping("/find-auth-user")
    @ResponseBody
    public OnlineUser findAuthUser() {
        Users user = JsfUtil.authUser();
        if (user == null) {
            throw new BeanNotFoundException("Unauthenticated");
        }
        //username,  fullName,  telephone,  email,  subscribed
        return new OnlineUser(user.getUsername(), user.getFullName(), user.getTelephone(), user.getEmail(), user.isSubscribed());
    }

    @GetMapping("/bulk-uploads")
    public String bulk(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);

        Employee emp = user.getStaffId();
        model.addAttribute("emp", emp);
        model.addAttribute("user", user);

        model.addAttribute("msg", "Waiting...");

        return "bulk-upload";

    }

    @GetMapping("/advertise-a-job")
    public String employers(Model model) {

        List<Companies> cm = companies();
        model.addAttribute("complist", cm);


        return "advertise-a-job";
    }

    @GetMapping("/services")
    public String products(Model model) {
        // List<Companies> cm = companies();
        //  model.addAttribute("complist", cm);

        return "services";

    }

    @GetMapping("/request-cv")
    public String requesCv(Model model) {
        return "request-cv";
    }

    @GetMapping("/company-list")
    @ResponseBody
    public List<Companies> companies() {
        List<Employer> emps = empService.findByPopular(true);
        //Integer id, String name, String fileName, String location

        return emps.stream().map(c ->
                        new Companies(
                                c.getId(), c.getCompanyName(), c.getFileName(), c.getLocation()
                        )
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/statistics")
    @ResponseBody
    public Statistics companyStatistics() {
        //Integer posted, Integer companies, Integer filled, Integer members
        return new Statistics(getPostedJobs(), getCompaniesCount(), getFilledJobs(), getJobSeekersCount());
    }

    private Integer getPostedJobs() {
        return service.getPostedJobs("Posted");
    }

    private Integer getCompaniesCount() {
        return empService.getCompaniesCount();
    }

    private Integer getFilledJobs() {
        return service.getPostedJobs("Filled");
    }

    private Integer getJobSeekersCount() {
        return jservice.getJobSeekersCount();
    }


    @GetMapping("/job-seekers")
    public String jobSeekes(Model model, @RequestParam(defaultValue = "0") Integer page) {

        List<CategoryCount> clist = findByCategoryCountLimit();
        model.addAttribute("catlist", clist);
        List<Jobs> joblist = service.findFiveJobs();
        model.addAttribute("joblist", createJobSearch(joblist));


        //Adverts
        loadAdverst(model);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());


        Optional<Company> company = cservice.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("com", com);


        List<Subscription> subList = subService.findAll();
        if (!subList.isEmpty()) {
            // JsfUtil.loadEmployerModel(model,subList);
            JsfUtil.loadSubscription(model, subList, 1);
        }

        List<CareerGuidance> glist = gservice.findThreeEvents();
        model.addAttribute("careerlist", glist);

        return "job-seekers";
    }

    @GetMapping("/employer-packages")
    public String employerPackages(Model model, @RequestParam(defaultValue = "0") Integer page) {
        List<CategoryCount> clist = findByCategoryCountLimit();
        model.addAttribute("catlist", clist);
        List<Jobs> joblist = service.findFiveJobs();
        model.addAttribute("joblist", joblist);


        //Adverts
        loadAdverst(model);

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("currentPage", page);
        model.addAttribute("timeAgo", new TimeAgo());

        Optional<Company> company = cservice.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("com", com);

        model.addAttribute("basicMsg", new Subscription("Basic", "Default Basic Subscription", BigDecimal.ZERO));
        model.addAttribute("proMsg", new Subscription("Professional", "Default professional Subscription", BigDecimal.ZERO));
        model.addAttribute("extendedMsg", new Subscription("Premium", "Default premium Subscription", BigDecimal.ZERO));

        //Career
        List<CareerGuidance> glist = gservice.findThreeEvents();
        model.addAttribute("careerlist", glist);

        loadTestimonial(model);

        return "employer-packages";
    }


    private void loadEmployerSubModel(Model model, List<Subscription> subList) {
        Subscription optional = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Optional"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));

        List<String> optionItems = JsfUtil.createListFromString(optional.getMessage());
        List<String> optionalMsg = new ArrayList<>();
        optionItems.forEach(optionalMsg::add);
        optionalMsg.add(optional.getCvCount() + " CV Downloads ");


        Subscription basic = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Basic"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));

        List<String> basicItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> basicMsg = new ArrayList<>();
        basicItems.forEach(basicMsg::add);
        basicMsg.add(basic.getCvCount() + " CV Downloads ");


        Subscription premium = subList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getName().equalsIgnoreCase("Employer Premium"))
                .findAny()
                .orElse(new Subscription(0, BigDecimal.ZERO));

        List<String> premiumItems = JsfUtil.createListFromString(basic.getMessage());
        List<String> premiumMsg = new ArrayList<>();
        premiumItems.forEach(premiumMsg::add);
        premiumMsg.add(premium.getCvCount() + " CV Downloads ");

        model.addAttribute("list", subList);
        model.addAttribute("optional", optional);
        model.addAttribute("basic", basic);
        model.addAttribute("premium", premium);


        model.addAttribute("basicMsg", optionalMsg);
        model.addAttribute("proMsg", basicMsg);
        model.addAttribute("extendedMsg", premiumMsg);


        //Career
        List<CareerGuidance> glist = gservice.findThreeEvents();
        model.addAttribute("careerlist", glist);

    }


    @GetMapping("/cv-restructuring")
    public String cvRestructuring(Model model) {
        Optional<ServiceType> serviceType = serviceTypeService.findByName("CV Restructuring");
        if (serviceType.isPresent()) {
            model.addAttribute("service", serviceType.get());
        } else {
            model.addAttribute("service", null);
        }
        return "cv-restructuring";
    }

    @GetMapping("/interview-grooming")
    public String interviewGrooming(Model model) {
        Optional<ServiceType> serviceType = serviceTypeService.findByName("Interview Grooming");
        if (serviceType.isPresent()) {
            model.addAttribute("service", serviceType.get());
        } else {
            model.addAttribute("service", null);
        }
        return "interview-grooming";
    }

    private List<JobSearch> createJobSearch(List<Jobs> list) {
        List<JobSearch> serchlist = new ArrayList<>();
        /**
         * Integer id, String category, PostedBy postedBy, Boolean showCompanyName, 
         String country, String city, String region, String natureOfContract, Date publishedDate,
         Date expireDate, String datePublished, String endDate, BigDecimal renumeration, BigDecimal
         toRenumeration, String profession, String location, String fileName, String details,
         List<String> skillls, List<String> dutiesAndRespo, List<String> specialRequirements,
         String howToApply, String industry, String qualification, Integer slot, String telephone,
         Integer minYearsExperience, String minQualification, String gender, String posted, String prefInterviewDate,
         String prefStartDate, Integer startAge, Integer maxAge, String transactionId) {
         */


        list.stream().forEach((item) -> {

            JobSearch js = new JobSearch();
            Employer emp = item.getPostedBy();
            PostedBy postedBy = JsfUtil.createPostedBy(emp);

            js.setPostedBy(postedBy);
            if (emp != null) {
                js.setFileName(emp.getFileName());
            }

            js.setId(item.getId());
            js.setCategory(findDetails(item.getCategory()));
            js.setCountry(findDetails(item.getCountry()));
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

        return serchlist;

    }

    @GetMapping("/profile-complete-issues")
    public String profileIssues(Model model) {
        return "profile-complete-issues";
    }

    @GetMapping("/subscription-issues")
    public String subscriptionIsseus(Model model) {
        return "subscription-issues";
    }

    private void sendSMS(Users user, HttpServletRequest request) {

        String url = request.getScheme() + "://" + request.getServerName();
        String content = "Your account has been created on xjobs successfully,To activate your account, click the link below:\n" + appUrl + "/activate?token=" + user.getActivationToken();
        JsfUtil.sendSMS(user.getTelephone(), content, restTemplate);

    }

    @GetMapping("/cv-request")
    public String cvRequest(Model model) {
        return "cv-request";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model, Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        Page<Users> ls = userService.findAll(page, max);

        PageWrapper<Users> pages = new PageWrapper<>(ls, "/admin/users");
        model.addAttribute("page", pages);
        List<Users> list = pages.getContent();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("list", list);

        return "admin/users";
    }

    @GetMapping("/admin/users/api")
    @ResponseBody
    public UserApiHeader findUses(Principal principal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer max) {
        Page<Users> pages = userService.findAll(page, max);
        int totalPages = pages.getTotalPages();
        long totalElement = pages.getTotalElements();
        List<Users> list = pages.getContent();

        List<UserApi> lines = convertToApi(list);

        return new UserApiHeader(totalElement, totalPages, max, page, lines);


    }

    private List<UserApi> convertToApi(List<Users> list) {
        return list.stream().map(n ->
                //String fn, String ln, String username, String password, String telphone, String email, String role, Long id
                new UserApi(
                        n.getFullName(),
                        null,
                        n.getUsername(),
                        n.getPassword(),
                        n.getTelephone(),
                        n.getEmail(),
                        null,
                        n.getId()
                )
        ).collect(Collectors.toList());
    }


    @GetMapping("/recruiter/current-user")
    @ResponseBody
    public CurrentUser currentUser(Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        Employee emp = user.getStaffId();
        String fileName = null;
        if (emp != null) {
            fileName = JsfUtil.findfile(emp.getFileName());
        }
        return new CurrentUser(user.getFullName(), fileName, user.getId());
    }

    @PostMapping("/admin/resend-link/{id}")
    @ResponseBody
    public ResponseEntity<String> resendLink(@PathVariable Long id, HttpServletRequest request) {
        try {
            Optional<Users> opp = userService.findById(id);
            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid UserId provided: " + id);
            }
            Users user = opp.get();
            sendEmail(request, user);
            String tel = user.getTelephone();
            if (tel != null) {
                sendSMS(user, request);
            }
            return new ResponseEntity("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
