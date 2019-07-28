package com.photosend.photosendserver01.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    @Column(name = "zip_code")
    @NonNull
    private String zipCode;
    @NonNull
    private String address1;
    @NonNull
    private String address2;
}
