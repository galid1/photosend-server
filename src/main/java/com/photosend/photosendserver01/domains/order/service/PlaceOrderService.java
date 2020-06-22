package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.common.event.EmailEvent;
import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.common.util.email.EmailType;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderProduct;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderRequest;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.PlaceOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceOrderService {
    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, long ordererId) {
        List<OrderLine> orderLineList = makeOrderLineList(placeOrderRequest.getOrderProductList());

        // emit email event
        eventPublisher.publishEvent(new EmailEvent(EmailType.ORDERED, ordererId, null));

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
                            .description(orderProduct.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
