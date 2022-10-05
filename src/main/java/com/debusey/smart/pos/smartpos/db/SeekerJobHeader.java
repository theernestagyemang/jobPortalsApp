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
public class SeekerJobHeader {
    private Integer page;
    private Integer max;
    private List<SeekerJob> lines;
    private Integer numberOfElements;
    private Integer totalPages;
    private Integer number;

    public SeekerJobHeader() {
    }

    public SeekerJobHeader(Integer page, Integer max, List<SeekerJob> lines, Integer numberOfElements, Integer totalPages, Integer number) {
        this.page = page;
        this.max = max;
        this.lines = lines;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.number = number;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public List<SeekerJob> getLines() {
        return lines;
    }

    public void setLines(List<SeekerJob> lines) {
        this.lines = lines;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
