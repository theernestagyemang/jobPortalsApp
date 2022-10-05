package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.*;

import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author SL002
 */

public class JobSeekerResponse {

    private Integer id;
    private Integer age;
    private String proffesionalTitile;
    private BigDecimal currentSalary;
    private String description;
    private String email;
    private String postcode;
    private String address;
    private String uniqueId;
    private String fullName;
    private String gender;
    private Date dob;
    private String countryOfOrigin;
    private String currentLocation;
    private String currentCompany;
    private String maritalStatus;
    private String primaryContact;
    private BigDecimal expectedSalary;
    private String ssn;
    private String tin;
    private String spokenLanguages;
    private String availabilityStatus;
    private Date startDate;
    private Date entryDate;
    private Date modifiedDate;
    private String yearsOfExperience;
    private Boolean approved;
    private String transactionId;
    private String otherContact;
    private String keySkills;
    private String shift;

    private String passportNo;
    private String physicallyChallenged;
    private String profileSummary;
    private Subscription subscriptionId;
    private AssessmentLines assessmentLines;

    private LearningPlan learningPlan;

    private String cvFileType;
    private String cvFileName;
    private String uploadedResume;
    private String percentCompleted;


    private String picFileType;
    private String picFileName;


    private String profileStrenghPct;
    private String profileStrenght;


    private List<WorkExperience> workExperienceList;

    private List<EducationalExperience> educationalExperienceList;

    private String educationLevel;
    private String highestQualification;

    private String industry;
    private String department;
    private String seekerRole;
    private String jobType;
    private String employmentType;

    private String desiredLocation;
    private String desiredIndustry;
    private String homeTown;
    private String telephone;

    private String statusCheck;

    private String contractType;


    private String blacklistComment;

    private List<Awards> awardss;

    private BigDecimal expMinSalary;
    private String facebook;
    private String twitter;
    private String google;
    private String linkedIn;
    private String website;
    private String latitude;
    private String longitude;
    private String coverLetter;
    private Boolean blacklisted;
    private LocalDate blacklistedDate;
    private String blacklistedBy;

    public JobSeekerResponse() {
    }

