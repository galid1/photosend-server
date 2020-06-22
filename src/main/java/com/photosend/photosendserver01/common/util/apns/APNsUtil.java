package com.photosend.photosendserver01.common.util.apns;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class APNsUtil {
    private final Environment env;

    private String KEY_ID = "8NYW4PKBLS";
    private String TEAM_ID = "4WJY57P5H9";

    private String TEST_PROFILE = "test";
    private String DEPLOY_PROFILE = "deploy";
    @Value("${photosend.bundle}")
    private String TOPIC;
    @Value("${photosend.credential.apns.file-path}")
    private String APNS_KEY_FILE_PATH;

    public void sendPushNotification(String deviceToken, NotificationPayload notificationPayload) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InterruptedException {
        ApnsClient apnsClient = getApnsClient();

        String token = TokenUtil.sanitizeTokenString(deviceToken);
        String payload = makePayload(notificationPayload);
        SimpleApnsPushNotification simpleApnsPushNotification = new SimpleApnsPushNotification(token, TOPIC, payload);

        handleSendNotificationFuture(apnsClient.sendNotification(simpleApnsPushNotification));
    }

    private ApnsClient getApnsClient() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return new ApnsClientBuilder()
                .setApnsServer(getServerDomainByActiveProfile())
                .setSigningKey(ApnsSigningKey.loadFromPkcs8File(getTokenFile(), TEAM_ID, KEY_ID))
                .build();
    }

    private File getTokenFile() throws IOException {
        String apnsKeyFilePath = System.getProperty("user.home") + "/" + APNS_KEY_FILE_PATH;
        return new File(apnsKeyFilePath);
    }

    private String getServerDomainByActiveProfile() {
        String host = "";
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if(activeProfiles.contains(TEST_PROFILE)) {
            host = ApnsClientBuilder.DEVELOPMENT_APNS_HOST;
        } else if (activeProfiles.contains(DEPLOY_PROFILE)) {
            host = ApnsClientBuilder.PRODUCTION_APNS_HOST;
        }

        return host;
    }

    private String makePayload(NotificationPayload notificationPayload) {
        ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertTitle(notificationPayload.getAlertTitle());
        payloadBuilder.setAlertBody(notificationPayload.getAlertBody());
        return payloadBuilder.buildWithDefaultMaximumLength();
    }

    private void handleSendNotificationFuture(PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> simpleNotificationFuture)
            throws InterruptedException {
        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    simpleNotificationFuture.get();

            if (pushNotificationResponse.isAccepted()) {
                System.out.println("Push notification accepted by APNs gateway.");
            } else {
                System.out.println("Notification rejected by the APNs gateway: " +
                        pushNotificationResponse.getRejectionReason());

                if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
                    System.out.println("\t…and the token is invalid as of " +
                            pushNotificationResponse.getTokenInvalidationTimestamp());
                }
            }
        } catch (final ExecutionException e) {
            System.err.println("Failed to send push notification.");
            e.printStackTrace();
        }
    }

}
