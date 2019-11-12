package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserSignInService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserSignInResponse signIn(UserSignInRequest signInRequest) {
//        if(signInRequest.getLoginType() == LoginType.GUEST)
//            userEntity = signInByGuest();
        UserEntity userEntity = userRepository.findByDeviceId(signInRequest.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다. 회원가입을 먼저 진행해주세요."));

        return UserSignInResponse.builder()
                .jwtToken(userEntity.getToken().getJwtToken())
                .userId(userEntity.getUserId())
                .build();
    }

}
