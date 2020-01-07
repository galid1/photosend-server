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
    private int value;

    public Money multiply(int multiply){
        return new Money(value * multiply);
    }
    public Money plus(Money money) {
        return new Money(value + money.getValue());
    }
}
