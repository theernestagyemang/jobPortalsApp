/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import java.io.Serializable;


public class Industries implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer industryId;
    private String industryName;


    public Industries() {
    }

    public Industries(Integer industryId) {
        this.industryId = industryId;
    }

    public Industries(Integer industryId, String industryName) {
        this.industryId = industryId;
        this.industryName = industryName;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (industryId != null ? industryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Industries)) {
            return false;
        }
        Industries other = (Industries) object;
        return (this.industryId != null || other.industryId == null) && (this.industryId == null || this.industryId.equals(other.industryId));
    }

    @Override
    public String toString() {
        return "entity.Industries[ industryId=" + industryId + " ]";
    }

}
