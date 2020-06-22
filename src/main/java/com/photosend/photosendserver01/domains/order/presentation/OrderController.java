package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderDetailResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProduct;
import com.photosend.photosendserver01.domains.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

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
