package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Ticket {
    // Required Field
    @NonNull
    @Column(name = "image_path")
    private String ticketImagePath;

    private Timestamp ticketImageChangeTime;
}
