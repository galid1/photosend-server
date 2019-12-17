package com.photosend.photosendserver01.domains.appinfo.controller.request_response;

import lombok.Getter;

@Getter
public class CouponImageResponse {
    private String couponImageUrl;

    public CouponImageResponse(String couponImageUrl) {
        this.couponImageUrl = couponImageUrl;
    }
}
