package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.exception.TokenWrongAudienceException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterResponse;
import com.photosend.photosendserver01.domains.user.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/users")
    public UserRegisterResponse registerUser(@RequestBody UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = userRegisterService.registerUser(request);

        return userRegisterResponse;
    }

    //TODO 토큰 익셉션 핸들러를 이용해 토큰 재발급 로직 처리
    @ExceptionHandler
    public String reissueToken(TokenWrongAudienceException tokenException) {
        return "new Tokuest";
    }
}
