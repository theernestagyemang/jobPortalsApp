/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.db;

import java.util.Date;

/**
 * @author AlhassanHussein
 */
public class ExceptionResponse {
    private final Date timestamp;
    private final String message;
    private final String detalles;
    private final String httpCodeMessage;

    public ExceptionResponse(Date timestamp, String message, String details, String httpCodeMessage) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.detalles = details;
        this.httpCodeMessage = httpCodeMessage;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }


    public String getDetalles() {
        return detalles;
    }
}
