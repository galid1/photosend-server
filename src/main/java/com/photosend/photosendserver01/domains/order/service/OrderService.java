package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedLineResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedResponse;
import com.photosend.photosendserver01.domains.user.domain.ProductInformation;
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

    public List<OrderedResponse> getOrderedList(String ordererWechatUid) {
        // TODO N+1 조회 성능 문제로 JPQL을 사용해야함
        // 사용자 주문 리스트
        List<OrderedResponse> orderedResponseList = new ArrayList<>();

        List<OrderEntity> usersOrderEntities = orderRepository.findByOrdererWechatUid(ordererWechatUid);

        usersOrderEntities.stream().forEach(orderEntity -> {
            List<OrderedLineResponse> orderedLineResponseList = new ArrayList<>();

            orderEntity.getOrderLines().stream().forEach(orderLine -> {
                orderedLineResponseList.add(OrderedLineResponse.builder()
                        .productImagePath(productRepository.findByPidAndUserWechatUid(orderLine.getProductId(), ordererWechatUid).getProductImagePath())
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
}
