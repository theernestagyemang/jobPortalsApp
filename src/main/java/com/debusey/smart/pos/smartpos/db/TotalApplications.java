/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author AlhassanHussein
 */
public class TotalApplications {
    private float totalApplication;
    private float shortlisted;
    private float onHold;
    private float rejected;
    private float hired;
    //PCT

    private BigDecimal pctShortlisted;
    private BigDecimal pctOnHold;
    private BigDecimal pctRejected;
    private BigDecimal pctHired;

    public TotalApplications() {
    }

    public TotalApplications(float totalApplication, float shortlisted, float onHold, float rejected, float hired) {
        this.totalApplication = totalApplication;
        this.shortlisted = shortlisted;
        this.onHold = onHold;
        this.rejected = rejected;
        this.hired = hired;

        this.pctShortlisted = pct(shortlisted, totalApplication);
        this.pctOnHold = pct(onHold, totalApplication);
        this.pctRejected = pct(rejected, totalApplication);
        this.pctHired = pct(hired, totalApplication);
    }

    public BigDecimal pct(float val, float total) {
        if (total == 0) {
            return BigDecimal.ZERO;
        }
        float pc = (val / total) * 100;
        return new BigDecimal(pc).setScale(2, RoundingMode.HALF_UP);
    }

    public Integer pct(Integer applicationType, Integer total) {
        if (total == 0) {
            return 0;
        }
        System.out.println("applicationType " + applicationType);
        System.out.println("total " + total);
        int pct = (applicationType / total) * 100;

        System.out.println("pct " + pct);

        return (applicationType / total) * 100;
    }

    public float getTotalApplication() {
        return totalApplication;
    }

    public void setTotalApplication(float totalApplication) {
        this.totalApplication = totalApplication;
    }

    public float getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(float shortlisted) {
        this.shortlisted = shortlisted;
    }

    public float getOnHold() {
        return onHold;
    }

    public void setOnHold(float onHold) {
        this.onHold = onHold;
    }

    public float getRejected() {
        return rejected;
    }

    public void setRejected(float rejected) {
        this.rejected = rejected;
    }

    public float getHired() {
        return hired;
    }

    public void setHired(float hired) {
        this.hired = hired;
    }

    public BigDecimal getPctShortlisted() {
        return pctShortlisted;
    }

    public void setPctShortlisted(BigDecimal pctShortlisted) {
        this.pctShortlisted = pctShortlisted;
    }

    public BigDecimal getPctOnHold() {
        return pctOnHold;
    }

    public void setPctOnHold(BigDecimal pctOnHold) {
        this.pctOnHold = pctOnHold;
    }

    public BigDecimal getPctRejected() {
        return pctRejected;
    }

    public void setPctRejected(BigDecimal pctRejected) {
        this.pctRejected = pctRejected;
    }

    public BigDecimal getPctHired() {
        return pctHired;
    }

    public void setPctHired(BigDecimal pctHired) {
        this.pctHired = pctHired;
    }


}
