package com.debusey.smart.pos.smartpos.db;

public class PaymentHelper {

    private Integer id;
    private String entity;
    private String amount;
    private String transactionId;

    public PaymentHelper(Integer id, String entity, String amount, String transactionId) {
        this.id = id;
        this.entity = entity;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public PaymentHelper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
