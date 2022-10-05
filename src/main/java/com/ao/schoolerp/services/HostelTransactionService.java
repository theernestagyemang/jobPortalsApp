package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.HostelTransaction;
import com.ao.schoolerp.repo.HostelTransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelTransactionService {
    private HostelTransactionRepo hostelTransactionRepo;

    public HostelTransactionService(HostelTransactionRepo hostelTransactionRepo) {
        this.hostelTransactionRepo = hostelTransactionRepo;
    }

    public boolean save(HostelTransaction hostelTransaction){
        try{
            hostelTransactionRepo.save(hostelTransaction);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelTransaction> hostelTransactions){
        try {
            hostelTransactionRepo.saveAll(hostelTransactions);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelTransaction> findAll(){
        try {
            return hostelTransactionRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelTransaction> findById(Integer id){
        try {
            return hostelTransactionRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelTransactionRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
