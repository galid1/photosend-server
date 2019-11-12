package com.photosend.photosendserver01.common.event;

import com.photosend.photosendserver01.common.util.email.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailEventHandler {
    @Autowired
    private EmailUtil emailUtil;

    @Async
    @EventListener
    public void sendEmailBecauseImageUploaded(EmailEvent emailEvent) {
        emailEvent
                .getBufferList()
                .forEach(imageByteArray -> {
                    emailUtil.sendEmailWithImage(imageByteArray);
                });
    }
}
