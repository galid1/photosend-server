package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderProduct;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderRequest;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, long ordererId) {
        List<OrderLine> orderLineList = makeOrderLineList(placeOrderRequest.getOrderProductList());

        OrderEntity savedOrderEntity = orderRepository.save(
                OrderEntity.builder()
                .ordererId(ordererId)
                .orderLines(orderLineList)
                .shippingInformation(placeOrderRequest.getShippingInformation())
                .paymentMethod(placeOrderRequest.getPaymentMethod())
                .build());

        return PlaceOrderResponse.builder()
                .orderId(savedOrderEntity.getOid())
                .totalAmount(savedOrderEntity.getTotalAmount().getValue())
                .ordererId(savedOrderEntity.getOrdererId())
                .build();
    }

    private List<OrderLine> makeOrderLineList(List<OrderProduct> orderProductList) {
        return orderProductList
                .stream()
                .map(orderProduct -> {
                    ProductEntity productEntity = productRepository.findById(orderProduct.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    return OrderLine.builder()
                            .productId(orderProduct.getProductId())
                            .productPrice(Money.builder()
                                    .value(productEntity.getProductInformation().getPrice())
                                    .build())
                            .quantity(orderProduct.getQuantity())
                            .size(orderProduct.getSize())
                                .build();
                })
                .collect(Collectors.toList());
    }
}
