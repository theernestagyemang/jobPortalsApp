package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.repo.HostelBlockRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelBlockService {
    private HostelBlockRepo hostelBlockRepo;

    public HostelBlockService(HostelBlockRepo hostelBlockRepo) {
        this.hostelBlockRepo = hostelBlockRepo;
    }

    public boolean save(HostelBlock hostelBlock){
        try{
            hostelBlockRepo.save(hostelBlock);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelBlock> hostelBlocks){
        try {
            hostelBlockRepo.saveAll(hostelBlocks);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelBlock> findAll(){
        try {
            return hostelBlockRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelBlock> findById(Integer id){
        try {
            return hostelBlockRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelBlockRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //get all block based on the hostel passed in parameter
    public List<HostelBlock> findAllByHostel(Hostel hostel){
        try {
           return hostelBlockRepo.findByHostel(hostel);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
