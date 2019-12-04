package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedProduct {
    private long productId;
    private String productImagePath;
}
