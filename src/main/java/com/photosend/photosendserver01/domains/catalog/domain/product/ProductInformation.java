package com.photosend.photosendserver01.domains.catalog.domain.product;

import lombok.*;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductInformation {
    private String name;

    @NonNull
    private Double price;

    private String brand;

    // 관리자 편의를 위한
    private String memo;

    @ElementCollection
    @CollectionTable(name = "retention_stock_size", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> size = new ArrayList<>();
}
