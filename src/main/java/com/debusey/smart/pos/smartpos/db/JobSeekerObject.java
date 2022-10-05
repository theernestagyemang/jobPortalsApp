/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Admin
 */
public class JobSeekerObject {
    private Long id;
    private String name;
    private String proffession;
    private String location;
    private String fileName;
    private LocalDate usageDate;


    public JobSeekerObject() {
    }

    public JobSeekerObject(Long id, String name, String proffession, String location, String fileName, LocalDate usageDate) {
        this.id = id;
        this.name = name;
        this.proffession = proffession;
        this.location = location;
        this.fileName = fileName;
        this.usageDate = usageDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProffession() {
        return proffession;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final JobSeekerObject other = (JobSeekerObject) obj;
        return Objects.equals(this.id, other.id);
    }


}
