package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.HostelBooking;
import com.ao.schoolerp.services.HostelBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

public class HostelBookingController {
    private HostelBookingService hostelBookingService;

    public HostelBookingController(HostelBookingService hostelBookingService) {
        this.hostelBookingService = hostelBookingService;
    }

    @GetMapping("/hostel-booking")
    public String viewHostelBooking (Model model, Principal principal){

        List<HostelBooking> hostelBookingList = hostelBookingService.findAll();
        model.addAttribute("hostelBookings", hostelBookingList);
        return "hostel/hostel-booking";
    }

    @PostMapping("/create-hostel-booking")
    @ResponseBody
    public ResponseEntity addHostelBooking (@RequestBody HostelBooking hostelBooking){
//       if(hostelBookingService.save(hostel)  return "redirect: /hostels" : return "re" ;
        boolean status = hostelBookingService.save(hostelBooking);
        if(status){
            return ResponseEntity.ok().body("SUCCESS");
        }else {
            return ResponseEntity.ok().body("FAILED");
        }
    }

    @GetMapping("/hostel-booking/{id}")
    @ResponseBody
    public HostelBooking findById(@PathVariable Integer id, Model model){
        return hostelBookingService.findById(id).get();
    }
}
