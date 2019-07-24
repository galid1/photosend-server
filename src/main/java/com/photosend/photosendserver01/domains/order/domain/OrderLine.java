package com.photosend.photosendserver01.domains.order.domain;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.user.domain.Size;
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
    private Money price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "total_price"))
    private Money amounts;

    @Builder
    public OrderLine(Long productId, Money price, Integer quantity, Size size) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return this.price.multiply(this.quantity);
    }
}
