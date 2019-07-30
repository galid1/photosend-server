package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderConfirmUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class OrderConfirmService {
    @Value("${server.port}")
    private String serverPort;

    public OrderConfirmUrl makeOrderConfirmUrl(String usersId, Long ordersId) {
        String orderConfirmUrl = "";
        try {
            orderConfirmUrl = "http://"
                    + InetAddress.getLocalHost().getHostAddress()
                    + serverPort
                    + "/orders/"
                    + usersId
                    + "/confirm/"
                    + ordersId;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return OrderConfirmUrl.builder()
                .orderConfirmUrl(orderConfirmUrl)
                .build();
    }

}
