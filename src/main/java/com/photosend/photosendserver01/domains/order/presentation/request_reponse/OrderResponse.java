package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.OrderState;
import com.photosend.photosendserver01.domains.order.domain.PaymentMethod;
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
    private List<OrderLineResponse> orderLineList;
}
