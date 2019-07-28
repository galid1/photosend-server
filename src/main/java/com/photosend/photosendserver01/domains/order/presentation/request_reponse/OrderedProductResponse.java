package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedProductResponse {
    private Long orderId;
    private List<String> productImagePaths;
}
