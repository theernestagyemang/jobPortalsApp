/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.DbFile;
import com.debusey.smart.pos.smartpos.entity.CareerGuidance;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.CareerGuidanceService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */

@Controller
public class CareerController {

    private static final String UPLOADED_FILE_SESSION = "wk-file-upload";
    @Autowired
    private CareerGuidanceService service;

    @GetMapping("/work-shop")
    public String workshop(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        if (user != null) {
            Employee emp = user.getStaffId();
            model.addAttribute("emp", emp);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("emp", new Employee());
        }

        List<CareerGuidance> list = service.findAll();
        model.addAttribute("list", list);


        return "work-shop";
    }

    @GetMapping("/create-work-shop")
    public String addWorkshop(Model model, Principal principal, @RequestParam(defaultValue = "0") Integer id) {
        Users user = JsfUtil.findOnline(principal);
        if (user != null) {
            Employee emp = user.getStaffId();
            model.addAttribute("emp", emp);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("emp", new Employee());
        }

        CareerGuidance guidance = service.findById(id).orElse(new CareerGuidance());
        model.addAttribute("wk", guidance);


        return "create-work-shop";
    }

    @PostMapping("/work-shop")
    @ResponseBody
    public String postWorkShop(Principal principal, Integer id, String caption, String eventDate, String message, String title, HttpSession session) {

        CareerGuidance wk = service.findById(id).orElse(new CareerGuidance());
        wk.setCaption(caption);
        wk.setEntryDate(new Date());
        wk.setEnventDate(JsfUtil.convertToDate(eventDate));
        wk.setMessage(message);
        wk.setTitle(title);

        DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);

        if (dbfile != null) {
            wk.setFileName(dbfile.getFileName());
            wk.setFileType(dbfile.getFileType());
            JsfUtil.saveToDisk(dbfile);
        }

        return service.save(wk) ? "SUCCESS" : "FAILED";

    }

    @RequestMapping(value = "/create-work-shop", method = RequestMethod.POST)
    public ModelAndView postRecJob(ModelAndView modelAndView, Principal principal, Integer id, String caption, String eventDate, String message, String title, HttpSession session) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
        modelAndView.addObject("user", user);
        Employee recruiter = user.getStaffId();
        modelAndView.addObject("emp", recruiter);

        CareerGuidance wk = service.findById(id).orElse(new CareerGuidance());
        wk.setCaption(caption);
        wk.setEntryDate(new Date());
        wk.setEnventDate(JsfUtil.convertToDate(eventDate));
        wk.setMessage(message);
        wk.setTitle(title);

        DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);

        if (dbfile != null) {
            wk.setFileName(dbfile.getFileName());
            wk.setFileType(dbfile.getFileType());
            JsfUtil.saveToDisk(dbfile);
        }
        modelAndView.addObject("wk", wk);
        if (service.save(wk)) {
            modelAndView.addObject("msg_success", "Article created Successully");
        } else {
            modelAndView.addObject("errorMessage", "Could not save....");
        }

        modelAndView.setViewName("create-work-shop");

        return modelAndView;

    }

    @GetMapping("/findEvent/{id}")
    @ResponseBody
    public CareerGuidance findAdvert(@PathVariable Integer id) {
        return service.findById(id).orElse(new CareerGuidance());
    }


    @GetMapping("/deleteEvent/{id}")
    @ResponseBody
    public boolean deleteAdvert(@PathVariable Integer id) {
        Optional<CareerGuidance> oppAdd = service.findById(id);

        if (oppAdd.isPresent()) {
            CareerGuidance add = oppAdd.get();
            String fileName = add.getFileName();
            JsfUtil.deleteFromDisk(fileName);

            return service.delete(add);

        }

        return false;
    }

    @PostMapping("/upload-wk-file")
    @ResponseBody
    public void fileUpload(@RequestParam("file") MultipartFile files, Principal principal, Model model, HttpSession session) {

        if (files != null) {
            try {

                DbFile seeker = new DbFile();

                Long name = new Date().getTime();

                String fileName = name + "_" + files.getOriginalFilename();

                seeker.setFileName(fileName);
                String contentType = files.getContentType();
                byte[] file = files.getBytes();
                seeker.setFileType(contentType);

                DbFile dbFile = new DbFile(fileName, contentType, file);

                session.setAttribute(UPLOADED_FILE_SESSION, dbFile);


            } catch (IOException ex) {
                Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @GetMapping("/article")
    public String article(Model model, Integer rt) {
        CareerGuidance wk = service.findById(rt).orElse(new CareerGuidance());
        model.addAttribute("wk", wk);

        return "article";
    }
}
