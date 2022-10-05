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
public class JobDetails {
    private String companyName;
    private String companyPicture;
    private String companyAddress;
    private String companyWebsite;
    private String companyPhone;
    private String companyEmail;
    private String natureOfContract;
    private BigDecimal renumeration;
    private BigDecimal toRenumeration;
    private String location;
    private String category;

    public JobDetails() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(String companyPicture) {
        this.companyPicture = companyPicture;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getNatureOfContract() {
        return natureOfContract;
    }

    public void setNatureOfContract(String natureOfContract) {
        this.natureOfContract = natureOfContract;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getRenumeration() {
        return renumeration;
    }

    public void setRenumeration(BigDecimal renumeration) {
        this.renumeration = renumeration;
    }

    public BigDecimal getToRenumeration() {
        return toRenumeration;
    }

    public void setToRenumeration(BigDecimal toRenumeration) {
        this.toRenumeration = toRenumeration;
    }


}
