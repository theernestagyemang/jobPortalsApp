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
public class RecruitersHeader {
    private long totalElement;
    private Integer totalPages;
    private Integer max;
    private Integer page;
    private List<Recruiters> lines;

    public RecruitersHeader() {
    }

    public RecruitersHeader(long totalElement, Integer totalPages, Integer max, Integer page, List<Recruiters> lines) {
        this.totalElement = totalElement;
        this.totalPages = totalPages;
        this.max = max;
        this.page = page;
        this.lines = lines;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Recruiters> getLines() {
        return lines;
    }

    public void setLines(List<Recruiters> lines) {
        this.lines = lines;
    }


}
