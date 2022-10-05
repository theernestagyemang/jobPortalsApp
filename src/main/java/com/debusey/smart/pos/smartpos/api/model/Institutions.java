/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.model;

import java.io.Serializable;


public class Institutions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer institutionId;
    private String institutionName;

    public Institutions() {
    }

    public Institutions(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public Institutions(Integer institutionId, String institutionName) {
        this.institutionId = institutionId;
        this.institutionName = institutionName;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (institutionId != null ? institutionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institutions)) {
            return false;
        }
        Institutions other = (Institutions) object;
        return !((this.institutionId == null && other.institutionId != null) || (this.institutionId != null && !this.institutionId.equals(other.institutionId)));
    }

    @Override
    public String toString() {
        return "entity.Institutions[ institutionId=" + institutionId + " ]";
    }

}
