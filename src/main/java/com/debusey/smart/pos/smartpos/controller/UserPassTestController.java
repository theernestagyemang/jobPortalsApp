package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.MultipleChoiceHelper;
import com.debusey.smart.pos.smartpos.db.UserPassTestResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserPassTestController {
    @Autowired
    private UserPassTestService userPassTestService;

    @Autowired
    private AssessmentTransactionService assessmentTransactionService;

    @Autowired
    private AssessmentLinesService assessmentLineService;

    @Autowired
    private AssessmentQuestionService assessmentQuestionService;

    @Autowired
    private MultipleChoiceService multipleChoiceService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/seeker/assessment-exam/{id}")
    public String getAssessmentExamPage(Principal principal, @PathVariable Integer id, Model model) {
        Users user = JsfUtil.findOnline(principal);
        AssessmentLines assessmentLine = assessmentLineService.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("assessment",assessmentLine);
        return "seeker/assessment-test-page";
    }

    @GetMapping("/seeker/current-assessment")
    public String getCurrentAssessment(Principal principal, Model model) {
        List<AssessmentLines> assessmentLines = new ArrayList<>();
        try {
            Users user = JsfUtil.findOnline(principal);

            if (user != null) {
                List<Assessment> assessmentTransaction = assessmentTransactionService.findByUserAndStatus(user, true);

                assessmentTransaction.forEach(assessment -> {
                    assessmentLines.add(assessment.getAssessmentLines());
                });

                model.addAttribute("assessments", assessmentLines);
            }
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/current-assessment";
    }

    @GetMapping("/seeker/assessment-result/{id}")
    public String getAssessmentResult(@PathVariable Integer id, Principal principal, Model model) {
//        List<AssessmentLines> assessmentLines = new ArrayList<>();
//        UserPassTest userPassTest = new UserPassTest();

        try {
            Users user = JsfUtil.findOnline(principal);
//            AssessmentLines assessmentLines = assessmentLineService.findById(id).orElse(null);


            if (user != null) {
                UserPassTest userPassTest = userPassTestService.findById(id);

                List<Rating> ratings = ratingService.findAll();
                model.addAttribute("assessmentResult", userPassTest);

                List<String> ratingTitles = new ArrayList<>();
                ratings.forEach(rating -> {
                    ratingTitles.add(rating.getTitle());

                    if(userPassTest.getScore() >= rating.getMinScore() && userPassTest.getScore() <= rating.getMaxScore()){

                        model.addAttribute("rating",rating.getTitle());
                    }
                });

                model.addAttribute("ratings",ratings);
            } else {
                UserPassTest userPassTest = new UserPassTest();
                model.addAttribute("assessmentResult", userPassTest);
            }
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/assessment-results";
    }

    @GetMapping("/seeker/assessment-exam/api/{id}")
    @ResponseBody
    public List<MultipleChoiceHelper> getTestForm(Principal principal, @PathVariable Integer id) {
        try {
            //find current user
            Users user = JsfUtil.findOnline(principal);
            AssessmentLines assessmentLine = assessmentLineService.findById(id).orElse(null);

            Assessment assessmentTransaction = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                    user, assessmentLine, true
            );
            //check if the user has a valid assessment subscription
            if(assessmentTransaction != null){
                List<MultipleChoice> multipleChoices = new ArrayList<>();

                multipleChoices = multipleChoiceService.findAllByAssessment(assessmentLine.getName());

                List<MultipleChoiceHelper> tempData = new ArrayList<>();
                MultipleChoiceHelper helper = new MultipleChoiceHelper();
                List<String> tempOptions = new ArrayList<>();
                for (int j = 0; j < multipleChoices.size(); j++) {

                    if (j == 0) {
                        helper.setId(multipleChoices.get(j).getAssessmentQuestion().getId());
                        helper.setQuestion(multipleChoices.get(j).getAssessmentQuestion().getQuestion());
                        helper.setAnswer(multipleChoices.get(j).getAssessmentQuestion().getAnswer());
                        helper.setAssessmentRound(multipleChoices.get(j).getAssessmentQuestion().getAssessmentRound());
                        tempOptions.add(multipleChoices.get(j).getChoice());
                        helper.setDisplayOrder(multipleChoices.get(j).getAssessmentQuestion().getDisplayOrder());
                        helper.setAssessmentLineId(multipleChoices.get(j).getAssessmentQuestion().getAssessmentLines().getId());
                    } else if (multipleChoices.get(j).getAssessmentQuestion().getQuestion() == helper.getQuestion()) {
//                    helper.setOptions(multipleChoices.get(j).getChoice());
                        tempOptions.add(multipleChoices.get(j).getChoice());

                    } else {
                        helper.setOptions(tempOptions);
                        tempData.add(helper);
                        helper = new MultipleChoiceHelper();
                        tempOptions = new ArrayList<>();

                        helper.setId(multipleChoices.get(j).getAssessmentQuestion().getId());
                        helper.setQuestion(multipleChoices.get(j).getAssessmentQuestion().getQuestion());
                        helper.setAnswer(multipleChoices.get(j).getAssessmentQuestion().getAnswer());
                        tempOptions.add(multipleChoices.get(j).getChoice());
                        helper.setAssessmentRound(multipleChoices.get(j).getAssessmentQuestion().getAssessmentRound());
                        helper.setDisplayOrder(multipleChoices.get(j).getAssessmentQuestion().getDisplayOrder());
                        helper.setAssessmentLineId(multipleChoices.get(j).getAssessmentQuestion().getAssessmentLines().getId());

                    }

                    if ((j + 1) == multipleChoices.size()) {
                        helper.setOptions(tempOptions);
                        tempData.add(helper);
                    }

                }

                List<MultipleChoiceHelper> questionsByRound = new ArrayList<>();

                for (int index=0; index<tempData.size(); index++){
                    List<MultipleChoiceHelper> multipleChoiceHelper = new ArrayList<>();
                    if(
                            (tempData.get(index).getAssessmentRound().getRoundPriority() ==
                                    assessmentTransaction.getAssessmentRound().getRoundPriority()) ||
                                    (tempData.get(index).getAssessmentRound().getRoundPriority() == 0)
                    ){
                        questionsByRound.add(tempData.get(index));
                    }
                }

                return questionsByRound;
            }else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/seeker/assessment-exam")
    public String createAssessmentTest(
            @RequestBody UserPassTestResponse userPassTestResponse, Principal principal) {

        try {
            Users user = JsfUtil.findOnline(principal);
            AssessmentLines assessmentLines = assessmentLineService.findById(
                    Integer.parseInt(userPassTestResponse.getAssessmentLines())
            ).orElse(null);
            UserPassTest userPassTest = new UserPassTest();

            Assessment currentAssessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(
                    user,assessmentLines,true
            );
            userPassTest.setAssessmentLines(assessmentLines);
            userPassTest.setUsers(user);
            userPassTest.setScore(userPassTestResponse.getScore());
            userPassTest.setOutOf(userPassTestResponse.getOutOf());
            userPassTest.setTestDate(userPassTestResponse.getTestDate());
            userPassTest.setTransactionId(JsfUtil.generateSerial());
            userPassTest.setRound(currentAssessment.getAssessmentRound().getRoundPriority());
            userPassTest.setDuration(userPassTestResponse.getDuration());

            String status = userPassTestService.addUserPassTest(userPassTest) ? "SUCCESS" : "FAILED";

            //remove this assessment from the current assessment of user
            Assessment assessment = assessmentTransactionService.findByUserAndAssessmentLineAndStatus(user, assessmentLines, true);
            assessment.setStatus(false);
            assessmentTransactionService.addAssessmentTransaction(assessment);

            //redirect user to the assessment result

            return "redirect:/seeker/current-assessment";
//            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
