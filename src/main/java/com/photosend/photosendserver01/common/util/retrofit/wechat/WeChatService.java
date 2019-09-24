package com.photosend.photosendserver01.common.util.retrofit.wechat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface WeChatService {
    @GET("/sns/oauth2/access_token")
    Call<WeChatAuthResponse> requestGetWeChatOpenId(@QueryMap Map<String, String> params);
}
