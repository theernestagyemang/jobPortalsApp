package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseContent;
import com.debusey.smart.pos.smartpos.repo.CourseContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseContentService {

    @Autowired
    private CourseContentRepo courseContentRepo;

    public List<CourseContent> findAll() {
        return courseContentRepo.findAll();
    }

    public List<CourseContent> findByCourse(Course course) {
        return courseContentRepo.findByCourse(course);
    }

    public CourseContent findById(Integer id) {
        return courseContentRepo.findById(id).orElse(null);
    }

    public boolean deleteById(Integer id) {
        try {
            courseContentRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCourseContent(CourseContent courseContent) {
        try {
            courseContentRepo.save(courseContent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
