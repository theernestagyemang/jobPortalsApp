package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.CourseQuizHelper;

import com.debusey.smart.pos.smartpos.db.UserPassTestResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserTakeQuizController {
    @Autowired
    private UserTakeQuizService userTakeQuizService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseTransactionService courseTransactionService;

    @Autowired
    private CourseQuizService courseQuizService;

    @Autowired
    private QuizOptionService quizOptionService;


    @PostMapping("/seeker/course-quiz")
    public String createQuizTest(
            @RequestBody UserPassTestResponse userPassTestResponse, Principal principal) {

        try {
            Users user = JsfUtil.findOnline(principal);
            Course course = courseService.findById(Integer.parseInt(userPassTestResponse.getAssessmentLines())).get();

            UserTakeCourseQuiz userTakeCourseQuiz = new UserTakeCourseQuiz();

            userTakeCourseQuiz.setCourse(course);
            userTakeCourseQuiz.setUsers(user);
            userTakeCourseQuiz.setScore(userPassTestResponse.getScore());
            userTakeCourseQuiz.setOutOf(userPassTestResponse.getOutOf());
            userTakeCourseQuiz.setTestDate(userPassTestResponse.getTestDate());
            userTakeCourseQuiz.setTransactionId(JsfUtil.generateSerial());

            userTakeQuizService.addUserTakeQuiz(userTakeCourseQuiz);

            //mark this quiz as taken
            CourseTransaction courseTransaction = courseTransactionService.findByUserAndCourseAndStatus(
                    user,course
            );
            courseTransaction.setStatus(false);
            courseTransactionService.addCourseTransaction(courseTransaction);

            //redirect user to the assessment result

            return "redirect:/seeker/current-assessment";
//            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/seeker/quiz-exam/{id}")
    public String getQuizExamPage(Principal principal, @PathVariable Integer id, Model model) {
        Users user = JsfUtil.findOnline(principal);
        Course course = courseService.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("course",course);
        return "seeker/course-quiz-page";
    }

    @GetMapping("/seeker/course-exam/api/{id}")
    @ResponseBody
    public List<CourseQuizHelper> getQuizForm(Principal principal, @PathVariable Integer id) {
        try {
            //find current user
            Users user = JsfUtil.findOnline(principal);
            Course course = courseService.findById(id).get();

            CourseTransaction currentTransaction = courseTransactionService.findByUserAndCourseAndStatus(
                    user,course
            );

            List<QuizOption>  multipleChoices = new ArrayList<>();

//            multipleChoices = multipleChoiceService.findAllByAssessment(assessmentLine.getName());
            multipleChoices = quizOptionService.findAllByCourse(course.getCourseTitle());

//            List<MultipleChoiceHelper> tempData = new ArrayList<>();
            List<CourseQuizHelper> tempData = new ArrayList<>();
//            MultipleChoiceHelper helper = new MultipleChoiceHelper();
            CourseQuizHelper helper = new CourseQuizHelper();
            List<String> tempOptions = new ArrayList<>();
            for (int j = 0; j < multipleChoices.size(); j++) {

                if (j == 0) {
                    helper.setId(multipleChoices.get(j).getCourseQuiz().getId());
                    helper.setQuestion(multipleChoices.get(j).getCourseQuiz().getQuestion());
                    helper.setAnswer(multipleChoices.get(j).getCourseQuiz().getAnswer());
//                    helper.setAssessmentRound(multipleChoices.get(j).getAssessmentQuestion().getAssessmentRound());
                    tempOptions.add(multipleChoices.get(j).getQuizOption());
//                    helper.setDisplayOrder(multipleChoices.get(j).getAssessmentQuestion().getDisplayOrder());
                    helper.setCourseId(multipleChoices.get(j).getCourseQuiz().getCourse().getId());
                } else if (multipleChoices.get(j).getCourseQuiz().getQuestion() == helper.getQuestion()) {
//                    helper.setOptions(multipleChoices.get(j).getChoice());
                    tempOptions.add(multipleChoices.get(j).getQuizOption());

                } else {
                    helper.setOptions(tempOptions);
                    tempData.add(helper);
                    helper = new CourseQuizHelper();
                    tempOptions = new ArrayList<>();

                    helper.setId(multipleChoices.get(j).getCourseQuiz().getId());
                    helper.setQuestion(multipleChoices.get(j).getCourseQuiz().getQuestion());
                    helper.setAnswer(multipleChoices.get(j).getCourseQuiz().getAnswer());
                    tempOptions.add(multipleChoices.get(j).getQuizOption());
                    helper.setCourseId(multipleChoices.get(j).getCourseQuiz().getCourse().getId());

                }

                if ((j + 1) == multipleChoices.size()) {
                    helper.setOptions(tempOptions);
                    tempData.add(helper);
                }

            }

            return tempData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
