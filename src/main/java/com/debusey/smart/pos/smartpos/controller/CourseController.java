/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.CourseReponse;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class CourseController {
    @Autowired
    private CourseService service;

    @Autowired
    private CourseCategoryService categoryService;

    @Autowired
    private CourseContentService courseContentService;

    @Autowired
    private CourseVideoService courseVideoService;

    @Autowired
    private UserTakeQuizService userTakeQuizService;

    @Autowired
    private CourseTransactionService courseTransactionService;

    @GetMapping("/recruiter/courses")
    public String courses(Model model) {
        return "recruiter/courses";
    }

    @GetMapping("/recruiter/courses/api")
    @ResponseBody
    public List<CourseReponse> coursesList() {
        List<Course> list = service.findAll();
        return converted(list);
    }


    @GetMapping("/all-courses")
    public String getCourses(Model model) {
        List<Course> courses = service.findAllByStatus("Active");
        model.addAttribute("courses", courses);
        return "all-courses";
    }


    @GetMapping("/recruiter/findCourse/{id}")
    @ResponseBody
    public Course findCourse(@PathVariable Integer id) {
        Optional<Course> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        return opp.get();
    }

    @GetMapping("/course-details/{id}")
    public String getCourse(@PathVariable Integer id, Model model, Principal principal) {
        Optional<Course> opp = service.findById(id);

        if (principal != null) {
            Users user = JsfUtil.findOnline(principal);
            CourseTransaction trainingTransaction = courseTransactionService.findByUserAndCourse(
                    user,opp.get()
            );

            if(trainingTransaction != null){
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        model.addAttribute("course", opp.get());
        return "course-details";
    }

    @GetMapping("/seeker/course-detail/{id}")
    public String getJobSeekerCourse(@PathVariable Integer id, Model model, Principal principal) {
        Optional<Course> opp = service.findById(id);
        Users user = JsfUtil.findOnline(principal);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        Course course = opp.get();

        //retrieve course video
        List<CourseVideo> courseVideo = courseVideoService.findByCourse(course);

        //retrieve course content
        List<CourseContent> courseContents = courseContentService.findByCourse(course);

        CourseTransaction courseTransaction = courseTransactionService.findByUserAndCourse(user,course);
        UserTakeCourseQuiz userTakeCourseQuiz = userTakeQuizService.findByUserAndCourse(user,course);


        model.addAttribute("course", course);
        model.addAttribute("transaction", courseTransaction);
        model.addAttribute("quiz", userTakeCourseQuiz);
        model.addAttribute("user", user);

        if(courseVideo.size() != 0 && courseContents.size() != 0 ){
            model.addAttribute("videoFirst", courseVideo.get(0));
            model.addAttribute("courseContents", courseContents);
            model.addAttribute("courseVideos", courseVideo);

        }else {
            model.addAttribute("courseVideos", null);
            model.addAttribute("courseContents", null);
        }

        return "seeker/course-contents";
    }

    @GetMapping("/seeker/course-detail/api/{id}")
    @ResponseBody
    public List<CourseVideo> getJobSeekerCourseVideo(@PathVariable Integer id, Model model, Principal principal) {
        Optional<Course> opp = service.findById(id);

        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        Course course = opp.get();

        //retrieve course video
        List<CourseVideo> courseVideo = courseVideoService.findByCourse(course);

        return courseVideo;
    }


    @PostMapping("/recruiter/courses")
    @ResponseBody
    public ResponseEntity<String> addCourse(@RequestParam(value = "picture", required = false) MultipartFile picture, String id,
                                            String courseTitle, String courseCategory, String description, String courseStatus,
                                            boolean featured, Double price,
                                            Principal principal) {

        try {
            Integer categoryId = Integer.parseInt(courseCategory);
            Optional<CourseCategory> opp = categoryService.findById(categoryId);

            if (!opp.isPresent()) {
                throw new BeanNotFoundException("Invalid Id");
            }
            CourseCategory category = opp.get();
            Users user = JsfUtil.findOnline(principal);
            Course co = service.findById(Integer.parseInt(id)).orElse(new Course());
            co.setCourseTitle(courseTitle);
            co.setEnteredBy(user.getUsername());
            co.setStatus(courseStatus);
            co.setDescription(description);
            co.setFeaturedCourse(featured);
            co.setCategory(category.getName());
            co.setPrice(price);
            co.setTransactionId(JsfUtil.generateSerial());
            co.setCourseCategory(category);

            String fileName = null;
            if (picture != null) {
                JsfUtil.deleteFromDisk(co.getPicture());
                fileName = JsfUtil.createFileName(picture).getFileName();
                if (fileName != null) {
                    co.setPicture(fileName);
                }
            }

            String status = service.save(co) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (BeanNotFoundException e) {
            e.printStackTrace();
            throw new BeanNotFoundException("Invalid Id");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/recruiter/delete-course/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            String status = service.deleteById(id) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CourseCategory findCategory(Course c) {
        CourseCategory category = c.getCourseCategory();
        if (category == null) {
            return new CourseCategory();
        }
        return category;
    }

    private List<CourseReponse> converted(List<Course> list) {
        return list.stream().map(n ->
                //Integer id, String enteredBy, String courseTitle, String status, String category, Integer courseCategory
                new CourseReponse(
                        n.getId(),
                        n.getEnteredBy(),
                        n.getCourseTitle(),
                        n.getStatus(),
                        findCategory(n).getName(),
                        findCategory(n).getId(),
                        n.getDescription(),
                        n.isFeaturedCourse(),
                        n.getPrice()
                )
        ).collect(Collectors.toList());
    }
}
