package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.repo.AssessmentRoundRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentRoundService {
    @Autowired
    private AssessmentRoundRepo assessmentRoundRepo;

    public boolean addRound(AssessmentRound assessmentRound) {
        try {
            assessmentRoundRepo.save(assessmentRound);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AssessmentRound> findAll() {
        try {
            return assessmentRoundRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AssessmentRound findById(Integer id) {
        try {
            return assessmentRoundRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AssessmentRound findByRoundPriority(Integer round){
        try {
            return assessmentRoundRepo.findByRoundPriority(round);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            assessmentRoundRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
