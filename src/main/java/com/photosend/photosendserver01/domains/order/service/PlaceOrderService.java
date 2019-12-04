package com.photosend.photosendserver01.domains.order.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductEntity;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.order.domain.OrderEntity;
import com.photosend.photosendserver01.domains.order.domain.OrderLine;
import com.photosend.photosendserver01.domains.order.domain.OrderRepository;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderProduct;
import com.photosend.photosendserver01.domains.order.presentation.request_reponse.OrderRequest;
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
    public Long placeOrder(OrderRequest orderRequest, long ordererId) {
        List<OrderLine> orderLineList = makeOrderLineList(orderRequest.getOrderProductList());

        OrderEntity orderEntity = OrderEntity.builder()
                .ordererId(ordererId)
                .orderLines(orderLineList)
                .shippingInformation(orderRequest.getShippingInformation())
                .build();

        return orderRepository.save(orderEntity).getOid();
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
