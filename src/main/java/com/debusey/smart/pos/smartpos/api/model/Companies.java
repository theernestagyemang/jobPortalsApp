/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import java.io.Serializable;
import java.util.Date;


public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer companyId;
    private String companyName;
    private String email;
    private String password;
    private String salt;
    private String contactNumber;
    private Date lastUpdate;
    private short hasBeenActivated;
    private Integer industryId;

    public Companies() {
    }

    public Companies(Integer companyId) {
        this.companyId = companyId;
    }

    public Companies(Integer companyId, String email, String password, Date lastUpdate, short hasBeenActivated) {
        this.companyId = companyId;
        this.email = email;
        this.password = password;
        this.lastUpdate = lastUpdate;
        this.hasBeenActivated = hasBeenActivated;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public short getHasBeenActivated() {
        return hasBeenActivated;
    }

    public void setHasBeenActivated(short hasBeenActivated) {
        this.hasBeenActivated = hasBeenActivated;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Companies)) {
            return false;
        }
        Companies other = (Companies) object;
        return (this.companyId != null || other.companyId == null) && (this.companyId == null || this.companyId.equals(other.companyId));
    }

    @Override
    public String toString() {
        return "entity.Companies[ companyId=" + companyId + " ]";
    }

}
