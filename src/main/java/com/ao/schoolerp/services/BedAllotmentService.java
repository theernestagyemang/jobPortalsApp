package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.BedAllotment;
import com.ao.schoolerp.repo.BedAllotmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedAllotmentService {
    
    private BedAllotmentRepo bedAllotmentRepo;

    public BedAllotmentService(BedAllotmentRepo bedAllotmentRepo) {
        this.bedAllotmentRepo = bedAllotmentRepo;
    }

    public boolean save(BedAllotment bedAllotment){
        try{
            bedAllotmentRepo.save(bedAllotment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<BedAllotment> BedAllotments){
        try {
            bedAllotmentRepo.saveAll(BedAllotments);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<BedAllotment> findAll(){
        try {
            return bedAllotmentRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<BedAllotment> findById(Integer id){
        try {
            return bedAllotmentRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            bedAllotmentRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
