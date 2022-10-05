package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.MultipleChoiceResponse;
import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import com.debusey.smart.pos.smartpos.entity.MultipleChoice;
import com.debusey.smart.pos.smartpos.service.AssessmentQuestionService;
import com.debusey.smart.pos.smartpos.service.MultipleChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MultipleChoiceController {
    @Autowired
    private MultipleChoiceService multipleChoiceService;

    @Autowired
    private AssessmentQuestionService assessmentQuestionService;

    @GetMapping("/recruiter/multiple-choice")
    public String getChoice() {
        return "admin/multiple-choice";
    }

    @GetMapping("/recruiter/multiple-choices")
    @ResponseBody
    public List<MultipleChoiceResponse> findAll() {
        List<MultipleChoiceResponse> response = new ArrayList<>();
        List<MultipleChoice> multipleChoiceList = multipleChoiceService.findAll();

        for (int i = 0; i < multipleChoiceList.size(); i++) {
            MultipleChoiceResponse tem = new MultipleChoiceResponse(
                    multipleChoiceList.get(i).getId(),
                    multipleChoiceList.get(i).getChoice(),
                    multipleChoiceList.get(i).getAssessmentLine(),
                    multipleChoiceList.get(i).getAssessmentQuestion().getQuestion()
            );
            response.add(tem);
        }

        return response;
    }

    @GetMapping("/recruiter/multiple-choices/{id}")
    @ResponseBody
    public List<MultipleChoice> findAllByAssessment(@PathVariable Integer id) {
        AssessmentQuestion assessmentQuestion = assessmentQuestionService.findById(id);
        return multipleChoiceService.findAllByAssessmentQuestion(assessmentQuestion);
    }

    @PostMapping("/recruiter/multiple-choice")
    @ResponseBody
    public ResponseEntity<String> createMultipleChoiceQuestion(@RequestBody List<MultipleChoiceResponse> multipleChoiceResponses) {
        List<MultipleChoice> multipleChoices = new ArrayList<>();
//        AssessmentQuestion temp = assessmentQuestionService.findById(multipleChoiceResponses.get(0).getAssessmentQuestionId())
        try {
            AssessmentQuestion assessmentQuestion = assessmentQuestionService.findById(
                    Integer.parseInt(multipleChoiceResponses.get(0).getAssessmentQuestionId())
            );

            for (int j = 0; j < multipleChoiceResponses.size(); j++) {
                MultipleChoice temp = new MultipleChoice(multipleChoiceResponses.get(j).getChoice());
                temp.setAssessmentQuestion(assessmentQuestion);
                temp.setAssessmentLine(assessmentQuestion.getAssessmentLines().getName());
                multipleChoices.add(temp);
            }

            String status = multipleChoiceService.saveAll(multipleChoices) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
