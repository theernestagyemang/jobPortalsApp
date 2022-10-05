package com.debusey.smart.pos.smartpos.db;

public class ShortlistedEmployerResponse {
    Integer id;
    String jobTitle;
    Integer jobId;
    String seekerName;
    String seekerPicture;
    String seekerProfessionalTitle;
    String seekerCurrentCompany;
    Integer seekerId;

    String applicationStatus;
    String yesOfExperience;

    String location;


    public ShortlistedEmployerResponse() {
    }

    public ShortlistedEmployerResponse(Integer id, String jobTitle, Integer jobId, String seekerName, String seekerPicture,
                                       String seekerProfessionalTitle, String seekerCurrentCompany, Integer seekerId,
                                       String applicationStatus,String yesOfExperience, String location) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.jobId = jobId;
        this.seekerName = seekerName;
        this.seekerPicture = seekerPicture;
        this.seekerProfessionalTitle = seekerProfessionalTitle;
        this.seekerCurrentCompany = seekerCurrentCompany;
        this.seekerId = seekerId;
        this.applicationStatus = applicationStatus;
        this.yesOfExperience = yesOfExperience;
        this.location = location;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getYesOfExperience() {
        return yesOfExperience;
    }

    public void setYesOfExperience(String yesOfExperience) {
        this.yesOfExperience = yesOfExperience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getSeekerPicture() {
        return seekerPicture;
    }

    public void setSeekerPicture(String seekerPicture) {
        this.seekerPicture = seekerPicture;
    }

    public String getSeekerProfessionalTitle() {
        return seekerProfessionalTitle;
    }

    public void setSeekerProfessionalTitle(String seekerProfessionalTitle) {
        this.seekerProfessionalTitle = seekerProfessionalTitle;
    }

    public String getSeekerCurrentCompany() {
        return seekerCurrentCompany;
    }

    public void setSeekerCurrentCompany(String seekerCurrentCompany) {
        this.seekerCurrentCompany = seekerCurrentCompany;
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }
}
