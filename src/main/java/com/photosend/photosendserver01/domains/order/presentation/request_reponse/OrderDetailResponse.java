package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.common.model.Money;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailResponse {
    private long productId;
    private String productImagePath;
    private int quantity;
    private String size;
    private Money totalPrice;
}
