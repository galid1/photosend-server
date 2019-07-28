package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.domains.user.domain.ProductEntity;
import com.photosend.photosendserver01.domains.user.domain.ProductLocation;
import com.photosend.photosendserver01.domains.user.domain.ProductState;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
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

    public ProductEntity toEntity(ProductLocation productLocation, UserEntity user) {
        return ProductEntity.builder()
                .productLocation(productLocation)
                .productImagePath(this.productImageUrl)
                .userEntity(user)
                .build();
    }
}
