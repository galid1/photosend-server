package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.catalog.domain.product.*;
import com.photosend.photosendserver01.domains.user.exception.TokenWrongAudienceException;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterRequest;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.UserRegisterResponse;
import com.photosend.photosendserver01.domains.user.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/users")
    public UserRegisterResponse registerUser(@RequestBody UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = userRegisterService.registerUser(request);

        ProductEntity discountCoupon = ProductEntity.builder()
                .uploaderId(userRegisterResponse.getUserId())
                .productImageInformation(ProductImageInformation.builder()
                        .productImagePath("https://photosend-coupon-img.s3.ap-northeast-2.amazonaws.com/tempDisCount.png")
                        .productLocation(ProductLocation.builder()
                                .latitude(1.11f)
                                .longitude(1.22f)
                                .build())
                        .build())
                .build();
        discountCoupon.setProductInformation(ProductInformation.builder()
                .brand("COUPON")
                .memo("")
                .name("COUPON")
                .price(-9.0)
                .size(null)
                .build());
        discountCoupon.setProductState(ProductState.POPULATED);

        productRepository.save(discountCoupon);

        return userRegisterResponse;
    }

    //TODO 토큰 익셉션 핸들러를 이용해 토큰 재발급 로직 처리
    @ExceptionHandler
    public String reissueToken(TokenWrongAudienceException tokenException) {
        return "new Tokuest";
    }
}
