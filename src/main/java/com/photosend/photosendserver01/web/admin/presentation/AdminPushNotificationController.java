package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminPushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminPushNotificationController {

    private final AdminPushNotificationService pushNotificationService;

    @PostMapping("/admin/notification")
    public void sendPushNotification(@RequestBody List<Long> productIdList) {
        pushNotificationService.sendPushNotification(productIdList);
    }

}
