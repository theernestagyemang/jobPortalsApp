package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.CourseQuizResponse;
import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import com.debusey.smart.pos.smartpos.service.CourseQuizService;
import com.debusey.smart.pos.smartpos.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseQuizController {

    @Autowired
    private CourseQuizService courseQuizService;

    @Autowired
    private CourseService courseService;


    @GetMapping("/recruiter/find-course-quiz/{id}")
    @ResponseBody
    public List<CourseQuiz> findAllByCourse(@PathVariable Integer id) {
        try {
            Course course = courseService.findById(id).orElse(null);
            return courseQuizService.findAllByCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/recruiter/courses-quiz")
    @ResponseBody
    public List<CourseQuiz> findAll() {
        return courseQuizService.findAll();
    }

    @GetMapping("/recruiter/courses-quiz/{id}")
    @ResponseBody
    public CourseQuiz findById(@PathVariable Integer id) {
        return courseQuizService.findById(id);
    }

//    public ResponseEntity<String> deleteBYId(@PathVariable Integer id){
//
//    }

    @PostMapping("/recruiter/course-quiz")
    @ResponseBody
    public Integer createCourseQuiz(@RequestBody CourseQuizResponse courseQuizResponse) {

        CourseQuiz courseQuiz = new CourseQuiz();
        courseQuiz.setQuestion(courseQuizResponse.getQuestion());
        courseQuiz.setAnswer(courseQuizResponse.getAnswer());
        courseQuiz.setCourse(courseService.findById(courseQuizResponse.getCourseId()).orElse(null));

        CourseQuiz result = courseQuizService.save(courseQuiz);
//        System.out.println(result.getId());
        return result.getId();

    }

}
