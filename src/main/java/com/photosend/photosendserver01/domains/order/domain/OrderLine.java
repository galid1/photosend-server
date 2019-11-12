package com.photosend.photosendserver01.domains.order.domain;

import com.photosend.photosendserver01.common.model.Money;
import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {
    @Column(name = "product_id")
    private Long productId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_price"))
    private Money productPrice;

    private Integer quantity;

//    @Enumerated(EnumType.STRING)
//    private Size size;
    private String size;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "total_price"))
    private Money totalPrice;

    @Builder
    public OrderLine(Long productId, Money productPrice, Integer quantity, String size) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.size = size;
        this.totalPrice = calculateAmounts();
    }

    private Money calculateAmounts() {
        return this.productPrice.multiply(this.quantity);
    }
}
