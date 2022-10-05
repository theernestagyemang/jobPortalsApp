package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.UserTakeCourseQuiz;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTakeQuizRepo extends JpaRepository<UserTakeCourseQuiz, Integer> {
    public List<UserTakeCourseQuiz> findByUsers(Users users);

    public UserTakeCourseQuiz findByUsersAndCourse(Users users, Course course);

}
