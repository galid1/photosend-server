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
import com.photosend.photosendserver01.domains.user.domain.exception.ProductNotFoundException;
import com.photosend.photosendserver01.domains.user.domain.exception.UserNotFoundException;
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
            ProductEntity productEntity = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

            productEntity.productOrdered(); // 상품 주문처리

            OrderLine orderLine = OrderLine.builder()
                    .productPrice(Money.builder().value(productEntity.getProductInformation().getPrice()).build())
                    .productId(productEntity.getPid())
                    .quantity(orderRequest.getQuantity())
                    .size(orderRequest.getSize())
                    .build();

            orderLines.add(orderLine);
        });

        UserEntity orderer = userRepository.findById(userWechatUid)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        OrderEntity orderEntity = OrderEntity.builder()
                                    .orderer(orderer)
                                    .orderLines(orderLines)
                                    .build();

        return orderRepository.save(orderEntity).getOid();
    }
}
