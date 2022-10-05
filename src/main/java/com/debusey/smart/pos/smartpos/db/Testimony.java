/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;


import java.util.Objects;

/**
 * @author Admin
 */
public class Testimony {

    private Long id;
    private String message;
    private String candidtate;
    private String fileName;
    private String candidateProfession;

    public Testimony() {
    }

    public Testimony(Long id, String message, String candidtate, String fileName, String candidateProfession) {
        this.id = id;
        this.message = message;
        this.candidtate = candidtate;
        this.fileName = fileName;
        this.candidateProfession = candidateProfession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCandidtate() {
        return candidtate;
    }

    public void setCandidtate(String candidtate) {
        this.candidtate = candidtate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCandidateProfession() {
        return candidateProfession;
    }

    public void setCandidateProfession(String candidateProfession) {
        this.candidateProfession = candidateProfession;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Testimony other = (Testimony) obj;
        return Objects.equals(this.id, other.id);
    }


}
