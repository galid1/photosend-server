package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.user.domain.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @NonNull
    private Long productId;
    @NonNull
    private Integer quantity;
    @NonNull
    private Size size;
}
