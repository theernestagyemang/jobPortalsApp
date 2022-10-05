/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.CourseCategoryResponse;
import com.debusey.smart.pos.smartpos.db.DbFile;
import com.debusey.smart.pos.smartpos.db.FileStorageException;
import com.debusey.smart.pos.smartpos.entity.CourseCategory;
import com.debusey.smart.pos.smartpos.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class CourseCategoryController {
    private static final String UPLOADED_FILE_SESSION = "notice-file-upload";
    @Autowired
    private CourseCategoryService service;

    @GetMapping("/recruiter/course-category")
    public String courseCategory(Model model) {
        return "recruiter/course-category";
    }


    @GetMapping("/recruiter/course-category/api")
    @ResponseBody
    public List<CourseCategory> coursesList() {
        return service.findAll();
    }

    @GetMapping("/recruiter/findCourseCategory/{id}")
    @ResponseBody
    public CourseCategory findCourse(@PathVariable Integer id) {
        Optional<CourseCategory> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        return opp.get();
    }

    @GetMapping("/course-categories")
    @ResponseBody
    public List<CourseCategoryResponse> categories() {
        List<CourseCategory> list = service.findAll();
        return converted(list);
    }

    @PostMapping("/recruiter/courseCategory")
    @ResponseBody
    public ResponseEntity<String> addCourse(@RequestParam(value = "picture", required = false) MultipartFile picture, Principal principal, String category, String description) {
        try {
            CourseCategory co = service.findByName(category).orElse(new CourseCategory());
            co.setDescription(description);
            co.setStatus("Active");
            co.setName(category);
            co.setTransactionId(JsfUtil.generateSerial());

            String fileName = null;
            if (picture != null) {
                JsfUtil.deleteFromDisk(co.getFileName());
                fileName = JsfUtil.createFileName(picture).getFileName();
                if (fileName != null) {
                    co.setFileName(fileName);
                }
            }
            String status = service.save(co) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void clearSession(HttpSession session) {
        session.removeAttribute(UPLOADED_FILE_SESSION);
    }


    @GetMapping("/recruiter/delete-course-category/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            String status = service.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin/upload-category-img")
    @ResponseBody
    public void uploadPartner(@RequestParam("file") MultipartFile[] files, HttpSession session) {
        try {

            DbFile dbFile = null;
            for (MultipartFile file : files) {
                String fileName2 = StringUtils.cleanPath(file.getOriginalFilename());

                if (fileName2.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName2);
                }
                String fileName = JsfUtil.renameFile(fileName2);
                dbFile = new DbFile(fileName, file.getContentType(), file.getBytes());
            }

            //DbFile db = createDbFile(files);
            session.setAttribute(UPLOADED_FILE_SESSION, dbFile);


        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    private List<CourseCategoryResponse> converted(List<CourseCategory> list) {
        return list.stream().map(n ->
                new CourseCategoryResponse(
                        n.getId(),
                        n.getName()
                )
        ).collect(Collectors.toList());
    }


}
