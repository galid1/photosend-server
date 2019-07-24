package com.photosend.photosendserver01.domains.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

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
    private String passPortNum;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm", timezone = "Asia/Seoul")
    private Timestamp departureTime;
}
