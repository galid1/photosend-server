package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.RenewApnsDeviceTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApnsDeviceTokenService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void renewApnsDeviceToken(long userId, RenewApnsDeviceTokenRequest request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userEntity.setAPNsDeviceToken(request.getApnsDeviceToken());
    }
}
