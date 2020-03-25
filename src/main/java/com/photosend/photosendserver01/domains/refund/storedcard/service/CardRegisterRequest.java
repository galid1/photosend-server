package com.photosend.photosendserver01.domains.refund.storedcard.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CardRegisterRequest {
    private String cardNum;
    private String serial;
}
