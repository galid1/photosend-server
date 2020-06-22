package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UserUploadedProduct;
import com.photosend.photosendserver01.domains.catalog.service.CatalogUserUploadedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogUserUploadedProductController {
    private final CatalogUserUploadedProductService userUploadedProductService;

    @GetMapping("/catalog/products/users/{userId}")
    public List<UserUploadedProduct> getUserUploadedProductList(@PathVariable("userId") long uploaderId) {
        return userUploadedProductService.getUsersUploadProductList(uploaderId);
    }
}
