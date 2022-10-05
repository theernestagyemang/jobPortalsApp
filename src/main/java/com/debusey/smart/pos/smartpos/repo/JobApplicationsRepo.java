/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;


import com.debusey.smart.pos.smartpos.entity.JobApplications;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */
public interface JobApplicationsRepo extends PagingAndSortingRepository<JobApplications, Integer> {

    List<JobApplications> findByJobseeker(JobSeeker seeker);

    Page<JobApplications> findByJobseeker(JobSeeker seeker, Pageable p);

    List<JobApplications> findByJob(Jobs seeker);

    Page<JobApplications> findByJob(Jobs seeker, Pageable p);

    List<JobApplications> findByEntryDate(LocalDate email);

    List<JobApplications> findByApplicationStatus(String appStatus);

    Page<JobApplications> findByEntryDate(LocalDate email, Pageable p);

    Page<JobApplications> findByApplicationStatus(String appStatus, Pageable p);

    Optional<JobApplications> findByTransactionId(String trx);

    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.appliedDate = CURRENT_DATE")
    Integer findDailyTotalJobApplications();

    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.appliedDate between :start and :end ")
    Integer findDailyTotalJobApplications(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT c FROM JobApplications c WHERE  c.appliedDate between :start and :end ")
    List<JobApplications> findDailyJobApplications(@Param("start") LocalDate start, @Param("end") LocalDate end);


    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.appliedDate = CURRENT_DATE and c.applicationStatus = :applicationStatus")
    Integer findDailyTotalJobApplications(@Param("applicationStatus") String status);

    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.appliedDate  between :start and :end and c.applicationStatus = :applicationStatus")
    Integer findDailyTotalJobApplications(@Param("applicationStatus") String status, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(nativeQuery = true, value = "select count(id) , MONTH(applied_date)  from job_applications \n" +
            "where YEAR(applied_date) = YEAR(CURDATE()) and application_status = :shortlisted and MONTH(applied_date) in (1,2,3,4,5,6,7,8,9,10,11,12) \n" +
            "group by MONTH(applied_date)")
    List<Object[]> findMonthlySalesByYear(@Param("shortlisted") String shortlisted);


    @Query(nativeQuery = true, value = "select COALESCE(count(id), 0) , MONTH(entry_date)  from job_applications \n" +
            "where YEAR(entry_date) = :year  and MONTH(entry_date) in (1,2,3,4,5,6,7,8,9,10,11,12) \n" +
            "group by MONTH(entry_date)")
    List<Object[]> findMonthlySalesByYear(@Param("year") Integer year);


    @Query(nativeQuery = true, value = "select date_format(applied_date,'%M'), count(id) from job_applications where year(applied_date) = :year group by year(applied_date),month(applied_date) order by year(applied_date),month(applied_date)")
    List<Object[]> findMonthlyApplication(@Param("year") Integer year);


    //public Optional<JobApplications> findByJobSeekerAndJob(Jobs job, JobSeeker seeker);

    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.job = :job")
    Integer findByJobCount(@Param("job") Jobs job);

    @Query("SELECT count(c.id) FROM JobApplications c WHERE  c.job = :job and c.applicationStatus = :applicationStatus")
    Integer findByJobAndApplicationStatusCount(@Param("job") Jobs job, @Param("applicationStatus") String applicationStatus);

    Page<JobApplications> findByJobAndApplicationStatus(Jobs job, String applicationStatus, Pageable of);

    @Query(nativeQuery = true, value = "select * from job_applications where entry_date = CURRENT_DATE limit 5")
    List<JobApplications> lastFiveApplications();


    @Query("SELECT count(c.id), c.job.category FROM JobApplications c GROUP BY c.job.category")
    List<Object[]> findApplicationsByJobCategory();

    @Query("SELECT count(c.id), c.job.category FROM JobApplications c where c.appliedDate between :start and :end GROUP BY c.job.category")
    List<Object[]> findApplicationsByJobCategory(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT c FROM JobApplications c WHERE  c.appliedDate between :start and :end  and c.applicationStatus = :appStatus")
    Page<JobApplications> findByApplicationStatusAndDates(@Param("appStatus") String appStatus, @Param("start") LocalDate start, @Param("end") LocalDate end, Pageable of);


}
