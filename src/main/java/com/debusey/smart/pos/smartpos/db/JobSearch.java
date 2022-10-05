/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
public class JobSearch {

    Integer startAge;
    Integer maxAge;
    private Integer id;
    private String category;
    private PostedBy postedBy;
    private Boolean showCompanyName;
    private String country;
    private String city;
    private String region;
    private String natureOfContract;
    private Date publishedDate;
    private Date expireDate;
    private String datePublished;
    private String endDate;
    private BigDecimal renumeration;
    private BigDecimal toRenumeration;
    private String profession;
    private String location;
    private String fileName;
    private String details;
    private List<String> skillls;
    private List<String> dutiesAndRespo;
    private List<String> specialRequirements;
    private String howToApply;
    private String industry;
    private String qualification;
    private Integer slot;
    private String telephone;
    private Integer minYearsExperience;
    private String minQualification;
    private String gender;
    private String posted;
    private String prefInterviewDate;
    private String prefStartDate;
    private String transactionId;

    public JobSearch() {
    }

    public JobSearch(Integer id, String category, PostedBy postedBy, Boolean showCompanyName,
                     String country, String city, String region, String natureOfContract, Date publishedDate,
                     Date expireDate, String datePublished, String endDate, BigDecimal renumeration, BigDecimal
                             toRenumeration, String profession, String location, String fileName, String details,
                     List<String> skillls, List<String> dutiesAndRespo, List<String> specialRequirements,
                     String howToApply, String industry, String qualification, Integer slot, String telephone,
                     Integer minYearsExperience, String minQualification, String gender, String posted, String prefInterviewDate,
                     String prefStartDate, Integer startAge, Integer maxAge, String transactionId) {
        this.id = id;
        this.category = category;
        this.postedBy = postedBy;
        this.showCompanyName = showCompanyName;
        this.country = country;
        this.city = city;
        this.region = region;
        this.natureOfContract = natureOfContract;
        this.publishedDate = publishedDate;
        this.expireDate = expireDate;
        this.datePublished = datePublished;
        this.endDate = endDate;
        this.renumeration = renumeration;
        this.toRenumeration = toRenumeration;
        this.profession = profession;
        this.location = location;
        this.fileName = fileName;
        this.details = details;
        this.skillls = skillls;
        this.dutiesAndRespo = dutiesAndRespo;
        this.specialRequirements = specialRequirements;
        this.howToApply = howToApply;
        this.industry = industry;
        this.qualification = qualification;
        this.slot = slot;
        this.telephone = telephone;
        this.minYearsExperience = minYearsExperience;
        this.minQualification = minQualification;
        this.gender = gender;
        this.posted = posted;
        this.prefInterviewDate = prefInterviewDate;
        this.prefStartDate = prefStartDate;
        this.startAge = startAge;
        this.maxAge = maxAge;
        this.transactionId = transactionId;
    }


    public JobSearch(Integer id, String category, PostedBy postedBy, Boolean showCompanyName, String country, String natureOfContract,
                     Date publishedDate, Date expireDate, BigDecimal renumeration, BigDecimal toRenumeration, String profession,
                     String minQualification, String location, String fileName, String details) {
        this.id = id;
        this.category = category;
        this.postedBy = postedBy;
        this.showCompanyName = showCompanyName;
        this.country = country;
        this.natureOfContract = natureOfContract;
        this.publishedDate = publishedDate;
        this.expireDate = expireDate;
        this.renumeration = renumeration;
        this.toRenumeration = toRenumeration;
        this.profession = profession;
        this.minQualification = minQualification;
        this.location = location;
        this.fileName = fileName;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PostedBy getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(PostedBy postedBy) {
        this.postedBy = postedBy;
    }


    public Boolean getShowCompanyName() {
        return showCompanyName;
    }

    public void setShowCompanyName(Boolean showCompanyName) {
        this.showCompanyName = showCompanyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNatureOfContract() {
        return natureOfContract;
    }

    public void setNatureOfContract(String natureOfContract) {
        this.natureOfContract = natureOfContract;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

//    public byte[] getCompanyLog() {
//        return companyLog;
//    }
//
//    public void setCompanyLog(byte[] companyLog) {
//        this.companyLog = companyLog;
//    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getSkillls() {
        return skillls;
    }

    public void setSkillls(List<String> skillls) {
        this.skillls = skillls;
    }


    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }


    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public List<String> getDutiesAndRespo() {
        return dutiesAndRespo;
    }

    public void setDutiesAndRespo(List<String> dutiesAndRespo) {
        this.dutiesAndRespo = dutiesAndRespo;
    }

    public List<String> getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(List<String> specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public Integer getMinYearsExperience() {
        return minYearsExperience;
    }

    public void setMinYearsExperience(Integer minYearsExperience) {
        this.minYearsExperience = minYearsExperience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getMinQualification() {
        return minQualification;
    }

    public void setMinQualification(String minQualification) {
        this.minQualification = minQualification;
    }

    public String getPrefInterviewDate() {
        return prefInterviewDate;
    }

    public void setPrefInterviewDate(String prefInterviewDate) {
        this.prefInterviewDate = prefInterviewDate;
    }

    public String getPrefStartDate() {
        return prefStartDate;
    }

    public void setPrefStartDate(String prefStartDate) {
        this.prefStartDate = prefStartDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
