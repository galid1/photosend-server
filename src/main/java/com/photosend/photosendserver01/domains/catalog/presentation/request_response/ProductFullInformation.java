package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductState;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductFullInformation {
    private Long productId;
    @NotNull
    private String productImagePath;
    // Optional Field
    private FoundProductInformation foundProductInformation;
    private ProductState productState;
}
