package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.photosend.photosendserver01.domains.user.domain.user.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
//    private LoginType loginType;
    private String deviceId;
    private UserInformation userInformation;
}
