package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OrderedResponse> getOrderedList(Long ordererId) {
        // TODO N+1 조회 성능 문제로 JPQL을 사용해야함
        // 사용자 주문 리스트
        List<OrderedResponse> orderedResponseList = new ArrayList<>();



        return orderedResponseList;
    }

}
