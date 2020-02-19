package com.photosend.photosendserver01.common.event;

import com.photosend.photosendserver01.common.util.email.EmailType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEvent {
    private EmailType type;
    private long userId;
    private List<byte[]> bufferList;

    @Builder
    public EmailEvent(EmailType type, long userId, List<byte[]> fileByteArrList) {
        this.type = type;
        this.userId = userId;
        this.bufferList = fileByteArrList;
    }
}
