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
    @NonNull
    private int value;

    public Money multiply(int multiply){
        return new Money(value * multiply);
    }
}
