package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.user.domain.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.ProductRepository;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PlaceOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long placeOrder(OrderRequest orderRequest) {
        ProductEntity productEntity = productRepository.findById(orderRequest.getOrderProduct().getProductId()).get();
        UserEntity orderer = userRepository.findById(orderRequest.getOrdererWechatUid()).get();

        productEntity.productOrdered(); // 상품 주문처리

        OrderEntity orderEntity = OrderEntity.builder()
                                    .orderer(orderer)
                                    .orderLine(OrderLine.builder()
                                            .price(Money.builder().value(productEntity.getProductInformation().getPrice()).build())
                                            .productId(productEntity.getPid())
                                            .quantity(orderRequest.getOrderProduct().getQuantity())
                                            .size(orderRequest.getOrderProduct().getSize())
                                            .build()
                                    )
                                    .departureTime(orderer.getUserInformation().getDepartureTime().toLocalDateTime())
                                    .build();

        return orderRepository.save(orderEntity).getOid();
    }
}
