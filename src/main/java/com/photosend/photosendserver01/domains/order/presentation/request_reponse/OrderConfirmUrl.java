package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderConfirmUrl {
    private String orderConfirmUrl;
}
