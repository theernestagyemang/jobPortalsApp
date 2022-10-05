/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos;


/**
 * @author Admin
 */
public class WordObject {

    private String category;
    private String company;
    private String text;

    private int lineCount;
    private int sectionCount;
    private int slideCount;

    public WordObject() {
    }

    public WordObject(String text) {
        this.text = text;
    }

    public WordObject(String category, String company, int lineCount, int sectionCount, int slideCount) {
        this.category = category;
        this.company = company;
        this.lineCount = lineCount;
        this.sectionCount = sectionCount;
        this.slideCount = slideCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }


}

