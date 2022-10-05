/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.DbFile;
import com.debusey.smart.pos.smartpos.db.Recruiters;
import com.debusey.smart.pos.smartpos.db.RecruitersHeader;
import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.EmployeeService;
import com.debusey.smart.pos.smartpos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */


@Controller
public class RecruiterController {

    @Autowired
    private UserService service;

    @Autowired
    private EmployeeService empService;

    @GetMapping("/recruiter/team")
    public String team(Model model) {
        return "recruiter/team";
    }

    @GetMapping("/recruiter/profile")
    public String profile(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        Employee staff = user.getStaffId();

        model.addAttribute("user", user);
        model.addAttribute("staff", staff);
        return "recruiter/profile";
    }

    @GetMapping("/recruiter/profile-password")
    public String profilePassword(Model model, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        Employee staff = user.getStaffId();

        model.addAttribute("user", user);
        return "recruiter/profile-password";
    }

    @GetMapping("/recruiter/team-api")
    @ResponseBody
    public RecruitersHeader recruiter(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") Integer max) {
        Page<Users> pages = service.findByUserType("Recruiter", page, max);
        List<Users> list = pages.getContent();

        long totalElement = pages.getTotalElements();
        Integer totalPages = pages.getTotalPages();

        List<Recruiters> lines = converttoLst(list);

        return new RecruitersHeader(totalElement, totalPages, max, page, lines);
    }

    private List<Recruiters> converttoLst(List<Users> list) {
        return list.stream().map(n ->
                //Integer id, String fullName, String telephone, String email, String userType, String entryDate, Integer staffId
                new Recruiters(
                        n.getId(),
                        n.getFullName(),
                        n.getTelephone(),
                        n.getEmail(),
                        n.getUserType(),
                        null,
                        findStaff(n.getStaffId())
                )
        ).collect(Collectors.toList());
    }

    public Integer findStaff(Employee e) {
        if (e == null) {
            return 0;
        }

        return e.getEmployeeid();
    }

    @PostMapping("/recruiter/upload-pic")
    @ResponseBody
    public void uploadEmployeePic(@RequestParam("userPic") MultipartFile files, @RequestParam("id") Integer id) {

        String appendName = String.valueOf((new Date()).getTime());

        Optional<Employee> opp = empService.findById(id);
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
                    JsfUtil.deleteFromDisk(employee.getFileName());
                    employee.setFileName(fileName);
                    employee.setFileType(dbfile.getFileType());
                    JsfUtil.saveToDisk(dbfile);

                    if (empService.save(employee)) {
                    }


                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
