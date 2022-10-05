package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.entities.HostelFloor;
import com.ao.schoolerp.helpers.FloorHelper;
import com.ao.schoolerp.services.HostelBlockService;
import com.ao.schoolerp.services.HostelFloorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hostel")
public class HostelFloorController {
    private final HostelFloorService hostelFloorService;
    private final HostelBlockService hostelBlockService;

    public HostelFloorController(HostelFloorService hostelFloorService, HostelBlockService hostelBlockService) {
        this.hostelFloorService = hostelFloorService;
        this.hostelBlockService = hostelBlockService;
    }

    @GetMapping("/floor")
    public String floor(){
        return "hostel/floor";
    }
    @GetMapping("/floors")
    @ResponseBody
    public List<HostelFloor> getFloors (){
        return hostelFloorService.findAll();
    }

    @PostMapping("/create-floor")
    @ResponseBody
    public ResponseEntity addHostelFloor(@RequestBody FloorHelper floorHelper){
//       if(hostelFloorService.save(hostel)  return "redirect: /hostels" : return "re" ;
        Optional<HostelBlock> hostelBlock = hostelBlockService.findById(floorHelper.getBlockId());
        if(hostelBlock.isPresent()){
            HostelFloor hostelFloor = new HostelFloor();
            hostelFloor.setId(floorHelper.getId());
            hostelFloor.setName(floorHelper.getName());
            hostelFloor.setHostelBlock(hostelBlock.get());
            hostelFloor.setStatus(floorHelper.getStatus());
            hostelFloor.setNumberOfRooms(floorHelper.getNumberOfRooms());
            boolean status = hostelFloorService.save(hostelFloor);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }

        }else {
            return ResponseEntity.ok().body("Hostel Block does not exist");
        }

    }

    @GetMapping("/floors/{id}")
    @ResponseBody
    public HostelFloor findById(@PathVariable Integer id,Model model){
        return hostelFloorService.findById(id).get();
    }

    @GetMapping("/floor/delete/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable Integer id){
        return hostelFloorService.deleteById(id);
    }

    @GetMapping("/floors-by-block/{id}")
    @ResponseBody
    public List<HostelFloor> findByHostel(@PathVariable Integer id){
//        Hostel hostel = hostelService.findById(id).get();
        HostelBlock hostelBlock = hostelBlockService.findById(id).get();
        return hostelFloorService.findAllByBlock(hostelBlock);
    }
}
