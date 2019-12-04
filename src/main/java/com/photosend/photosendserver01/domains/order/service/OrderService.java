package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderDetailResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderSummaryResponse;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OrderSummaryResponse> getUsersOrderSummaryList(Long ordererId) {
       // 사용자 주문 리스트
        return orderRepository
                .findByOrdererId(ordererId)
                .stream()
                .map(orderEntity -> {
                    return toOrderSummaryResponse(orderEntity);
                })
                .collect(Collectors.toList());
    }

    private OrderSummaryResponse toOrderSummaryResponse(OrderEntity orderEntity) {
        String mainImagePath = productRepository.findById(orderEntity
                                                            .getOrderLines()
                                                            .get(0)
                                                            .getProductId())
                                                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                    .getProductImageInformation()
                    .getProductImagePath();

        return OrderSummaryResponse.builder()
                .orderId(orderEntity.getOid())
                .orderState(orderEntity.getOrderState())
                .mainImagePath(mainImagePath)
                .build();
    }

    public List<OrderDetailResponse> getOrderDetail(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."))
                .getOrderLines()
                .stream()
                .map(orderLine -> {
                    return toOrderDetailResponse(orderLine);
                })
                .collect(Collectors.toList());
    }

    private OrderDetailResponse toOrderDetailResponse(OrderLine orderLine) {
        return OrderDetailResponse.builder()
                .productId(orderLine.getProductId())
                .productImagePath(
                        productRepository
                                .findById(orderLine.getProductId())
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                                .getProductImageInformation()
                                .getProductImagePath())
                .quantity(orderLine.getQuantity())
                .size(orderLine.getSize())
                .totalPrice(orderLine.getTotalPrice())
                .build();
    }

    public List<OrderedProduct> getOrderedProductIdList(long userId) {
        List<OrderEntity> usersOrderList = orderRepository.findByOrdererId(userId);
        List<OrderedProduct> orderedProductList = new ArrayList<>();

        usersOrderList.stream().forEach(order -> {
            order.getOrderLines().stream().forEach(orderLine -> {
                orderedProductList.add(toOrderedProduct(orderLine));
            });
        });

        return orderedProductList;
    }

    private OrderedProduct toOrderedProduct(OrderLine orderLine) {
        String productImagePath = productRepository.findById(orderLine.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                .getProductImageInformation()
                .getProductImagePath();

        return OrderedProduct.builder()
                .productId(orderLine.getProductId())
                .productImagePath(productImagePath)
                .build();
    }
}
