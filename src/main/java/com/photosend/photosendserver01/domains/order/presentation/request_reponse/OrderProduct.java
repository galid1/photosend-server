package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    private long productId;
    private int quantity;
    private String size;
}
