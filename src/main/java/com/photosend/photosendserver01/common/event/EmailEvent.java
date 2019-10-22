package com.photosend.photosendserver01.common.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEvent {
    private String userName;
    private MultipartFile[] multipartFiles;

    @Builder
    public EmailEvent(String userName, MultipartFile[] multipartFiles) {
        this.userName = userName;
        this.multipartFiles = multipartFiles;
    }
}
