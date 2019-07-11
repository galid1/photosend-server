package com.photosend.photosendserver01.util.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMessageUtil {
    @Autowired
    private ObjectMapper objMapper;

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
