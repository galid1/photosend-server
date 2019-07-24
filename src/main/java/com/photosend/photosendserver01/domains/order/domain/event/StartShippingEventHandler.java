package com.photosend.photosendserver01.domains.order.domain.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartShippingEventHandler {
    @EventListener
    public void onStartShipping(StartShippingEvent event) {

    }
}
