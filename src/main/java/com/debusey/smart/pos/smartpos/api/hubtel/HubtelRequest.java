/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.hubtel;

import java.math.BigDecimal;

/**
 * @author AlhassanHussein
 */
public class HubtelRequest {
    /**
     * "totalAmount": 100,
     * "description": "Book Shop Checkout",
     * "callbackUrl": "https://webhook.site/8b4bbd0a-5f98-4b3d-abbe-b9b49767f7d5",
     * "returnUrl": "http://hubtel.com/online",
     * "merchantAccountNumber": "11684",
     * "cancellationUrl": "http://hubtel.com/online",
     * "clientReference": "inv0012"
     */

    private BigDecimal totalAmount;
    private String description;
    private String callbackUrl;
    private String returnUrl;
    private String merchantAccountNumber;
    private String cancellationUrl;
    private String clientReference;

    public HubtelRequest() {
    }

    public HubtelRequest(BigDecimal totalAmount, String description, String callbackUrl, String returnUrl, String merchantAccountNumber, String cancellationUrl, String clientReference) {
        this.totalAmount = totalAmount;
        this.description = description;
        this.callbackUrl = callbackUrl;
        this.returnUrl = returnUrl;
        this.merchantAccountNumber = merchantAccountNumber;
        this.cancellationUrl = cancellationUrl;
        this.clientReference = clientReference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getMerchantAccountNumber() {
        return merchantAccountNumber;
    }

    public void setMerchantAccountNumber(String merchantAccountNumber) {
        this.merchantAccountNumber = merchantAccountNumber;
    }

    public String getCancellationUrl() {
        return cancellationUrl;
    }

    public void setCancellationUrl(String cancellationUrl) {
        this.cancellationUrl = cancellationUrl;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }


}
