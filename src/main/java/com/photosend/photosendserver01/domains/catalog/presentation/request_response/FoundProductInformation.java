package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import com.photosend.photosendserver01.domains.catalog.domain.product.ProductInformation;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoundProductInformation {
    private Long pid;
    private String name;
    private Integer price;
    private String brand;
    private Set<Long> categoryIdList;
    private List<String> sizeList;
//    private List<Size> size = new ArrayList<>();

    public ProductInformation toProductInformation() {
        return ProductInformation.builder()
                .name(name)
                .price(price)
                .brand(brand)
                .category(categoryIdList)
                .size(sizeList)
                .build();
    }
}
