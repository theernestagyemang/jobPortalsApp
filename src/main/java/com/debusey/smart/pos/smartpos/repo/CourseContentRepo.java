package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseContentRepo extends JpaRepository<CourseContent, Integer> {
    List<CourseContent> findByCourse(Course course);
}
