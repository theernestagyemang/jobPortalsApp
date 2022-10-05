/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;


import com.debusey.smart.pos.smartpos.JsfUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Admin
 */
public class MyDateobj {

    private final DayOfWeek firstDayOfWeek;
    private final DayOfWeek lastDayOfWeek;
    private Date morning;
    private Date midnight;
    private LocalDateTime localMording;
    private LocalDateTime localMidnight;
    private Calendar c;
    private List<LocalDate> output;

    public MyDateobj() {
        LocalTime min = LocalTime.MIN;
        LocalTime max = LocalTime.MAX;

        LocalDate today = LocalDate.now();
        localMording = LocalDateTime.of(today, min);
        localMidnight = LocalDateTime.of(today, max);

        morning = JsfUtil.convertToDateViaInstant(localMording);
        midnight = JsfUtil.convertToDateViaInstant(localMidnight);

        this.firstDayOfWeek = WeekFields.of(Locale.UK).getFirstDayOfWeek();
        this.lastDayOfWeek = DayOfWeek.of(((this.firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    }

    public List<LocalDate> getCalendar() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //Get current Day of week and Apply suitable offset to bring the new calendar
        //back to the appropriate Monday, i.e. this week or next
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                c.add(Calendar.DATE, 1);
                break;

            case Calendar.MONDAY:
                //Don't need to do anything on a Monday
                //included only for completeness
                break;

            case Calendar.TUESDAY:
                c.add(Calendar.DATE, -1);
                break;

            case Calendar.WEDNESDAY:
                c.add(Calendar.DATE, -2);
                break;

            case Calendar.THURSDAY:
                c.add(Calendar.DATE, -3);
                break;

            case Calendar.FRIDAY:
                c.add(Calendar.DATE, -4);
                break;

            case Calendar.SATURDAY:
                c.add(Calendar.DATE, 2);
                break;
        }

        //Add the Monday to the output
        //output.add(c.getTime().toString());
        output.add(JsfUtil.convertToDate(c.getTime()));
        for (int x = 1; x < 7; x++) {

            //Add the remaining days to the output
            c.add(Calendar.DATE, 1);
            output.add(JsfUtil.convertToDate(c.getTime()));
        }
        return this.output;
    }

    public Date getMorning() {
        return morning;
    }

    public void setMorning(Date morning) {
        this.morning = morning;
    }

    public Date getMidnight() {
        return midnight;
    }

    public void setMidnight(Date midnight) {
        this.midnight = midnight;
    }

    public LocalDateTime getLocalMording() {
        return localMording;
    }

    public void setLocalMording(LocalDateTime localMording) {
        this.localMording = localMording;
    }

    public LocalDateTime getLocalMidnight() {
        return localMidnight;
    }

    public void setLocalMidnight(LocalDateTime localMidnight) {
        this.localMidnight = localMidnight;
    }

    public List<LocalDate> getOutput() {
        return output;
    }

    public void setOutput(List<LocalDate> output) {
        this.output = output;
    }


    public LocalDate getFirstDay() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek));
    }

    public LocalDate getLastDay() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek));
    }

}

