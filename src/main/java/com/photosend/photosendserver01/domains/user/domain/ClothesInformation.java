package com.photosend.photosendserver01.domains.user.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClothesInformation {
    private String name;

    @NonNull
    private Integer price;

    private String brand;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "retention_stock_size", joinColumns = @JoinColumn(name = "clothes_id"))
    private List<Size> size = new ArrayList<>();
}
