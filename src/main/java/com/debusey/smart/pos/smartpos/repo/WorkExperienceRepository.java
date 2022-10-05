/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.WorkExperience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface WorkExperienceRepository extends CrudRepository<WorkExperience, Integer> {

    List<WorkExperience> findByJobSeekerId(JobSeeker seeker);

    @Query("SELECT COUNT(c.id), c.designation FROM WorkExperience c group by c.designation")
    Optional<List<Object[]>> findByWorkSpecialization();

    List<WorkExperience> findByDesignationIn(@Param("ids") List<String> ids);

    @Query(nativeQuery = true, value = "SELECT id, designation,  sum( year_stoped_work - year_started_work ) exp, job_seeker_id FROM work_experience group by designation having job_seeker_id= :jobseekerId")
    Optional<List<Object[]>> findWorkExperience(@Param("jobseekerId") Integer jobseekerId);
}
