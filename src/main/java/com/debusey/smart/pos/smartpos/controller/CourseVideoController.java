package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.db.CourseVideoResponse;
import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseVideo;
import com.debusey.smart.pos.smartpos.service.CourseService;
import com.debusey.smart.pos.smartpos.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseVideoController {
    @Autowired
    private CourseVideoService courseVideoService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/recruiter/course-videos")
    public String getCourVideoPage() {
        return "admin/course-video";
    }

    @GetMapping("/recruiter/course-video")
    @ResponseBody
    public List<CourseVideo> findAll() {
        try {
            return courseVideoService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/recruiter/find-courses-video/{id}")
    @ResponseBody
    public List<CourseVideo> findAllByCourse(@PathVariable Integer id) {
        try {
            Course course = courseService.findById(id).orElse(null);
            return courseVideoService.findByCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping("/recruiter/findVideo/{id}")
    @ResponseBody
    public CourseVideo findById(@PathVariable Integer id) {
        try {
            return courseVideoService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/recruiter/course-video")
    @ResponseBody
    public ResponseEntity createVideoCourse(@RequestBody CourseVideoResponse courseVideoResponse) {
        CourseVideo courseVideo = new CourseVideo();
        try {
            Course course = courseService.findById(courseVideoResponse.getCourseId()).orElse(null);
            if (course != null) {
                courseVideo.setCourse(course);
                courseVideo.setDisplayOrder(courseVideoResponse.getDisplayOrder());
                courseVideo.setName(courseVideoResponse.getName());
                courseVideo.setUrl(courseVideoResponse.getUrl());

                String result = courseVideoService.createVideoCourse(courseVideo) ? "SUCCESS" : "FAILED";

                return new ResponseEntity(result, HttpStatus.OK);
            } else {
                return new ResponseEntity("Course does not exist", HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
