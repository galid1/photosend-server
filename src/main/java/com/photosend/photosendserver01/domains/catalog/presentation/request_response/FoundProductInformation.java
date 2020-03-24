package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductInformation;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoundProductInformation {
    private Long pid;
    private String name;
    private Double price;
    private String brand;
    private String memo;
    private List<String> sizeList;

    public ProductInformation toProductInformation() {
        return ProductInformation.builder()
                .name(name)
                .price(price)
                .brand(brand)
                .memo(memo)
                .size(sizeList)
                .build();
    }
}
