package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime uploadedTime;
    private ProductState productState;
}
