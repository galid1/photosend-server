package com.photosend.photosendserver01.domains.appinfo.controller;

import com.amazonaws.regions.Regions;
import com.photosend.photosendserver01.common.util.aws.SimpleS3Client;
import com.photosend.photosendserver01.domains.appinfo.controller.request_response.CouponImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/appinfo/coupons")
public class CouponController {
    @Autowired
    private SimpleS3Client s3Client;

    private static String COUPON_IMAGE_BUCKET_NAME = "photosend-coupon-img";
    private static String COUPON_BUCKET_URL = "https://photosend-coupon-img.s3.ap-northeast-2.amazonaws.com";

    @GetMapping()
    public List<CouponImageResponse> getCouponImageUrlList() {
        return s3Client.getS3Client(Regions.AP_NORTHEAST_2)
                .listObjectsV2(COUPON_IMAGE_BUCKET_NAME)
                .getObjectSummaries()
                .stream()
                .map(object -> {
                    return new CouponImageResponse(COUPON_BUCKET_URL + "/" + object.getKey());
                })
                .collect(Collectors.toList());
    }
}
