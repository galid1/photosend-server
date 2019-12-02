package com.photosend.photosendserver01.domains.appinfo.controller.request_response;

import lombok.Getter;

@Getter
public class AppInfoImageResponse {
    private String appInfoImageUrl;

    public AppInfoImageResponse(String appInfoImageUrl) {
        this.appInfoImageUrl = appInfoImageUrl;
    }
}
