/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.AssessmentTransactionResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AlhassanHussein
 */
@Controller
public class AssessmentController {

    @Autowired
    private AssessmentTransactionService assessmentTransactionService;

    @Autowired
    private AssessmentLinesService assessmentLinesService;

    @Autowired
    private UserPassTestService userPassTestService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssessmentRoundService assessmentRoundService;

    @PostMapping("/seeker/assessment-transaction")
    public String createAssessmentTransaction(@RequestBody AssessmentTransactionResponse assessmentTransactionResponse,
                                                              Principal principal) {
        Assessment assessmentTransaction = new Assessment(); // variable to hold data to be stored
        Assessment currentAssessment = new Assessment(); // data fetch from db to test if user has a valid subscription

        try {
            Users user = JsfUtil.findOnline(principal);
            AssessmentLines assessmentLines = assessmentLinesService.findById(
                    Integer.parseInt(assessmentTransactionResponse.getAssessmentLine())).orElse(
                    null
            );
//
            currentAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                    user, assessmentLines, true
            );

            List<AssessmentRound> assessmentRounds = assessmentRoundService.findAll();
//            Integer id = checkUserAssessmentAndRound(assessmentTransaction1, assessmentRounds);

            if (currentAssessment == null){
                //grab all assessment the user has done in that particular package
                List<Assessment> completedAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                        user, assessmentLines, false, user.getUserType()
                );

                //check if we still have another round to go for
                if (completedAssessment.size() < assessmentRounds.size()) {
                    //grab the last round taken and then subscribe to the next one based on priority
                    AssessmentRound assessmentRoundFuture = assessmentRoundService.findByRoundPriority(
                            completedAssessment.size() + 1
                    );
                    assessmentTransaction.setAssessmentLines(assessmentLines);
                    assessmentTransaction.setUsers(user);
                    assessmentTransaction.setUserType(user.getUserType());
                    assessmentTransaction.setStatus(true);
                    assessmentTransaction.setAmount(assessmentTransactionResponse.getAmount());
                    assessmentTransaction.setAssessmentRound(assessmentRoundFuture);
                    assessmentTransaction.setTransactionId(assessmentTransactionResponse.getTransactionId());

                    assessmentTransactionService.addAssessmentTransaction(assessmentTransaction);
                }
                return "redirect:/seeker/current-assessment";
            }else {
                return "redirect:/seeker/current-assessment";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer checkUserAssessmentAndRound(Assessment assessment, List<AssessmentRound> assessmentRound) {
        Integer id = 0;

        for (int i = 0; i < assessmentRound.size(); i++) {
            if (assessment.getAssessmentRound().getId() == assessmentRound.get(i).getId()) {
                id = assessmentRound.get(i).getId();
                break;
            }
        }

        return id;
    }

    @GetMapping("/admin/assessment-transactions")
    public List<Assessment> findAll() {
        return assessmentTransactionService.findAll();
    }


    @GetMapping("/seeker/completed-assessments")
    public String findByUserAndStatus(Principal principal, Model model) {
        List<UserPassTest> completedAssessment = new ArrayList<>();
        try {
            Users user = JsfUtil.findOnline(principal);
            completedAssessment = userPassTestService.findByUser(user);
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("assessments", completedAssessment);
        return "seeker/completed-assessment";
    }


    public Assessment findById(@PathVariable Integer id) {
        return assessmentTransactionService.findById(id);
    }

}
