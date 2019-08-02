package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UidAndToken;
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
    public UidAndToken registerUser(UserRegisterRequest userRegisterRequest) {
        verifyDuplicatedUser(userRegisterRequest.getWechatUid());

        String jwtToken = jwtTokenProvider.createToken(userRegisterRequest.getWechatUid());
        String uid = userRepository.save(userRegisterRequest.toEntity(jwtToken)).getWechatUid();

        return UidAndToken.builder()
                .uid(uid)
                .jwtToken(jwtToken)
                .build();
    }

    private void verifyDuplicatedUser(String wechatUid) {
        if(userRepository.findById(wechatUid).isPresent())
            throw new UserDuplicatedException("ID已存在.");
//            throw new UserDuplicatedException("사용자가 이미 존재합니다.");
    }

}
