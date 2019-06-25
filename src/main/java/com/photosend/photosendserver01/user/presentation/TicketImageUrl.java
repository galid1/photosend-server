package com.photosend.photosendserver01.user.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketImageUrl {
    @JsonProperty("image-path")
    private String ticketImageUrl;

    @Builder
    public TicketImageUrl(String ticketImageUrl) {
        this.ticketImageUrl = ticketImageUrl;
    }
}
