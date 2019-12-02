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
        verifyExistUser(userRegisterRequest.getDeviceId());

        UserEntity newUserEntity = createAndSaveUserEntity(userRegisterRequest);

        return UserRegisterResponse.builder()
                .userId(newUserEntity.getUserId())
                .jwtToken(newUserEntity.getToken().getJwtToken())
                .build();
    }

    private UserEntity createAndSaveUserEntity(UserRegisterRequest registerRequest) {
        return userRepository.save(UserEntity.builder()
                .deviceId(registerRequest.getDeviceId())
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken()).build())
                .userInformation(registerRequest.getUserInformation())
                .build());
    }

    private void verifyExistUser(String userUniqueId) {
        if(userRepository.findByDeviceId(userUniqueId).isPresent()) {
            throw new UserDuplicatedException("이미 회원가입이 된 유저입니다.");
        }
    }
}
