package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.order.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class PlaceOrderController {
    @Autowired
    private PlaceOrderService placeOrderService;

    @PostMapping("/{usersId}")
    public Long placeOrder(@RequestBody List<OrderRequest> orderRequests, @PathVariable("usersId")Long ordererId) {
        return placeOrderService.placeOrder(orderRequests, ordererId);
    }
}
