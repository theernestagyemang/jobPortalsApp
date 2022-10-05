/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Admin
 */


public class SearchedSeeker {

    private String fullName;
    private String gender;
    private String email;
    private String primaryContact;
    private String proffesionalTitile;
    private String currentLocation;
    private String currentCompany;
    private String employmentType;
    private String keySkills;
    private Date dob;
    private String maritalStatus;
    private String description;
    private String picFileName;
    private Integer id;
    private String yearsOfExperiece;
    private String transactionId;
    private String resume;
    private List<String> jobTitle;


    public SearchedSeeker() {
    }

    public SearchedSeeker(String fullName, String gender, String email, String primaryContact, String proffesionalTitile,
                          String currentLocation, String currentCompany, String employmentType, String keySkills,
                          Date dob, String maritalStatus, String description, String picFileName, Integer id, String yearsOfExperiece, String transactionId, String resume, List<String> jobTitle) {
        this.fullName = fullName;
        this.gender = gender;
        this.yearsOfExperiece = yearsOfExperiece;
        this.email = email;
        this.primaryContact = primaryContact;
        this.proffesionalTitile = proffesionalTitile;
        this.currentLocation = currentLocation;
        this.currentCompany = currentCompany;
        this.employmentType = employmentType;
        this.keySkills = keySkills;
        this.dob = dob;
        this.maritalStatus = maritalStatus;
        this.description = description;
        this.picFileName = picFileName;
        this.id = id;
        this.transactionId = transactionId;
        this.resume = resume;
        this.jobTitle = jobTitle;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getProffesionalTitile() {
        return proffesionalTitile;
    }

    public void setProffesionalTitile(String proffesionalTitile) {
        this.proffesionalTitile = proffesionalTitile;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final SearchedSeeker other = (SearchedSeeker) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }

    public String getYearsOfExperiece() {
        return yearsOfExperiece;
    }

    public void setYearsOfExperiece(String yearsOfExperiece) {
        this.yearsOfExperiece = yearsOfExperiece;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<String> getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(List<String> jobTitle) {
        this.jobTitle = jobTitle;
    }


}
