package com.photosend.photosendserver01.domains.user.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInformation {
    // Required Field
    @NonNull
    @Min(value = 1, message = "")
    private String name;
//    @NonNull
    @Pattern(regexp = "", message = "")
    private String phoneNum;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private Timestamp departureTime;
}
