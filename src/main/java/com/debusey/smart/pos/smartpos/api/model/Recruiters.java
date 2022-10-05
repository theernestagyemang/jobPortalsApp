/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import java.io.Serializable;


public class Recruiters implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer recruiterId;
    private String fullname;
    private String email;
    private String password;
    private String salt;
    private String dialCode;
    private String contactNumber;
    private String profilePicture;
    private Integer positionId;
    private Integer branchId;
    private short recruiterType;

    public Recruiters() {
    }

    public Recruiters(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public Recruiters(Integer recruiterId, short recruiterType) {
        this.recruiterId = recruiterId;
        this.recruiterType = recruiterType;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public short getRecruiterType() {
        return recruiterType;
    }

    public void setRecruiterType(short recruiterType) {
        this.recruiterType = recruiterType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recruiterId != null ? recruiterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recruiters)) {
            return false;
        }
        Recruiters other = (Recruiters) object;
        return (this.recruiterId != null || other.recruiterId == null) && (this.recruiterId == null || this.recruiterId.equals(other.recruiterId));
    }

    @Override
    public String toString() {
        return "entity.Recruiters[ recruiterId=" + recruiterId + " ]";
    }

}
