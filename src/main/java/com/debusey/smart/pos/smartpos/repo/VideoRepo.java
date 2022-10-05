package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepo extends JpaRepository<CourseVideo, Integer> {
    List<CourseVideo> findByCourse(Course course);
}
