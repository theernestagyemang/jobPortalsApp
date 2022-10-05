/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Admin
 */
public class ProfileVievBd {
    private Integer id;
    private String fileName;
    private String companyName;
    private LocalDateTime viewDate;

    public ProfileVievBd() {
    }

    public ProfileVievBd(Integer id, String fileName, String companyName, LocalDateTime viewDate) {
        this.id = id;
        this.fileName = fileName;
        this.companyName = companyName;
        this.viewDate = viewDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getViewDate() {
        return viewDate;
    }

    public void setViewDate(LocalDateTime viewDate) {
        this.viewDate = viewDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ProfileVievBd other = (ProfileVievBd) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return companyName;
    }


}
