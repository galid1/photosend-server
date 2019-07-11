package com.photosend.photosendserver01.domains.user.service;

public interface JwtTokenVerifier {
    void verifyToken(String uid, String jwtTokenRequest);
}
