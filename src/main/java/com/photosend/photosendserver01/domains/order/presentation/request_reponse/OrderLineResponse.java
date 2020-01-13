package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class OrderLineResponse {
    private long productId;
    private String productImagePath;
    private String name;
    private int price;
}
