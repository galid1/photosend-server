package com.photosend.photosendserver01.domains.order.domain.domain_service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import lombok.Builder;

import java.util.List;

public class CalculateTotalAmountService {

    private List<OrderEntity> orderEntities;

    @Builder
    public CalculateTotalAmountService(List<OrderEntity> orderEntities) {
        setOrderEntities(orderEntities);
    }

    private void setOrderEntities(List<OrderEntity> orderEntities) {
        verifyLeastOneOrMoreOrderEntities(orderEntities);
    }

    private void verifyLeastOneOrMoreOrderEntities(List<OrderEntity> orderEntities) {
        if(orderEntities == null || orderEntities.isEmpty())
            throw new IllegalArgumentException("하나 이상의 OrderEntity가 필요합니다.");
    }

    public Money calculateTotalAmount() {
        return Money.builder()
                .value(this.orderEntities
                        .stream()
                        .mapToInt(orderEntity -> orderEntity.getOrderLine().getPrice().getValue())
                        .sum()
                )
                .build();
    }
}
