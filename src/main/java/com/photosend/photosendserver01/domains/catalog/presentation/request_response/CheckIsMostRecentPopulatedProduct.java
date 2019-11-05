package com.photosend.photosendserver01.domains.catalog.presentation.request_response;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class CheckIsMostRecentPopulatedProduct {
    private boolean isMostRecentPopulatedProduct;
}
