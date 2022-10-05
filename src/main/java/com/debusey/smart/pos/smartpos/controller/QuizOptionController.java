package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.QuizOptionResponse;
import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import com.debusey.smart.pos.smartpos.entity.QuizOption;
import com.debusey.smart.pos.smartpos.service.CourseQuizService;
import com.debusey.smart.pos.smartpos.service.QuizOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizOptionController {
    @Autowired
    private QuizOptionService quizOptionService;

    @Autowired
    private CourseQuizService courseQuizService;


    @PostMapping("/recruiter/quiz-option")
    @ResponseBody
    public ResponseEntity<String> createQuizOption(@RequestBody List<QuizOptionResponse> quizOptionList) {
        List<QuizOption> quizOptions = new ArrayList<>();
        try {
            CourseQuiz courseQuiz = courseQuizService.findById(
                    quizOptionList.get(0).getQuizQuestionId()
            );
            String course = courseQuiz.getCourse().getCourseTitle();
            for (int j = 0; j < quizOptionList.size(); j++) {
                QuizOption option = new QuizOption(
                        quizOptionList.get(j).getOption(),
                        course
                );
                option.setCourseQuiz(courseQuiz);
                quizOptions.add(option);
            }
            String status = quizOptionService.saveAll(quizOptions) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
