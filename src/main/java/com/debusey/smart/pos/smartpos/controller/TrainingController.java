/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.TrainingScheduleResponse;
import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import com.debusey.smart.pos.smartpos.entity.TrainingTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.CourseService;
import com.debusey.smart.pos.smartpos.service.TrainingScheduleService;
import com.debusey.smart.pos.smartpos.service.TrainingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author AlhassanHussein
 */

@Controller
public class TrainingController {

    @Autowired
    public TrainingScheduleService service;

    @Autowired
    private CourseService cservice;

    @Autowired
    private TrainingTransactionService trainingTransactionService;

    @GetMapping("/recruiter/training-schedule")
    public String training(Model model, Principal principal) {
        List<TrainingSchedule> tx = service.findAll();
        model.addAttribute("tx", tx);
        return "recruiter/training-schedule";
    }

    @GetMapping("/all-trainings")
    public String getTrainings(Model model) {
        List<TrainingSchedule> trainings = service.findAllByStatus("Active");
        model.addAttribute("trainings", trainings);
        return "all-trainings";
    }

    @GetMapping("/recruiter/find-training-schedule/{id}")
    @ResponseBody
    public TrainingSchedule findTrainingSchedule(@PathVariable Integer id) {
        Optional<TrainingSchedule> opp = service.findById(id);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided: " + id);
        }
        return opp.get();
    }

    @GetMapping("/seeker/training-schedule/{id}")
    public String trainingLayout(@PathVariable Integer id, Principal principal, Model model) {

        Users user = JsfUtil.findOnline(principal);
        Optional<TrainingSchedule> opp = service.findById(id);
        TrainingTransaction trainingTransaction= trainingTransactionService.findByUserAndTrainingAndStatus(
                user,opp.get()
        );
        model.addAttribute("user",user);
        model.addAttribute("training",trainingTransaction);
        model.addAttribute("transaction",trainingTransaction);

        return "seeker/training-layout";
    }

    @GetMapping("/recruiter/delete-training-schedule/{id}")
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

//    @PostMapping("/recruiter/training-schedule")
//    @ResponseBody
//    public ResponseEntity<String> addSchedule(@RequestBody TrainingScheduleResponse sch,Principal principal){
//        try{
////            Optional<Course> opp =
//            Users user = JsfUtil.findOnline(principal);
//            TrainingSchedule trx = service.findById(sch.getId()).orElse(new TrainingSchedule());
//            trx.setCourse(findCourse(sch.getCourseId()));
//            trx.setEnteredBy(user.getUsername());
//            trx.setEntryDate(LocalDate.now());
//            trx.setCourseScheduled(findCourseName(sch.getCourseId()));
//            //2022-04-21T08:55
//            LocalDate eventDate = JsfUtil.convertToLocalDate(sch.getEventDate(),"yyyy-MM-dd");
//            trx.setEventDate(eventDate);
//
//            trx.setEventDate(eventDate);
//            trx.setYear(eventDate.getYear());
//            trx.setEventMonth(eventDate.getMonth().toString());
//            trx.setLocation(sch.getLocation());
//            trx.setTrainingStatus("Active");
//            trx.setTransactionId(JsfUtil.generateSerial());
//            trx.setTimeTo(sch.getTimeTo());
//            trx.setTimeFrom(sch.getTimeFrom());
//            trx.setDescription(sch.getDescription());
//            trx.setTraining_cost(sch.getTrainingCost());
//
//
//            String status = service.save(trx) ? "SUCCESS" : "FAILED";
//            return new ResponseEntity(status,HttpStatus.OK);
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }

    @PostMapping("/recruiter/training-schedule")
    @ResponseBody
    public ResponseEntity<String> addSchedule(@RequestParam(value = "picture", required = false) MultipartFile picture,
                                              TrainingScheduleResponse sch, Principal principal) {
        try {
            Users user = JsfUtil.findOnline(principal);
            TrainingSchedule trx = service.findById(sch.getId()).orElse(new TrainingSchedule());
//            trx.setCourse(findCourse(sch.getCourseId()));
            trx.setEnteredBy(user.getUsername());
            trx.setEntryDate(LocalDate.now());
            trx.setCourseScheduled(sch.getCourse());
            LocalDate eventDate = JsfUtil.convertToLocalDate(sch.getEventDate(), "yyyy-MM-dd");
            trx.setEventDate(eventDate);
            trx.setEventDate(eventDate);
            trx.setYear(eventDate.getYear());
            trx.setEventMonth(eventDate.getMonth().toString());
            trx.setLocation(sch.getLocation());
            trx.setTrainingStatus(sch.getTrainingStatus());
            trx.setTransactionId(JsfUtil.generateSerial());
            trx.setTimeTo(sch.getTimeTo());
            trx.setTimeFrom(sch.getTimeFrom());
            trx.setDescription(sch.getDescription());
            trx.setTraining_cost(sch.getTrainingCost());

            String fileName = null;
            if (picture != null) {
                JsfUtil.deleteFromDisk(trx.getPicture());
                fileName = JsfUtil.createFileName(picture).getFileName();
                if (fileName != null) {
                    trx.setPicture(fileName);
                }
            }

            String status = service.save(trx) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    private Course findCourse(Integer courseId) {
        Optional<Course> opp = cservice.findById(courseId);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided");
        }
        return opp.get();
    }

    private String findCourseName(Integer courseId) {
        Optional<Course> opp = cservice.findById(courseId);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Id provided");
        }
        return opp.get().getCourseTitle();
    }
}
