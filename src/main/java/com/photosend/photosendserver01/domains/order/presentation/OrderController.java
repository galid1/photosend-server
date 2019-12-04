package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderDetailResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderProductList;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderSummaryResponse;
import com.photosend.photosendserver01.domains.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}")
    public List<OrderSummaryResponse> getUsersOrderSummaryList(@PathVariable("userId") long userId) {
        return orderService.getUsersOrderSummaryList(userId);
    }

    @GetMapping("/{orderId}")
    public List<OrderDetailResponse> getOrderDetail(@PathVariable("orderId") long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @GetMapping("/users/{userId}/products")
    public OrderProductList getOrderedProductIdList(@PathVariable("userId") long userId) {
        return orderService.getOrderedProductIdList(userId);
    }
}
