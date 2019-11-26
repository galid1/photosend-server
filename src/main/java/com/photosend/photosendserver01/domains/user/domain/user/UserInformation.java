package com.photosend.photosendserver01.domains.user.domain.user;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInformation {
    @NonNull
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private int age;
}
