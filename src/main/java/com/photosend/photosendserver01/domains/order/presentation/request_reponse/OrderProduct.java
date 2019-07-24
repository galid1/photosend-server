package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.domains.user.domain.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @JsonProperty("product-id")
    private Long productId;
    private Integer quantity;
    private Size size;
}
