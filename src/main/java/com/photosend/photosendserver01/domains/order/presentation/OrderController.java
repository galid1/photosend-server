package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.order.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private PlaceOrderService placeOrderService;

    @PostMapping("/")
    public Long placeOrder(@RequestBody OrderRequest orderRequest) {
        return placeOrderService.placeOrder(orderRequest);
    }

}
