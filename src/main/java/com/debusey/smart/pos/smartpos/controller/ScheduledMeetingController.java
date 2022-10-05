/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.BeanNotFoundException;
import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.Meeting;
import com.debusey.smart.pos.smartpos.db.MeetingJobSeeker;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ScheduledMeeting;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.JobSeekerService;
import com.debusey.smart.pos.smartpos.service.ScheduledMeetinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlhassanHussein
 */

@Controller
public class ScheduledMeetingController {
    @Autowired
    private JobSeekerService seekerService;

    @Autowired
    private ScheduledMeetinService service;

    @GetMapping("/recruiter/shedule-meeting")
    public String sheduledMeeting(Model model) {
        return "recruiter/shedule-meeting";
    }

    @GetMapping("/recruiter/shedule-meeting-api")
    @ResponseBody
    public List<Meeting> allMeeting(Principal principal, @RequestParam(required = false) String limit) {
        List<ScheduledMeeting> list = service.findAll();

        if ("All".equals(limit)) {
            return convertToMeetings(list);
        }
        if (list.size() > 5) {
            list = list.subList(0, 5);
        } else {
            return convertToMeetings(list);
        }

        return convertToMeetings(list);
    }

    @GetMapping("/recruiter/find-event/{trx}")
    @ResponseBody
    public Meeting findMeeting(@PathVariable String trx) {
        Optional<ScheduledMeeting> opp = service.findByTransactionId(trx);
        if (!opp.isPresent()) {
            throw new BeanNotFoundException("Invalid Credentials: " + trx);
        }
        return convertToMeeting(opp.get());
    }

    @PostMapping("/recruiter/shedule-meeting-api")
    @ResponseBody
    public ResponseEntity<String> addMeeting(Principal principal, @RequestBody Meeting meeting) {
        try {
            Users user = JsfUtil.findOnline(principal);
            ScheduledMeeting sh = new ScheduledMeeting();
            sh.setEnteredBy(user.getUsername());
            sh.setEntryDate(LocalDateTime.now());
            sh.setTitle(meeting.getTitle());
            sh.setTransactionId(JsfUtil.generateSerial());
            sh.setWorkStatus("Pending");
            sh.setComment(meeting.getComment());
            sh.setTimeFrom(meeting.getTimeFrom());
            sh.setTimeTo(meeting.getTimeTo());
            sh.setEventDate(JsfUtil.convertToLocalDate(meeting.getEventDate(), "dd/MM/yyyy"));


            String status = service.save(sh) ? "SUCCESS" : "FAILED";
            return new ResponseEntity(status, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recruiter/shedule-meeting-api/{status}")
    @ResponseBody
    public List<Meeting> allMeeting2(Principal principal, @PathVariable String status) {
        List<ScheduledMeeting> list = service.findByWorkStatusLimit(status);
        return convertToMeetings(list);
    }

    private List<Meeting> convertToMeetings(List<ScheduledMeeting> list) {
        return list.stream().map(n ->
                //id,  enteredBy,  entryDate,  title,  timeFrom,  timeTo,  transactionId,  workStatus,  comment
                new Meeting(
                        n.getId(),
                        n.getEnteredBy(),
                        n.getEventDate(),
                        n.getTitle(),
                        n.getTimeFrom(),
                        n.getTimeTo(),
                        n.getTransactionId(),
                        n.getWorkStatus(),
                        n.getComment()
                )
        ).collect(Collectors.toList());

    }

    private Meeting convertToMeeting(ScheduledMeeting n) {
        return new Meeting(
                n.getId(),
                n.getEnteredBy(),
                n.getEventDate(),
                n.getTitle(),
                n.getTimeFrom(),
                n.getTimeTo(),
                n.getTransactionId(),
                n.getWorkStatus(),
                n.getComment()
        );

    }


    private Set<JobSeeker> findJobSeekers(List<MeetingJobSeeker> list) {
        Set<JobSeeker> seekers = new HashSet<>();
        list.stream().forEach((item) -> {
            Optional<JobSeeker> opp = seekerService.findByTransactionId(item.getTranx());
            if (opp.isPresent()) {
                seekers.add(opp.get());
            }
        });
        return seekers;
    }
}
