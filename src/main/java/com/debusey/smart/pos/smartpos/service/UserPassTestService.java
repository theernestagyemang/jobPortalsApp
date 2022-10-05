package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.UserPassTest;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.UserPassTestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPassTestService {
    @Autowired
    private UserPassTestRepo userPassTestRepo;

    public boolean addUserPassTest(UserPassTest userPassTest) {
        try {
            userPassTestRepo.save(userPassTest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserPassTest> findAll() {
        try {
            return userPassTestRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserPassTest> findByUser(Users users) {
        try {
            return userPassTestRepo.findByUsers(users);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public UserPassTest findById(Integer id) {
        try {
            return userPassTestRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserPassTest findByUserAndAssessment(Users users, AssessmentLines assessmentLines) {
        return userPassTestRepo.findByUsersAndAssessmentLines(users, assessmentLines);
    }
}
