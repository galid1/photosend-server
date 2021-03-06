package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Map<User, List<OrderInformation>> getOrderListGroupingByUser() {
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(order -> order.getOrdererId()))
                .entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> toUser(entry.getKey()),
                                entry -> toOrderInformation(entry.getValue())
                        )
                );
    }

    private User toUser(long ordererId) {
        return new User(ordererId, userRepository.findById(ordererId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문자입니다."))
                .getUserInformation()
                .getName());
    }

    private List<OrderInformation> toOrderInformation(List<OrderEntity> orderList) {
        return orderList.stream()
                .map(order -> OrderInformation.builder()
                .oid(order.getOid())
                .address(order.getShippingInformation().getAddress())
                .ordererId(order.getOrdererId())
                .orderLineList(order.getOrderLines().stream()
                        .map(orderLine -> toOrderLineInformation(orderLine))
                        .collect(Collectors.toList())
                )
                .orderState(order.getOrderState())
                .receiveTime(order.getShippingInformation().getReceiveTime())
                .totalAmount(order.getTotalAmount())
                .build())
                .collect(Collectors.toList());
    }

    private OrderLineInformation toOrderLineInformation(OrderLine orderLine) {
        ProductEntity product = productRepository.findById(orderLine.getProductId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return OrderLineInformation.builder()
                .productId(orderLine.getProductId())
                .productImagePath(product.getProductImageInformation().getProductImagePath())
                .productInformation(product.getProductInformation())
                .quantity(orderLine.getQuantity())
                .totalPrice(orderLine.getTotalPrice())
                .size(orderLine.getSize())
                .build();
    }

    @Transactional
    public void cancel(long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        verifyExistOrder(orderEntity);

        orderEntity.cancel();
    }

    @Transactional
    public void ship(long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        verifyExistOrder(orderEntity);

        orderEntity.ship();
    }

    private void verifyExistOrder(OrderEntity orderEntity) {
        if(orderEntity == null)
            throw new IllegalArgumentException("존재하지 않는 주문입니다.");
    }

    @Getter
    private class User {
        private long userId;
        private String name;

        public User(long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }

}
