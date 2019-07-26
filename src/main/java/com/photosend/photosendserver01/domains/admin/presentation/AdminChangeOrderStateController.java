package com.photosend.photosendserver01.domains.admin.presentation;

import com.photosend.photosendserver01.domains.admin.presentation.request_response.AdminChangeOrderStateRequest;
import com.photosend.photosendserver01.domains.admin.service.AdminChangeOrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminChangeOrderStateController {

    @Autowired
    private AdminChangeOrderStateService adminChangeOrderStateService;

    @PutMapping("/orders/{ordersId}")
    public void changeOrderStateTo(@PathVariable("ordersId") Long ordersId, @RequestBody AdminChangeOrderStateRequest adminChangeOrderStateRequest) {
        adminChangeOrderStateService.changeOrderStateTo(ordersId, adminChangeOrderStateRequest.getNewOrderState());
    }

}
