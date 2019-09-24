package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.util.retrofit.wechat.WeChatOpenIdRequestClient;
import com.photosend.photosendserver01.common.util.retrofit.wechat.WeChatRetrofitClient;
import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterResponse;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.common.util.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        String weChatOpenId = requestGetWeChatOpenId(userRegisterRequest.getWeChatTempCode());
        verifyDuplicatedUser(weChatOpenId);

        UserEntity newUserEntity = createUserEntity(weChatOpenId, userRegisterRequest);
        Long savedUserId = userRepository.save(newUserEntity).getUserId();

        return UserRegisterResponse.builder()
                .userId(savedUserId)
                .jwtToken(newUserEntity.getToken().getJwtToken())
                .build();
    }

    private void verifyDuplicatedUser(String weChatOpenId) {
        if(userRepository.findByWeChatOpenId(weChatOpenId).isPresent())
            throw new UserDuplicatedException("ID已存在.");
//            throw new UserDuplicatedException("사용자가 이미 존재합니다.");
    }

    private UserEntity createUserEntity(String weChatOpenId, UserRegisterRequest registerRequest) {
        jwtTokenProvider.createToken(registerRequest.getWeChatTempCode());

        return UserEntity.builder()
                .weChatOpenId(weChatOpenId)
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken(weChatOpenId)).build())
                .userInformation(registerRequest.getUserInformation())
                .build();
    }

    private String requestGetWeChatOpenId(String weChatTempCode) {
        return WeChatRetrofitClient.getWeChatOpenId(weChatTempCode);
    }

}
