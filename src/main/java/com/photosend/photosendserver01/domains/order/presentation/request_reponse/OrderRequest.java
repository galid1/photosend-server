package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.user.domain.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {
    @NonNull
    private Long productId;
    @NonNull
    private Integer quantity;
    @NonNull
    private Size size;
}
