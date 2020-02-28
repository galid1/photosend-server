package com.photosend.photosendserver01.domains.order.domain;

public enum OrderState {
    // 주문 완료                             // 결제 완료                             // 배송중                                // 배송완료                         //주문 취소
    ORDER_COMPLEMENT("complete"), PAYMENT_COMPLETED("paymeny complete"),  SHIPPING_IN_PROGRESS("ship in progress"), SHIPMENT_COMPLETED("ship complete"), ORDER_CANCELD("order canceld");

    private String orderState;

    OrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState(){
        return this.orderState;
    }
}
