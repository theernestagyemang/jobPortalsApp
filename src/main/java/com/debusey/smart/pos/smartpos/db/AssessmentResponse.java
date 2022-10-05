/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.List;

/**
 * @author AlhassanHussein
 */
public class AssessmentResponse {
    private Integer id;
    private String title;
    private String academy;
    private List<AssessmentLinesResponse> lines;

    public AssessmentResponse() {
    }

    public AssessmentResponse(Integer id, String title, String academy, List<AssessmentLinesResponse> lines) {
        this.id = id;
        this.title = title;
        this.academy = academy;
        this.lines = lines;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AssessmentLinesResponse> getLines() {
        return lines;
    }

    public void setLines(List<AssessmentLinesResponse> lines) {
        this.lines = lines;
    }


}
