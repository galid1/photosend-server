package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.order.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class PlaceOrderController {
    @Autowired
    private PlaceOrderService placeOrderService;

    @PostMapping("/{userId}")
    public Long placeOrder(@RequestBody OrderRequest orderRequest, @PathVariable("userId")Long ordererId) {
        return placeOrderService.placeOrder(orderRequest, ordererId);
    }
}
