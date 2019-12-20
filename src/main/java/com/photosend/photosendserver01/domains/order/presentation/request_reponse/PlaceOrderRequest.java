package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import com.photosend.photosendserver01.domains.order.domain.PaymentMethod;
import com.photosend.photosendserver01.domains.order.domain.ShippingInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceOrderRequest {
    private PaymentMethod paymentMethod;
    private List<OrderProduct> orderProductList = new ArrayList<>();
    private ShippingInformation shippingInformation;
}
