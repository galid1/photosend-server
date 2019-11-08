package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.util.token.JwtTokenProvider;
import com.photosend.photosendserver01.domains.user.domain.user.Token;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.LoginType;
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
        UserEntity userEntity = null;

        if(signInRequest.getLoginType() == LoginType.GUEST)
            userEntity = signInByGuest();

        return UserSignInResponse.builder()
                .jwtToken(userEntity.getToken().getJwtToken())
                .userId(userEntity.getUserId())
                .build();
    }

    //TODO 임시 로직, SIGN IN BY WECHAT 완성된 후 변경 요함
    @Autowired
    private JwtTokenProvider jwtTokenProvider; // 변경하면서 지우기

    private UserEntity signInByGuest() {
        UserEntity userEntity = UserEntity.builder()
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken()).build())
                .userInformation(UserInformation.builder()
                        .name("GUEST")
                        .build())
                .build();

        return userRepository.save(userEntity);
    }
}
