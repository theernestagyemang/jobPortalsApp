package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.HostelPayment;
import com.ao.schoolerp.repo.HostelPaymentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelPaymentService {
    private HostelPaymentRepo hostelPaymentRepo;

    public HostelPaymentService(HostelPaymentRepo hostelPaymentRepo) {
        this.hostelPaymentRepo = hostelPaymentRepo;
    }

    public boolean save(HostelPayment hostelPayment){
        try{
            hostelPaymentRepo.save(hostelPayment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelPayment> hostelPayments){
        try {
            hostelPaymentRepo.saveAll(hostelPayments);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelPayment> findAll(){
        try {
            return hostelPaymentRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelPayment> findById(Integer id){
        try {
            return hostelPaymentRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelPaymentRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
