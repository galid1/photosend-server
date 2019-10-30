package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.util.token.JwtTokenProvider;
import com.photosend.photosendserver01.domains.user.domain.user.Token;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterResponse;
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
        UserEntity newUserEntity = createUserEntity(userRegisterRequest);
        Long savedUserId = userRepository.save(newUserEntity).getUserId();

        return UserRegisterResponse.builder()
                .userId(savedUserId)
                .jwtToken(newUserEntity.getToken().getJwtToken())
                .build();
    }

    private UserEntity createUserEntity(UserRegisterRequest registerRequest) {
        verifyDuplicatedUser(registerRequest.getWeChatOpenId());

        return UserEntity.builder()
                .weChatOpenId(registerRequest.getWeChatOpenId())
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken()).build())
                .userInformation(registerRequest.getUserInformation())
                .build();
    }


    private void verifyDuplicatedUser(String weChatOpenId) {
        if(userRepository.findByWeChatOpenId(weChatOpenId).isPresent())
            throw new UserDuplicatedException("ID已存在."); // ID 중복
    }

}
