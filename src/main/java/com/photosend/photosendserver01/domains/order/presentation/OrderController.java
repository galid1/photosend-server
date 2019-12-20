package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderDetailResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProduct;
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
    public List<OrderResponse> getUsersOrderSummaryList(@PathVariable("userId") long userId) {
        return orderService.getUsersOrderList(userId);
    }

    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@PathVariable("orderId") long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @GetMapping("/users/{userId}/products")
    public List<OrderedProduct> getOrderedProductIdList(@PathVariable("userId") long userId) {
        return orderService.getOrderedProductIdList(userId);
    }
}
