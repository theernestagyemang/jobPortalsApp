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
public class ActiveJobHeader {
    private Long id;
    private List<ActiveJob> lines;
    private Integer year;

    public ActiveJobHeader() {
    }

    public ActiveJobHeader(Long id, List<ActiveJob> lines, Integer year) {
        this.id = id;
        this.lines = lines;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<ActiveJob> getLines() {
        return lines;
    }

    public void setLines(List<ActiveJob> lines) {
        this.lines = lines;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


}
