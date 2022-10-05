/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Employee;
import com.debusey.smart.pos.smartpos.entity.Employer;
import com.debusey.smart.pos.smartpos.entity.Jobs;
import com.debusey.smart.pos.smartpos.repo.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author SL002
 */

@Service
public class JobsService {

    @Autowired
    public JobsRepository repository;


    public List<Jobs> findFiveJobs() {
        return repository.findFiveJobs();
    }

    public Page<Jobs> findAll(Integer page, Integer max) {
        return repository.findAllJobs(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<Jobs> findAllByOrderByIdDesc(Integer page, Integer max) {
        return repository.findAllByOrderByIdDesc(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<Jobs> findAllByOrderByIdAsc(Integer page, Integer max) {
        return repository.findAllByOrderByIdAsc(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }


    public List<Jobs> findAll() {
        List<Jobs> items = new ArrayList<>();
        repository.findAll().forEach(items::add);
        return items;
    }

    public List<Jobs> findByCategoryLike(String profession) {

        return repository.findByCategoryLike(profession);

    }

    public boolean save(Jobs t) {
        try {
            repository.save(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean saveAll(List<Jobs> t) {

        try {
            repository.saveAll(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Jobs t) {
        try {
            repository.delete(t);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteById(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Optional<Jobs> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<Jobs> findByTransactionId(String id) {
        return repository.findByTransactionId(id);
    }

    public List<Jobs> findJobByCategoryAndLocation(String profession, String location, String category) {
        return repository.findJobByCategoryAndLocation(profession, location, category);
    }

    public List<Jobs> findJobByProfCategory(String profession, String category) {
        PageRequest pg = PageRequest.of(0, 10);
        return repository.findJobByProfCategory(profession, category, pg);
    }

    public Optional<List<Object[]>> findByCategoryCount() {
        return repository.findByCategoryCount();
    }

    public Optional<List<Object[]>> findByQualificationCount() {
        return repository.findByQualificationCount();
    }

    public Optional<List<Object[]>> findByCategoryCountLimit() {
        return repository.findByCategoryCountLimit();
    }

    public Optional<List<Object[]>> findByCountryCount() {
        return repository.findByCountryCount();
    }

    public Optional<List<Object[]>> findCountriesLimit() {
        return repository.findByCountryCount();
    }


    public List<String> findByCategories() {
        return repository.findByCategories();
    }

    public Page<Jobs> findByEmployer(Employer employer, Integer page, Integer max) {
        return repository.findByPostedBy(employer, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "expireDate")));
    }

    public List<Jobs> findByProcessed(boolean status) {
        return repository.findByProcessed(status);
    }

    public List<Jobs> findByAssigned(boolean b) {
        return repository.findByAssigned(b);
    }

    public List<Jobs> findByAssignedTo(Employee b) {
        return repository.findByAssignedTo(b);
    }

//    public List<Jobs> findByCategory(String b) {
//        return repository.findByCategory(b);
//    }

    public Page<Jobs> findByCountry(String b, Integer page) {
        return repository.findByCountry(b, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public Page<Jobs> findByJobCity(String b, Integer page) {
        return repository.findByJobCity(b, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public Page<Jobs> findByProfession(String b, Integer page) {
        return repository.findByProfession(PageRequest.of(page - 1, 10, Sort.by("profession")), b);
    }


    //==============Defs==============


    public List<String> findByDistictProfession() {
        return repository.findByDistictProfession();
    }

    public Integer findByProfessionCount(String profession) {
        return repository.findByProfessionCount(profession);
    }

    public List<String> findByDistictCategory() {
        return repository.findByDistictCategory();
    }

    public Integer findByCategoryCount(String category) {
        return repository.findByCategoryCount(category);
    }

    public List<Jobs> findByLocation(String b) {
        return repository.findByLocation(b);
    }

    public List<String> findByDistictLocation() {
        return repository.findByDistictLocation();
    }

    public Integer findByLocationCount(String location) {
        return repository.findByLocationCount(location);
    }


    public Page<Jobs> findByProfessionAndCategory(String profession, String category, Integer page) {
        PageRequest pg = PageRequest.of(page - 1, 10);
        return repository.findByProfessionAndCategory(profession, category, pg);
    }

    public Page<Jobs> findByProfessionCategoryLocation(String profession, String location, String category, Integer page) {
        PageRequest pg = PageRequest.of(page - 1, 10);
        return repository.findByProfessionAndCategoryAndLocation(profession, category, location, pg);
    }

    public Page<Jobs> findByCategory(String category, Integer page) {
        PageRequest pg = PageRequest.of(page - 1, 10);
        return repository.findByCategory(category, pg);
    }

    public Page<Jobs> findByPublishedCategory(String category, Integer page) {
        PageRequest pg = PageRequest.of(page - 1, 10);
        return repository.findByPublishedCategory(category, pg);
    }

    public Page<Jobs> findByPublishedCategory(String category, Integer page, Integer max) {
        PageRequest pg = PageRequest.of(page - 1, max);
        return repository.findByPublishedCategory(category, pg);
    }

    public Page<Jobs> findByPublishedQualification(String category, Integer page) {
        PageRequest pg = PageRequest.of(page - 1, 10);
        return repository.findByPublishedQualification(category, pg);
    }

    public List<Jobs> findByPreferedSkills(String category) {
        return repository.findByPrefSkillsAttribute(category);
    }

    public Page<Jobs> findByProfessionAndLocation(String profession, String location, Integer page) {

//        if(location.equalsIgnoreCase("All Locations")){
//            PageRequest pg = PageRequest.of(page, 10);
//            return repository.findByProfession(pg,profession);
//        }

        if (location.equalsIgnoreCase("All Locations") & (profession.equalsIgnoreCase("All Functions"))) {

            return findAll(page - 1, 10);
        }
        return repository.findByProfessionAndLocation(profession, location, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public List<String> findByCountry() {
        return repository.findByCountry();
    }

    public Optional<List<Object[]>> findByCompanyCount() {
        return repository.findByCompanyCount();
    }

    public Page<Jobs> findByNameOfCompany(String company, int page) {
        return repository.findByNameOfCompany(company, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public Page<Jobs> findByMinYearsExperience(Integer start, Integer end, int page) {
        return repository.findByMinYearsExperience(start, end, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public Page<Jobs> findByRenumeration(BigDecimal start, BigDecimal end, int page) {
        return repository.findByRenumeration(start, end, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public List<Jobs> findByRenumeration(BigDecimal start, BigDecimal end) {
        return repository.findByRenumeration(start, end);
    }

    public Page<Jobs> findByExperience(Integer start, Integer end, int page) {
        return repository.findByExperience(start, end, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }

    public Page<Jobs> findByExperienceAbove(Integer start, int page) {
        return repository.findByExperienceAbove(start, PageRequest.of(page - 1, 10, Sort.by("profession")));
    }


    public Optional<List<Object[]>> findByAssignedCount() {
        return repository.findByAssignedCount();
    }


    public Page<Jobs> findByDate(Date st, Date end, Integer page, Integer max) {
        return repository.findByDate(st, end, PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "expireDate")));
    }

    public List<Jobs> findByDateAndFilled(Date st, Date end) {
        return repository.findByDateAndFilled(st, end);
    }

    public List<String> findByDistinctPrefSkillsAttribute() {
        return repository.findByDistinctPrefSkillsAttribute();
    }

    public Integer findByCategory(String category) {
        return repository.findByCategoryCount(category);
    }

    public List<Jobs> findByMinQualification(String qual) {
        return repository.findByMinQualification(qual);
    }

    public List<Jobs> findByAlertSent(boolean status) {
        return repository.findByAlertSent(status);
    }

    public Integer findByPostedByCount(Employer employer) {
        return repository.findByPostedByCount(employer);
    }

    public Integer getPostedJobs(String posted) {
        List<Jobs> job = new ArrayList<>();
        switch (posted) {
            case "posted":
                job = repository.findByPublished(true);
                break;
            default:
                job = repository.findByJobStatus("Filled");
                break;
        }

        return job.size();
    }

    public Page<String> findByDistinctCategoryPaginated(Integer page, Integer max) {
        return repository.findByDistinctCategoryPaginated(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "category")));
    }

    public List<Jobs> findByCategoryIn(List<String> q) {
        return repository.findByCategoryIn(q);
    }

    public List<String> findFourCategories() {
        return repository.findFourCategories();
    }

    public List<Jobs> findNullTransactions() {
        return repository.findNullTransactions();
    }


    public List<Jobs> findByRenumerationAbove(BigDecimal salary) {
        return repository.findByRenumerationAbove(salary);
    }


}

