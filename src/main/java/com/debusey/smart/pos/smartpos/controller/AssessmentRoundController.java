package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.service.AssessmentRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AssessmentRoundController {
    @Autowired
    private AssessmentRoundService assessmentRoundService;

    @GetMapping("/admin/rounds")
    public String getRounds() {
        return "admin/rounds";
    }

    @PostMapping("/admin/add-round")
    @ResponseBody
    public ResponseEntity<String> addRound(@RequestBody AssessmentRound assessmentRound) {
        try {
            String status = assessmentRoundService.addRound(assessmentRound) ? "SUCCESS" : "FAILED";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/round")
    @ResponseBody
    public List<AssessmentRound> findAll() {
        return assessmentRoundService.findAll();
    }

    @GetMapping("/admin/round/{id}")
    @ResponseBody
    public AssessmentRound findById(@PathVariable Integer id) {
        return assessmentRoundService.findById(id);
    }

    @GetMapping("/admin/delete-round/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        String status = assessmentRoundService.deleteById(id) ? "SUCCESS" : "FAILED";
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
