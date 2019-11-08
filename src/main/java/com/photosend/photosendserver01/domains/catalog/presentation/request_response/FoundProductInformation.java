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
    private Integer price;
    private String brand;
    private String type;
    private List<String> size;
//    private List<Size> size = new ArrayList<>();

    public ProductInformation toProductInformation() {
        return ProductInformation.builder()
                .name(name)
                .price(price)
                .brand(brand)
                .type(type)
                .size(size)
                .build();
    }
}
