/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Admin
 */

@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public List<CompanyAddress> listOfAddress;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String longitude;
    private String latitude;
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkin;
    private String google;
    private String email, phone;
    private String dbaccessFileName;
    private String reportProblemFileName;
    private String siteMapFileName;
    @Lob
    private String tnc;
    private String tncFileName;
    private String privacyPolicyFileName;
    private String aboutUsFileName;
    @Lob
    private String aboutUs;
    @Lob
    private String privacyPolicy;
    @Lob
    private String resume;
    private String resumeFileName;
    private String faqFileName;
    @Column(name = "activate_payment", columnDefinition = "boolean default false")
    private Boolean activatePayment;
    private String notificationEmail;
    @Lob
    private String assessmentTitle;
    @Lob
    private String academyTitle;

    public Company() {

    }

    public Company(String name, boolean activatePayment) {
        this.name = name;
        this.activatePayment = activatePayment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkin() {
        return linkin;
    }

    public void setLinkin(String linkin) {
        this.linkin = linkin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Company other = (Company) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return name;
    }

//    public List<CompanyAddress> getListOfAddress() {
//        return listOfAddress;
//    }

//    public void setListOfAddress(List<CompanyAddress> listOfAddress) {
//        this.listOfAddress = listOfAddress;
//    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }

    public String getDbaccessFileName() {
        return dbaccessFileName;
    }

    public void setDbaccessFileName(String dbaccessFileName) {
        this.dbaccessFileName = dbaccessFileName;
    }

    public String getReportProblemFileName() {
        return reportProblemFileName;
    }

    public void setReportProblemFileName(String reportProblemFileName) {
        this.reportProblemFileName = reportProblemFileName;
    }

    public String getSiteMapFileName() {
        return siteMapFileName;
    }

    public void setSiteMapFileName(String siteMapFileName) {
        this.siteMapFileName = siteMapFileName;
    }

    public String getTncFileName() {
        return tncFileName;
    }

    public void setTncFileName(String tncFileName) {
        this.tncFileName = tncFileName;
    }

    public String getPrivacyPolicyFileName() {
        return privacyPolicyFileName;
    }

    public void setPrivacyPolicyFileName(String privacyPolicyFileName) {
        this.privacyPolicyFileName = privacyPolicyFileName;
    }

    public String getAboutUsFileName() {
        return aboutUsFileName;
    }

    public void setAboutUsFileName(String aboutUsFileName) {
        this.aboutUsFileName = aboutUsFileName;
    }

    public String getFaqFileName() {
        return faqFileName;
    }

    public void setFaqFileName(String faqFileName) {
        this.faqFileName = faqFileName;
    }

    public Boolean getActivatePayment() {
        return activatePayment;
    }

    public void setActivatePayment(Boolean activatePayment) {
        this.activatePayment = activatePayment;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getTnc() {
        return tnc;
    }

    public void setTnc(String tnc) {
        this.tnc = tnc;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAcademyTitle() {
        return academyTitle;
    }

    public void setAcademyTitle(String academyTitle) {
        this.academyTitle = academyTitle;
    }

    public List<CompanyAddress> getListOfAddress() {
        return listOfAddress;
    }

    public void setListOfAddress(List<CompanyAddress> listOfAddress) {
        this.listOfAddress = listOfAddress;
    }
}
