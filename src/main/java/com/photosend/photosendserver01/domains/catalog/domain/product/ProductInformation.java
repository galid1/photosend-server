package com.photosend.photosendserver01.domains.catalog.domain.product;

import lombok.*;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "product_category_list", joinColumns = @JoinColumn(name = "product_id"))
    private Set<Long> category = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "retention_stock_size", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> size = new ArrayList<>();
//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    @CollectionTable(name = "retention_stock_size", joinColumns = @JoinColumn(name = "product_id"))
//    private List<Size> size = new ArrayList<>();
}
