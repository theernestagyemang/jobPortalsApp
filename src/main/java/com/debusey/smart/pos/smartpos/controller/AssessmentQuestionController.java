package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.AssessmentQuestionResponse;
import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.AssessmentLinesService;
import com.debusey.smart.pos.smartpos.service.AssessmentQuestionService;
import com.debusey.smart.pos.smartpos.service.AssessmentRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AssessmentQuestionController implements Serializable {
    @Autowired
    public AssessmentQuestionService assessmentQuestionService;

    @Autowired
    public AssessmentLinesService assessmentLinesService;

    @Autowired
    private AssessmentRoundService assessmentRoundService;

    @GetMapping("/admin/assessment-question")
    public String getAssessmentQuestion() {
        return "admin/assessment-question";
    }

    @GetMapping("/recruiter/assessment-questions")
    @ResponseBody
    public List<AssessmentQuestionResponse> findAll() {
        List<AssessmentQuestion> list = assessmentQuestionService.findAll();
        List<AssessmentQuestionResponse> assessmentQuestionResponses = new ArrayList<>();

        if(list.size() != 0){
            for (int i = 0; i < list.size(); i++) {
                AssessmentQuestionResponse data = new AssessmentQuestionResponse(
                        list.get(i).getId(),
                        list.get(i).getQuestion(),
                        list.get(i).getAnswer(),
                        list.get(i).getAssessmentRound().getId().toString(),
                        list.get(i).getAssessmentLines().getName(),
                        list.get(i).getDisplayOrder()
                );
                assessmentQuestionResponses.add(data);
            }
        }

//       System.out.println(assessmentQuestionResponses);
        return assessmentQuestionResponses;
    }

    @GetMapping("/recruiter/assess-questions/{id}")
    @ResponseBody
    public List<AssessmentQuestion> findQuestionsByAssessmentLine(@PathVariable Integer id) {
        AssessmentLines assessmentLines = assessmentLinesService.findById(id).orElse(null);
        return assessmentQuestionService.findQuestionsByAssessmentLines(assessmentLines);
    }

    @GetMapping("/recruiter/assess-questions/details/{id}")
    @ResponseBody
    public AssessmentQuestion findQuestionById(@PathVariable Integer id) {
        return assessmentQuestionService.findById(id);
    }

    @GetMapping("/recruiter/delete-one/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            String status = assessmentQuestionService.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recruiter/assess-question")
    @ResponseBody
    public Integer createAssessmentQuestion(
            @RequestBody AssessmentQuestionResponse assessmentQuestionResponse, Principal principal) {
        Users user = JsfUtil.findOnline(principal);
        AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
        AssessmentLines assessmentLine = assessmentLinesService.findById(
                Integer.parseInt(assessmentQuestionResponse.getAssessmentLineId())).orElse(new AssessmentLines());
        AssessmentRound assessmentRound = assessmentRoundService.findById(
                Integer.parseInt(assessmentQuestionResponse.getAssessmentRoundId()));
        assessmentQuestion.setQuestion(assessmentQuestionResponse.getQuestion());
        assessmentQuestion.setAnswer(assessmentQuestionResponse.getAnswer());
        assessmentQuestion.setEnteredBy(user.getUsername());
        assessmentQuestion.setAssessmentLines(assessmentLine);
        assessmentQuestion.setAssessmentRound(assessmentRound);
        assessmentQuestion.setUsers(user);
        assessmentQuestion.setDisplayOrder(assessmentQuestionResponse.getDisplayOrder());
        AssessmentQuestion result = assessmentQuestionService.save(assessmentQuestion);
//        System.out.println(result.getId());
        return result.getId();

    }


}
