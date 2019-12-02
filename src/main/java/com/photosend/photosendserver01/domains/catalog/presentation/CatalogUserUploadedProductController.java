package com.photosend.photosendserver01.domains.catalog.presentation;

import com.photosend.photosendserver01.domains.catalog.presentation.request_response.UserUploadedProduct;
import com.photosend.photosendserver01.domains.catalog.service.CatalogUserUploadedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogUserUploadedProductController {
    @Autowired
    private CatalogUserUploadedProductService userUploadedProductService;

    @GetMapping("/catalog/products/users/{userId}")
    public List<UserUploadedProduct> getUserUploadedProductList(@PathVariable("userId") long uploaderId) {
        return userUploadedProductService.getUsersUploadProductList(uploaderId);
    }
}
