/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Requisitions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer requisitionId;
    private int companyId;
    private String company;

    private String requestedBy;
    private String contactNumber;
    private String email;
    private String jobDescription;
    private Integer minExp;
    private Integer ageFrom;
    private Integer ageTo;
    private short genderId;
    private String conditions;
    private short currencyId;
    private Integer minSalary;
    private Integer maxSalary;
    private int countryId;
    private int stateId;
    private Integer townId;
    private boolean contractType;
    private Date interviewDate;
    private Date startDate;
    private Date dateAdded;
    private BigDecimal amtPaid;
    private Integer assignedTo;
    private int statusId;
    private boolean totalShortlisted;
    private boolean assignmentStatusId;
    private short numberNeeded;
    private boolean published;
    private int numOfApplicants;
    private Integer deletedByEmployer;
    private Integer deletedByRecruiter;
    private String title;
    private String profession;

    public Requisitions() {
    }

    public Requisitions(Integer requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Requisitions(Integer requisitionId, int companyId, String requestedBy, String contactNumber, String email, String jobDescription, short genderId, short currencyId, int countryId, int stateId, boolean contractType, Date startDate, Date dateAdded, BigDecimal amtPaid, int statusId, boolean totalShortlisted, boolean assignmentStatusId, short numberNeeded, boolean published, int numOfApplicants) {
        this.requisitionId = requisitionId;
        this.companyId = companyId;
        this.requestedBy = requestedBy;
        this.contactNumber = contactNumber;
        this.email = email;
        this.jobDescription = jobDescription;
        this.genderId = genderId;
        this.currencyId = currencyId;
        this.countryId = countryId;
        this.stateId = stateId;
        this.contractType = contractType;
        this.startDate = startDate;
        this.dateAdded = dateAdded;
        this.amtPaid = amtPaid;
        this.statusId = statusId;
        this.totalShortlisted = totalShortlisted;
        this.assignmentStatusId = assignmentStatusId;
        this.numberNeeded = numberNeeded;
        this.published = published;
        this.numOfApplicants = numOfApplicants;
    }

    public Integer getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Integer requisitionId) {
        this.requisitionId = requisitionId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getMinExp() {
        return minExp;
    }

    public void setMinExp(Integer minExp) {
        this.minExp = minExp;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

    public short getGenderId() {
        return genderId;
    }

    public void setGenderId(short genderId) {
        this.genderId = genderId;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public short getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(short currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public boolean getContractType() {
        return contractType;
    }

    public void setContractType(boolean contractType) {
        this.contractType = contractType;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public BigDecimal getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(BigDecimal amtPaid) {
        this.amtPaid = amtPaid;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public boolean getTotalShortlisted() {
        return totalShortlisted;
    }

    public void setTotalShortlisted(boolean totalShortlisted) {
        this.totalShortlisted = totalShortlisted;
    }

    public boolean getAssignmentStatusId() {
        return assignmentStatusId;
    }

    public void setAssignmentStatusId(boolean assignmentStatusId) {
        this.assignmentStatusId = assignmentStatusId;
    }

    public short getNumberNeeded() {
        return numberNeeded;
    }

    public void setNumberNeeded(short numberNeeded) {
        this.numberNeeded = numberNeeded;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getNumOfApplicants() {
        return numOfApplicants;
    }

    public void setNumOfApplicants(int numOfApplicants) {
        this.numOfApplicants = numOfApplicants;
    }

    public Integer getDeletedByEmployer() {
        return deletedByEmployer;
    }

    public void setDeletedByEmployer(Integer deletedByEmployer) {
        this.deletedByEmployer = deletedByEmployer;
    }

    public Integer getDeletedByRecruiter() {
        return deletedByRecruiter;
    }

    public void setDeletedByRecruiter(Integer deletedByRecruiter) {
        this.deletedByRecruiter = deletedByRecruiter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requisitionId != null ? requisitionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requisitions)) {
            return false;
        }
        Requisitions other = (Requisitions) object;
        return !((this.requisitionId == null && other.requisitionId != null) || (this.requisitionId != null && !this.requisitionId.equals(other.requisitionId)));
    }

    @Override
    public String toString() {
        return "entity.Requisitions[ requisitionId=" + requisitionId + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

}
