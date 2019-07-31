package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.domain.exception.TokenExpiredException;
import com.photosend.photosendserver01.domains.user.domain.exception.TokenWrongAudienceException;
import com.photosend.photosendserver01.domains.user.infra.wechat.WechatSignUpRequester;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UidAndToken;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.domains.user.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private WechatSignUpRequester signUpRequester;

    @PostMapping("/")
    public UidAndToken registerUser(@RequestBody UserRegisterRequest request) {
        signUpRequester.requestSignUp(request.getWechatUid());
        UidAndToken uidAndToken = userRegisterService.registerUser(request);

        return uidAndToken;
    }

    //TODO 토큰 익셉션 핸들러를 이용해 토큰 재발급 로직 처리
    @ExceptionHandler
    public String reissueToken(TokenWrongAudienceException tokenException) {
        return "new Tokuest";
    }

    @ExceptionHandler
    public String tokenError(TokenExpiredException tokenExpiredException) {
        return "";
    }
}
