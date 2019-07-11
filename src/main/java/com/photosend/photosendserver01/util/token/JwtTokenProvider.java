package com.photosend.photosendserver01.util.token;

public interface JwtTokenProvider {
    String createToken(String uid);
}
