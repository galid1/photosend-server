package com.photosend.photosendserver01.domains.user.domain.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
    private String jwtToken;
}
