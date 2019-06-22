package com.photosend.photosendserver01.user.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketImageUrl {
    @JsonProperty("image_path")
    private String ticketImageUrl;
}
