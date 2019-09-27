package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

public enum LoginType {
    GUEST("guest"), WECHAT("weChat");

    private String loginType;

    LoginType(String loginType) {
        this.loginType = loginType;
    }
}
