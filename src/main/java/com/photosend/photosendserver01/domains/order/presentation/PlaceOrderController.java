package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderRequest;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderResponse;
import com.photosend.photosendserver01.domains.order.service.PlaceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class PlaceOrderController {
    private final PlaceOrderService placeOrderService;

    @PostMapping("/{userId}")
    public PlaceOrderResponse placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest, @PathVariable("userId")Long ordererId) {
        return placeOrderService.placeOrder(placeOrderRequest, ordererId);
    }
}
