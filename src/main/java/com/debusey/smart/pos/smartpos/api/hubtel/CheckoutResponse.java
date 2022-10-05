/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.hubtel;

/**
 * @author AlhassanHussein
 */
public class CheckoutResponse {
    private String checkoutUrl;
    private String checkoutId;
    private String clientReference;
    private String message;
    private String checkoutDirectUrl;

    public CheckoutResponse() {
    }

    public CheckoutResponse(String checkoutUrl, String checkoutId, String clientReference, String message, String checkoutDirectUrl) {
        this.checkoutUrl = checkoutUrl;
        this.checkoutId = checkoutId;
        this.clientReference = clientReference;
        this.message = message;
        this.checkoutDirectUrl = checkoutDirectUrl;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCheckoutDirectUrl() {
        return checkoutDirectUrl;
    }

    public void setCheckoutDirectUrl(String checkoutDirectUrl) {
        this.checkoutDirectUrl = checkoutDirectUrl;
    }


}
