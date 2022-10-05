/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

/**
 * @author AlhassanHussein
 */
public class EmployerShortlisted {

    private SearchedSeeker jobSeekerId;
    private String jobTitle;
    private String entryDate;
    private String transactionNo;

    public EmployerShortlisted() {
    }

    public EmployerShortlisted(SearchedSeeker jobSeekerId, String jobTitle, String entryDate, String transactionNo) {
        this.jobSeekerId = jobSeekerId;
        this.jobTitle = jobTitle;
        this.entryDate = entryDate;
        this.transactionNo = transactionNo;
    }

    public SearchedSeeker getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(SearchedSeeker jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }


}
