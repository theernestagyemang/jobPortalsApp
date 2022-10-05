package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.HostelCheckIn;
import com.ao.schoolerp.repo.HostelCheckInRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelCheckInService {
    private HostelCheckInRepo hostelCheckInRepo;

    public HostelCheckInService(HostelCheckInRepo hostelCheckInRepo) {
        this.hostelCheckInRepo = hostelCheckInRepo;
    }

    public boolean save(HostelCheckIn hostelCheckIn){
        try{
            hostelCheckInRepo.save(hostelCheckIn);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelCheckIn> hostelCheckIns){
        try {
            hostelCheckInRepo.saveAll(hostelCheckIns);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelCheckIn> findAll(){
        try {
            return hostelCheckInRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelCheckIn> findById(Integer id){
        try {
            return hostelCheckInRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelCheckInRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
