package com.photosend.photosendserver01.domains.order.presentation.request_reponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductList {
    private List<Long> productIdList;
}
