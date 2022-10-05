/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.*;
import com.debusey.smart.pos.smartpos.entity.Company;
import com.debusey.smart.pos.smartpos.entity.CompanyAddress;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.service.AddressService;
import com.debusey.smart.pos.smartpos.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */

@Controller
public class CompanySetupController {

    private static final String UPLOADED_FILE_SESSION = "file-uploads";
    @Autowired
    private CompanyService service;
    @Autowired
    private AddressService addService;

    @GetMapping("/find-compay")
    public String findCompany(Model model) {

        Optional<Company> company = service.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
        model.addAttribute("company", com);

        return "find-company";
    }

    @GetMapping("/find-company")
    @ResponseBody
    public CompanyDb findCompany2() {


        Company com = service.findByName("L'aine").orElse(null);
        if(com == null){
            service.save(new Company("L'aine",false));
            com = service.findByName("L'aine").orElse(null);
        }

        List<CompanyAddress> address = addService.findByCompany(com);

        CompanyAddress compAddress = address.stream()
                .filter(customer -> customer.getLocation() != null)
                .filter(customer -> customer.getLocation().contains("Adabraka"))
                .findAny()
                .orElse(new CompanyAddress());

        String activatePayment = com.getActivatePayment() ? "Yes" : "No";


        Adabraka adabraka = new Adabraka();
        adabraka.setAddress(compAddress.getAddress());
        adabraka.setEmail(compAddress.getEmail());
        adabraka.setFacebook(compAddress.getFacebook());
        adabraka.setId(compAddress.getId());
        adabraka.setInstagram(compAddress.getInstagram());
        adabraka.setLocation(compAddress.getLocation());
        adabraka.setName("Adabraka");
        adabraka.setPhone(compAddress.getPhone());
        adabraka.setTwitter(compAddress.getTwitter());
        adabraka.setLongitude(compAddress.getLongitude());
        adabraka.setLatitude(compAddress.getLatitude());


        CompanyDb db = new CompanyDb();
        db.setEmail(com.getEmail());
        db.setFacebook(com.getFacebook());
        db.setGoogle(com.getGoogle());
        db.setId(com.getId());
        db.setLatitude(com.getLatitude());
        db.setLinkin(com.getLinkin());
        db.setLongitude(com.getLongitude());
        db.setTwitter(com.getTwitter());
        db.setPhone(com.getPhone());
        db.setName(com.getName());
        db.setAdabraka(adabraka);
        db.setActivatePayment(activatePayment);
        db.setNotificationEmail(com.getNotificationEmail());

        return db;

    }

