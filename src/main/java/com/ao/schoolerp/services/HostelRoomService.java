package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.entities.HostelFloor;
import com.ao.schoolerp.entities.HostelRoom;
import com.ao.schoolerp.repo.HostelRoomRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelRoomService {
    private HostelRoomRepo hostelRoomRepo;

    public HostelRoomService(HostelRoomRepo hostelRoomRepo) {
        this.hostelRoomRepo = hostelRoomRepo;
    }

    public boolean save(HostelRoom hostelRoom){
        try{
            hostelRoomRepo.save(hostelRoom);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelRoom> hostelRooms){
        try {
            hostelRoomRepo.saveAll(hostelRooms);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelRoom> findAll(){
        try {
            return hostelRoomRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelRoom> findById(Integer id){
        try {
            return hostelRoomRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelRoomRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //get all rooms based on the floor passed in parameter
    public List<HostelRoom> findAllByFloor(HostelFloor hostelFloor){
        try {
            return hostelRoomRepo.findByHostelFloor(hostelFloor);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
