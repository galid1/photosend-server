package com.photosend.photosendserver01.common.util.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Component;

// Controller의 ExceptionHandler에서 사용할 기능
@Component
@RequiredArgsConstructor
public class ExceptionMessageUtil {
    private final ObjectMapper objMapper;

    public String createExceptionMessage(String message) throws JsonProcessingException {
        ExceptionMessage exceptionMessage = new ExceptionMessage(message);
        return objMapper.writeValueAsString(exceptionMessage);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class ExceptionMessage {
        private String message;
    }
}
