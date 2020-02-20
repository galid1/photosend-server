package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductSummaryForCatalog {
    private long productId;
    private String name;
    private String brand;
    private Double price;
    private String productImagePath;
    private LocalDateTime uploadedTime;
}
