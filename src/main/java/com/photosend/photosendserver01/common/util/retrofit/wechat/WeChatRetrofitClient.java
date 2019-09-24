package com.photosend.photosendserver01.common.util.retrofit.wechat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WeChatRetrofitClient {
    private static String BASE_URL = "https://api.weixin.qq.com/";

    public static String getWeChatOpenId(String weChatTempCode) {
        WeChatAuthResponse authResponse = null;
        try {
                 authResponse = getInstance()
                    .create(WeChatService.class)
                    .requestGetWeChatOpenId(makeRequestParameters(weChatTempCode))
                    .execute()
                    .body();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return authResponse.getOpenid();
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private static Map<String, String> makeRequestParameters(String weChatTempCode) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", "wx4a3bd4045f38d7b2");
        params.put("secret", "a328c5e3c9e9c41f8079e3fa0b6fef4f");
        params.put("code", weChatTempCode);
        params.put("grant_type", "authorization_code");

        return params;
    }
}
