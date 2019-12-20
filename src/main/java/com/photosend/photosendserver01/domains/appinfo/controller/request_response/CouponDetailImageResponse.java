package com.photosend.photosendserver01.domains.appinfo.controller.request_response;

import lombok.Getter;

@Getter
public class CouponDetailImageResponse {
    private String couponDetailImageUrl;

    public CouponDetailImageResponse(String couponDetailImageUrl) {
        this.couponDetailImageUrl = couponDetailImageUrl;
    }
}
