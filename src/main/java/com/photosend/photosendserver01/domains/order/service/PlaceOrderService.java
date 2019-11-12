package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.exception.ProductNotFoundException;
import com.photosend.photosendserver01.domains.user.exception.UserNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long placeOrder(List<OrderRequest> orderRequests, Long ordererId) {
        List<OrderLine> orderLines = toOrderLineList(orderRequests);

        OrderEntity orderEntity = OrderEntity.builder()
                                    .orderer(userRepository.findById(ordererId)
                                            .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다.")))
                                    .orderLines(orderLines)
                                    .build();

        return orderRepository.save(orderEntity).getOid();
    }

    private List<OrderLine> toOrderLineList(List<OrderRequest> orderRequests) {
        return orderRequests
                .stream()
                .map(orderRequest -> {
                    ProductEntity orderedProduct = productRepository.findById(orderRequest.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

                    return OrderLine.builder()
                            .productPrice(Money.builder().value(orderedProduct.getProductInformation().getPrice()).build())
                            .productId(orderedProduct.getPid())
                            .quantity(orderRequest.getQuantity())
                            .size(orderRequest.getSize())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
