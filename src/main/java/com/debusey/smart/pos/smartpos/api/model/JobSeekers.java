/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Admin
 */

public class JobSeekers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer jobSeekerId;
    private String fname;
    private String onames;
    private Date dob;
    private String email;
    private String password;
    private String personalSummary;
    private String contactNumber;
    private String additionalContacts;
    private String profilePicture;
    private String uploadedResume;
    private short hasBeenActivated;
    private Date registrationDate;
    private Date lastUpdate;
    private boolean hasPaid;
    private boolean percentCompleted;
    private short placementCount;
    private BigDecimal salary;
    private String socialSecurity;
    private boolean availability;
    private Date startDate;


    public JobSeekers() {
    }

    public JobSeekers(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public JobSeekers(Integer jobSeekerId, short hasBeenActivated, Date lastUpdate, boolean hasPaid, boolean percentCompleted, short placementCount, BigDecimal salary, boolean availability) {
        this.jobSeekerId = jobSeekerId;
        this.hasBeenActivated = hasBeenActivated;
        this.lastUpdate = lastUpdate;
        this.hasPaid = hasPaid;
        this.percentCompleted = percentCompleted;
        this.placementCount = placementCount;
        this.salary = salary;
        this.availability = availability;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getOnames() {
        return onames;
    }

    public void setOnames(String onames) {
        this.onames = onames;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPersonalSummary() {
        return personalSummary;
    }

    public void setPersonalSummary(String personalSummary) {
        this.personalSummary = personalSummary;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAdditionalContacts() {
        return additionalContacts;
    }

    public void setAdditionalContacts(String additionalContacts) {
        this.additionalContacts = additionalContacts;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUploadedResume() {
        return uploadedResume;
    }

    public void setUploadedResume(String uploadedResume) {
        this.uploadedResume = uploadedResume;
    }

    public short getHasBeenActivated() {
        return hasBeenActivated;
    }

    public void setHasBeenActivated(short hasBeenActivated) {
        this.hasBeenActivated = hasBeenActivated;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public boolean getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(boolean percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public short getPlacementCount() {
        return placementCount;
    }

    public void setPlacementCount(short placementCount) {
        this.placementCount = placementCount;
    }


    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSeekerId != null ? jobSeekerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobSeekers)) {
            return false;
        }
        JobSeekers other = (JobSeekers) object;
        return !((this.jobSeekerId == null && other.jobSeekerId != null) || (this.jobSeekerId != null && !this.jobSeekerId.equals(other.jobSeekerId)));
    }

    @Override
    public String toString() {
        return "entity.JobSeekers[ jobSeekerId=" + jobSeekerId + " ]";
    }

}
