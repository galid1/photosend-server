package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProductResponse;
import com.photosend.photosendserver01.domains.user.domain.ProductRepository;
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

    public List<OrderedProductResponse> getOrderedList(String ordererWechatUid) {
        List<OrderedProductResponse> orderedProductResponses = new ArrayList<>();

        List<OrderEntity> usersOrderEntities = orderRepository.findByOrdererWechatUid(ordererWechatUid);
        usersOrderEntities.stream().forEach(orderEntity -> {
            String productImagePath = productRepository.findById(orderEntity.getOrderLine().getProductId()).get().getProductImagePath();
            OrderedProductResponse orderedProductResponse = OrderedProductResponse.builder()
                                                                .orderId(orderEntity.getOid())
                                                                .productImagePath(productImagePath)
                                                                .build();
            orderedProductResponses.add(orderedProductResponse);
        });

        return orderedProductResponses;
    }
}
