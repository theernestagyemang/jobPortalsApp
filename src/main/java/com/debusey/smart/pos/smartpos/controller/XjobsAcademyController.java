/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.*;
import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */
@Controller
public class XjobsAcademyController {

    private static final String PATTERN = "dd-MM-yyyy";
    private static final String PATTERN_TIME = "dd-MM-yyyy HH:mm:ss";
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService categoryService;

    @Autowired
    private FacilitatorsService facilitatorsService;

    @Autowired
    private TrainingScheduleService trainingScheduleService;

    @Autowired
    private LearningPlanService learningPlanService;

    @Autowired
    private TrainingTransactionService trainingTransactionService;

    @RequestMapping("/academy")
    public String academy(Model model) {

        model.addAttribute("imgUtil", new ImageUtil());
        return "academy";
    }


    @GetMapping("/learningPlan")
    @ResponseBody
    public List<LearningPlanResponse> learningPlan() {
        List<LearningPlan> list2 = learningPlanService.findAll();
        return convertToPlan(list2);

    }


    @GetMapping("/trainingSchedule")
    @ResponseBody
    public List<TrainingSchedule> trainingSchedule() {
        List<TrainingSchedule> list = trainingScheduleService.findByYear(LocalDateTime.now().getYear());
        return list;
    }

