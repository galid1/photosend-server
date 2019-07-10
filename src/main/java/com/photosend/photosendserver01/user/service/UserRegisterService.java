package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.UserRepository;
import com.photosend.photosendserver01.user.presentation.request_reponse.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String registerUser(UserRegisterRequest userRegisterRequest) {
        return userRepository.save(userRegisterRequest.toEntity()).getWechatUid();
    }

}