    public JobSeekerResponse(Integer id, Integer age, String proffesionalTitile, BigDecimal currentSalary,
                             String description, String email, String postcode, String address, String uniqueId,
                             String fullName, String gender, Date dob, String countryOfOrigin, String currentLocation,
                             String currentCompany, String maritalStatus, String primaryContact,
                             BigDecimal expectedSalary, String ssn, String tin, String spokenLanguages,
                             String availabilityStatus, Date startDate, Date entryDate, Date modifiedDate,
                             String yearsOfExperience, Boolean approved, String transactionId, String otherContact,
                             String keySkills, String shift, String passportNo, String physicallyChallenged,
                             String profileSummary, Subscription subscriptionId, AssessmentLines assessmentLines,
                             LearningPlan learningPlan, String cvFileType, String cvFileName, String uploadedResume,
                             String percentCompleted, String picFileType, String picFileName, String profileStrenghPct,
                             String profileStrenght, List<WorkExperience> workExperienceList, List<EducationalExperience> educationalExperienceList, String educationLevel, String highestQualification, String industry, String department, String seekerRole, String jobType, String employmentType, String desiredLocation, String desiredIndustry, String homeTown, String telephone, String statusCheck, String contractType, String blacklistComment, List<Awards> awardss, BigDecimal expMinSalary, String facebook, String twitter, String google, String linkedIn, String website, String latitude, String longitude, String coverLetter, Boolean blacklisted, LocalDate blacklistedDate, String blacklistedBy) {
        this.id = id;
        this.age = age;
        this.proffesionalTitile = proffesionalTitile;
        this.currentSalary = currentSalary;
        this.description = description;
        this.email = email;
        this.postcode = postcode;
        this.address = address;
        this.uniqueId = uniqueId;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.countryOfOrigin = countryOfOrigin;
        this.currentLocation = currentLocation;
        this.currentCompany = currentCompany;
        this.maritalStatus = maritalStatus;
        this.primaryContact = primaryContact;
        this.expectedSalary = expectedSalary;
        this.ssn = ssn;
        this.tin = tin;
        this.spokenLanguages = spokenLanguages;
        this.availabilityStatus = availabilityStatus;
        this.startDate = startDate;
        this.entryDate = entryDate;
        this.modifiedDate = modifiedDate;
        this.yearsOfExperience = yearsOfExperience;
        this.approved = approved;
        this.transactionId = transactionId;
        this.otherContact = otherContact;
        this.keySkills = keySkills;
        this.shift = shift;
        this.passportNo = passportNo;
        this.physicallyChallenged = physicallyChallenged;
        this.profileSummary = profileSummary;
        this.subscriptionId = subscriptionId;
        this.assessmentLines = assessmentLines;
        this.learningPlan = learningPlan;
        this.cvFileType = cvFileType;
        this.cvFileName = cvFileName;
        this.uploadedResume = uploadedResume;
        this.percentCompleted = percentCompleted;
        this.picFileType = picFileType;
        this.picFileName = picFileName;
        this.profileStrenghPct = profileStrenghPct;
        this.profileStrenght = profileStrenght;
        this.workExperienceList = workExperienceList;
        this.educationalExperienceList = educationalExperienceList;
        this.educationLevel = educationLevel;
        this.highestQualification = highestQualification;
        this.industry = industry;
        this.department = department;
        this.seekerRole = seekerRole;
        this.jobType = jobType;
        this.employmentType = employmentType;
        this.desiredLocation = desiredLocation;
        this.desiredIndustry = desiredIndustry;
        this.homeTown = homeTown;
        this.telephone = telephone;
        this.statusCheck = statusCheck;
        this.contractType = contractType;
        this.blacklistComment = blacklistComment;
        this.awardss = awardss;
        this.expMinSalary = expMinSalary;
        this.facebook = facebook;
        this.twitter = twitter;
        this.google = google;
        this.linkedIn = linkedIn;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coverLetter = coverLetter;
        this.blacklisted = blacklisted;
        this.blacklistedDate = blacklistedDate;
        this.blacklistedBy = blacklistedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPhysicallyChallenged() {
        return physicallyChallenged;
    }

    public void setPhysicallyChallenged(String physicallyChallenged) {
        this.physicallyChallenged = physicallyChallenged;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getProffesionalTitile() {
        return proffesionalTitile;
    }

    public void setProffesionalTitile(String proffesionalTitile) {
        this.proffesionalTitile = proffesionalTitile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }


    @XmlTransient
    public List<WorkExperience> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    @XmlTransient
    public List<EducationalExperience> getEducationalExperienceList() {
        return educationalExperienceList;
    }

    public void setEducationalExperienceList(List<EducationalExperience> educationalExperienceList) {
        this.educationalExperienceList = educationalExperienceList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    @Override
    public String toString() {
        return fullName;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSeekerRole() {
        return seekerRole;
    }

    public void setSeekerRole(String seekerRole) {
        this.seekerRole = seekerRole;
    }


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDesiredLocation() {
        return desiredLocation;
    }

    public void setDesiredLocation(String desiredLocation) {
        this.desiredLocation = desiredLocation;
    }

    public String getDesiredIndustry() {
        return desiredIndustry;
    }

    public void setDesiredIndustry(String desiredIndustry) {
        this.desiredIndustry = desiredIndustry;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getCvFileType() {
        return cvFileType;
    }

    public void setCvFileType(String cvFileType) {
        this.cvFileType = cvFileType;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public void setCvFileName(String cvFileName) {
        this.cvFileName = cvFileName;
    }

    public String getPicFileType() {
        return picFileType;
    }

    public void setPicFileType(String picFileType) {
        this.picFileType = picFileType;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(String statusCheck) {
        this.statusCheck = statusCheck;
    }

    public String getBlacklistComment() {
        return blacklistComment;
    }

    public void setBlacklistComment(String blacklistComment) {
        this.blacklistComment = blacklistComment;
    }

    public String getProfileStrenghPct() {
        return profileStrenghPct;
    }

    public void setProfileStrenghPct(String profileStrenghPct) {
        this.profileStrenghPct = profileStrenghPct;
    }

    public String getProfileStrenght() {
        return profileStrenght;
    }

    public void setProfileStrenght(String profileStrenght) {
        this.profileStrenght = profileStrenght;
    }

    public List<Awards> getAwardss() {
        return awardss;
    }

    public void setAwardss(List<Awards> awardss) {
        this.awardss = awardss;
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

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getExpMinSalary() {
        return expMinSalary;
    }

    public void setExpMinSalary(BigDecimal expMinSalary) {
        this.expMinSalary = expMinSalary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Boolean getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public LocalDate getBlacklistedDate() {
        return blacklistedDate;
    }

    public void setBlacklistedDate(LocalDate blacklistedDate) {
        this.blacklistedDate = blacklistedDate;
    }


    public String getBlacklistedBy() {
        return blacklistedBy;
    }

    public void setBlacklistedBy(String blacklistedBy) {
        this.blacklistedBy = blacklistedBy;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Subscription getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Subscription subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUploadedResume() {
        return uploadedResume;
    }

    public void setUploadedResume(String uploadedResume) {
        this.uploadedResume = uploadedResume;
    }

    public String getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(String percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public LearningPlan getLearningPlan() {
        return learningPlan;
    }

    public void setLearningPlan(LearningPlan learningPlan) {
        this.learningPlan = learningPlan;
    }

    public AssessmentLines getAssessmentLines() {
        return assessmentLines;
    }

    public void setAssessmentLines(AssessmentLines assessmentLines) {
        this.assessmentLines = assessmentLines;
    }


}


