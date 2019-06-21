package com.photosend.photosendserver01.user.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ticket {
    // Required Field
    @NonNull
    private String ticketImagePath;

}
