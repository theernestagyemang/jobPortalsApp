/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author SL002
 */

@Indexed
@Entity
@Table(name = "jobs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"transaction_id"})})
public class Jobs implements Serializable {

    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "assigned_to_emp", referencedColumnName = "employeeid")
    @ManyToOne
    private Employee assignedTo;
    @Column(name = "min_years_experience")
    private Integer minYearsExperience;
    @Column(name = "no_needed")
    private Integer noNeeded;
    @ColumnDefault("0")
    private Boolean showCompanyName;
    @FullTextField
    private String minQualification;
    private Integer countryId;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinTable(name = "jobs_jobseeker", joinColumns = {
            @JoinColumn(name = "jobseeker_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "job_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<JobSeeker> jobseekers = new HashSet<>(0);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @FullTextField
    private String jobTitle;
    @Column(name = "job_tags", length = 2147483647)
    private String jobTags;
    @Column(columnDefinition = "varchar(50) default 'Full Time'")
    private String jobType;
    private String experience;
    @FullTextField
    private String region;
    @Lob
    @Column(name = "requirements", length = 2147483647)
    private String requirements;

    private Integer recruiterId;
    private Boolean assigned;

    @Lob
    @Column(name = "condition_of_service")
    private String conditionOfService;
    @FullTextField
    @Column(name = "country", length = 50)
    private String country;
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "deleted_date")
    @Temporal(TemporalType.DATE)
    private Date deletedDate;
    @Column(name = "from_age")
    private Integer fromAge;

    @Column(name = "gender", length = 20)
    private String gender;

    @FullTextField
    @Column(name = "job_city", length = 100)
    private String jobCity;

    @FullTextField
    @Column(name = "job_country", length = 100)
    private String jobCountry;

    @Lob
    @Column(name = "job_description", length = 2147483647)
    private String jobDescription;

    @Lob
    @Column(name = "duties_and_respo", length = 2147483647)
    private String dutiesAndRespo;

    @Lob
    @Column(name = "special_requirements", length = 2147483647)
    private String specialRequirements;
    @Lob
    @Column(name = "how_to_apply", length = 2147483647)
    private String howToApply;


    @Column(name = "job_state_region", length = 50)
    private String jobStateRegion;

    @Column(name = "job_status", length = 200)
    private String jobStatus;

    @Column(name = "logo", length = 20)
    private String logo;

    @Column(name = "name", length = 200)
    private String name;

    @FullTextField
    @Column(name = "name_of_company", length = 100)
    private String nameOfCompany;

    @Column(name = "name_of_requester", length = 100)
    private String nameOfRequester;

    @Column(name = "nature_of_contract", columnDefinition = "varchar(50) default 'Full Time'")
    private String natureOfContract;
    private String industry;


    @Column(name = "other_condition_of_service", length = 50)
    private String otherConditionOfService;

    @Column(name = "other_nature_of_contract", length = 100)
    private String otherNatureOfContract;

    @Column(name = "other_positions", length = 100)
    private String otherPositions;


    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "payment_status", length = 200)
    private String paymentStatus;
    @Column(name = "posted_date")
    @Temporal(TemporalType.DATE)
    private Date postedDate;

    @Column(name = "pref_interview_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prefInterviewDate;

    @Column(name = "pref_skills_attribute", length = 100)
    private String prefSkillsAttribute;

    @Column(name = "pref_start_date")
    @Temporal(TemporalType.DATE)
    private Date prefStartDate;
    @Column(name = "processed")
    private Boolean processed;
    @Column(name = "published")
    private Boolean published;

    @Column(name = "published_by", length = 200)
    private String publishedBy;
    @Column(name = "published_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;
    @Column(name = "renumeration", precision = 8, scale = 2)
    private BigDecimal renumeration;
    @Column(name = "telephone", length = 30)
    private String telephone;
    @Column(name = "to_age")
    private Integer toAge;
    @Column(name = "to_renumeration", precision = 8, scale = 2)
    private BigDecimal toRenumeration;
    @Column(name = "expire_date")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Temporal(TemporalType.DATE)
    private Date assignedDate;


    @JoinColumn(name = "posted_by", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Employer postedBy;
    @FullTextField
    private String profession;
    @FullTextField
    private String location;
    @FullTextField
    private String category;

    private String attachedFileType;
    private String attachedFileName;

    @OneToMany(mappedBy = "jobId", cascade = CascadeType.PERSIST)
    private List<JobRequirement> requirementList;

    @Column(name = "alert_sent", columnDefinition = "boolean default false")
    private Boolean alertSent;

    public Jobs() {
    }

    public Jobs(Integer id) {
        this.id = id;
    }

    public Jobs(Integer id, String jobCity, String jobCountry, String jobDescription, String jobStateRegion, Integer minYearsExperience, String natureOfContract, int noNeeded, Date prefInterviewDate, Date prefStartDate, BigDecimal renumeration, String telephone, BigDecimal toRenumeration) {
        this.id = id;

        this.jobCity = jobCity;
        this.jobCountry = jobCountry;
        this.jobDescription = jobDescription;
        this.jobStateRegion = jobStateRegion;
        this.minYearsExperience = minYearsExperience;
        this.natureOfContract = natureOfContract;
        this.noNeeded = noNeeded;
        this.prefInterviewDate = prefInterviewDate;
        this.prefStartDate = prefStartDate;
        this.renumeration = renumeration;
        this.telephone = telephone;
        this.toRenumeration = toRenumeration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditionOfService() {
        return conditionOfService;
    }

    public void setConditionOfService(String conditionOfService) {
        this.conditionOfService = conditionOfService;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }


    public Integer getFromAge() {
        return fromAge;
    }

    public void setFromAge(Integer fromAge) {
        this.fromAge = fromAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    public String getJobCountry() {
        return jobCountry;
    }

    public void setJobCountry(String jobCountry) {
        this.jobCountry = jobCountry;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobStateRegion() {
        return jobStateRegion;
    }

    public void setJobStateRegion(String jobStateRegion) {
        this.jobStateRegion = jobStateRegion;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getMinYearsExperience() {
        return minYearsExperience;
    }

    public void setMinYearsExperience(Integer minYearsExperience) {
        this.minYearsExperience = minYearsExperience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getNameOfRequester() {
        return nameOfRequester;
    }

    public void setNameOfRequester(String nameOfRequester) {
        this.nameOfRequester = nameOfRequester;
    }

    public String getNatureOfContract() {
        return natureOfContract;
    }

    public void setNatureOfContract(String natureOfContract) {
        this.natureOfContract = natureOfContract;
    }


    public String getOtherConditionOfService() {
        return otherConditionOfService;
    }

    public void setOtherConditionOfService(String otherConditionOfService) {
        this.otherConditionOfService = otherConditionOfService;
    }

    public String getOtherNatureOfContract() {
        return otherNatureOfContract;
    }

    public void setOtherNatureOfContract(String otherNatureOfContract) {
        this.otherNatureOfContract = otherNatureOfContract;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getPrefInterviewDate() {
        return prefInterviewDate;
    }

    public void setPrefInterviewDate(Date prefInterviewDate) {
        this.prefInterviewDate = prefInterviewDate;
    }

    public String getPrefSkillsAttribute() {
        return prefSkillsAttribute;
    }

    public void setPrefSkillsAttribute(String prefSkillsAttribute) {
        this.prefSkillsAttribute = prefSkillsAttribute;
    }

    public Date getPrefStartDate() {
        return prefStartDate;
    }

    public void setPrefStartDate(Date prefStartDate) {
        this.prefStartDate = prefStartDate;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public BigDecimal getRenumeration() {
        return renumeration;
    }

    public void setRenumeration(BigDecimal renumeration) {
        this.renumeration = renumeration;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getToAge() {
        return toAge;
    }

    public void setToAge(Integer toAge) {
        this.toAge = toAge;
    }

    public BigDecimal getToRenumeration() {
        return toRenumeration;
    }

    public void setToRenumeration(BigDecimal toRenumeration) {
        this.toRenumeration = toRenumeration;
    }


    public Employer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Employer postedBy) {
        this.postedBy = postedBy;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTags() {
        return jobTags;
    }

    public void setJobTags(String jobTags) {
        this.jobTags = jobTags;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobs)) {
            return false;
        }
        Jobs other = (Jobs) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        if (nameOfCompany == null) {
            return String.valueOf(jobTitle);
        }
        return jobTitle + " [" + nameOfCompany + "]";
    }

    public String getOtherPositions() {
        return otherPositions;
    }

    public void setOtherPositions(String otherPositions) {
        this.otherPositions = otherPositions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<JobSeeker> getJobseekers() {
        return jobseekers;
    }

    public void setJobseekers(Set<JobSeeker> jobseekers) {
        this.jobseekers = jobseekers;
    }

//    public Employee getAssignedTo() {
//        return assignedTo;
//    }
//
//    public void setAssignedTo(Employee assignedTo) {
//        this.assignedTo = assignedTo;
//    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getNoNeeded() {
        return noNeeded;
    }

    public void setNoNeeded(Integer noNeeded) {
        this.noNeeded = noNeeded;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public Boolean getShowCompanyName() {
        return showCompanyName;
    }

    public void setShowCompanyName(Boolean showCompanyName) {
        this.showCompanyName = showCompanyName;
    }

    public String getMinQualification() {
        return minQualification;
    }

    public void setMinQualification(String minQualification) {
        this.minQualification = minQualification;
    }

    public List<JobRequirement> getRequirementList() {
        return requirementList;
    }

    public void setRequirementList(List<JobRequirement> requirementList) {
        this.requirementList = requirementList;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Boolean getAlertSent() {
        return alertSent;
    }

    public void setAlertSent(Boolean alertSent) {
        this.alertSent = alertSent;
    }

    public String getDutiesAndRespo() {
        return dutiesAndRespo;
    }

    public void setDutiesAndRespo(String dutiesAndRespo) {
        this.dutiesAndRespo = dutiesAndRespo;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAttachedFileType() {
        return attachedFileType;
    }

    public void setAttachedFileType(String attachedFileType) {
        this.attachedFileType = attachedFileType;
    }

    public String getAttachedFileName() {
        return attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
