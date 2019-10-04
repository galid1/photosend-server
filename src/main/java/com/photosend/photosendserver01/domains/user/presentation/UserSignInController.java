package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.domain.exception.UserSignInFailBecauseNotExistException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInFailBecauseNotExistResponse;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInResponse;
import com.photosend.photosendserver01.domains.user.service.UserSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSignInController {

    @Autowired
    private UserSignInService userSignInService;

    @PutMapping("/users/")
    public UserSignInResponse signIn(@RequestBody UserSignInRequest userSignInRequest) {
        System.out.println(userSignInRequest.getWeChatTempCode());
        return userSignInService.signIn(userSignInRequest);
    }

    @ExceptionHandler
    public UserSignInFailBecauseNotExistResponse userNotExistExceptionHandler(UserSignInFailBecauseNotExistException userSignInFailBecauseNotExistException) {
        return new UserSignInFailBecauseNotExistResponse(userSignInFailBecauseNotExistException.getWeChatOpenId());
    }
}