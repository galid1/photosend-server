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

    @GetMapping("/{usersId}/product/{productId}")
    public ProductFullInformation getProductFullInformation(@PathVariable("usersId")Long userId, @PathVariable("productId") Long productId){
        return userProductService.getProductInformation(userId, productId);
    }

    @GetMapping("/{usersId}/product")
    public List<ProductFullInformation> getAllProductFullInformation(@PathVariable("usersId")Long userId) {
        System.out.println("프로덕트 리스트 요청받음");
        return userProductService.getAllProductInformation(userId);
    }

    @PostMapping(value = "/{usersId}/product/pictures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ProductImageUrl> uploadProductImages(@PathVariable("usersId") long userId,
                                                     @RequestParam("location") ProductLocation[] productLocations,
                                                     @RequestParam("file") MultipartFile[] productImageFiles) {
        return userProductService.uploadProductImages(userId, productLocations, productImageFiles);
    }

    @DeleteMapping(value = "/{usersId}/product/{productId}")
    public void deleteProduct(@PathVariable("usersId") Long userId, @PathVariable("productId") Long productId) {
        userProductService.deleteProductImage(userId, productId);
    }

}
