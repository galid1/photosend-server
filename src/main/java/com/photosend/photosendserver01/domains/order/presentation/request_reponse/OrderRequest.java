package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.ShippingInformation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {
    private List<OrderProduct> orderProductList = new ArrayList<>();
    private ShippingInformation shippingInformation;
}
