package com.photosend.photosendserver01;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class TestController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value= "/test")
    public String test(@RequestBody String message) throws UnsupportedEncodingException, JsonProcessingException {
        String decoded = URLDecoder.decode(message, "UTF-8");
        String removeLast = decoded.substring(0, decoded.length() - 1);

        TestModel testModel = objectMapper.readValue(removeLast, TestModel.class);
        System.out.println(testModel.toString());

        return "OK";
    }



}
