package com.photosend.photosendserver01.domains.admin.service;

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

    @Transactional
    public void changeOrderStateTo(Long ordersId, OrderState orderState) {
        if(orderState != OrderState.PAYMENT_COMPLETED
                && orderState != OrderState.SHIPMENT_COMPLETED)
            throw new IllegalArgumentException("변경을 원하는 상태가 결제완료 또는 배송완료여야 합니다.");

        OrderEntity orderEntity = orderRepository.findById(ordersId).get();

        if(orderState == OrderState.PAYMENT_COMPLETED)
            orderEntity.paymentCompleted();

        if(orderState == OrderState.SHIPMENT_COMPLETED)
            orderEntity.shipmentCompleted();
    }
}
