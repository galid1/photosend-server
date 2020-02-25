package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.presentation.request_reponse.RenewApnsDeviceTokenRequest;
import com.photosend.photosendserver01.domains.user.service.UserApnsDeviceTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserApnsDeviceTokenController {
    @Autowired
    private UserApnsDeviceTokenService apnsDeviceTokenService;

    @PutMapping("/{userId}/apns")
    public ResponseEntity renewApnsDeviceToken(@PathVariable("userId") long userId, @RequestBody RenewApnsDeviceTokenRequest request) {
        apnsDeviceTokenService.renewApnsDeviceToken(userId, request);

        return ResponseEntity.ok().build();
    }
}
