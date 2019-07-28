package com.photosend.photosendserver01.config.interceptor;

import org.apache.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AdminAuthenticationInterceptor implements HandlerInterceptor {
    private String adminCredentailFilePath = System.getProperty("user.home") + "/.photosend/adminCredential.txt";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminPassword = readAdminCredentialFilePassword();
        String authorizationInHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!adminPassword.equals(authorizationInHeader))
            throw new IllegalArgumentException("Admin Password 가 일치하지 않습니다.");

        return true;
    }

    private String readAdminCredentialFilePassword() throws IOException {
        File adminCredentailFile = new File(adminCredentailFilePath);
        BufferedReader br = new BufferedReader(new FileReader(adminCredentailFile));
        return br.readLine().split("=")[1];
    }

}
