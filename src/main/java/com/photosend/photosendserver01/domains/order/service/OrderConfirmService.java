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
    @Value("${server.domain}")
    private String serverDomain;

    public OrderConfirmUrl makeOrderConfirmUrl(String usersId, Long ordersId) {
        String orderConfirmUrl = "https://" + serverDomain + ":" + serverPort + "/orders/" + usersId + "/" + ordersId;

        return OrderConfirmUrl.builder()
                .orderConfirmUrl(orderConfirmUrl)
                .build();
    }

}
