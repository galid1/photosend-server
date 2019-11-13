package com.photosend.photosendserver01.domains.location.presentation.request_response;

import com.photosend.photosendserver01.domains.location.domain.location.Location;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationResponse {
    private String name;
    private String locationImagePath;
    private Location location;
}
