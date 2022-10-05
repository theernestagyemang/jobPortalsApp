/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Admin
 */

public interface JobsRepository extends PagingAndSortingRepository<Jobs, Integer> {

    //All
    @Query("SELECT c FROM Jobs c WHERE  c.profession LIKE  %:profession% and c.location LIKE  %:location% and c.category LIKE  %:category% AND c.published = 1")
    List<Jobs> findJobByCategoryAndLocation(@Param("profession") String profession, @Param("location") String location, @Param("category") String category);

    //==================== Profession List=====================
    Page<Jobs> findByProfession(PageRequest pageable, String b);

    //==================== Profession Distinc=====================
    @Query("SELECT DISTINCT c.profession FROM Jobs c where c.expireDate > CURRENT_DATE")
    List<String> findByDistictProfession();

    //==================== Profession Count=====================
    @Query("SELECT COUNT(c.id)  FROM Jobs c WHERE c.profession = :profession and c.expireDate > CURRENT_DATE")
    Integer findByProfessionCount(@Param("profession") String profession);


    //==================== Category List=====================

    Page<Jobs> findByCategory(String b, Pageable p);

    @Query("SELECT c FROM Jobs c WHERE c.category = :category and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByPublishedCategory(@Param("category") String category, Pageable p);

    @Query("SELECT c FROM Jobs c WHERE c.minQualification = :minQualification and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByPublishedQualification(@Param("minQualification") String minQualification, Pageable p);

    //==================== Category Distinc=====================
    @Query("SELECT DISTINCT c.category FROM Jobs c where c.expireDate > CURRENT_DATE")
    List<String> findByDistictCategory();


    @Query("SELECT DISTINCT c.category FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<String> findByDistinctCategoryPaginated(Pageable pageabl);

    // @Query("SELECT DISTINCT c.category FROM Jobs c where c.expireDate > CURRENT_DATE")
    @Query(nativeQuery = true, value = "select DISTINCT category FROM jobs where expire_date > CURRENT_DATE AND published = 1 ORDER BY RAND() LIMIT 4")
    List<String> findFourCategories();


    //==================== Category Count=====================
    @Query("SELECT COUNT(c.id)  FROM Jobs c WHERE c.category = :category and c.expireDate > CURRENT_DATE AND c.published = 1")
    Integer findByCategoryCount(@Param("category") String category);


    //==================== Location List=====================
    @Query("SELECT c FROM Jobs c WHERE c.location = :location and c.expireDate > CURRENT_DATE AND c.published = 1")
    List<Jobs> findByLocation(@Param("location") String location);

    //==================== Category Distinc=====================
    @Query("SELECT DISTINCT c.location FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1")
    List<String> findByDistictLocation();

    //==================== Category Count=====================
    @Query("SELECT COUNT(c.id)  FROM Jobs c WHERE c.location = :location and c.expireDate > CURRENT_DATE AND c.published = 1")
    Integer findByLocationCount(@Param("location") String location);


    @Query("SELECT m FROM Jobs m WHERE m.profession LIKE  %:profession% AND m.published = 1")
    List<Jobs> findByCategoryLike(String profession);


    @Query(nativeQuery = true, value = "select COUNT(id), category FROM jobs where expire_date > CURRENT_DATE AND published = 1 group by category LIMIT 6")
    Optional<List<Object[]>> findByCategoryCountLimit();

    @Query("SELECT COUNT(c.id), c.category FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1 group by c.category")
    Optional<List<Object[]>> findByCategoryCount();

    @Query("SELECT COUNT(c.id), c.minQualification FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1 group by c.minQualification")
    Optional<List<Object[]>> findByQualificationCount();

    @Query("SELECT DISTINCT c.category FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1")
    List<String> findByCategories();

    @Query("SELECT COUNT(c.id), c.country FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1 group by c.country")
    Optional<List<Object[]>> findByCountryCount();

    @Query(nativeQuery = true, value = "select COUNT(id), country FROM jobs where expire_date > CURRENT_DATE AND c.published = 1 group by country LIMIT 3")
    Optional<List<Object[]>> findCountriesLimit();

    @Query("SELECT DISTINCT c.country FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1")
    List<String> findByCountry();

    Page<Jobs> findByPostedBy(Employer employer, Pageable pageabl);

    List<Jobs> findByProcessed(boolean status);

    List<Jobs> findByAssigned(boolean b);

    List<Jobs> findByAssignedTo(Employee b);

    Page<Jobs> findByJobCity(String b, Pageable pageable);

    Page<Jobs> findByCountry(String b, Pageable pageable);

    @Query("SELECT c  FROM Jobs c WHERE  c.profession = :profession and c.category = :category and c.expireDate > CURRENT_DATE AND c.published = 1")
    List<Jobs> findJobByProfCategory(@Param("profession") String profession, @Param("category") String category, Pageable pageable);

    @Query("SELECT c  FROM Jobs c WHERE  c.profession = :profession and c.category = :category and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByProfessionAndCategory(@Param("profession") String profession, @Param("category") String category, Pageable pageable);

    @Query("SELECT c  FROM Jobs c WHERE  c.profession = :profession and c.category = :category and c.location = :location and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByProfessionAndCategoryAndLocation(@Param("profession") String profession, @Param("category") String category, @Param("location") String location, Pageable pageable);

    @Query("SELECT c  FROM Jobs c WHERE c.category = :category and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByCategories(@Param("category") String profession, Pageable pageable);

    Page<Jobs> findByProfessionAndLocation(String profession, String location, Pageable pageable);

    @Query("SELECT COUNT(c.id), c.nameOfCompany FROM Jobs c where c.showCompanyName=0 AND c.published = 1 and c.expireDate > CURRENT_DATE group by c.nameOfCompany ")
    Optional<List<Object[]>> findByCompanyCount();

    @Query("SELECT c FROM Jobs c where c.nameOfCompany = :company and c.showCompanyName=0 AND c.published = 1")
    Page<Jobs> findByNameOfCompany(@Param("company") String company, Pageable pageable);

    @Query("SELECT c FROM Jobs c where c.minYearsExperience between :start and :end and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByMinYearsExperience(@Param("start") Integer start, @Param("end") Integer end, Pageable pageable);

    @Query("SELECT c FROM Jobs c where c.renumeration between :start and :end and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByRenumeration(BigDecimal start, BigDecimal end, Pageable pageable);

    @Query("SELECT c FROM Jobs c where c.renumeration between :start and :end and c.expireDate > CURRENT_DATE AND c.published = 1")
    List<Jobs> findByRenumeration(BigDecimal start, BigDecimal end);

    @Query("SELECT c FROM Jobs c where c.expireDate > CURRENT_DATE AND c.published = 1 ORDER BY c.expireDate asc")
        //@Query("SELECT c FROM Jobs c  ORDER BY c.id desc")
    Page<Jobs> findAllJobs(Pageable pageable);


    Page<Jobs> findAllByOrderByIdAsc(Pageable pageable);

    Page<Jobs> findAllByOrderByIdDesc(Pageable pageable);

    @Query(nativeQuery = true, value = "select * FROM jobs where expire_date > CURRENT_DATE and published =1 ORDER BY expire_date asc LIMIT 10")
    List<Jobs> findFiveJobs();


    @Query("SELECT count(c.id),c.assignedTo FROM Jobs c GROUP BY c.assignedTo")
    Optional<List<Object[]>> findByAssignedCount();

    @Query("SELECT c FROM Jobs c where c.postedDate between :start and :end")
    Page<Jobs> findByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);


    @Query("SELECT c FROM Jobs c where c.jobStatus = 'Filled' AND c.postedDate between :start and :end ")
    List<Jobs> findByDateAndFilled(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT DISTINCT c.prefSkillsAttribute FROM Jobs c where expire_date > CURRENT_DATE and published =1")
    List<String> findByDistinctPrefSkillsAttribute();

    List<Jobs> findByPrefSkillsAttribute(String prefSkillsAttribute);

    //@Query("SELECT c FROM Jobs c where c.renumeration between :start and :end and c.expireDate > CURRENT_DATE")
    // public Page<Jobs> findByPrefSkillsAttribute(BigDecimal start, BigDecimal end,  Pageable pageable);

    List<Jobs> findByMinQualification(String qual);


    @Query("SELECT COUNT(c.id) FROM Jobs c where c.postedBy = :employer")
    Integer findByPostedByCount(@Param("employer") Employer employer);

    List<Jobs> findByAlertSent(boolean status);

    List<Jobs> findByPublished(boolean b);

    List<Jobs> findByJobStatus(String filled);

    @Query("SELECT c FROM Jobs c where c.category IN (:q) and c.expireDate > CURRENT_DATE AND c.published = 1")
    List<Jobs> findByCategoryIn(@Param("q") List<String> q);

    @Query("SELECT c FROM Jobs c where c.minYearsExperience between :start and :end and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByExperience(@Param("start") Integer start, @Param("end") Integer end, Pageable of);

    @Query("SELECT c FROM Jobs c where c.minYearsExperience >= :exp and c.expireDate > CURRENT_DATE AND c.published = 1")
    Page<Jobs> findByExperienceAbove(@Param("exp") Integer exp, Pageable of);


    Optional<Jobs> findByTransactionId(String id);

    @Query(nativeQuery = true, value = "select * from jobs where transaction_id is null limit 100")
    List<Jobs> findNullTransactions();

    @Query("SELECT c FROM Jobs c where c.renumeration >= :salary and c.expireDate > CURRENT_DATE AND c.published = 1")
    List<Jobs> findByRenumerationAbove(@Param("salary") BigDecimal salary);


////    
////    @Query("SELECT p FROM Jobs p JOIN FETCH p.jobseekers WHERE p.id = (:id)")
////    public Optional<Jobs> findByIdAndFetchJobSeekersEagerly(@Param("id") Integer id);

}