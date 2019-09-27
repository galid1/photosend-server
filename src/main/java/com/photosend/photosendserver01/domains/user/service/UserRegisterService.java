package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.util.retrofit.wechat.WeChatRetrofitClient;
import com.photosend.photosendserver01.common.util.token.JwtTokenProvider;
import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserRepository;
import com.photosend.photosendserver01.domains.user.domain.exception.DepartureTimeException;
import com.photosend.photosendserver01.domains.user.domain.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.LoginType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

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
        verifyDepartureTime(registerRequest.getUserInformation().getDepartureTime().toLocalDateTime());

        String weChatOpenId = makeWeChatOpenIdByLoginType(registerRequest);

        verifyDuplicatedUser(weChatOpenId);

        return UserEntity.builder()
                .weChatOpenId(weChatOpenId)
                .token(Token.builder().jwtToken(jwtTokenProvider.createToken(weChatOpenId)).build())
                .userInformation(registerRequest.getUserInformation())
                .build();
    }

    private String makeWeChatOpenIdByLoginType(UserRegisterRequest registerRequest) {
        String weChatOpenId = "";

        if(registerRequest.getLoginType() == LoginType.WECHAT) {
            weChatOpenId = requestGetWeChatOpenId(registerRequest.getWeChatTempCode());
        }
        if(registerRequest.getLoginType() == LoginType.GUEST) {
            weChatOpenId = UUID.randomUUID().toString();
        }

        return weChatOpenId;
    }

    private void verifyDuplicatedUser(String weChatOpenId) {
        if(userRepository.findByWeChatOpenId(weChatOpenId).isPresent())
            throw new UserDuplicatedException("ID已存在.");
//            throw new UserDuplicatedException("사용자가 이미 존재합니다.");
    }

    private int registerMaxTime = 20;
    private int registerMinTime = 10;

    private void verifyDepartureTime(LocalDateTime departureTime) {
        LocalDateTime now = LocalDateTime.now();
        if(Duration.between(now, departureTime).toDays() < 1)
            throw new DepartureTimeException("为了方便配送,至少在出境前一天可以加入.");

        int departureHour = departureTime.getHour();
        if(departureHour < registerMinTime || departureHour > registerMaxTime)
            throw new DepartureTimeException("为了确保顺畅地配送,仅允许上午10点至下午8点出境的用户使用.");
    }

    private String requestGetWeChatOpenId(String weChatTempCode) {
        return WeChatRetrofitClient.getWeChatOpenId(weChatTempCode);
    }

}
