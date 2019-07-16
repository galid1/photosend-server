package com.photosend.photosendserver01.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.user.domain.ClothesLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

@Component
public class ClothesLocationFormatter implements Formatter<ClothesLocation> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ClothesLocation parse(String text, Locale locale) throws ParseException {
        ClothesLocation clothesLocation = null;

        try {
            clothesLocation = objectMapper.readValue(text, ClothesLocation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clothesLocation;
    }

    @Override
    public String print(ClothesLocation object, Locale locale) {
        return object.toString();
    }
}
