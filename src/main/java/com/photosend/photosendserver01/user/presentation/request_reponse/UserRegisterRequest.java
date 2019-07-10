package com.photosend.photosendserver01.user.presentation.request_reponse;

import com.photosend.photosendserver01.user.domain.UserEntity;
import com.photosend.photosendserver01.user.domain.UserInformation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String wechatUid;
    private UserInformation userInformation;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .wechatUid(wechatUid)
                .userInformation(userInformation)
                .build();
    }
}
