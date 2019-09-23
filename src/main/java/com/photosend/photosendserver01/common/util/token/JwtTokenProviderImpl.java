package com.photosend.photosendserver01.common.util.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Value("${photosend.jwt.secretkey}")
    private String secretKey;

    @Value("${photosend.jwt.expiredtime}")
    private Long expiredTime;

    @Override
    public String createToken(String uid) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("exp", makeExpiredTime());
        claims.put("uid", uid);

        return Jwts.builder()
                        .setHeader(headers)
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                        .compact();
    }

    private Date makeExpiredTime() {
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);
        return now;
    }

}
