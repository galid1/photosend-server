package com.photosend.photosendserver01.common.util.apns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NotificationPayload {
    private String alertTitle;
    private String alertBody;
}
