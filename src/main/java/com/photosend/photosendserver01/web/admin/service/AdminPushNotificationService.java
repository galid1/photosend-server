package com.photosend.photosendserver01.web.admin.service;

import com.photosend.photosendserver01.common.util.apns.APNsUtil;
import com.photosend.photosendserver01.common.util.apns.NotificationPayload;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductRepository;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminPushNotificationService {
    private final APNsUtil apnsUtil;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private String ALERT_TITLE = "PHOTOSEND ALERT";
    private String ALERT_BODY = "Product is found.";

    public void sendPushNotification(List<Long> productIdList) {
        getUserAPNsDeviceTokenSetByProductIdList(productIdList)
                .forEach(apnsDeviceToken -> {
                    try {
                        apnsUtil.sendPushNotification(apnsDeviceToken, makeNotificationPayload());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
    }

    private Set<String> getUserAPNsDeviceTokenSetByProductIdList(List<Long> productIdList) {
        return productIdList.stream()
                .map(productId -> productRepository
                        .findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                        .getUploaderId())
                .map(userId -> userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))
                        .getApnsDeviceToken())
                .collect(Collectors.toSet());
    }

    private NotificationPayload makeNotificationPayload() {
        return NotificationPayload.builder()
                .alertTitle(ALERT_TITLE)
                .alertBody(ALERT_BODY)
                .build();
    }
}
