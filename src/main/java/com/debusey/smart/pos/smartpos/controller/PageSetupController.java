/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.ImageUtil;
import com.debusey.smart.pos.smartpos.entity.Company;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.entity.WebUtils;
import com.debusey.smart.pos.smartpos.service.CompanyService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;

/**
 * @author AlhassanHussein
 */

@Controller
public class PageSetupController {

    @Autowired
    private CompanyService service;

    @PostMapping("/admin/about-us")
    public String addAboutUs(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setAboutUs(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }
            model.addAttribute("aboutUs", company.getAboutUs());
            return "admin/about-us";
        } catch (Exception e) {

            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/about-us";
    }

    @GetMapping("/admin/about-us")
    public String aboutUs(Model model, Principal principal) {

        defaultModel(principal, model);

        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getAboutUs());

        return "admin/about-us";
    }


    @GetMapping("/admin/academy-intro")
    public String academyIntro(Model model, Principal principal) {

        defaultModel(principal, model);

        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getAcademyTitle());

        return "admin/academy-intro";
    }


    @PostMapping("/admin/academy-intro")
    public String academyIntro(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setAcademyTitle(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }

            model.addAttribute("aboutUs", company.getAcademyTitle());

            return "admin/academy-intro";
        } catch (Exception e) {

            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/academy-intro";
    }


    @PostMapping("/admin/assessments")
    public String assessment(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setAssessmentTitle(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }

            model.addAttribute("aboutUs", company.getAssessmentTitle());

            return "admin/assessment";
        } catch (Exception e) {

            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/assessment";
    }


    @GetMapping("/admin/privacy-policy")
    public String privacyPolicy(Model model) {
        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", company.getPrivacyPolicy());

        return "admin/privacy-policy";
    }

    @PostMapping("/admin/privacy-policy")
    public String addPrivacyPolicy(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setPrivacyPolicy(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }
            model.addAttribute("aboutUs", company.getPrivacyPolicy());
            return "admin/privacy-policy";

        } catch (Exception e) {

            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/privacy-policy";
    }


    //======================Carres
    @GetMapping("/admin/careers-with-us")
    public String careersWithUs(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        // Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));

        model.addAttribute("aboutUs", JsfUtil.fetchText("careersWithUs"));

        return "admin/careers-with-us";
    }

    @PostMapping("/admin/careers-with-us")
    public String addCareersWithUs(String file, Model model) {
        try {
            writeToDisk("careersWithUs.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/careers-with-us";

        } catch (Exception e) {

            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/careers-with-us";
    }


    //=======tnc===========================
    @GetMapping("/admin/tnc")
    public String tnc(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));

        model.addAttribute("aboutUs", company.getTnc());
        //model.addAttribute("aboutUs", JsfUtil.fetchText("tnc") );
        return "admin/tnc";
    }

    @PostMapping("/admin/tnc")
    public String addtnc(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setTnc(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }
            model.addAttribute("aboutUs", company.getTnc());
            return "admin/tnc";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/tnc";
    }


    //======Site Map============
    @GetMapping("/admin/site-map")
    public String siteMap(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("siteMap"));
        return "admin/site-map";
    }

    @PostMapping("/admin/site-map")
    public String addsiteMap(String file, Model model) {
        try {
            writeToDisk("siteMap.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/site-map";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/site-map";
    }


    @GetMapping("/admin/faq")
    public String faq(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("faq"));
        return "admin/faq";
    }

    @PostMapping("/admin/faq")
    public String addfaq(String file, Model model) {
        try {
            writeToDisk("faq.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/faq";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/faq";
    }


    @GetMapping("/admin/report-problem")
    public String reportProble(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("reportProble"));

        return "admin/report-problem";
    }

    @PostMapping("/admin/report-problem")
    public String addreportProble(String file, Model model) {
        try {
            writeToDisk("reportProble.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/report-problem";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/report-problem";
    }


    @GetMapping("/admin/resume-writting")
    public String resumeWriting(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
        model.addAttribute("aboutUs", company.getResume());

        return "admin/resume-writting";
    }

    @PostMapping("/admin/resume-writting")
    public String addresumeWriting(String file, Model model) {
        try {
            Company company = service.findByName("L'aine").orElse(new Company("L'aine", false));
            company.setResume(file);
            boolean status = service.save(company);
            if (status) {
                model.addAttribute("msg_success", "File written successfully");
            } else {
                model.addAttribute("errorMessage", "Could not write file to disk..");
            }
            model.addAttribute("aboutUs", company.getResume());
            return "admin/resume-writting";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/resume-writting";
    }


    @GetMapping("/admin/resume-database-access")
    public String resumeDbAccess(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("resumeDbAccess"));

        return "admin/resume-database-access";
    }

    @PostMapping("/admin/resume-database-access")
    public String addresumeDbAccess(String file, Model model) {
        try {
            writeToDisk("resumeDbAccess.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/resume-database-access";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/resume-database-access";
    }


    @GetMapping("/admin/job-posting")
    public String jobPosting(Model model) {
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("aboutUs", JsfUtil.fetchText("jobPosting"));

        return "admin/job-posting";
    }

    @PostMapping("/admin/job-posting")
    public String addjobPosting(String file, Model model) {
        try {
            writeToDisk("jobPosting.txt", file);
            model.addAttribute("msg_success", "File written successfully");

            return "admin/job-posting";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not write file to disk..");
            e.printStackTrace();
        }
        return "admin/job-posting";
    }

    private void writeToDisk(String fileName, String txt) {
        try {
            //String fileName = "aboutUs.text";
            //JsfUtil.deleteFromDisk(fileName);
            File file = new File("uploads/" + fileName);
            // file.createNewFile();
            String path = file.getAbsolutePath();
            System.out.println("Path..." + path);

            try (FileWriter writer = new FileWriter(path, true)) {
                writer.write(txt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void defaultModel(Principal principal, Model model) {
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.userRole(loginedUser);
        Users user = loginedUser.getUser();
        Employee employee = user.getStaffId();

        if (employee == null) {
            employee = new Employee();
        }

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("emp", employee);
    }

}
