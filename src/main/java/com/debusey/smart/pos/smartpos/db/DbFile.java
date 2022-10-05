/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author SL002
 */


public class DbFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String fileName;
    private String transactionId;
    private String fileType;
    private byte[] uploadedFile;


    public DbFile() {

    }

    public DbFile(String id, String fileName, String fileType, byte[] uploadedFile) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedFile = uploadedFile;
        this.id = id;
    }

    public DbFile(String fileName, String fileType, byte[] uploadedFile) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedFile = uploadedFile;
    }

    public DbFile(String fileName, byte[] uploadedFile) {
        this.fileName = fileName;
        this.uploadedFile = uploadedFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(byte[] uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.fileName);
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
        final DbFile other = (DbFile) obj;
        return Objects.equals(this.fileName, other.fileName);
    }


}