    @GetMapping("/training-schedule-details/{id}")
    public String trainingScheduleDetails(@PathVariable Integer id, Model model, Principal principal) {
        Optional<TrainingSchedule> trainingSchedule = trainingScheduleService.findById(id);

        if (principal != null) {
            Users user = JsfUtil.findOnline(principal);
            TrainingTransaction trainingTransaction = trainingTransactionService.findByUserAndTrainingAndStatus(
                    user,trainingSchedule.get()
            );

            if(trainingTransaction != null){
                model.addAttribute("user", user);
            }else {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        model.addAttribute("trainingSchedule", trainingSchedule.get());
        return "training-details";
    }


    @GetMapping("/trainingScheduleByMonth")
    @ResponseBody
    public List<TrainingSchedule> trainingSchedule(@RequestParam("month") List<String> month) {
        List<TrainingSchedule> list = trainingScheduleService.findByEventMonths(month);

        return list.stream()
                .filter(c -> c != null)
//                 .filter(c -> c.getCourse() != null)
                .collect(Collectors.toList());
    }

    @GetMapping("/facilitators")
    @ResponseBody
    public List<FacilitatorResponse> facilitatorses() {
        List<Facilitators> fc = facilitatorsService.findLimit(3);
        return convertFc(fc);
    }


    @RequestMapping("/recruiter/academy")
    public String academy(Model model, Principal principal) {
        List<CategoryCourse> list = createCategoryLocations();
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("list", list);
        return "recruiter/academy";
    }

    @GetMapping("/courses/api")
    @ResponseBody
    public List<CategoryCourse> createCategoryLocations() {
        List<CategoryCourse> catlist = new ArrayList<>();
        List<CourseCategory> locations = categoryService.findAll();

        locations.stream().forEach((item) -> {
            CategoryCourse cat = new CategoryCourse();
            List<Course> jobs = courseService.findByCourseCategory(item);
            cat.setCategory(item.getName());
            cat.setListOfCourses(convertedCourse(jobs));
            catlist.add(cat);
            cat.setFileName(item.getFileName());
            cat.setDescription(truncateText(item.getDescription()));

        });
        return catlist;
    }

    @GetMapping("/featured-courses/api")
    @ResponseBody
    public List<Course> getFeaturedCourses() {
        List<Course> featuredCourses = courseService.findFeaturedCourses();
//       System.out.println(featuredCourses);
        return featuredCourses;
    }

    public final String truncateText(String txt) {
        if ("".equals(txt) || txt.isEmpty()) {
            return "";
        }
        if (txt.length() > 80) {
            return txt.substring(0, 80) + " ...";
        }
        return txt;
    }

    @GetMapping("/academy/api")
    @ResponseBody
    public AcademyResponse academy() {

        List<Facilitators> facilitators = fetchFacilitators(LocalDate.now());
        List<Course> courses = fechCourses();
        List<TrainingScheduleResponse> training = fetchTrainingSchedule(LocalDate.now().getYear());
        List<LearningPlan> learningPlan = fetchLearningPlance();

        AcademyResponse response = new AcademyResponse(Integer.MIN_VALUE, facilitators, courses, training, learningPlan);
        return response;
    }

    private List<Facilitators> fetchFacilitators(LocalDate now) {
        return facilitatorsService.findAll();
    }


    @GetMapping("/recruiter/training-schedule/api")
    @ResponseBody
    public List<TrainingScheduleResponse> fetchTraining(Principal principal) {

        List<TrainingSchedule> tx = trainingScheduleService.findAll();

        return convertedTS(tx);
    }

    private List<Course> fechCourses() {
        return courseService.findAll();
    }

    private List<TrainingScheduleResponse> fetchTrainingSchedule(int year) {
        List<TrainingSchedule> list = trainingScheduleService.findAll();
        return convertedTS(list);
    }

    //convertToLocalDate
    private List<TrainingScheduleResponse> convertedTS(List<TrainingSchedule> list) {
        return list.stream().map(n ->
                        //Integer id, String enteredBy, Integer courseId, String location, String eventDate, String entryDate, String trainingStatus
                        new TrainingScheduleResponse(
                                n.getId(),
                                n.getEnteredBy(),
//                        findCourseId(n.getCourse()),
                                n.getLocation(),
                                JsfUtil.convertLocalDateToString(n.getEventDate(), PATTERN),
                                JsfUtil.convertLocalDateToString(n.getEntryDate(), PATTERN),
                                n.getTrainingStatus(),
                                n.getTransactionId(),
                                findMonth(n),
                                findYear(n),
                                n.getCourseScheduled(),
                                n.getTimeFrom(),
                                n.getTimeTo(),
                                n.getTraining_cost(),
                                n.getDescription()
                        )
        ).collect(Collectors.toList());
    }


    public String findMonth(TrainingSchedule n) {
        LocalDate eventDate = n.getEventDate();
        if (eventDate != null) {
            return eventDate.getMonth().toString();
        }
        return "";
    }

    public Integer findYear(TrainingSchedule n) {
        LocalDate eventDate = n.getEventDate();
        if (eventDate != null) {
            return eventDate.getYear();
        }
        return null;
    }


    public Integer findCourseId(Course c) {
        if (c == null) {
            return 0;
        }
        return c.getId();
    }

    private List<LearningPlan> fetchLearningPlance() {
        return learningPlanService.findAll();
        // return convertLP(list);
    }

    private List<LearningPlanResponse> convertToPlan(List<LearningPlan> list) {
        return list.stream().map(n ->
                //Integer id, String enteredBy, String name, BigDecimal price, String duration, List<String> description, String transactionId
                new LearningPlanResponse(
                        n.getId(),
                        n.getEnteredBy(),
                        n.getName(),
                        n.getPrice(),
                        n.getDuration(),
                        JsfUtil.convertToList(n.getDescription()),
                        n.getTransactionId(),
                        n.getColor(),
                        n.getBtnText()
                )
        ).collect(Collectors.toList());
    }

//    private String findCourse(TrainingSchedule n) {
//        Course courses = n.getCourse();
//        if(courses != null){
//            return courses.getCourseTitle();
//        }
//        return "";
//    }

    private List<FacilitatorResponse> convertFc(List<Facilitators> fc) {
        return fc.stream().map(n ->
                //Integer id, String firstName, String lastName, String contactNumber, String email, String fileName
                new FacilitatorResponse(
                        n.getId(),
                        n.getFirstName(),
                        n.getLastName(),
                        n.getExperience(),
                        n.getContactNumber(),
                        n.getEmail(),
                        fetchPicture(n.getFileName()),
                        n.getTitle(),
                        n.getSkill(),
                        n.getProfile()
                )
        ).collect(Collectors.toList());
    }

    private String fetchPicture(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return "team.jpg";
        }
        return fileName;
    }


    public CourseCategory findCategory(Course c) {
        CourseCategory category = c.getCourseCategory();
        if (category == null) {
            return new CourseCategory();
        }
        return category;
    }

    private List<CourseReponse> convertedCourse(List<Course> list) {
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
