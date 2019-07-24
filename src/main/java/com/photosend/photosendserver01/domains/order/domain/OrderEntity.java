package com.photosend.photosendserver01.domains.order.domain;

import com.photosend.photosendserver01.common.event.MyApplicationEventPublisher;
import com.photosend.photosendserver01.domains.order.domain.event.OrderCanceledEvent;
import com.photosend.photosendserver01.domains.order.domain.event.StartShippingEvent;
import com.photosend.photosendserver01.domains.order.domain.exception.NoOrderLineException;
import com.photosend.photosendserver01.domains.order.domain.exception.ShipStateException;
import com.photosend.photosendserver01.domains.order.domain.exception.ShippingTimeOutOfRangeException;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long oid;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Embedded
    private ShippingInfo shippingInfo;

    @Embedded
    @ManyToOne
    @JoinColumn(name = "orderer_wechat_uid")
    private UserEntity orderer;

    @Embedded
    private OrderLine orderLine;

//  UserEntity와 OrderEntity가 1:1 관계일 경우 사용
//    @Embedded
//    private Money totalAmount;

    @Builder
    public OrderEntity(OrderLine orderLine, ShippingInfo shippingInfo, UserEntity orderer) {
        setOrderLines(orderLine);
        setShippingInfo(shippingInfo);
        this.orderer = orderer;
        this.orderState = OrderState.ORDER_COMPLETE;
    }

    private void setOrderLines(OrderLine orderLine) {
        verifyOrderLine(orderLine);
        this.orderLine = orderLine;
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if(shippingInfo.getReceiver() == null) throw new IllegalArgumentException("");
        if(shippingInfo.getReceiveTime() == null) throw new IllegalArgumentException("");
        verifyShippableTime(shippingInfo.getReceiveTime().toLocalDateTime());
        this.shippingInfo = shippingInfo;
    }

    @Transient
    @Value("${shipping.limit.time.min}")
    private int shippableMinTime;
    @Transient
    @Value("${shipping.limit.time.max}")
    private int shippableMaxTime;

    // 오전 10시 ~ 오후 8시 사이에만 배송가능한 시간으로 설정
    private void verifyShippableTime(LocalDateTime receiveTime) {
        int receiveHour = receiveTime.getHour();

        if(receiveHour < shippableMinTime && receiveHour > shippableMaxTime)
            throw new ShippingTimeOutOfRangeException("배송 가능한 시간이 아닙니다.");
    }

//  UserEntity와 OrderEntity가 1:1 관계일 경우 사용
//    private void setOrderLines(List<OrderLine> orderLines) {
//        verifyLeastOneOrMoreOrderLines(orderLines);
//        this.orderLines = orderLines;
//        calculateTotalAmount();
//    }

//
    private void verifyOrderLine(OrderLine orderLine) {
        if(orderLine == null)
            throw new NoOrderLineException("OrderLine이 존재해야 합니다.");
    }

//  UserEntity와 OrderEntity가 1:1 관계일 경우 사용
//    private void calculateTotalAmount() {
//        this.totalAmount = Money.builder()
//                        .value(this.orderLines.stream()
//                                .mapToInt(orderLine -> orderLine.getPrice().getValue())
//                                .sum())
//                        .build();
//    }

    // 배송 시작 (결제가 완료 되었으며, 취소된 상태가 아니어야 함)
    public void startShipping() {
        verifyShippableState();
        verifyNotCanceld();
        this.orderState = OrderState.SHIP_ONPROGRESS;
        MyApplicationEventPublisher.getPublisher().publishEvent(new StartShippingEvent());
    }

    private void verifyShippableState() {
        if(this.orderState != OrderState.PAYMENT_COMPLETE)
            throw new ShipStateException("배송시작 가능한 상태가 아닙니다.");
    }

    // 배송 취소 (배송 시작 전과 주문 취소 전에만 가능)
    public void cancelOrder() {
        verifyNotYetStartShipping();
        verifyNotCanceld();
        this.orderState = OrderState.ORDER_CANCELD;
        MyApplicationEventPublisher.getPublisher().publishEvent(new OrderCanceledEvent());
    }

    // 아직 배송시작을 안했는지 검증하는 메소드
    private void verifyNotYetStartShipping() {
        if(this.orderState != OrderState.ORDER_COMPLETE && this.orderState != OrderState.PAYMENT_COMPLETE)
            throw new ShipStateException("이미 배송중입니다.");
    }

    // 주문 취소가 되지 않았는지 검증하는 메소드
    private void verifyNotCanceld() {
        if(this.orderState == OrderState.ORDER_CANCELD)
            throw new ShipStateException("주문이 취소된 상태입니다.");
    }
}




