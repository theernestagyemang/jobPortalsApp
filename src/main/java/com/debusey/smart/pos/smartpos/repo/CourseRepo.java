/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.Course;
import com.debusey.smart.pos.smartpos.entity.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByTransactionId(String transactionId);

    @Query("SELECT DISTINCT c.category FROM Course c")
    List<String> findByDistictCourse();

    List<Course> findByCategory(String category);

    List<Course> findByCourseCategory(CourseCategory category);

    List<Course> findByFeaturedCourseAndStatus(boolean featured, String status);

    List<Course> findByStatus(String status);

}
