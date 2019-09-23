package com.photosend.photosendserver01.common.util.token;

public interface JwtTokenVerifier {
    void verifyToken(Long userId, String jwtTokenRequest);
}
