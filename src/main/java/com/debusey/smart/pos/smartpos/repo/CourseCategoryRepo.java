/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Admin
 */
public interface CourseCategoryRepo extends JpaRepository<CourseCategory, Integer> {
    Optional<CourseCategory> findByName(String seeker);

    Optional<CourseCategory> findByTransactionId(String trx);

}
