package com.photosend.photosendserver01.common.event;

import com.photosend.photosendserver01.common.util.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventHandler {
    private final EmailUtil emailUtil;

    @Async
    @EventListener
    public void sendEmail(EmailEvent emailEvent) {
        emailUtil.sendEmail(emailEvent.getType(), emailEvent.getUserId(), emailEvent.getBufferList());
    }
}
