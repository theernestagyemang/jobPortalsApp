package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.repo.HostelRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelService {
    private HostelRepo hostelRepo;

    public HostelService(HostelRepo hostelRepo) {
        this.hostelRepo = hostelRepo;
    }

    public boolean save(Hostel hostel){
        try{
            hostelRepo.save(hostel);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<Hostel> hostels){
        try {
            hostelRepo.saveAll(hostels);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Hostel> findAll(){
        try {
            return hostelRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Hostel> findById(Integer id){
        try {
            return hostelRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
