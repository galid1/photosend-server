package com.photosend.photosendserver01.domains.appinfo.controller;

import com.amazonaws.regions.Regions;
import com.photosend.photosendserver01.common.util.aws.SimpleS3Client;
import com.photosend.photosendserver01.domains.appinfo.controller.request_response.CouponDetailImageResponse;
import com.photosend.photosendserver01.domains.appinfo.controller.request_response.CouponImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/appinfo/coupons")
public class CouponController {
    @Autowired
    private SimpleS3Client s3Client;

    @Value("${photosend.aws.s3.upload-path.prefix.app-img}")
    private String s3AppImgPrefix;

    @Value("${photosend.aws.s3.bucket-name.app-img}")
    private String BUCKET_NAME;

    private String COUPON_IMAGE_DIRECTORY = "coupon-imgs";
    private String COUPON_DETAIL_IMAGE_DIRECTORY = "coupon-detail-imgs";

    @GetMapping()
    public List<CouponImageResponse> getCouponImageUrlList(@RequestParam("language") String language) {
        return s3Client.getS3Client(Regions.AP_NORTHEAST_2)
                .listObjectsV2(BUCKET_NAME)
                .getObjectSummaries()
                .stream()
                .map(object -> object.getKey())
                .filter(objectsKey -> objectsKey.startsWith(COUPON_IMAGE_DIRECTORY + "/" + language) && objectsKey.endsWith(".png"))
                .map(objectsKey -> new CouponImageResponse(s3AppImgPrefix + objectsKey))
                .collect(Collectors.toList());
    }

    @GetMapping("/detail")
    public List<CouponDetailImageResponse> getCouponDetailImageUrlList(@RequestParam("language") String language) {
        return s3Client.getS3Client(Regions.AP_NORTHEAST_2)
                .listObjectsV2(BUCKET_NAME)
                .getObjectSummaries()
                .stream()
                .map(object -> object.getKey())
                .filter(objectsKey -> objectsKey.startsWith(COUPON_DETAIL_IMAGE_DIRECTORY + "/" + language) && objectsKey.endsWith(".png"))
                .map(objectsKey -> new CouponDetailImageResponse(s3AppImgPrefix + objectsKey))
                .collect(Collectors.toList());
    }

}
