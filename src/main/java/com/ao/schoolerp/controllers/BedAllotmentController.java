package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.BedAllotment;
import com.ao.schoolerp.services.BedAllotmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/library")
public class BedAllotmentController {
    private BedAllotmentService bedAllotmentService;

    public BedAllotmentController(BedAllotmentService bedAllotmentService) {
        this.bedAllotmentService = bedAllotmentService;
    }

    @GetMapping("/bed-allotment")
    public String viewBedAllotment (Model model, Principal principal){

        List<BedAllotment> bedAllotmentList = bedAllotmentService.findAll();
        model.addAttribute("bedAllotments", bedAllotmentList);
        return "hostel/bed-allotment";
    }

    @PostMapping("/create-bed-allotment")
    @ResponseBody
    public ResponseEntity addBedAllotment (@RequestBody BedAllotment bedAllotment){
//       if(bedAllotmentService.save(hostel)  return "redirect: /hostels" : return "re" ;
        boolean status = bedAllotmentService.save(bedAllotment);
        if(status){
            return ResponseEntity.ok().body("SUCCESS");
        }else {
            return ResponseEntity.ok().body("FAILED");
        }
    }

    @GetMapping("/bed-allotment/{id}")
    @ResponseBody
    public BedAllotment findById(@PathVariable Integer id,Model model){
        return bedAllotmentService.findById(id).get();
    }
    
}
