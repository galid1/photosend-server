package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    @NonNull
    @Enumerated(EnumType.STRING)
    private Size size;
}
