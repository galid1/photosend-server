package com.photosend.photosendserver01.util.token;

public interface JwtTokenVerifier {
    void verifyToken(String uid, String jwtTokenRequest);
}
