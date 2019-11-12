package com.photosend.photosendserver01.common.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEvent {
    private List<byte[]> bufferList;

    @Builder
    public EmailEvent(List<byte[]> fileByteArray) {
        this.bufferList = fileByteArray;
    }
}
