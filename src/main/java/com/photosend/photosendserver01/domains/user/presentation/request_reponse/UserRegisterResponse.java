package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterResponse {
    private Long userId;
    private String token;
}
