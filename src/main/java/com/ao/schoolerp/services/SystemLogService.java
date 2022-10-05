package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.SystemLog;
import com.ao.schoolerp.repo.SystemLogRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemLogService {
    private SystemLogRepo systemLogRepo;

    public SystemLogService(SystemLogRepo systemLogRepo) {
        this.systemLogRepo = systemLogRepo;
    }

    public boolean save(SystemLog systemLog){
        try{
            systemLogRepo.save(systemLog);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<SystemLog> systemLogs){
        try {
            systemLogRepo.saveAll(systemLogs);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<SystemLog> findAll(){
        try {
            return systemLogRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<SystemLog> findById(Integer id){
        try {
            return systemLogRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            systemLogRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
