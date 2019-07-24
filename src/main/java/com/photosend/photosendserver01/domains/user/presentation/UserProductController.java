package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.domain.ProductLocation;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ProductFullInformation;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ProductImageUrl;
import com.photosend.photosendserver01.domains.user.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;

    @GetMapping("/{users-id}/product")
    public List<ProductFullInformation> getProductFullInformation(@PathVariable("users-id")String userId) {
        return userProductService.getProductInformation(userId);
    }

    @PostMapping(value = "/{users-id}/product/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ProductImageUrl> uploadProductImages(@PathVariable("users-id") String userId, @RequestParam("location") ProductLocation productLocation, @RequestParam("file") MultipartFile[] productImageFiles) {
        return userProductService.uploadProductImages(userId, productLocation, productImageFiles);
    }

    @DeleteMapping(value = "/{users-id}/product/{product-id}")
    public void deleteProduct(@PathVariable("users-id") String userId, @PathVariable("product-id") Integer productId) {
        userProductService.deleteProductImage(userId, productId);
    }

}
