/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;


/**
 * @author AlhassanHussein
 */
public class UploadFileMeta {
    private String fileName;
    private String fileType;

    public UploadFileMeta() {
    }

    public UploadFileMeta(String fileName, String fileType, Integer fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public UploadFileMeta(DbFile dbFile) {
        this.fileName = dbFile.getFileName();
        this.fileType = dbFile.getFileType();
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


}

