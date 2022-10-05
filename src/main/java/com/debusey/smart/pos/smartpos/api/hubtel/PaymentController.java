/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.api.hubtel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author AlhassanHussein
 */

@RestController
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.callbackUrl}")
    private String callbackUrl;
    @Value("${app.cancellationUrl}")
    private String cancellationUrl;
    @Value("${app.merchantAccountNumber}")
    private String merchantAccountNumber;
    @Value("${app.returnUrl}")
    private String returnUrl;
    @Value("${app.hubtelUrl}")
    private String hubtelUrl;

    @PostMapping("/public/payhubtel")
    public CheckoutResponseHeader payment(BigDecimal amount, String description, String clientReference) {

        HubtelRequest req = new HubtelRequest();

        req.setCallbackUrl(callbackUrl);
        req.setCancellationUrl(cancellationUrl);
        req.setClientReference(clientReference);
        req.setMerchantAccountNumber(merchantAccountNumber);
        req.setReturnUrl(returnUrl);

        req.setDescription(description);
        req.setTotalAmount(amount);


        return restTemplate.postForObject(hubtelUrl, req, CheckoutResponseHeader.class);
    }


}
