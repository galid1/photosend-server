package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.util.retrofit.wechat.WeChatRetrofitClient;
import com.photosend.photosendserver01.common.util.token.JwtTokenProvider;
import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.UserSignInFailBecauseNotExistException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.LoginType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserSignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserSignInService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserSignInResponse signIn(UserSignInRequest signInRequest) {
        UserEntity userEntity = null;

        if(signInRequest.getLoginType() == LoginType.GUEST)
            userEntity = signInByGuest();

        if(signInRequest.getLoginType() == LoginType.WECHAT)
            userEntity = signInByWeChat(signInRequest.getWeChatTempCode());

        return UserSignInResponse.builder()
                .jwtToken(userEntity.getToken().getJwtToken())
                .userId(userEntity.getUserId())
                .build();
    }

    private UserEntity signInByWeChat(String weChatTempCode) {
        String weChatOpenId = WeChatRetrofitClient.getWeChatOpenId(weChatTempCode);
        return userRepository.findByWeChatOpenId(weChatOpenId)
                .orElseThrow(() -> new UserSignInFailBecauseNotExistException("등록되지 않은 사용자입니다. 회원가입을 먼저 해주세요.", weChatOpenId));
    }

    //TODO 임시 로직, SIGN IN BY WECHAT 완성된 후 변경 요함
    @Autowired
    private JwtTokenProvider jwtTokenProvider; // 변경하면서 지우기

    private UserEntity signInByGuest() {
        UserEntity userEntity = UserEntity.builder()
                .weChatOpenId(UUID.randomUUID().toString())
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken()).build())
                .userInformation(UserInformation.builder()
                        .departureTime(Timestamp.valueOf(LocalDateTime.of(2020,12,12,12,12)))
                        .name("GUEST")
                        .build())
                .build();

        return userRepository.save(userEntity);
    }
}
