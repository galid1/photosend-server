package com.photosend.photosendserver01.common.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEvent {
    private String userName;
    private byte[] buffer;

    @Builder
    public EmailEvent(String userName, MultipartFile multipartFile) {
        this.userName = userName;

        try {
            this.buffer = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
