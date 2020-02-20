package com.photosend.photosendserver01.common.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Money {
    private Double value;

    public Money multiply(int multiply){
        return Money
                .builder()
                .value(value * multiply)
                .build();
    }
}
