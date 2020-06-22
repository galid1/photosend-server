package com.photosend.photosendserver01.domains.appinfo.controller;

import com.amazonaws.regions.Regions;
import com.photosend.photosendserver01.common.util.aws.SimpleS3Client;
import com.photosend.photosendserver01.domains.appinfo.controller.request_response.AppInfoImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appinfo/images")
@RequiredArgsConstructor
public class AppInfoController {

    private final SimpleS3Client s3Client;

    private static String APP_INFO_IMAGE_BUCKET_NAME = "photosend-info-img";
    private static String APP_INFO_BUCKET_URL = "https://photosend-info-img.s3.ap-northeast-2.amazonaws.com";

    @GetMapping()
    public List<AppInfoImageResponse> getAppInfoImageUrlList() {
        return s3Client.getS3Client(Regions.AP_NORTHEAST_2)
                .listObjectsV2(APP_INFO_IMAGE_BUCKET_NAME)
                .getObjectSummaries()
                .stream()
                .map(object -> {
                    return new AppInfoImageResponse(APP_INFO_BUCKET_URL + "/" + object.getKey());
                })
                .collect(Collectors.toList());
    }
}
