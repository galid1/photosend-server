package com.photosend.photosendserver01.user.service;

public interface JwtTokenVerifier {
    void verifyToken(String uid, String jwtTokenRequest);
}
