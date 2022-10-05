package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseQuizRepo extends JpaRepository<CourseQuiz, Integer> {
    List<CourseQuiz> findByCourse(Course course);
}
