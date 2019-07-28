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
        // TODO N+1 조회 성능 문제로 JPQL을 사용해야함
        List<OrderedProductResponse> orderedProductResponseList = new ArrayList<>();

        List<OrderEntity> usersOrderEntities = orderRepository.findByOrdererWechatUid(ordererWechatUid);
        usersOrderEntities.stream().forEach(orderEntity -> {
            List<String> productPaths = new ArrayList<>();

            orderEntity.getOrderLines().stream().forEach(orderLine -> {
                productPaths.add(productRepository.findByPidAndUserWechatUid(orderLine.getProductId(),
                                    orderEntity.getOrderer().getWechatUid())
                                .getProductImagePath());
            });

            orderedProductResponseList.add(OrderedProductResponse.builder()
                    .orderId(orderEntity.getOid())
                    .productImagePaths(productPaths)
                    .build()
            );
        });

        return orderedProductResponseList;
    }
}
