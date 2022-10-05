package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Assessment;
import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.AssessmentTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentTransactionService {
    @Autowired
    private AssessmentTransactionRepo assessmentTransactionRepo;

    public List<Assessment> findAll() {
        try {
            return assessmentTransactionRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Assessment> findByUserAndStatus(Users users, boolean status) {
        return assessmentTransactionRepo.findByUsersAndStatus(users, status);
    }

    public Assessment findByUserAndAssessmentLineAndStatus(Users user, AssessmentLines assessmentLine, boolean status) {
        try {
            return assessmentTransactionRepo.findByUsersAndAssessmentLinesAndStatus(user, assessmentLine, status);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Assessment> findByUserAndAssessmentLineAndStatus(
            Users users, AssessmentLines assessmentLines, boolean status,String userType){
        try {
            return assessmentTransactionRepo.findByUsersAndAssessmentLinesAndStatusAndUserType(
                    users,assessmentLines,status,userType
            );
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Assessment findByUserAndAssessmentLineAndStatusAndAssessmentRound(
            Users user, AssessmentLines assessmentLine, boolean status, AssessmentRound assessmentRound) {
        try {
            return assessmentTransactionRepo.findByUsersAndAssessmentLinesAndStatusAndAssessmentRound(
                    user, assessmentLine, status, assessmentRound);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Assessment findById(Integer id) {
        try {
            return assessmentTransactionRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addAssessmentTransaction(Assessment assessmentTransaction) {
        try {
            assessmentTransactionRepo.save(assessmentTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
