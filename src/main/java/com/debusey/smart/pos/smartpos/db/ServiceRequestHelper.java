package com.debusey.smart.pos.smartpos.db;

import com.debusey.smart.pos.smartpos.entity.Users;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceRequestHelper {

    private Integer id;
    private Date entryDate;
    private String transactionId;
    private BigDecimal Amount;
    private String comment;
    private Users requestedBy;
    private String workStatus;
    private String fileCopy;
    private Date upload_date;
    private Integer processedBy;

    private Integer serviceTypeId;

    public ServiceRequestHelper() {
    }

    public ServiceRequestHelper(Integer id, Date entryDate, String transactionId, BigDecimal amount, String comment,
                                Users requestedBy, String workStatus, String fileCopy, Date upload_date,
                                Integer processedBy, Integer serviceTypeId) {
        this.id = id;
        this.entryDate = entryDate;
        this.transactionId = transactionId;
        this.Amount = amount;
        this.comment = comment;
        this.serviceTypeId = serviceTypeId;
        this.requestedBy = requestedBy;
        this.workStatus = workStatus;
        this.fileCopy = fileCopy;
        this.upload_date = upload_date;
        this.processedBy = processedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Users getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Users requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getFileCopy() {
        return fileCopy;
    }

    public void setFileCopy(String fileCopy) {
        this.fileCopy = fileCopy;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }
}
