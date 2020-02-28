package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.common.model.Money;
import com.photosend.photosendserver01.domains.order.domain.Address;
import com.photosend.photosendserver01.domains.order.domain.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderInformation {
    private long oid;

    private String orderState;
    private String address;
    private LocalDateTime receiveTime;

    private long ordererId;

    private List<OrderLineInformation> orderLineList;

    private Money totalAmount;

    @Builder
    public OrderInformation(long oid, OrderState orderState, Address address, LocalDateTime receiveTime, long ordererId, List<OrderLineInformation> orderLineList, Money totalAmount) {
        this.oid = oid;
        this.orderState = orderState.toString();
        setAddress(address);
        this.receiveTime = receiveTime;
        this.ordererId = ordererId;
        this.orderLineList = orderLineList;
        this.totalAmount = totalAmount;
    }

    private void setAddress(Address address) {
        this.address = address.getAddressType() + " " + address.getAddress1() + " " + address.getAddress2();
    }
}
