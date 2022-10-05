package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.HostelBed;
import com.ao.schoolerp.repo.HostelBedRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelBedService {
    private HostelBedRepo hostelBedRepo;

    public HostelBedService(HostelBedRepo hostelBedRepo) {
        this.hostelBedRepo = hostelBedRepo;
    }

    public boolean save(HostelBed hostelBed){
        try{
            hostelBedRepo.save(hostelBed);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelBed> hostelBeds){
        try {
            hostelBedRepo.saveAll(hostelBeds);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelBed> findAll(){
        try {
            return hostelBedRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelBed> findById(Integer id){
        try {
            return hostelBedRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelBedRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
