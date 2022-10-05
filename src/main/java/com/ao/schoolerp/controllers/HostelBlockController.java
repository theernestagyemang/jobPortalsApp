package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.helpers.BlockHelper;
import com.ao.schoolerp.services.HostelBlockService;
import com.ao.schoolerp.services.HostelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hostel")
public class HostelBlockController {
    private final HostelBlockService hostelBlockService;
    private final HostelService hostelService;

    public HostelBlockController(HostelBlockService hostelBlockService, HostelService hostelService) {
        this.hostelBlockService = hostelBlockService;
        this.hostelService = hostelService;
    }

    @GetMapping("/blocks")
    public String viewHostelBlock (Model model){
        return "hostel/blocks";
    }

    @GetMapping("/block")
    public String block(){
        return "hostel/block";
    }

    @GetMapping("/block-list")
    @ResponseBody
    public List<HostelBlock> getBlocks (){
        return hostelBlockService.findAll();
    }


    @PostMapping("/create-block")
    @ResponseBody
    public ResponseEntity addHostelBlock (@RequestBody BlockHelper blockHelper){
//       if(HostelBlockService.save(hostel)  return "redirect: /hostels" : return "re" ;
        Optional<Hostel> hostel = hostelService.findById(blockHelper.getHostelId());
        if(hostel.isPresent()){
            HostelBlock hostelBlock = new HostelBlock();
            hostelBlock.setId(blockHelper.getId());
            hostelBlock.setHostel(hostel.get());
            hostelBlock.setName(blockHelper.getName());
            hostelBlock.setStatus(blockHelper.getStatus());
            hostelBlock.setDescription(blockHelper.getDescription());
            hostelBlock.setNumberOfFloors(blockHelper.getNumberOfFloors());
            boolean status = hostelBlockService.save(hostelBlock);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }
        }else {
            return ResponseEntity.ok().body("Hostel selected does not exist");
        }


    }

    @GetMapping("/blocks/{id}")
    @ResponseBody
    public HostelBlock findById(@PathVariable Integer id, Model model){
        return hostelBlockService.findById(id).get();
    }

    @GetMapping("/block/delete/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable Integer id){
        return hostelBlockService.deleteById(id);
    }

    @GetMapping("/blocks-by-hostel/{id}")
    @ResponseBody
    public List<HostelBlock> findByHostel(@PathVariable Integer id){
        Hostel hostel = hostelService.findById(id).get();
        return hostelBlockService.findAllByHostel(hostel) ;
    }
}
