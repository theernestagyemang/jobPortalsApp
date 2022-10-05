/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.PageWrapper;
import com.debusey.smart.pos.smartpos.db.ReportProblemResponse;
import com.debusey.smart.pos.smartpos.entity.ReportProblem;
import com.debusey.smart.pos.smartpos.service.ReportProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author AlhassanHussein
 */

@Controller
public class ReportProblemController {

    @Autowired
    private ReportProblemService service;


    @GetMapping("/admin/reported-problems")
    public String reportedIssues(Model model, Principal principal, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") Integer max) {

        Page<ReportProblem> ls = service.findAll(page, max);
        List<ReportProblem> list = ls.getContent();

        PageWrapper<ReportProblem> pages = new PageWrapper<>(ls, "/admin/reported-problems");
        model.addAttribute("page", pages);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("max", max);
        model.addAttribute("joblist", list);

        model.addAttribute("list", list);

        return "admin/reported-problems";
    }


    @PostMapping("/report-problem")
    @ResponseBody
    public ResponseEntity<String> reportProbl(@RequestBody ReportProblemResponse p) {
        try {
            ReportProblem prob = new ReportProblem();
            prob.setEmail(p.getEmail());
            prob.setEntryDate(LocalDate.now());
            prob.setFullName(p.getFullName());
            prob.setMessage(p.getMessage());
            prob.setResponded(false);
            prob.setSubject(p.getSubject());
            prob.setTelephone(p.getTelephone());
            prob.setTransactionId(JsfUtil.generateSerial());
            prob.setTransactionDate(LocalDateTime.now());

            String status = service.save(prob) ? "SUCCESS" : "FAILED";

            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/delete-problem/{id}")
    @ResponseBody
    public boolean delete(@PathVariable Integer id) {
        return service.deleteById(id);
    }

    @GetMapping("/admin/find-reported-problem/{id}")
    @ResponseBody
    public ReportProblemResponse findProblemResponse(@PathVariable Integer id) {
        Optional<ReportProblem> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id prodived: " + id);
        }

        return converted(opp.get());
    }

    private ReportProblemResponse converted(ReportProblem p) {
        return new ReportProblemResponse(p.getId(), p.getTelephone(),
                p.getFullName(),
                p.getEmail(),
                p.getMessage(),
                p.getSubject(),
                JsfUtil.convertToString(p.getEntryDate(), "dd/MM/yyyy"));
    }

}
