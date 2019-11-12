package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

import javax.validation.constraints.Min;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {
    @NonNull
    private Long productId;
    @NonNull
    @Min(1)
    private Integer quantity;
    @NonNull
    private String size;
}
