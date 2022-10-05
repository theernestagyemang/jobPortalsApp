/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface JobSeekerRepository extends PagingAndSortingRepository<JobSeeker, Integer> {

    Optional<JobSeeker> findByTransactionId(String id);

    Optional<JobSeeker> findByEmail(String id);

    @Query("SELECT u FROM JobSeeker u WHERE  u.email = :email")
    List<JobSeeker> findDuplicates(@Param("email") String email);

    Optional<JobSeeker> findByUniqueId(String token);

    @Query("SELECT count(id) FROM JobSeeker")
    Integer getJobSeekersCount();

    @Query("SELECT COUNT(c.id), c.proffesionalTitile FROM JobSeeker c group by c.proffesionalTitile")
    Optional<List<Object[]>> findBySeekerSpecialization();

    //public List<JobSeeker> findJobSeekerBySpecialization(List<String> q);
    //@Query("MATCH (u:User) WHERE u.id in $ids RETURN u.id as id")
    List<JobSeeker> findByProffesionalTitileIn(@Param("ids") List<String> ids);

    @Query(nativeQuery = true, value = "select * from job_seeker where transaction_id is null limit 1000")
    List<JobSeeker> findNullTransactions();
}
