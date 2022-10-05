package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.entities.HostelFloor;
import com.ao.schoolerp.repo.HostelFloorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelFloorService {
    private HostelFloorRepo hostelFloorRepo;

    public HostelFloorService(HostelFloorRepo hostelFloorRepo) {
        this.hostelFloorRepo = hostelFloorRepo;
    }

    public boolean save(HostelFloor hostelFloor){
        try{
            hostelFloorRepo.save(hostelFloor);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelFloor> hostelFloors){
        try {
            hostelFloorRepo.saveAll(hostelFloors);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelFloor> findAll(){
        try {
            return hostelFloorRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelFloor> findById(Integer id){
        try {
            return hostelFloorRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelFloorRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //get all floors based on the block passed in parameter
    public List<HostelFloor> findAllByBlock(HostelBlock hostelBlock){
        try {
            return hostelFloorRepo.findByHostelBlock(hostelBlock);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
