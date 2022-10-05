/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.DbFile;
import com.debusey.smart.pos.smartpos.db.ImageUtil;
import com.debusey.smart.pos.smartpos.entity.Adverts;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.AdvertService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.debusey.smart.pos.smartpos.JsfUtil.DOCUMENTS;

/**
 * @author Admin
 */

@Controller
public class AdvertController {
    private static final String UPLOADED_FILE_SESSION = "notice-file-upload";
    @Autowired
    private AdvertService service;

    @GetMapping("/adverts")
    public String adverts(Model model, Principal principal) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();

        if (user != null) {
            Employee employee = user.getStaffId();
            model.addAttribute("emp", employee);
        } else {
            model.addAttribute("emp", new Employee());
        }


        model.addAttribute("imgUtil", new ImageUtil());

        List<Adverts> list = service.findAll();
        model.addAttribute("list", list);

        return "adverts";
    }


    private void saveToDisk(DbFile dbfile) {
        try {


            File file = new File(DOCUMENTS);
            if (file.exists()) {
                file.createNewFile();
            }

            //Path currentPath = Paths.get("."); // resources folder
            //Path absolutePath = currentPath.toAbsolutePath();

            String fileName = dbfile.getFileName();
            Path path = Paths.get(DOCUMENTS + "/advert_uploads/" + fileName);
            //Path path = Paths.get(DOCUMENTS+"/"+fileName);


            BufferedImage bi = JsfUtil.simpleResizeImage(dbfile.getUploadedFile(), 140, 140);
            byte[] uploadedFile = JsfUtil.convertToByte(bi);

            Files.write(path, uploadedFile);

        } catch (IOException ex) {
            Logger.getLogger(AdvertController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdvertController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @PostMapping("/add-adverts")
    @ResponseBody
    public String addNotice(String client, String message, String loc, String extUrl, Integer catId, Principal principal, HttpSession session) {
        try {


            Adverts notices = null;

            Optional<Adverts> oppCat = service.findById(catId);
            if (oppCat.isPresent()) {
                notices = oppCat.get();
                deleteFromDisk(notices.getFileName());
            } else {
                notices = new Adverts();
                List<Adverts> add = service.findByPageLocation(loc);
                if (add != null) {
                    if (!add.isEmpty()) {
                        return "ALREADY-EXIST";
                    }
                }
            }


            DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);


            if (dbfile != null) {

                notices.setFileName(dbfile.getFileName());
                notices.setFileType(dbfile.getFileType());
                //ufferedImage bi = JsfUtil.simpleResizeImage(dbfile.getUploadedFile(), 728, 90);
                JsfUtil.saveToDisk(dbfile);
            }

            notices.setClient(client);
            notices.setEntryDate(new Date());
            notices.setStatus(true);
            notices.setPageLocation(loc);
            notices.setExternalLink(extUrl);

            return service.save(notices) ? "SUCCESS" : "FAILED";

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearSession(session);
        }

        return "FAILED";
    }


    private void clearSession(HttpSession session) {
        session.removeAttribute(UPLOADED_FILE_SESSION);
    }

    @GetMapping("/findAdvert/{id}")
    @ResponseBody
    public Adverts findAdvert(@PathVariable Integer id) {
        return service.findById(id).orElse(new Adverts());
    }


    @GetMapping("/deleteAdvert/{id}")
    @ResponseBody
    public boolean deleteAdvert(@PathVariable Integer id) {
        Optional<Adverts> oppAdd = service.findById(id);

        if (oppAdd.isPresent()) {
            Adverts add = oppAdd.get();
            String fileName = add.getFileName();

            JsfUtil.deleteFromDisk(fileName);

            return service.delete(add);

        }

        return false;
    }


    @PostMapping("/advert-upload")
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

                //JsfUtil.saveToDisk(dbFile);

                //seeker.setCv(files.getBytes());

            } catch (IOException ex) {
                Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    private boolean deleteFromDisk(String fileName) {
        try {

            Path path = Paths.get(DOCUMENTS + "/advert_uploads/" + fileName);

            File file = path.toFile();
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception io) {
            io.printStackTrace();
        }
        return false;
    }


}
