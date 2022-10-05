/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;

/**
 * @author AlhassanHussein
 */
public class ApplicationsResponse {
    private BigDecimal total;

    private BigDecimal shortlisted;
    private BigDecimal onHold;
    private BigDecimal rejected;


    private BigDecimal shortlistedPct;
    private BigDecimal onHoldPct;
    private BigDecimal rejectedPct;
    private int jan, feb, mar, apr, may, june, jul;
    private int s_jan, s_feb, s_mar, s_apr, s_may, s_june, s_jul;
    private int r_jan, r_feb, r_mar, r_apr, r_may, r_june, r_jul;

    private int h_jan, h_feb, h_mar, h_apr, h_may, h_june, h_jul;


    public ApplicationsResponse() {
    }

    public ApplicationsResponse(BigDecimal total, BigDecimal shortlisted, BigDecimal onHold, BigDecimal rejected) {
        this.total = total;
        this.shortlisted = shortlisted;
        this.onHold = onHold;
        this.rejected = rejected;

        if (this.shortlisted == null) {
            this.shortlisted = BigDecimal.ZERO;
        }

        if (this.onHold == null) {
            this.onHold = BigDecimal.ZERO;
        }

        if (this.rejected == null) {
            this.rejected = BigDecimal.ZERO;
        }

        if (this.total == null || this.total.compareTo(BigDecimal.ZERO) == 0) {
            this.shortlistedPct = BigDecimal.ZERO;
            this.rejectedPct = BigDecimal.ZERO;
            this.onHoldPct = BigDecimal.ZERO;
        } else {
            this.shortlistedPct = (this.shortlisted.divide(this.total)).multiply(BigDecimal.valueOf(100));
            this.rejectedPct = (this.rejected.divide(this.total)).multiply(BigDecimal.valueOf(100));
            this.onHoldPct = (this.onHold.divide(this.total)).multiply(BigDecimal.valueOf(100));
        }


    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(BigDecimal shortlisted) {
        this.shortlisted = shortlisted;
    }

    public BigDecimal getOnHold() {
        return onHold;
    }

    public void setOnHold(BigDecimal onHold) {
        this.onHold = onHold;
    }

    public BigDecimal getRejected() {
        return rejected;
    }

    public void setRejected(BigDecimal rejected) {
        this.rejected = rejected;
    }

    public BigDecimal getShortlistedPct() {
        return shortlistedPct;
    }

    public void setShortlistedPct(BigDecimal shortlistedPct) {
        this.shortlistedPct = shortlistedPct;
    }

    public BigDecimal getOnHoldPct() {
        return onHoldPct;
    }

    public void setOnHoldPct(BigDecimal onHoldPct) {
        this.onHoldPct = onHoldPct;
    }

    public BigDecimal getRejectedPct() {
        return rejectedPct;
    }

    public void setRejectedPct(BigDecimal rejectedPct) {
        this.rejectedPct = rejectedPct;
    }

    public int getJan() {
        return jan;
    }

    public void setJan(int jan) {
        this.jan = jan;
    }

    public int getFeb() {
        return feb;
    }

    public void setFeb(int feb) {
        this.feb = feb;
    }

    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int getApr() {
        return apr;
    }

    public void setApr(int apr) {
        this.apr = apr;
    }

    public int getMay() {
        return may;
    }

    public void setMay(int may) {
        this.may = may;
    }

    public int getJune() {
        return june;
    }

    public void setJune(int june) {
        this.june = june;
    }

    public int getJul() {
        return jul;
    }

    public void setJul(int jul) {
        this.jul = jul;
    }


    public int getS_jan() {
        return s_jan;
    }

    public void setS_jan(int s_jan) {
        this.s_jan = s_jan;
    }

    public int getS_feb() {
        return s_feb;
    }

    public void setS_feb(int s_feb) {
        this.s_feb = s_feb;
    }

    public int getS_mar() {
        return s_mar;
    }

    public void setS_mar(int s_mar) {
        this.s_mar = s_mar;
    }

    public int getS_apr() {
        return s_apr;
    }

    public void setS_apr(int s_apr) {
        this.s_apr = s_apr;
    }

    public int getS_may() {
        return s_may;
    }

    public void setS_may(int s_may) {
        this.s_may = s_may;
    }

    public int getS_june() {
        return s_june;
    }

    public void setS_june(int s_june) {
        this.s_june = s_june;
    }

    public int getS_jul() {
        return s_jul;
    }

    public void setS_jul(int s_jul) {
        this.s_jul = s_jul;
    }


    public int getR_jan() {
        return r_jan;
    }

    public void setR_jan(int r_jan) {
        this.r_jan = r_jan;
    }

    public int getR_feb() {
        return r_feb;
    }

    public void setR_feb(int r_feb) {
        this.r_feb = r_feb;
    }

    public int getR_mar() {
        return r_mar;
    }

    public void setR_mar(int r_mar) {
        this.r_mar = r_mar;
    }

    public int getR_apr() {
        return r_apr;
    }

    public void setR_apr(int r_apr) {
        this.r_apr = r_apr;
    }

    public int getR_may() {
        return r_may;
    }

    public void setR_may(int r_may) {
        this.r_may = r_may;
    }

    public int getR_june() {
        return r_june;
    }

    public void setR_june(int r_june) {
        this.r_june = r_june;
    }

    public int getR_jul() {
        return r_jul;
    }

    public void setR_jul(int r_jul) {
        this.r_jul = r_jul;
    }


    public int getH_jan() {
        return h_jan;
    }

    public void setH_jan(int h_jan) {
        this.h_jan = h_jan;
    }

    public int getH_feb() {
        return h_feb;
    }

    public void setH_feb(int h_feb) {
        this.h_feb = h_feb;
    }

    public int getH_mar() {
        return h_mar;
    }

    public void setH_mar(int h_mar) {
        this.h_mar = h_mar;
    }

    public int getH_apr() {
        return h_apr;
    }

    public void setH_apr(int h_apr) {
        this.h_apr = h_apr;
    }

    public int getH_may() {
        return h_may;
    }

    public void setH_may(int h_may) {
        this.h_may = h_may;
    }

    public int getH_june() {
        return h_june;
    }

    public void setH_june(int h_june) {
        this.h_june = h_june;
    }

    public int getH_jul() {
        return h_jul;
    }

    public void setH_jul(int h_jul) {
        this.h_jul = h_jul;
    }


}
