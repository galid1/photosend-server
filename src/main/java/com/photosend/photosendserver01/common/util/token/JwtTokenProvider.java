package com.photosend.photosendserver01.common.util.token;

public interface JwtTokenProvider {
    String createToken(String uid);
}
