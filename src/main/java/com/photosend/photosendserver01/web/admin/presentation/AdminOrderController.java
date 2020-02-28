package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminOrderController {
    @Autowired
    private AdminOrderService orderService;

    @PutMapping
    public ResponseEntity cancel(long orderId) {
        orderService.cancel(orderId);

        return ResponseEntity.ok().build();
    }
}
