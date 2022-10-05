package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.HostelCheckIn;
import com.ao.schoolerp.services.HostelCheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hostel")
public class HostelCheckInController {
    private HostelCheckInService hostelCheckInService;

    public HostelCheckInController(HostelCheckInService hostelCheckInService) {
        this.hostelCheckInService = hostelCheckInService;
    }

    @GetMapping("/check-in")
    public String viewHostelCheckIn (Model model, Principal principal){

        List<HostelCheckIn> hostelCheckInList = hostelCheckInService.findAll();
        model.addAttribute("hostelCheckIns", hostelCheckInList);
        return "hostel/check-in";
    }

    @PostMapping("/create-check-in")
    @ResponseBody
    public ResponseEntity addHostelCheckIn (@RequestBody HostelCheckIn hostelCheckIn){
//       if(hostelCheckInService.save(hostel)  return "redirect: /hostels" : return "re" ;
        boolean status = hostelCheckInService.save(hostelCheckIn);
        if(status){
            return ResponseEntity.ok().body("SUCCESS");
        }else {
            return ResponseEntity.ok().body("FAILED");
        }
    }

    @GetMapping("/check-in/{id}")
    @ResponseBody
    public HostelCheckIn findById(@PathVariable Integer id,Model model){
        return hostelCheckInService.findById(id).get();
    }
}
