package com.photosend.photosendserver01.domains.order.domain;

import com.photosend.photosendserver01.common.event.MyApplicationEventPublisher;
import com.photosend.photosendserver01.domains.order.domain.event.OrderCanceledEvent;
import com.photosend.photosendserver01.domains.order.domain.event.StartShippingEvent;
import com.photosend.photosendserver01.domains.order.domain.exception.NoOrderLineException;
import com.photosend.photosendserver01.domains.order.domain.exception.OrderTimeException;
import com.photosend.photosendserver01.domains.order.domain.exception.ShipStateException;
import com.photosend.photosendserver01.domains.user.domain.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    @JoinColumn(name = "orderer_wechat_uid")
    private UserEntity orderer;

    @Embedded
    private OrderLine orderLine;

    @Builder
    public OrderEntity(OrderLine orderLine, UserEntity orderer, LocalDateTime departureTime) {
        verifyOrderTime(departureTime);
        setOrderLines(orderLine);
        this.orderer = orderer;
        this.orderState = OrderState.SHIP_IN_PROGRESS;
    }

    private void setOrderLines(OrderLine orderLine) {
        verifyOrderLine(orderLine);
        this.orderLine = orderLine;
    }

    private void verifyOrderLine(OrderLine orderLine) {
        if(orderLine == null)
            throw new NoOrderLineException("OrderLine이 존재해야 합니다.");
    }

    private void verifyOrderTime(LocalDateTime departureTime) {
        int departureYear = departureTime.getYear();
        int departureMonth = departureTime.getMonthValue();
        int departureDay = departureTime.getDayOfMonth();

        LocalDateTime orderDeadLine = LocalDateTime.of(departureYear, departureMonth, departureDay - 1, 19, 0, 0);

        if(LocalDateTime.now().isAfter(orderDeadLine))
            throw new OrderTimeException("원활한 배송을 위해 주문은 전날 오후 7시이전까지만 가능합니다.");
    }

    // 배송 시작 (결제가 완료 되었으며, 취소된 상태가 아니어야 함)
    public void startShipping() {
        verifyShippableState();
        verifyNotCanceld();
        this.orderState = OrderState.SHIP_IN_PROGRESS;
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

    // 아직 배송시작을 안했는지(주문취소가 가능한 상태인지) 검증하는 메소드
    private void verifyNotYetStartShipping() {
    }

    // 주문 취소가 되지 않았는지 검증하는 메소드
    private void verifyNotCanceld() {
        if(this.orderState == OrderState.ORDER_CANCELD)
            throw new ShipStateException("주문이 취소된 상태입니다.");
    }
}




