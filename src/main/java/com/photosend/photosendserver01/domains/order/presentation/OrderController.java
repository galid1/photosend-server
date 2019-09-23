package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedResponse;
import com.photosend.photosendserver01.domains.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{usersId}")
    public List<OrderedResponse> getOrderedList(@PathVariable("usersId") Long userId) {
        return orderService.getOrderedList(userId);
    }

    //TODO 토큰검증 다시 하도록 CheckTokenInterceptor에서 설정하기
    @GetMapping("/{usersId}/{ordersId}")
    public OrderedResponse getAnOrdered(@PathVariable("usersId") Long ordererId, @PathVariable("ordersId") Long ordersId) {
        return orderService.getAnOrdered(ordererId, ordersId);
    }
}
