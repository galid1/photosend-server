package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequest {
    @NotNull
    private OrderProduct orderProduct;
    @NotNull
    private String ordererWechatUid;
}
