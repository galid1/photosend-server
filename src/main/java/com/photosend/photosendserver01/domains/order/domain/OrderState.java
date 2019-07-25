package com.photosend.photosendserver01.domains.order.domain;

public enum OrderState {
     // 결제 완료      ,  // 배송중          // 배송완료      //주문 취소
    PAYMENT_COMPLETE("paymeny complete"),  SHIP_IN_PROGRESS("ship in progress"), SHIP_COMPLETE("ship complete"), ORDER_CANCELD("order cancled");

    private String orderState;

    OrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState(){
        return this.orderState;
    }
}
