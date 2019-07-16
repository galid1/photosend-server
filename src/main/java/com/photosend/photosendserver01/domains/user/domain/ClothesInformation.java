package com.photosend.photosendserver01.domains.user.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClothesInformation {
    @NonNull
    private String name;
    @NonNull
    private Integer price;
    @NonNull
    private String brand;
}
