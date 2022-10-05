package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.*;
import com.debusey.smart.pos.smartpos.repo.UserTakeQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTakeQuizService {
    @Autowired
    private UserTakeQuizRepo userTakeQuizRepo;

    public boolean addUserTakeQuiz(UserTakeCourseQuiz userTakeCourseQuiz) {
        try {
            userTakeQuizRepo.save(userTakeCourseQuiz);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserTakeCourseQuiz> findAll() {
        try {
            return userTakeQuizRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserTakeCourseQuiz> findByUser(Users users) {
        try {
            return userTakeQuizRepo.findByUsers(users);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public UserTakeCourseQuiz findById(Integer id) {
        try {
            return userTakeQuizRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserTakeCourseQuiz findByUserAndCourse(Users users, Course course) {
        return userTakeQuizRepo.findByUsersAndCourse(users,course);
    }
}
