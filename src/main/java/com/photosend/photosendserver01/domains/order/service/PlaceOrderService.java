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
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long placeOrder(List<OrderRequest> orderRequests, String userWechatUid) {
        List<OrderLine> orderLines = new ArrayList<>();

        orderRequests.stream().forEach(orderRequest -> {
            ProductEntity productEntity = productRepository.findById(orderRequest.getProductId()).get();

            productEntity.productOrdered(); // 상품 주문처리

            OrderLine orderLine = OrderLine.builder()
                    .productPrice(Money.builder().value(productEntity.getProductInformation().getPrice()).build())
                    .productId(productEntity.getPid())
                    .quantity(orderRequest.getQuantity())
                    .size(orderRequest.getSize())
                    .build();

            orderLines.add(orderLine);
        });

        UserEntity orderer = userRepository.findById(userWechatUid).get();

        OrderEntity orderEntity = OrderEntity.builder()
                                    .orderer(orderer)
                                    .orderLines(orderLines)
                                    .departureTime(orderer.getUserInformation().getDepartureTime().toLocalDateTime())
                                    .build();

        return orderRepository.save(orderEntity).getOid();
    }
}
