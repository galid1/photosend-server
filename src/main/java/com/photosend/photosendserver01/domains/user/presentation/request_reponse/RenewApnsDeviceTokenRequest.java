package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenewApnsDeviceTokenRequest {
    private String apnsDeviceToken;
}
