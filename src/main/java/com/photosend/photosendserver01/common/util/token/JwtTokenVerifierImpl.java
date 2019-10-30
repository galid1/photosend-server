package com.photosend.photosendserver01.common.util.token;

import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.TokenExpiredException;
import com.photosend.photosendserver01.domains.user.domain.exception.TokenWrongAudienceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenVerifierImpl implements JwtTokenVerifier {
    @Autowired
    private UserRepository userRepository;

    @Value("${photosend.jwt.secretkey}")
    private String secretKey;

    @Override
    public void verifyToken(Long userId, String jwtTokenRequest) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwtTokenRequest)
                .getBody();

        verifyExpired((Long) claims.get("exp"));
        verifyAudience(userId, jwtTokenRequest);
    }

    private void verifyExpired(Long expiredDate) {
        Date expiredTime = new Date(expiredDate);
        Date now = new Date();

        if(now.after(expiredTime))
            throw new TokenExpiredException("토큰이 만료되었습니다.");
    }

    private void verifyAudience(Long userId, String jwtTokenRequest) {
        String userToken = userRepository.findById(userId).get().getToken().getJwtToken();

        if(!userToken.equals(jwtTokenRequest))
            throw new TokenWrongAudienceException("토큰이 일치하지 않습니다.");
    }
}
