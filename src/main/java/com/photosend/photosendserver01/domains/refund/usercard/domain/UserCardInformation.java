package com.photosend.photosendserver01.domains.refund.usercard.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class UserCardInformation {
    private String serial;
    private String cardNum;
}
