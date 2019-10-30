package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.photosend.photosendserver01.domains.user.domain.product.ProductState;
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
