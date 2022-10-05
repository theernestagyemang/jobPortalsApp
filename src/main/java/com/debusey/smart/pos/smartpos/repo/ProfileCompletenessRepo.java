/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.ProfileStrength;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface ProfileCompletenessRepo extends CrudRepository<ProfileStrength, Integer> {

    Optional<ProfileStrength> findByJobSeekerId(JobSeeker seeker);

    @Query(nativeQuery = true, value = "SELECT `job_seeker_id`, \n" +
            "SUM(\n" +
            "IFNULL(`attached_resume`, 0) \n" +
            "+ IFNULL(`cover_letter`, 0))\n" +
            "+ IFNULL(`desired_career_profile`, 0)\n" +
            "+ IFNULL(`education`, 0) \n" +
            "+ IFNULL(`employment`, 0) \n" +
            "+ IFNULL(`it_skills`, 0) \n" +
            "+ IFNULL(`key_skills`, 0) \n" +
            "+ IFNULL(`personal_details`, 0) \n" +
            "+ IFNULL(`profile_summary`, 0) \n" +
            "+ IFNULL(`resume_headline`, 0)\n" +
            "\n" +
            "AS `total_marks`\n" +
            "FROM profile_strength\n" +
            "GROUP BY `job_seeker_id`")
    Optional<List<Object[]>> findByCompletePct();

    @Query(nativeQuery = true, value = "SELECT  \n" +
            "SUM(\n" +
            "IFNULL(`attached_resume`, 0) \n" +
            "+ IFNULL(`cover_letter`, 0))\n" +
            "+ IFNULL(`desired_career_profile`, 0)\n" +
            "+ IFNULL(`education`, 0) \n" +
            "+ IFNULL(`employment`, 0) \n" +
            "+ IFNULL(`it_skills`, 0) \n" +
            "+ IFNULL(`key_skills`, 0) \n" +
            "+ IFNULL(`personal_details`, 0) \n" +
            "+ IFNULL(`profile_summary`, 0) \n" +
            "+ IFNULL(`resume_headline`, 0)\n" +
            "\n" +
            "AS `total_marks`\n" +
            "FROM profile_strength where job_seeker_id = :seekerId\n" +
            "GROUP BY `job_seeker_id`")
    Integer findByCompletePctAndJobSeeker(@Param("seekerId") Integer seeker);


}