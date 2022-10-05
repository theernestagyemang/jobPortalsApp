package com.ao.schoolerp.helpers;

import java.math.BigDecimal;

public class BedHelper {

    private Integer id;
    private String number;
    private BigDecimal price;
    private String status;
    private String termOfPayment;
    private Integer roomId;

    public BedHelper(Integer id, String number, BigDecimal price, String status, String termOfPayment, Integer roomId) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.status = status;
        this.termOfPayment = termOfPayment;
        this.roomId = roomId;
    }

    public BedHelper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTermOfPayment() {
        return termOfPayment;
    }

    public void setTermOfPayment(String termOfPayment) {
        this.termOfPayment = termOfPayment;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
