/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.JsfUtil;
import com.debusey.smart.pos.smartpos.entity.JobSeeker;

import java.math.BigDecimal;

/**
 * @author AlhassanHussein
 */
public class PersonalDetails {
    private String fullName;
    private String profTitle;
    private String languages;
    private String dob;
    private double curSalary;
    private double extMinSalary;
    private double extMaxSalary;
    private String phone;
    private String yearsOfExperience;

    private String educationLevel;
    private String maritalStatus;
    private String highestPosition;
    private String email;
    private String country;
    private String postCode;
    private String city;
    private String address;

    private String gender;
    private String facebook;
    private String twitter;
    private String linkedIn;
    private String google;

    public PersonalDetails() {
    }

    public PersonalDetails(String fullName, String profTitle, String languages, String dob, double curSalary, double extMinSalary, double extMaxSalary, String phone, String yearsOfExperience, String educationLevel, String maritalStatus, String highestPosition, String email, String country, String postCode, String city, String address, String gender, String facebook, String twitter, String linkedIn, String google) {
        this.fullName = fullName;
        this.profTitle = profTitle;
        this.languages = languages;
        this.dob = dob;
        this.curSalary = curSalary;
        this.extMinSalary = extMinSalary;
        this.extMaxSalary = extMaxSalary;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.educationLevel = educationLevel;
        this.maritalStatus = maritalStatus;
        this.highestPosition = highestPosition;
        this.email = email;
        this.country = country;
        this.postCode = postCode;
        this.city = city;
        this.address = address;
        this.gender = gender;
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedIn = linkedIn;
        this.google = google;
    }

    public PersonalDetails(JobSeeker seeker) {
        this.fullName = seeker.getFullName();
        this.profTitle = seeker.getProffesionalTitile();
        this.languages = seeker.getSpokenLanguages();
        this.dob = JsfUtil.convertFromDate(seeker.getDob(), "dd/MM/yyyy");
        this.curSalary = getSalary(seeker.getCurrentSalary());
        this.extMinSalary = getSalary(seeker.getExpMinSalary());
        this.extMaxSalary = getSalary(seeker.getExpectedSalary());
        this.phone = seeker.getPrimaryContact();
        this.yearsOfExperience = seeker.getYearsOfExperience();
        this.educationLevel = seeker.getEducationLevel();
        this.maritalStatus = seeker.getMaritalStatus();
        this.highestPosition = seeker.getHighestQualification();
        this.email = seeker.getEmail();
        this.country = seeker.getCountryOfOrigin();
        this.postCode = seeker.getPostcode();
        this.city = "";
        this.address = seeker.getAddress();
        this.gender = seeker.getGender();
        this.facebook = seeker.getFacebook();
        this.twitter = seeker.getTwitter();
        this.linkedIn = seeker.getLinkedIn();
        this.google = seeker.getGoogle();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfTitle() {
        return profTitle;
    }

    public void setProfTitle(String profTitle) {
        this.profTitle = profTitle;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getCurSalary() {
        return curSalary;
    }

    public void setCurSalary(double curSalary) {
        this.curSalary = curSalary;
    }

    public double getExtMinSalary() {
        return extMinSalary;
    }

    public void setExtMinSalary(double extMinSalary) {
        this.extMinSalary = extMinSalary;
    }

    public double getExtMaxSalary() {
        return extMaxSalary;
    }

    public void setExtMaxSalary(double extMaxSalary) {
        this.extMaxSalary = extMaxSalary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHighestPosition() {
        return highestPosition;
    }

    public void setHighestPosition(String highestPosition) {
        this.highestPosition = highestPosition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    private double getSalary(BigDecimal salary) {

        if (salary == null) {
            return 0;
        }
        return salary.doubleValue();
    }


}
