/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.controller;


import com.debusey.smart.pos.smartpos.entity.JobSeeker;
import com.debusey.smart.pos.smartpos.search.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * MainController class
 *
 * @author netgloo
 */
@Controller
public class Search {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // Inject the UserSearch object
    @Autowired
    private UserSearch userSearch;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Index main page.
     */
    @RequestMapping("/ft")
    @ResponseBody
    public String index() {
        return "Try to go here: " +
                "<a href='/search?q=hola'>/search?q=hola</a>";
    }


    /**
     * Show search results for the given query.
     *
     * @param q The search query.
     */


    @RequestMapping("/find")
    @ResponseBody
    public List<JobSeeker> find(String q) {
        List<JobSeeker> searchResults = null;
        try {
            searchResults = userSearch.search(q);

            return searchResults;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    @RequestMapping("/find-prof")
    @ResponseBody
    public List<String> findProf(String q) {
        List<String> searchResults = null;
        try {
            searchResults = userSearch.findProfessions(q);

            return searchResults;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    //
} // class MainController
