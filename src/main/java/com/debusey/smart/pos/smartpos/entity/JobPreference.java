/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AlhassanHussein
 */


public class JobPreference implements Serializable {


    private Date entryDate;

    public JobPreference() {
    }


    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }


}
