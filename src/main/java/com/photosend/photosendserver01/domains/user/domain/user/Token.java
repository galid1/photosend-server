package com.photosend.photosendserver01.domains.user.domain.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Token {
    @Column(name = "token")
    private String jwtToken;
}
