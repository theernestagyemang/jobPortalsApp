/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author Admin
 */
public class Address {
    private Integer id;
    private String companyAddress, adabrakaEmail, hodEmail;

    public Address() {
    }

    public Address(Integer id, String companyAddress, String adabrakaEmail, String hodEmail) {
        this.id = id;
        this.companyAddress = companyAddress;
        this.adabrakaEmail = adabrakaEmail;
        this.hodEmail = hodEmail;
    }

    public Address(Integer id, String companyAddress, String hodEmail) {
        this.id = id;
        this.companyAddress = companyAddress;
        this.hodEmail = hodEmail;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getAdabrakaEmail() {
        return adabrakaEmail;
    }

    public void setAdabrakaEmail(String adabrakaEmail) {
        this.adabrakaEmail = adabrakaEmail;
    }

    public String getHodEmail() {
        return hodEmail;
    }

    public void setHodEmail(String hodEmail) {
        this.hodEmail = hodEmail;
    }


}
