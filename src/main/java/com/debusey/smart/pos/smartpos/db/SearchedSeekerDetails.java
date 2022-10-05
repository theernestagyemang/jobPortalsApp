/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author AlhassanHussein
 */
public class SearchedSeekerDetails {
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
    private String resume, profileSummary;
    private String homeTown, countryOfOrigin;
    private String facebook, google;
    private String twitter, linkedIn;

    private List<Education> edulist;
    private List<WorkEx> wlist;
    private List<Skills> skillslist;
    private List<AwardsDb> awardList;
    private BigDecimal expectedSalary;
    private String educationLevel;
    private String industry;
    private String yearsOfExperience;
    private String highestQualification;
    private String blacklisted;
    private String blacklistComment;

    public SearchedSeekerDetails(String fullName, String gender, String email, String primaryContact, String proffesionalTitile,
                                 String currentLocation, String currentCompany, String employmentType, String keySkills,
                                 Date dob, String maritalStatus, String description, String picFileName, Integer id, String yearsOfExperiece, String transactionId, String resume,
                                 List<Education> edulist, List<WorkEx> wlist, List<Skills> skillslist, List<AwardsDb> awardList,
                                 BigDecimal expectedSalary, String educationLevel, String industry, String yearsOfExperience, String highestQualification, String profileSummary,
                                 String homeTown, String countryOfOrigin, String google, String facebook, String twitter, String linkedIn, boolean blacklist, String blacklistComment) {
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

        this.edulist = edulist;
        this.wlist = wlist;
        this.skillslist = skillslist;
        this.awardList = awardList;
        this.expectedSalary = expectedSalary;
        this.educationLevel = educationLevel;
        this.industry = industry;
        this.yearsOfExperience = yearsOfExperience;
        this.highestQualification = highestQualification;
        this.profileSummary = profileSummary;
        this.homeTown = homeTown;
        this.countryOfOrigin = countryOfOrigin;
        this.google = google;

        this.twitter = twitter;
        this.facebook = facebook;
        this.blacklistComment = blacklistComment;
        this.blacklisted = blacklist ? "Yes" : "No";
        this.linkedIn = linkedIn;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Education> getEdulist() {
        return edulist;
    }

    public void setEdulist(List<Education> edulist) {
        this.edulist = edulist;
    }

    public List<WorkEx> getWlist() {
        return wlist;
    }

    public void setWlist(List<WorkEx> wlist) {
        this.wlist = wlist;
    }

    public List<Skills> getSkillslist() {
        return skillslist;
    }

    public void setSkillslist(List<Skills> skillslist) {
        this.skillslist = skillslist;
    }

    public List<AwardsDb> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<AwardsDb> awardList) {
        this.awardList = awardList;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final SearchedSeekerDetails other = (SearchedSeekerDetails) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
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

    public String getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(String blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getBlacklistComment() {
        return blacklistComment;
    }

    public void setBlacklistComment(String blacklistComment) {
        this.blacklistComment = blacklistComment;
    }


}
