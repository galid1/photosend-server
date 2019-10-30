package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedLineResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedResponse;
import com.photosend.photosendserver01.domains.user.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<OrderEntity> usersOrderEntities = orderRepository.findByOrdererUserId(ordererId);

        usersOrderEntities.stream().forEach(orderEntity -> {
            List<OrderedLineResponse> orderedLineResponseList = new ArrayList<>();

            orderEntity.getOrderLines().stream().forEach(orderLine -> {
                orderedLineResponseList.add(OrderedLineResponse.builder()
                        .productImagePath(productRepository.findByPidAndUserUserId(orderLine.getProductId(), ordererId).getProductImagePath())
                        .orderLine(orderLine)
                        .build());
            });

            orderedResponseList.add(OrderedResponse.builder()
                    .orderId(orderEntity.getOid())
                    .orderState(orderEntity.getOrderState())
                    .orderedLineResponses(orderedLineResponseList)
                    .build()
            );
        });

        return orderedResponseList;
    }

    public OrderedResponse getAnOrdered(Long ordererId, Long ordersId) {
        OrderEntity orderEntity = orderRepository.findByOrdererUserIdAndOid(ordererId, ordersId);
        Optional.of(orderEntity).orElseThrow(() -> new IllegalArgumentException("주문내역이 존재하지 않습니다."));

        List<OrderedLineResponse> orderedLineResponseList = new ArrayList<>();
        orderEntity.getOrderLines().stream().forEach(orderLine -> {
            orderedLineResponseList.add(OrderedLineResponse.builder()
                    .productImagePath(productRepository.findByPidAndUserUserId(orderLine.getProductId(), ordererId).getProductImagePath())
                    .orderLine(orderLine)
                    .build());
        });

        OrderedResponse orderedResponse = OrderedResponse.builder()
                    .orderId(ordersId)
                    .orderState(orderEntity.getOrderState())
                    .orderedLineResponses(orderedLineResponseList)
                    .build();

        return orderedResponse;
    }
}
