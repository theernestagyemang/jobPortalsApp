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
public class Cart {
    private Integer seekerId;
    private Integer empId;
    private String transactionId;

    public Cart() {
    }

    public Cart(Integer seekerId, Integer empId, String transactionId) {
        this.seekerId = seekerId;
        this.empId = empId;
        this.transactionId = transactionId;
    }

    public Cart(Integer seekerId) {
        this.seekerId = seekerId;
    }


    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.seekerId);
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
        final Cart other = (Cart) obj;
        return Objects.equals(this.seekerId, other.seekerId);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
