package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderDetailResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderLineResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderResponse> getUsersOrderList(Long ordererId) {
       // 사용자 주문 리스트
        return orderRepository
                .findByOrdererIdOrderByCreatedDateDesc(ordererId)
                .stream()
                .map(orderEntity -> toOrderResponse(orderEntity))
                .collect(Collectors.toList());
    }

    private OrderResponse toOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .orderId(orderEntity.getOid())
                .paymentMethod(orderEntity.getPaymentMethod())
                .orderState(orderEntity.getOrderState())
                .orderedTime(orderEntity.getCreatedDate().toLocalDate())
                .shippingInformation(orderEntity.getShippingInformation())
                .orderLineList(orderEntity.getOrderLines()
                  .stream()
                  .map(orderLine -> toOrderLineResponse(orderLine))
                  .collect(Collectors.toList())
                )
                .build();
    }

    private OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        ProductEntity orderedProduct = productRepository.findById(orderLine.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품번호입니다."));

        return OrderLineResponse.builder()
                .productId(orderLine.getProductId())
                .productImagePath(orderedProduct.getProductImageInformation().getProductImagePath())
                .name(orderedProduct.getProductInformation().getName())
                .price(orderLine.getProductPrice().getValue())
                .quantity(orderLine.getQuantity())
                .lineTotalPrice(orderLine.getTotalPrice().getValue())
                .brand(orderedProduct.getProductInformation().getBrand())
                .build();
    }

    public OrderDetailResponse getOrderDetail(long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        return toOrderDetailResponse(orderEntity);
    }

    private OrderDetailResponse toOrderDetailResponse(OrderEntity orderEntity) {
        return OrderDetailResponse.builder()
                .receiveTime(orderEntity.getShippingInformation().getReceiveTime())
                .totalPrice(orderEntity.getTotalAmount().getValue())
                .shippingInformation(orderEntity.getShippingInformation())
                .build();
    }

    public List<OrderedProduct> getOrderedProductIdList(long userId) {
        List<OrderEntity> usersOrderList = orderRepository.findByOrdererId(userId);
        List<OrderedProduct> orderedProductList = new ArrayList<>();

        usersOrderList.stream().forEach(order -> {
            order.getOrderLines().stream().forEach(orderLine -> {
                orderedProductList.add(toOrderedProduct(orderLine.getProductId()));
            });
        });

        return orderedProductList;
    }

    private OrderedProduct toOrderedProduct(long orderedProductId) {
        String productImagePath = productRepository.findById(orderedProductId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                .getProductImageInformation()
                .getProductImagePath();

        return OrderedProduct.builder()
                .productId(orderedProductId)
                .productImagePath(productImagePath)
                .build();
    }
}
