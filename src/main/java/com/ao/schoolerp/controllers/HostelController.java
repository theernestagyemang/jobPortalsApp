package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.services.HostelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hostel")
public class HostelController {
    private HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @GetMapping("/hostel")
    public String hostelIndex(){
        return "hostel/hostel";
    }

    @GetMapping("/dashboard")
    public String viewHostelDashboard (Model model, Principal principal){

        List<Hostel> hostelList = hostelService.findAll();
        model.addAttribute("hostels",hostelList);
        return "hostel/index";
    }
    @GetMapping("/hostels")
    @ResponseBody
    public List<Hostel> getHostels (Model model, Principal principal){
        return  hostelService.findAll();
    }

    @PostMapping("/create-hostel")
    @ResponseBody
    public ResponseEntity addHostel(@RequestBody Hostel hostel){
      boolean status = hostelService.save(hostel);
      if(status){
          return ResponseEntity.ok().body("SUCCESS");
      }else {
          return ResponseEntity.ok().body("FAILED");
      }
    }

    @GetMapping("/hostels/{id}")
    @ResponseBody
    public Hostel findById(@PathVariable Integer id,Model model){
        return hostelService.findById(id).get();
    }

    @GetMapping("/hostel/delete/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable Integer id){
        return hostelService.deleteById(id);
    }
}
