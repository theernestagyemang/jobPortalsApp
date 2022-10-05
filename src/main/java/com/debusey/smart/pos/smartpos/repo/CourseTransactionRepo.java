package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseTransaction;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTransactionRepo extends JpaRepository<CourseTransaction, Integer> {
    List<CourseTransaction> findByUsers(Users users);
    CourseTransaction findByUsersAndCourseAndStatus(Users users, Course course, boolean status);

    CourseTransaction findByUsersAndCourse(Users users, Course course);
}
