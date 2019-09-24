package com.photosend.photosendserver01.common.util.retrofit.wechat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeChatAuthResponse {
    private String openid;
}
