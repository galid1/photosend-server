package com.photosend.photosendserver01.domains.catalog.domain.product;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductImageInformation {
    @Column(name = "product_image_path")
    private String productImagePath;
    @Embedded
    private ProductLocation productLocation;
}
