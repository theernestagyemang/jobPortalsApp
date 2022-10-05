/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.search;


import com.debusey.smart.pos.smartpos.entity.*;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Search methods for the entity User using Hibernate search.
 * The Transactional annotation ensure that transactions will be opened and
 * closed at the beginning and at the end of each method.
 *
 * @author netgloo
 */
@Repository
@Transactional
public class UserSearch {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------


    public List<JobSeeker> search(String text) {

        // get the full text entity manager
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<JobSeeker> result = searchSession.search(JobSeeker.class)
                .where(f -> f.match()
                        .fields("fullName", "currentLocation", "educationLevel", "proffesionalTitile", "highestQualification")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<JobSeeker> hits = result.hits();

        return hits;
    }


    public List<JobSeeker> searchMultipleTerm(String keywords) {
        String[] arrKeywords = keywords.split(",");
        return findByList(Arrays.asList(arrKeywords));
    }

    public List<JobSeeker> searchMultipleTerm(String keywords, Integer page, Integer max) {
        String[] arrKeywords = keywords.split(",");
        return findByList(Arrays.asList(arrKeywords));
    }

    public List<WorkExperience> searchWorkExperienceMultipleTerm(List<String> keywords, Integer page, Integer max) {
        //String[] arrKeywords = keywords.split(",");
        return findWorkExperienceByList(keywords, page, max);
    }

    public List<JobSeeker> findByList(List<String> text) {


        SearchSession searchSession = Search.session(entityManager);

        SearchResult<JobSeeker> result = searchSession.search(JobSeeker.class)
                .where(f -> f.match()
                        .fields("fullName", "currentLocation", "educationLevel", "proffesionalTitile", "highestQualification", "yearsOfExperience", "primaryContact")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<JobSeeker> hits = result.hits();

        return hits;


    } // method search


//  public List<JobSeeker> findByList (List<String> text,Integer page,Integer max) {
//        
//      // get the full text entity manager
//    FullTextEntityManager fullTextEntityManager =org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
//    
//    // create the query using Hibernate Search query DSL
//    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(JobSeeker.class).get();
//    
//    List<Query> queryList = new LinkedList<Query>();
//        Query query = null;
//
//        for (String keyword : text) {
//            query = queryBuilder.keyword().
//                    fields("fullName", "currentLocation", "educationLevel","proffesionalTitile","highestQualification","yearsOfExperience","primaryContact").
//                    matching(keyword).createQuery();
//            queryList.add(query);
//        }
//
//        BooleanQuery finalQuery = new BooleanQuery();
//        queryList.forEach((q) -> {
//            finalQuery.add(q, Occur.MUST);
//      });
//        
//        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, JobSeeker.class);
//       
//        //fullTextQuery.setProjection("fullName", "currentLocation", "educationLevel","proffesionalTitile","highestQualification");
//        fullTextQuery.setFirstResult(page-1); //start from the 15th element
//        fullTextQuery.setMaxResults(max);
//       
//       List<JobSeeker> list= fullTextQuery.getResultList();
//       //Pageable pageable= PageRequest.of(page, max, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "proffesionalTitile"));
//      // Page<JobSeeker> pageList= new PageImpl<>(list ,pageable,list.size());
//       
//       return list;
//       
//    
//  } 

    public List<WorkExperience> findWorkExperienceByList(List<String> text, Integer page, Integer max) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<WorkExperience> result = searchSession.search(WorkExperience.class)
                .where(f -> f.match()
                        .fields("jobTitle", "companyName", "designation")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<WorkExperience> hits = result.hits();

        return hits;

    }

// method search
//  public List<JobSeeker> searchPaginated(String text,Integer min,Integer max) {
//    return results;
//  } // method search

    public List<String> findProfessions(String keywords) {
        // Must be retrieved inside a transaction to take part of
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Jobs> result = searchSession.search(Jobs.class)
                .where(f -> f.match()
                        .fields("jobTitle", "country", "jobCountry", "jobCity", "nameOfCompany", "profession", "location", "category", "minQualification", "region")
                        .matching(keywords).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<Jobs> list = result.hits();


        List<String> prof = new ArrayList<>();
        list.stream().forEach((item) -> {
            prof.add(item.getProfession());
        });

        return prof;
    }


//  public List<Jobs> searchByKeyword(final String keywords,Integer page,Integer max) {
//        // Must be retrieved inside a transaction to take part of
//        final FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
//
//        // Prepare a search query builder
//        final QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Jobs.class).get();
//        
//       
//                    
//
//        // This is a boolean junction... I'll add at least a keyword query
//        final BooleanJunction<BooleanJunction> outer = queryBuilder.bool();
//        outer.must(
//                queryBuilder
//                .keyword().fuzzy()
//                .fields("jobTitle", "country", "jobCountry","jobCity","nameOfCompany","profession","location","category","minQualification","region")
//                .matching(keywords)
//                .createQuery()
//        );
//
////        
//        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(outer.createQuery(), Jobs.class);
//        
//        fullTextQuery.setFirstResult(page); //start from the 15th element
//        fullTextQuery.setMaxResults(max);
//        
//        return fullTextQuery.getResultList();
//    }

    public List<Jobs> find(String text) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Jobs> result = searchSession.search(Jobs.class)
                .where(f -> f.match()
                        .fields("jobTitle", "country", "jobCountry", "jobCity", "nameOfCompany", "profession", "location", "category")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<Jobs> list = result.hits();


        return list;
    }


    public List<Positions> searchPositions(String text) {

        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Positions> result = searchSession.search(Positions.class)
                .where(f -> f.match()
                        .fields("name")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<Positions> list = result.hits();

        return list;
    }


    //list of Employers
    public List<Employer> searchEmployers(String text) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Employer> result = searchSession.search(Employer.class)
                .where(f -> f.match()
                        .fields("companyName", "email", "phoneNumber")
                        .matching(text).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<Employer> list = result.hits();

        return list;

    } // method search

//  public List<Employer> searchPaginatedEmployers(String text,Integer first,Integer max) {
//    
//    return results;
//  } 

    public List<Employer> searchPaginatedEmployers(String q, Integer page, Integer max) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Employer> result = searchSession.search(Employer.class)
                .where(f -> f.match()
                        .fields("companyName", "email", "phoneNumber")
                        .matching(q).fuzzy())
                .fetch(50);

        long totalHitCount = result.total().hitCount();
        List<Employer> list = result.hits();

        return list;
    }

    public List<Jobs> searchByKeyword(String q, Integer page, int i) {
        return find(q);
    }

    public List<JobSeeker> searchPaginated(String q, Integer page, Integer max) {
        return search(q);
    }

} // class
