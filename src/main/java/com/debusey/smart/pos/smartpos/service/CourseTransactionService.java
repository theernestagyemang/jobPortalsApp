package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseTransaction;
import com.debusey.smart.pos.smartpos.entity.TrainingSchedule;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.repo.CourseTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTransactionService {
    @Autowired
    private CourseTransactionRepo courseTransactionRepo;

    public boolean addCourseTransaction(CourseTransaction courseTransaction) {
        try {
            courseTransactionRepo.save(courseTransaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public CourseTransaction findByUserAndCourseAndStatus(Users user, Course course){
        try {
            return courseTransactionRepo.findByUsersAndCourseAndStatus(user,course,true);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseTransaction> findByUser(Users user) {
        return courseTransactionRepo.findByUsers(user);
    }

    public CourseTransaction findByUserAndCourse(Users user, Course course) {
        try {
            return courseTransactionRepo.findByUsersAndCourse(user,course);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public List<CourseTransaction> findAll() {
        return courseTransactionRepo.findAll();
    }

    public CourseTransaction findById(Integer id) {
        return courseTransactionRepo.findById(id).orElse(null);
    }
}
