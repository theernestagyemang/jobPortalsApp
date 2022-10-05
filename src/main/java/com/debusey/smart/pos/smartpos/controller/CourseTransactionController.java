package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.AssessmentTransactionResponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.CourseService;
import com.debusey.smart.pos.smartpos.service.CourseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CourseTransactionController {
    @Autowired
    private CourseTransactionService courseTransactionService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/seeker/my-courses")
    public String getMyCourses(Principal principal, Model model) {
        List<Course> courses = new ArrayList<>();
        try {
            Users user = JsfUtil.findOnline(principal);

            if (user != null) {
                List<CourseTransaction> courseTransactions = courseTransactionService.findByUser(user);

                courseTransactions.forEach(course -> {
                    courses.add(course.getCourse());
                });

                model.addAttribute("courses", courses);
            }
            model.addAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "seeker/candidate-courses";
    }

    @PostMapping("/seeker/course-transaction")
    public String createCourseTransaction(@RequestBody AssessmentTransactionResponse assessmentTransactionResponse,
                                            Principal principal) {

//        TrainingTransaction currentTraining = new TrainingTransaction();

        CourseTransaction currentCourse = new CourseTransaction();
        try {
            Users user = JsfUtil.findOnline(principal);
            Course course = courseService.findById(
                    Integer.parseInt(assessmentTransactionResponse.getAssessmentLine())).orElse(
                    null
            );
//
            currentCourse = courseTransactionService.findByUserAndCourseAndStatus(
                    user,course
            );

            if(currentCourse == null) {
                CourseTransaction courseTransaction = new CourseTransaction();

                courseTransaction.setUsers(user);
                courseTransaction.setCourse(course);
                courseTransaction.setTransactionId(assessmentTransactionResponse.getTransactionId());
                courseTransaction.setAmount(assessmentTransactionResponse.getAmount());
                courseTransaction.setStatus(true);
                courseTransaction.setUserType(user.getUserType());
                courseTransaction.setEntryDate(new Date());

                courseTransactionService.addCourseTransaction(courseTransaction);
                return "redirect:/seeker/my-courses";
            }else {
                return "redirect:/seeker/my-courses";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
