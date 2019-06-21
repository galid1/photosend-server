package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInformation {
    // Required Field
    @NonNull
    private String name;
    @NonNull
    private Integer age;
}
