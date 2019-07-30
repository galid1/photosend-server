package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderConfirmUrl;
import com.photosend.photosendserver01.domains.order.service.OrderConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderConfirmController {
    @Autowired
    private OrderConfirmService orderConfirmService;

    @GetMapping("/{usersId}/confirm/{ordersId}")
    public OrderConfirmUrl confirmOrder(@PathVariable("usersId") String usersId, @PathVariable("ordersId") Long ordersId) {
        return orderConfirmService.makeOrderConfirmUrl(usersId, ordersId);
    }
}
