/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.JsfUtil;

import java.time.LocalDate;

/**
 * @author AlhassanHussein
 */
public class MyDateConverter {
    private LocalDate start;
    private LocalDate end;

    public MyDateConverter() {
    }

    public MyDateConverter(String st, String ed) {
        if ("today".equals(st) || "today".equals(ed)) {

            LocalDate initial = LocalDate.now();
            this.start = initial.withDayOfMonth(1);
            this.end = initial.withDayOfMonth(initial.lengthOfMonth());

        } else {
            this.start = JsfUtil.convertToLocalDate(st, "dd/MM/yyyy");
            this.end = JsfUtil.convertToLocalDate(ed, "dd/MM/yyyy");
        }
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

}
