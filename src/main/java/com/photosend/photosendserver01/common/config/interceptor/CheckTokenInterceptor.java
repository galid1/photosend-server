package com.photosend.photosendserver01.common.config.interceptor;

import com.photosend.photosendserver01.common.util.token.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
public class CheckTokenInterceptor implements HandlerInterceptor {
    private final JwtTokenVerifier jwtTokenVerifier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-JWT");
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String temp = (String) pathVariables.get("userId");
        Long userId = new Long(temp);

        if(token == null)
            throw new IllegalArgumentException("Token 또는 userId가 null 입니다.");

        if(jwtTokenVerifier == null)
            throw new IllegalArgumentException("token verifier가 존재하지 않습니다.");

        jwtTokenVerifier.verifyToken(userId, token);

        return true;
    }
}




