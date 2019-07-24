package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProductResponse;
import com.photosend.photosendserver01.domains.order.service.OrderService;
import com.photosend.photosendserver01.domains.order.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private PlaceOrderService placeOrderService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public Long placeOrder(@RequestBody OrderRequest orderRequest) {
        return placeOrderService.placeOrder(orderRequest);
    }

    @GetMapping("/{usersId}")
    public List<OrderedProductResponse> getOrderedList(@PathVariable("usersId") String ordererWechatUid) {
        return orderService.getOrderedList(ordererWechatUid);
    }

}
