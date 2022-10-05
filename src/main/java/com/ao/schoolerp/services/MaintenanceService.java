package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.Maintenance;
import com.ao.schoolerp.repo.MaintenanceRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    private MaintenanceRepo maintenanceRepo;

    public MaintenanceService(MaintenanceRepo maintenanceRepo) {
        this.maintenanceRepo = maintenanceRepo;
    }

    public boolean save(Maintenance maintenance){
        try{
            maintenanceRepo.save(maintenance);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<Maintenance> maintenances){
        try {
            maintenanceRepo.saveAll(maintenances);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Maintenance> findAll(){
        try {
            return maintenanceRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Maintenance> findById(Integer id){
        try {
            return maintenanceRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            maintenanceRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
