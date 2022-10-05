package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.HostelBooking;
import com.ao.schoolerp.repo.HostelBookingRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelBookingService {
    
    private HostelBookingRepo hostelBookingRepo;

    public HostelBookingService(HostelBookingRepo hostelBookingRepo) {
        this.hostelBookingRepo = hostelBookingRepo;
    }

    public boolean save(HostelBooking HostelBooking){
        try{
            hostelBookingRepo.save(HostelBooking);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<HostelBooking> hostelBookings){
        try {
            hostelBookingRepo.saveAll(hostelBookings);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<HostelBooking> findAll(){
        try {
            return hostelBookingRepo.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<HostelBooking> findById(Integer id){
        try {
            return hostelBookingRepo.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id){
        try {
            hostelBookingRepo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
