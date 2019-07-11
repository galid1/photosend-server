package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UidAndToken;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
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
    public UidAndToken registerUser(UserRegisterRequest userRegisterRequest) {
        //TODO 중복 확인 여기서 ?

        String jwtToken = jwtTokenProvider.createToken(userRegisterRequest.getWechatUid());
        String uid = userRepository.save(userRegisterRequest.toEntity(jwtToken)).getWechatUid();

        return UidAndToken.builder()
                .uid(uid)
                .jwtToken(jwtToken)
                .build();
    }

}
