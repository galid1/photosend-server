package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSignInResponse {
    private Long userId;
    private String token;
    private String apnsDeviceToken;
}
