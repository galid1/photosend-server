package com.photosend.photosendserver01.user.presentation.request_reponse;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UidAndToken {
    private String uid;
    private String jwtToken;
}
