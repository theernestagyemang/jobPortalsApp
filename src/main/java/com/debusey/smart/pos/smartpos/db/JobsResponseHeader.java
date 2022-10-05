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
public class JobsResponseHeader {
    private Integer numberOfElements;
    private Integer totalPages;
    private Integer page;
    private Integer max;
    private List<JobsResponse> lines;

    public JobsResponseHeader() {
    }

    public JobsResponseHeader(Integer numberOfElements, Integer totalPages, Integer page, Integer max, List<JobsResponse> lines) {
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.page = page;
        this.max = max;
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

    public List<JobsResponse> getLines() {
        return lines;
    }

    public void setLines(List<JobsResponse> lines) {
        this.lines = lines;
    }


}
