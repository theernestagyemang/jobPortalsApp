package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.HostelBed;
import com.ao.schoolerp.entities.HostelRoom;
import com.ao.schoolerp.services.HostelBedService;
import com.ao.schoolerp.services.HostelRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hostel")
public class HostelBedController {
    private final HostelBedService hostelBedService;
    private final HostelRoomService hostelRoomService;

    public HostelBedController(HostelBedService hostelBedService, HostelRoomService hostelRoomService) {
        this.hostelBedService = hostelBedService;
        this.hostelRoomService = hostelRoomService;
    }

    @GetMapping("/bed")
    public String bed(){
        return "hostel/bed";
    }

    @GetMapping("/beds")
    public String viewHostelBed (Model model, Principal principal){

        List<HostelBed> hostelBedList = hostelBedService.findAll();
        model.addAttribute("hostelBeds", hostelBedList);
        return "hostel/beds";
    }

    @PostMapping("/create-bed")
    @ResponseBody
    public ResponseEntity addHostelBed(@RequestBody HostelBed hostelBed){
//       if(hostelBedService.save(hostel)  return "redirect: /hostels" : return "re" ;
        Optional<HostelRoom> hostelRoom = hostelRoomService.findById(hostelBed.getId());
        if(hostelRoom.isPresent()){
            hostelBed.setId(null);
            hostelBed.setHostelRoom(hostelRoom.get());
            boolean status = hostelBedService.save(hostelBed);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }
        }else {
            return ResponseEntity.ok().body("Room selected does not exist");
        }

    }

    @GetMapping("/beds/{id}")
    @ResponseBody
    public HostelBed findById(@PathVariable Integer id,Model model){
       return hostelBedService.findById(id).get();
    }
}
