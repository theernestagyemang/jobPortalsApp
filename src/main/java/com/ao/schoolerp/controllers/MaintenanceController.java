package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.HostelRoom;
import com.ao.schoolerp.entities.Maintenance;
import com.ao.schoolerp.services.HostelRoomService;
import com.ao.schoolerp.services.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hostel")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;
    private final HostelRoomService hostelRoomService;

    public MaintenanceController(MaintenanceService maintenanceService, HostelRoomService hostelRoomService) {
        this.maintenanceService = maintenanceService;
        this.hostelRoomService = hostelRoomService;
    }

    @GetMapping("/maintenance")
    public String viewMaintenance (Model model, Principal principal){

        List<Maintenance> maintenanceList = maintenanceService.findAll();
        model.addAttribute("maintenances", maintenanceList);
        return "hostel/maintenance";
    }

    @PostMapping("/create-maintenance")
    @ResponseBody
    public ResponseEntity addMaintenance(@RequestBody Maintenance maintenance){
//       if(maintenanceService.save(hostel)  return "redirect: /hostels" : return "re" ;
        Optional<HostelRoom> room = hostelRoomService.findById(maintenance.getId());

        if(room.isPresent()){
            maintenance.setId(null);
            maintenance.setHostelRoom(room.get());
            boolean status = maintenanceService.save(maintenance);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }
        }else {
            return ResponseEntity.ok().body("Room selected does not exist");
        }

    }

    @GetMapping("/maintenance/{id}")
    @ResponseBody
    public Maintenance findById(@PathVariable Integer id, Model model){
        return maintenanceService.findById(id).get();
    }
}
