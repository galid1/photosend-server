package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetRecentlyPopulatedProductRequest {
    private int page;
    private int count;
}
