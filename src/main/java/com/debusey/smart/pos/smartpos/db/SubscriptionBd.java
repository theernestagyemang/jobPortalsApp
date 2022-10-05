/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Objects;

/**
 * @author Admin
 */
public class SubscriptionBd {
    //Transaction ID	Package	Expiry	Total Jobs/CVs	Used	Remaining	Status
    private Integer id;
    private String packageName;
    private String expiry;
    private Integer totalCv;
    private Integer used;
    private Integer remaining;
    private String status;

    public SubscriptionBd() {
    }

    public SubscriptionBd(Integer id, String packageName, String expiry, Integer totalCv, Integer used, Integer remaining, String status) {
        this.id = id;
        this.packageName = packageName;
        this.expiry = expiry;
        this.totalCv = totalCv;
        this.used = used;
        this.remaining = remaining;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Integer getTotalCv() {
        return totalCv;
    }

    public void setTotalCv(Integer totalCv) {
        this.totalCv = totalCv;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubscriptionBd other = (SubscriptionBd) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return packageName;
    }


}
