package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseVideo;
import com.debusey.smart.pos.smartpos.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseVideoService {
    @Autowired
    private VideoRepo videoRepo;

    public List<CourseVideo> findAll() {
        return videoRepo.findAll();
    }

    public List<CourseVideo> findByCourse(Course course) {
        return videoRepo.findByCourse(course);
    }

    public CourseVideo findById(Integer id) {
        return videoRepo.findById(id).orElse(null);
    }

    public Boolean deleteById(Integer id) {
        try {
            videoRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createVideoCourse(CourseVideo courseVideo) {
        try {
            videoRepo.save(courseVideo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
