package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedProductResponse {
    private Long orderId;
    private String productImagePath;
}
