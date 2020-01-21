package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.OrderState;
import com.photosend.photosendserver01.domains.order.domain.PaymentMethod;
import com.photosend.photosendserver01.domains.order.domain.ShippingInformation;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResponse {
    private Long orderId;
    private PaymentMethod paymentMethod;
    private OrderState orderState;
    private LocalDate orderedTime;
    private ShippingInformation shippingInformation;
    private List<OrderLineResponse> orderLineList;
}
