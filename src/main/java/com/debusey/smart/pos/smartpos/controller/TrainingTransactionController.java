package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.AssessmentTransactionResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.TrainingScheduleService;
import com.debusey.smart.pos.smartpos.service.TrainingTransactionService;
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
import java.util.Date;
import java.util.List;

@Controller
public class TrainingTransactionController {
    @Autowired
    private TrainingTransactionService trainingTransactionService;

    @Autowired
    private TrainingScheduleService trainingScheduleService;

    @GetMapping("/job-seeker/training-buy/{id}")
    public ResponseEntity<String> createTrainingTransaction(@PathVariable Integer id, Principal principal) {
        try {
            TrainingSchedule trainingSchedule = trainingScheduleService.findById(id).orElse(null);
            Users user = JsfUtil.findOnline(principal);
            if (trainingSchedule != null && user != null) {
                TrainingTransaction trainingTransaction = new TrainingTransaction();
                trainingTransaction.setUsers(user);
                trainingTransaction.setTrainingSchedule(trainingSchedule);
                trainingTransaction.setTransactionId(JsfUtil.generateSerial());
                trainingTransaction.setAmount(trainingSchedule.getTraining_cost());
                trainingTransaction.setStatus(true);
                trainingTransaction.setUserType(user.getUserType());
                trainingTransaction.setEntryDate(new Date());

                String status = trainingTransactionService.addTrainingTransaction(trainingTransaction) ? "SUCCESS" : "FAILED";
                return new ResponseEntity(status, HttpStatus.OK);
            } else {
                return new ResponseEntity("USER OR TRAINING NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/seeker/my-trainings")
    public String getMyTrainings(Principal principal, Model model) {
        List<TrainingSchedule> trainingSchedules = new ArrayList<>();
        try {
            Users user = JsfUtil.findOnline(principal);

            if (user != null) {
                List<TrainingTransaction> trainingTransactions = trainingTransactionService.findByUser(user);

                trainingTransactions.forEach(training -> {
                    trainingSchedules.add(training.getTrainingSchedule());
                });

                model.addAttribute("trainings", trainingSchedules);
            }
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/candidate-trainings";
    }

    @PostMapping("/seeker/training-transaction")
    public String createTrainingTransaction(@RequestBody AssessmentTransactionResponse assessmentTransactionResponse,
                                              Principal principal) {

        TrainingTransaction currentTraining = new TrainingTransaction();
        try {
            Users user = JsfUtil.findOnline(principal);
            TrainingSchedule trainingSchedule = trainingScheduleService.findById(
                    Integer.parseInt(assessmentTransactionResponse.getAssessmentLine())).orElse(
                    null
            );
//
           currentTraining = trainingTransactionService.findByUserAndTrainingAndStatus(
                   user,trainingSchedule
           );

            if(currentTraining == null) {
                    TrainingTransaction trainingTransaction = new TrainingTransaction();
                    trainingTransaction.setUsers(user);
                    trainingTransaction.setTrainingSchedule(trainingSchedule);
                    trainingTransaction.setTransactionId(assessmentTransactionResponse.getTransactionId());
                    trainingTransaction.setAmount(assessmentTransactionResponse.getAmount());
                    trainingTransaction.setParticipants(assessmentTransactionResponse.getId());
                    trainingTransaction.setStatus(true);
                    trainingTransaction.setUserType(user.getUserType());
                    trainingTransaction.setEntryDate(new Date());

                    trainingTransactionService.addTrainingTransaction(trainingTransaction);
                    return "redirect:/seeker/my-trainings";
            }else {
                return "redirect:/seeker/my-trainings";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
