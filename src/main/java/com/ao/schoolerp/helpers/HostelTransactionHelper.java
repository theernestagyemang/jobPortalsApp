package com.ao.schoolerp.helpers;

import java.util.Date;

public class HostelTransactionHelper {

    private Integer id;


    private String name;


    private String termOfPayment;


    private String paymentStatus;


    private Date startDate;


    private Date allotmentDate;

    private Date endDate;


    private String studentEmail;

    private Integer hostelPaymentId;

    public HostelTransactionHelper() {
    }

    public HostelTransactionHelper(Integer id, String name, String termOfPayment, String paymentStatus, Date startDate,
                                   Date allotmentDate, Date endDate, String studentEmail, Integer hostelPaymentId) {
        this.id = id;
        this.name = name;
        this.termOfPayment = termOfPayment;
        this.paymentStatus = paymentStatus;
        this.startDate = startDate;
        this.allotmentDate = allotmentDate;
        this.endDate = endDate;
        this.studentEmail = studentEmail;
        this.hostelPaymentId = hostelPaymentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermOfPayment() {
        return termOfPayment;
    }

    public void setTermOfPayment(String termOfPayment) {
        this.termOfPayment = termOfPayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(Date allotmentDate) {
        this.allotmentDate = allotmentDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Integer getHostelPaymentId() {
        return hostelPaymentId;
    }

    public void setHostelPaymentId(Integer hostelPaymentId) {
        this.hostelPaymentId = hostelPaymentId;
    }
}
