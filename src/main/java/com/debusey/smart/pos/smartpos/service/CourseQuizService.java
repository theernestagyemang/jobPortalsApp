package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import com.debusey.smart.pos.smartpos.repo.CourseQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseQuizService {
    @Autowired
    private CourseQuizRepo courseQuizRepo;

    @Autowired
    private CourseService courseService;

    public List<CourseQuiz> findAll() {
        try {
            return courseQuizRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseQuiz> findAllByCourse(Course course) {
        try {
            return courseQuizRepo.findByCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CourseQuiz findById(Integer id) {
        try {
            return courseQuizRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            courseQuizRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CourseQuiz save(CourseQuiz courseQuiz) {
        try {
            return courseQuizRepo.save(courseQuiz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
