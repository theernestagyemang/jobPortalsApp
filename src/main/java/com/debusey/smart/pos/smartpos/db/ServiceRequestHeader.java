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
public class ServiceRequestHeader {
    private List<ServiceRequestLines> lines;
    private Integer numberOfElements;
    private Integer totalPages;
    private Integer page;
    private Integer max;

    public ServiceRequestHeader() {
    }

    public ServiceRequestHeader(List<ServiceRequestLines> lines, Integer numberOfElements, Integer totalPages, Integer page, Integer max) {
        this.lines = lines;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.page = page;
        this.max = max;
    }

    public List<ServiceRequestLines> getLines() {
        return lines;
    }

    public void setLines(List<ServiceRequestLines> lines) {
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


}
