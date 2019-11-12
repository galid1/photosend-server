package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.OrderState;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSummaryResponse {
    private Long orderId;
    private OrderState orderState;
    private String mainImagePath;
}
