package com.photosend.photosendserver01.domains.user.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductLocation {
    private float longitude;
    private float latitude;
}
