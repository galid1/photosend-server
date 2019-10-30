package com.photosend.photosendserver01.domains.user.domain.product;

import lombok.*;

import javax.persistence.*;
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
    private Integer price;

    private String brand;

    private String size;
//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    @CollectionTable(name = "retention_stock_size", joinColumns = @JoinColumn(name = "product_id"))
//    private List<Size> size = new ArrayList<>();
}
