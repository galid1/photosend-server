package com.photosend.photosendserver01.domains.refund.usercard.domain;

import com.photosend.photosendserver01.common.model.Money;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsageHistory {
    private LocalDate date;
    private String place;
    private Money paymentAmount;
}
