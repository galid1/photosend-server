package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.photosend.photosendserver01.domains.user.domain.Token;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import com.photosend.photosendserver01.domains.user.domain.UserInformation;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String weChatOpenId;
    private UserInformation userInformation;

    public UserEntity toEntity(String jwtToken) {
        Token token = Token.builder()
                .jwtToken(jwtToken)
                .build();

        return UserEntity.builder()
                .token(token)
                .weChatOpenId(weChatOpenId)
                .userInformation(userInformation)
                .build();
    }
}
