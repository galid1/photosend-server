package com.photosend.photosendserver01.common.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.user.domain.ProductLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

@Component
public class ProductLocationFormatter implements Formatter<ProductLocation[]> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ProductLocation[] parse(String text, Locale locale) throws ParseException {
        ProductLocation[] productLocation = null;

        try {
            productLocation = objectMapper.readValue(text, ProductLocation[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productLocation;
    }

    @Override
    public String print(ProductLocation[] object, Locale locale) {
        return object.toString();
    }
}
