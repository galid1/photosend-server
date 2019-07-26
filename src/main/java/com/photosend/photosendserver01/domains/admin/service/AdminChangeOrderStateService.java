package com.photosend.photosendserver01.domains.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminChangeOrderStateService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objMapper;

    @Transactional
    public String changeOrderStateTo(Long ordersId, OrderState orderState) {
        if(orderState != OrderState.PAYMENT_COMPLETED
                && orderState != OrderState.SHIPMENT_COMPLETED)
            throw new IllegalArgumentException("변경을 원하는 상태가 결제완료 또는 배송완료여야 합니다.");

        Long returnOrdersId = null;
        OrderEntity orderEntity = orderRepository.findById(ordersId).get();

        if(orderState == OrderState.PAYMENT_COMPLETED)
            returnOrdersId = orderEntity.paymentCompleted();

        if(orderState == OrderState.SHIPMENT_COMPLETED)
            returnOrdersId = orderEntity.shipmentCompleted();

        return "{\"ordersId\" : "+ returnOrdersId + "}";
    }
}
