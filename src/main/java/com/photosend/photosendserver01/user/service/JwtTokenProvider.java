package com.photosend.photosendserver01.user.service;

public interface JwtTokenProvider {
    String createToken(String uid);
}
