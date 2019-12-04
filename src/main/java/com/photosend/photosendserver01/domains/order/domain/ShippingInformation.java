package com.photosend.photosendserver01.domains.order.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ShippingInformation {
    @Column(name = "user_sns_id")
    private String userSnsId;
    @Column(name = "address")
    private Address address;
    @Column(name = "receive_time")
    private LocalDateTime receiveTime;
}
