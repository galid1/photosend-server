package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.OrderState;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedResponse {
    private Long orderId;
    private OrderState orderState;
    private List<OrderedLineResponse> orderedLineResponses;
}
