package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
