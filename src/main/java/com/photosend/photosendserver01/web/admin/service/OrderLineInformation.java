package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineInformation {
    private long productId;
    private String productImagePath;
    private ProductInformation productInformation;
    private Money productPrice;
}