    @GetMapping("/contact")
    public String contactUs(Model model) {

        Optional<Company> company = service.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));

        List<CompanyAddress> list = addService.findAll();

        model.addAttribute("company", com);
        model.addAttribute("list", list);


        return "contact";
    }

    @GetMapping("/contact-us")
    public String contactUs2(Model model) {

        Optional<Company> company = service.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));

        List<CompanyAddress> list = addService.findAll();

        model.addAttribute("company", com);
        model.addAttribute("list", list);


        return "contact-us";
    }

    @GetMapping("/rec-contact")
    public String recontactUs(Model model) {
        Optional<Company> company = service.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));

        List<CompanyAddress> list = addService.findAll();

        model.addAttribute("company", com);
        model.addAttribute("list", list);

        return "rec-contact";
    }

    @GetMapping("/setup-address")
    public String setupAddress(Model model) {
        Optional<Company> company = service.findByName("L'aine");
        Company com = company.orElse(new Company("L'aine", false));
//        System.out.println("com "+com);

        List<CompanyAddress> list = addService.findAll();

        model.addAttribute("company", com);
        model.addAttribute("list", list);

        return "setup-address";
    }

    @PostMapping("/create-address")
    @ResponseBody
    public String createAddress(String location, String address, String email, String telephone, Integer strId, String caption, Principal principal) {
        try {
            if (!JsfUtil.isStaffUser(principal)) {
                return "INVALID-STAFF";
            }

            CompanyAddress companyAdd = new CompanyAddress();
            Optional<Company> company = service.findByName("L'aine");
            Company com = company.orElse(new Company("L'aine", false));

            Optional<CompanyAddress> oppAdd = addService.findById(strId);
            if (oppAdd.isPresent()) {
                companyAdd = oppAdd.get();
            }


            companyAdd.setAddress(address);
            companyAdd.setCompany(com);
            companyAdd.setEmail(email);
            companyAdd.setLocation(location);
            companyAdd.setPhone(telephone);
            companyAdd.setCaption(caption);

            return addService.save(companyAdd) ? "SUCCESS" : "FAILED";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }


    @GetMapping("/deleteAddress/{id}")
    @ResponseBody
    public boolean deleteAddress(@PathVariable Integer id, Principal principal) {
        try {
            if (JsfUtil.isStaffUser(principal)) {
                return addService.deleteById(id);

            }
        } catch (Exception ex) {
            Logger.getLogger(CompanySetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @PostMapping("/add-social")
    @ResponseBody
    public String addSocial(String fb, String tw, String google, String instag, String linkIn, String longitude, String latitude, Principal principal) {
        try {
            if (!JsfUtil.isStaffUser(principal)) {
                return "INVALID-STAFF";
            }

            Optional<Company> company = service.findByName("L'aine");
            Company com = company.orElse(new Company("L'aine", false));
            com.setFacebook(fb);
            com.setTwitter(tw);
            com.setLinkin(linkIn);
            com.setInstagram(instag);
            com.setGoogle(google);
            com.setLongitude(longitude);
            com.setLatitude(latitude);

            return service.save(com) ? "SUCCESS" : "FAILED";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "FAILED";
    }

    @GetMapping("/findAddress/{id}")
    @ResponseBody
    public CompanyAddress findCompanyAddress(@PathVariable Integer id, Principal principal) {
        try {
            if (!JsfUtil.isStaffUser(principal)) {
                return new CompanyAddress();
            }

            return addService.findById(id).orElse(new CompanyAddress());
        } catch (Exception ex) {
            Logger.getLogger(CompanySetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new CompanyAddress();
    }


    @GetMapping("/find-address")
    @ResponseBody
    public Address findAddress() {

        Optional<Company> comOpp = service.findByName("L'aine");
        if (comOpp.isPresent()) {
            Company company = comOpp.get();


            return new Address(1, company.getPhone(), company.getEmail());
        }

        return new Address(1, "0302-717039 / 0302-249832 / 0302-249833 / 0552588889", "hrconsulting@laineservices", "enquiries@laineservices.com");
    }

    @PostMapping("/upload-settings")
    @ResponseBody
    public void uploadSingleFile(@RequestParam("file") MultipartFile[] files, HttpSession session) {

        try {

            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            DbFile dbFile = null;
            for (MultipartFile file : files) {
                String fn = StringUtils.cleanPath(file.getOriginalFilename());


                if (fn.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fn);
                }
                dbFile = new DbFile(fn, file.getContentType(), file.getBytes());

                if (fn.contains("about")) {
                    company.setAboutUsFileName(fn);
                } else if (fn.contains("privacyPolicy")) {
                    company.setPrivacyPolicyFileName(fn);
                } else if (fn.contains("tnc")) {
                    company.setTncFileName(fn);
                } else if (fn.contains("siteMap")) {
                    company.setSiteMapFileName(fn);
                } else if (fn.contains("faq")) {
                    company.setFaqFileName(fn);
                } else if (fn.contains("reportProblem")) {
                    company.setReportProblemFileName(fn);
                } else if (fn.contains("resume")) {
                    company.setResumeFileName(fn);
                } else if (fn.contains("dbAccess")) {
                    company.setDbaccessFileName(fn);
                }

                JsfUtil.saveToDisk(dbFile);
                service.save(company);
            }

            //DbFile db = createDbFile(files);
            session.setAttribute(UPLOADED_FILE_SESSION, dbFile);


        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    @GetMapping("/settings")
    public String seetings(Model model, Principal principal) {
        return "settings";
    }

    @PostMapping("/setup-company")
    @ResponseBody
    public String setupCompany(String paymentMode, String notificationEmail, Principal principal) {
        try {
            if (!JsfUtil.isStaffUser(principal)) {
                return "INSUFFICIENT-PRIV";
            }
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));

            boolean activate = paymentMode.equalsIgnoreCase("Yes");
            company.setActivatePayment(activate);
            company.setNotificationEmail(notificationEmail);

            return service.save(company) ? "SUCCESS" : "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @GetMapping("/admin/compnay-setup")
    public String companySetup(Model model, Principal principal) {
        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("company", company);

        return "admin/company-setup";
    }

    @PostMapping("/admin/compnay-setup")
    public String addCompanySetup(Model model, Principal principal) {
        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("company", company);

        return "admin/company-setup";
    }
}
