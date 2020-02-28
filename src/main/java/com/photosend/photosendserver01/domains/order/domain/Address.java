package com.photosend.photosendserver01.domains.order.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class Address {
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String address1;
    private String address2;
}
