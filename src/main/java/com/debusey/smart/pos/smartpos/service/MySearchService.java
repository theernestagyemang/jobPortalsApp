/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;


/**
 * @author Admin
 */

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class MySearchService {


    private final EntityManager entityManager;

    @Autowired
    public MySearchService(final EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    @PostConstruct
    public void initializeHibernateSearch() {

        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer()
                    .startAndWait();


        } catch (InterruptedException ex) {
            Logger.getLogger(MySearchService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
