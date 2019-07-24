package com.photosend.photosendserver01.domains.order.domain;

public enum OrderState {
    //결제 대기와 동일 , // 결제 완료      , // 배송준비중,     // 배송중          // 배송완료     //주문 취소
    ORDER_COMPLETE, PAYMENT_COMPLETE, SHIP_PREPARING, SHIP_ONPROGRESS, SHIP_COMPLETE, ORDER_CANCELD
}
