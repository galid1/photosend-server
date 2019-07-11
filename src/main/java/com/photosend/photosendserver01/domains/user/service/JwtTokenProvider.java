package com.photosend.photosendserver01.domains.user.service;

public interface JwtTokenProvider {
    String createToken(String uid);
}
