package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductSummary {
    private long pid;
    private String name;
    private int price;
    private String productImagePath;
    private LocalDateTime uploadedTime;
}
