package com.photosend.photosendserver01.domains.admin.presentation.request_response;

import com.photosend.photosendserver01.domains.order.domain.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AdminChangeOrderStateRequest {
    private OrderState newOrderState;
}
