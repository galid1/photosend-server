package com.photosend.photosendserver01.domains.order.domain;

import com.photosend.photosendserver01.common.config.logging.BaseTimeEntity;
import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.order.exception.NoOrderLineException;
import com.photosend.photosendserver01.domains.order.exception.ShipStateException;
import com.photosend.photosendserver01.domains.user.domain.user.UserEntity;
import com.photosend.photosendserver01.domains.user.exception.DepartureTimeException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long oid;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Embedded
    @ManyToOne
    @JoinColumn(name = "orderer_wechat_uid")
    private UserEntity orderer;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> orderLines;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "totalAmount"))
    private Money totalAmount;

    @Builder
    public OrderEntity(List<OrderLine> orderLines, UserEntity orderer) {
        verifyDepartureTime(orderer.getUserInformation().getDepartureTime().toLocalDateTime());
        setOrderLines(orderLines);
        this.orderer = orderer;
        this.orderState = OrderState.SHIPPING_IN_PROGRESS;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyOrderLine(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmount();
    }

    @Transient
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private int shippingFee = 120;

    private void calculateTotalAmount() {
        int totalAmountTemp = shippingFee + this.orderLines.stream()
                                                    .mapToInt(orderLine -> orderLine.getTotalPrice().getValue())
                                                    .sum();
        this.totalAmount = Money.builder()
                .value(totalAmountTemp)
                .build();
    }

    private void verifyOrderLine(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty())
            throw new NoOrderLineException("至少要订购一个以上的商品.");
//            throw new NoOrderLineException("최소한 하나 이상의 상품을 주문해야 합니다.");
    }

    private void verifyDepartureTime(LocalDateTime departureTime) {
        int departureYear = departureTime.getYear();
        int departureMonth = departureTime.getMonthValue();
        int departureDay = departureTime.getDayOfMonth();

        LocalDateTime orderDeadLine = LocalDateTime.of(departureYear, departureMonth, departureDay, 19, 0, 0);

        if(LocalDateTime.now().isAfter(orderDeadLine))
            throw new DepartureTimeException("为了确保顺畅得配送,只能在出境前一天晚上7点之前订购。");
//            throw new DepartureTimeException("원활한 배송을 위해 주문은 전날 오후 7시이전까지만 가능합니다.");
    }

    // 배송 시작 (결제가 완료 되었으며, 취소된 상태가 아니어야 함)
    public void startShipping() {
        verifyShippableState();
        verifyNotCanceld();
        this.orderState = OrderState.SHIPPING_IN_PROGRESS;
    }

    private void verifyShippableState() {
        if(this.orderState != OrderState.PAYMENT_COMPLETED)
            throw new ShipStateException("배송완료 처리가 가능한 상태가 아닙니다.");
    }

    // 배송 취소 (배송 시작 전과 주문 취소 전에만 가능)
    public void cancelOrder() {
        verifyNotYetStartShipping();
        verifyNotCanceld();
        this.orderState = OrderState.ORDER_CANCELD;
    }

    // 아직 배송시작을 안했는지(주문취소가 가능한 상태인지) 검증하는 메소드
    private void verifyNotYetStartShipping() {
    }

    // 주문 취소가 되지 않았는지 검증하는 메소드
    private void verifyNotCanceld() {
        if(this.orderState == OrderState.ORDER_CANCELD)
            throw new ShipStateException("주문이 취소된 상태입니다.");
    }

    public Long shipmentCompleted() {
        this.orderState = OrderState.SHIPMENT_COMPLETED;
        return this.oid;
    }

    public Long paymentCompleted() {
        verifyShipmentCompleted();
        this.orderState = OrderState.PAYMENT_COMPLETED;
        return this.oid;
    }

    private void verifyShipmentCompleted() {
        if(this.orderState != OrderState.SHIPMENT_COMPLETED)
            throw new ShipStateException("결제를 하기전에 배송이 완료되어있어야 합니다.");
    }
}




