package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductImageUrl {
    @JsonProperty("productId")
    private Long pid;
    @JsonProperty("productImagePath")
    private String productImageUrl;
}
