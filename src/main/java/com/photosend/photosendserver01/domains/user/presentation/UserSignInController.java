package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInResponse;
import com.photosend.photosendserver01.domains.user.service.UserSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserSignInController {

    private final UserSignInService userSignInService;

    @PutMapping("/users")
    public UserSignInResponse signIn(@RequestBody UserSignInRequest userSignInRequest) {
        return userSignInService.signIn(userSignInRequest);
    }
}
