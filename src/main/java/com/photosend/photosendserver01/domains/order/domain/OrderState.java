package com.photosend.photosendserver01.domains.order.domain;

public enum OrderState {
     // 결제 완료                             // 배송중                                // 배송완료                         //주문 취소
    PAYMENT_COMPLETED("paymeny complete"),  SHIPPING_IN_PROGRESS("ship in progress"), SHIPMENT_COMPLETED("ship complete"), ORDER_CANCELD("order cancled");

    private String orderState;

    OrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState(){
        return this.orderState;
    }
}
