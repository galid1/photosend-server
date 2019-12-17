package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceOrderResponse {
    private long orderId;
    private int totalAmount;
    private long ordererId;
}
