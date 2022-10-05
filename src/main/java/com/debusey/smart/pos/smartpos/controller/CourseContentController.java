package com.debusey.smart.pos.smartpos.controller;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.db.CourseContentResponse;
import com.debusey.smart.pos.smartpos.db.DbFile;
import com.debusey.smart.pos.smartpos.db.FileStorageException;
import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseContent;
import com.debusey.smart.pos.smartpos.entity.Users;
import com.debusey.smart.pos.smartpos.service.CourseContentService;
import com.debusey.smart.pos.smartpos.service.CourseService;
import com.debusey.smart.pos.smartpos.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class CourseContentController {
    private static final String UPLOADED_FILE_SESSION = "content-upload";
    @Autowired
    private CourseContentService courseContentService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/recruiter/course-contents")
    public String getContentPage() {
        return "admin/course-content";
    }

    @GetMapping("/recruiter/course-content")
    @ResponseBody
    public List<CourseContent> findAll() {
        try {
            return courseContentService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/recruiter/find-contents-id/{id}")
    @ResponseBody
    public List<CourseContent> findByCourse(@PathVariable Integer id) {
        try {
            Course course = courseService.findById(id).orElse(null);
            return courseContentService.findByCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/content-upload")
    @ResponseBody
    public void fileUpload(@RequestParam("file") MultipartFile files, Principal principal, Model model) {

        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        Users user = loginedUser.getUser();
//        if(user !=null){
//            String email = user.getUsername();
//            JobSeeker seeker =null;
//
//            Optional<JobSeeker> opp = service.findByEmail(email);
//            //Optional<JobSeeker> opp = service.findById(id);
//            if(opp.isPresent()){
//
//                if(files !=null){
//                    try {
//                        seeker = opp.get();
//                        JsfUtil.deleteFromDisk(seeker.getCvFileName());
//
//                        Long name = new Date().getTime();
//
//                        String fileName =name+"_"+ files.getOriginalFilename();
//
//                        seeker.setCvFileName(fileName);
//                        String contentType = files.getContentType();
//                        byte[] file = files.getBytes();
//                        seeker.setCvFileType(contentType);
//
//
//                        DbFile dbFile = new DbFile(fileName, contentType, file);
//                        JsfUtil.saveToDisk(dbFile);
//
//                        if( service.save(seeker)){
//                            ProfileStrength strength = getProfileStrength(seeker);
//                            Integer st = strength.getAttachedResume();
//                            if(st == null || st != 1){
//                                strength.setAttachedResume(1);
//                                strengthService.save(strength);
//                            }
//                        }
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(JobSeekerController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }
    }

    @PostMapping("/recruiter/course-content")
    @ResponseBody
    public ResponseEntity createCourseContent(@RequestBody CourseContentResponse courseContentResponse, HttpSession session) {

        CourseContent courseContent = new CourseContent();
        try {
            Course course = courseService.findById(courseContentResponse.getCourseId()).orElse(null);
            if (course != null) {
                courseContent.setCourse(course);
                courseContent.setContent(courseContentResponse.getContent());
                courseContent.setDisplayOrder(courseContentResponse.getDisplayOrder());
                courseContent.setTitle(courseContentResponse.getTitle());
                courseContent.setIntroduction(courseContentResponse.getIntroduction());
                DbFile dbfile = (DbFile) session.getAttribute(UPLOADED_FILE_SESSION);
                if (dbfile != null) {
                    JsfUtil.deleteFromDisk(courseContent.getAttachedFileName());
                    courseContent.setAttachedFileName(dbfile.getFileName());
                    courseContent.setAttachedFileType(dbfile.getFileType());
                    // notices.setPic(dbfile.getUploadedFile());
                    JsfUtil.saveToDisk(dbfile);
                }
                String result = courseContentService.createCourseContent(courseContent) ? "SUCCESS" : "FAILED";

                return new ResponseEntity(result, HttpStatus.OK);
            } else {
                return new ResponseEntity("Course does not exist", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/content-uploads")
    @ResponseBody
    public void uploadCourseContentFile(@RequestParam("file") MultipartFile[] files, HttpSession session) {
        try {
            DbFile dbFile = null;
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                if (fileName.contains("..")) {
                    System.out.println("Invalid File " + fileName);
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                dbFile = new DbFile(fileName, file.getContentType(), file.getBytes());
            }

            session.setAttribute(UPLOADED_FILE_SESSION, dbFile);


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            clearSession(session);
        }
    }

    private void clearSession(HttpSession session) {
        session.removeAttribute(UPLOADED_FILE_SESSION);
    }
}
