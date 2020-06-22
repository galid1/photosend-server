package com.photosend.photosendserver01.common.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosend.photosendserver01.domains.catalog.domain.product.ProductLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProductLocationFormatter implements Formatter<ProductLocation[]> {

    private final ObjectMapper objectMapper;

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
