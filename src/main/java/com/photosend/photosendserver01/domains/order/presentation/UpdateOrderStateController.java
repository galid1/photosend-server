package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.service.UpdateOrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class UpdateOrderStateController {
    @Autowired
    private UpdateOrderStateService updateOrderStateService;

    @PutMapping("/{orderId}/payment")
    public void updateOrderState(@PathVariable("orderId") long orderId) {
        updateOrderStateService.updateToPayComplete(orderId);
    }
}
