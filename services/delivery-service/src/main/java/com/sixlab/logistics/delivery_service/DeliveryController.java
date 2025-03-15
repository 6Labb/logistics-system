package com.sixlab.logistics.delivery_service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class DeliveryController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/deliveries")
    public String getDelivery() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

}
