package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.AssessmentLinesService;
import com.debusey.smart.pos.smartpos.service.AssessmentQuestionService;
import com.debusey.smart.pos.smartpos.service.AssessmentTransactionService;
import com.debusey.smart.pos.smartpos.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class AssessmentLineController {
    @Autowired
    private AssessmentLinesService service;

    @Autowired
    private AssessmentQuestionService assessmentQuestionService;

    @Autowired
    private CompanyService cservice;

    @Autowired
    private AssessmentTransactionService assessmentTransactionService;

    @GetMapping("/assessment")
    public String assessment() {

        return "assessment";
    }

    public List<AssessmentQuestion> findByLines(AssessmentLines assessmentLines) {
        try {
            return assessmentQuestionService.findQuestionsByAssessmentLines(assessmentLines);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/admin/assessment")
    public String adminAssesmet(Model model, Principal principal) {

        return "admin/assessment";
    }

    @PostMapping("/recruiter/assessment-line")
    @ResponseBody
    public ResponseEntity<String> addSchedule(@RequestBody AssessmentLines assessmentLines, Principal principal) {

        try {
            Users user = JsfUtil.findOnline(principal);
            assessmentLines.setEntryDate(LocalDate.now());
            assessmentLines.setTransactionId(JsfUtil.generateSerial());
            String status = service.save(assessmentLines) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = {"/admin/assessment/api", "/assessment/api"})
    @ResponseBody
    public List<AssessmentLines> apiAssessment() {
        List<AssessmentLines> assessmentLines = service.findAll();
        return assessmentLines;
    }

    @RequestMapping(value = {"/admin/assessment-active/api", "/assessment-active/api"})
    @ResponseBody
    public List<AssessmentLines> getActiveAssessment() {
        List<AssessmentLines> assessmentLines = service.findAllActive(true);
        return assessmentLines;
    }

    @GetMapping("/assessment-details/{id}")
    public String getCourse(@PathVariable Integer id, Model model, Principal principal) {
//        Optional<Course> opp = service.findById(id);
        AssessmentLines assessmentLines = service.findById(id).orElse(null);
        if (principal != null) {
            Users user = JsfUtil.findOnline(principal);

            Assessment currentAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                    user,assessmentLines,true
            );

            if(currentAssessment != null){
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }

        model.addAttribute("assessment", assessmentLines);
        return "assessment-details";
    }

    @GetMapping("/recruiter/delete-assessment/{id}")
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

    @GetMapping("/recruiter/find-assessment/{id}")
    @ResponseBody
    public AssessmentLines findById(@PathVariable Integer id) {
        try {
            return service.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
