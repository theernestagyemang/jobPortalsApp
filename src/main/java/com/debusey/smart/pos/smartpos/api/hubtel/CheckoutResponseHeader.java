/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.hubtel;


/**
 * @author AlhassanHussein
 * <p>
 * "responseCode": "0000",
 * "status": "Success",
 * "data": {
 * "checkoutUrl": "https://pay.hubtel.com/7569a11e8b784f21baa9443b3fce31ed",
 * "checkoutId": "7569a11e8b784f21baa9443b3fce31ed",
 * "clientReference": "inv0012",
 * "message": "",
 * "checkoutDirectUrl": "https://pay.hubtel.com/7569a11e8b784f21baa9443b3fce31ed/direct"
 */


public class CheckoutResponseHeader {
    private String responseCode;
    private String status;
    private CheckoutResponse data;

    public CheckoutResponseHeader() {
    }

    public CheckoutResponseHeader(String responseCode, String status, CheckoutResponse data) {
        this.responseCode = responseCode;
        this.status = status;
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CheckoutResponse getData() {
        return data;
    }

    public void setData(CheckoutResponse data) {
        this.data = data;
    }


}
