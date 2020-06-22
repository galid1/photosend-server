package com.photosend.photosendserver01.domains.order.presentation;

import com.photosend.photosendserver01.domains.order.service.UpdateOrderStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class UpdateOrderStateController {
    private final UpdateOrderStateService updateOrderStateService;

    @PutMapping("/{orderId}/payment")
    public void updateOrderState(@PathVariable("orderId") long orderId) {
        updateOrderStateService.updateToPayComplete(orderId);
    }
}
