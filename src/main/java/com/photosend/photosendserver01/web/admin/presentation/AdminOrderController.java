package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class AdminOrderController {
    @Autowired
    private AdminOrderService orderService;

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity cancel(@PathVariable("orderId") long orderId) {
        orderService.cancel(orderId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity ship(@PathVariable("orderId") long orderId) {
        orderService.ship(orderId);

        return ResponseEntity.ok().build();
    }

}
