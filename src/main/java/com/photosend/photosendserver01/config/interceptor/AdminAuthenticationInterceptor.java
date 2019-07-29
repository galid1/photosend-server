package com.photosend.photosendserver01.config.interceptor;

import com.photosend.photosendserver01.util.file.KeyValueFileLoader;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private KeyValueFileLoader keyValueFileLoader;

    @Value("${file-path.admin.credential}")
    private String adminCredentailFilePath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminPassword = keyValueFileLoader.getValueFromFile(adminCredentailFilePath, "photosend_admin_password");
        String authorizationInHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!adminPassword.equals(authorizationInHeader))
            throw new IllegalArgumentException("Admin Password 가 일치하지 않습니다.");

        return true;
    }
}
