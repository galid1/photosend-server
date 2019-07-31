package com.photosend.photosendserver01.domains.user.infra.wechat;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WechatSignUpRequester {

    private String WECHAT_SIGNUP_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx4a3bd4045f38d7b2&secret=0f0d780be74d91bebe6448bbf5799a5f&grant_type=authorization_code&";

    public void requestSignUp(String code) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(WECHAT_SIGNUP_URL + "code=" + code)
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response.body().toString());
    }
}
