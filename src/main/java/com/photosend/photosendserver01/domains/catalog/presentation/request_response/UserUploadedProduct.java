package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductState;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserUploadedProduct {
    private long productId;
    private String productImagePath;
    private String uploaderName;
    private LocalDateTime uploadedTime;
    private ProductState productState;
}
