package com.ao.schoolerp.controllers;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.entities.HostelFloor;
import com.ao.schoolerp.entities.HostelRoom;
import com.ao.schoolerp.helpers.RoomHelper;
import com.ao.schoolerp.services.HostelFloorService;
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
public class HostelRoomController {
    private final HostelRoomService hostelRoomService;
    private final HostelFloorService hostelFloorService;

    public HostelRoomController(HostelRoomService hostelRoomService, HostelFloorService hostelFloorService) {
        this.hostelRoomService = hostelRoomService;
        this.hostelFloorService = hostelFloorService;
    }

    @GetMapping("/rooms")
    public String viewHostelRoom (Model model, Principal principal){

        List<HostelRoom> hostelRoomList = hostelRoomService.findAll();
        model.addAttribute("hostelRooms", hostelRoomList);
        return "hostel/rooms";
    }

    @GetMapping("/room-list")
    @ResponseBody
    public List<HostelRoom> getRooms(){
      return hostelRoomService.findAll();
    }

    @PostMapping("/create-room")
    @ResponseBody
    public ResponseEntity addHostelRoom (@RequestBody RoomHelper roomHelper){

        Optional<HostelFloor> hostelFloor = hostelFloorService.findById(roomHelper.getFloorId());

        if(hostelFloor.isPresent()){
            HostelRoom hostelRoom = new HostelRoom();
            hostelRoom.setId(roomHelper.getId());
            hostelRoom.setHostelFloor(hostelFloor.get());
            hostelRoom.setNumberOfBeds(roomHelper.getNumberOfBeds());
            hostelRoom.setName(roomHelper.getName());
            hostelRoom.setStatus(roomHelper.getStatus());
            hostelRoom.setRoomNumber(roomHelper.getRoomNumber());

            boolean status = hostelRoomService.save(hostelRoom);
            if(status){
                return ResponseEntity.ok().body("SUCCESS");
            }else {
                return ResponseEntity.ok().body("FAILED");
            }
        }else {
            return ResponseEntity.ok().body("Floor selected does not exist");
        }

    }

    @GetMapping("/room")
    public String room(){
        return "hostel/room";
    }

    @GetMapping("/rooms/{id}")
    @ResponseBody
    public HostelRoom findById(@PathVariable Integer id,Model model){
        return hostelRoomService.findById(id).get();
    }

    @GetMapping("/room/delete/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable Integer id){
        return hostelRoomService.deleteById(id);
    }

    @GetMapping("/rooms-by-floor/{id}")
    @ResponseBody
    public List<HostelRoom> findByFloor(@PathVariable Integer id){
        HostelFloor hostelFloor = hostelFloorService.findById(id).get();
        return hostelRoomService.findAllByFloor(hostelFloor);
    }
}
