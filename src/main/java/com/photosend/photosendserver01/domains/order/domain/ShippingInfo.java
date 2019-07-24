package com.photosend.photosendserver01.domains.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShippingInfo {
    private String staticAddress = "제2 터미널";

//    @NonNull
//    private Address address;
    @NonNull
    private Receiver receiver;

    @NonNull
    @Column(name = "receive_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private Timestamp receiveTime;

    @Column(name = "shipping_message")
    private String shippingMessage;
}
